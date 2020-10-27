package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalSimulationState implements ISimulationState{

    private IUserInput input;
    private IUserOutput output;
    private int season;
    private int totalSeasons;
    private String teamName;
    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private InternalStateContext context;
    private ScheduleUtlity utility;
    private String currentDate;
    private ISchedule schedule;
    private TrainingState trainingState;
    private GeneratePlayoffScheduleState playoffScheduleState;
    private AdvanceTimeState advanceTimeState;
    private SimulateGameState simulateGame;
    private AgingState agingState;
    private TradingState tradingState;
    private InjuryCheckState injuryCheck;
    private AdvanceToNextSeasonState advanceToNextSeason;
    private PersistState persistState;

    public InternalSimulationState(IUserInput input, IUserOutput output, int seasons, String teamName, League leagueToSimulate, InternalStateContext context){
        this.input = input;
        this.output = output;
        this.totalSeasons = seasons;
        this.teamName = teamName;
        this.leagueToSimulate = leagueToSimulate;
        this.stateName = "Simulate";
        this.season = 0;
        this.context = context;

        //========
        this.currentDate = "";
        //========
    }

    public void nextState(InternalStateContext context){
        this.nextStateName = "InternalEndState";
        context.setState(new InternalEndState(input, output));
    }

    public void doProcessing(){

        for(int i=1; i <= totalSeasons; i++){
            this.season = i-1;
            output.setOutput("Season " + i + " is simulating.");
            output.sendOutput();

//            context.setState(new GenerateRegularSeasonScheduleState(leagueToSimulate, this.input, this.output, this.season, this.context));
//            context.doProcessing();

            //===========

            GenerateRegularSeasonScheduleState initialState = new GenerateRegularSeasonScheduleState(leagueToSimulate, this.input, this.output, this.season, this.context);
            initialState.doProcessing();

            this.schedule = initialState.getSchedule();
            String startDate = initialState.getRegularSeasonStartDate();
            String endDate = initialState.getRegularSeasonEndDate();

            this.currentDate = startDate;

            boolean seasonPending = true;
            do {
                utility = new ScheduleUtlity(season);

                advanceTimeState = new AdvanceTimeState(schedule, leagueToSimulate, currentDate, endDate, utility, output, context);
                advanceTimeState.doProcessing();
                this.currentDate = advanceTimeState.getCurrentDate();

                output.setOutput(this.currentDate);
                output.sendOutput();
                boolean isLastDayForSeason = advanceTimeState.isALastDay();

                if (isLastDayForSeason) {
                    playoffScheduleState = new GeneratePlayoffScheduleState(leagueToSimulate, utility, currentDate, output, context);
                    playoffScheduleState.doProcessing();
                    schedule = playoffScheduleState.getSchedule();
                    trainingState = new TrainingState(leagueToSimulate, schedule, utility, currentDate, output, context);
                } else {
                    trainingState = new TrainingState(leagueToSimulate, schedule, utility, currentDate, output, context);
                }

                trainingState.doProcessing();
                boolean anyUnplayedGames = trainingState.anyUnplayedGames();

                while (anyUnplayedGames) {
                    simulateGame = new SimulateGameState(leagueToSimulate, schedule, context, utility, currentDate, output);
                    simulateGame.doProcessing();

                    injuryCheck = new InjuryCheckState(leagueToSimulate, schedule, context, utility, currentDate, output);
                    injuryCheck.doProcessing();
                    anyUnplayedGames = trainingState.anyUnplayedGames();
                }

                if (utility.isTradeDeadlinePending(this.currentDate)) {
                    tradingState = new TradingState(leagueToSimulate, schedule, context, utility, currentDate, output);
                    tradingState.doProcessing();
                }

                agingState = new AgingState(leagueToSimulate, schedule, context, utility, currentDate, output);
                agingState.doProcessing();

                if (utility.getSeasonOverStatus() | utility.isLastDayOfSeason(currentDate))  {
                    advanceToNextSeason = new AdvanceToNextSeasonState(leagueToSimulate, schedule, context, utility, currentDate, output);
                    advanceToNextSeason.doProcessing();
                    persistState = new PersistState(leagueToSimulate, schedule, context, utility, currentDate, output);
                    persistState.doProcessing();

                    seasonPending = false;
                }
                persistState = new PersistState(leagueToSimulate, schedule, context, utility, currentDate, output);
                persistState.doProcessing();

            }while(seasonPending);

            output.setOutput("Season " + i + " winner is : " + utility.getSeasonWinner());
            output.sendOutput();
        }
    }

    public String getStateName(){
        return this.stateName;
    }

    public String getNextStateName(){
        return this.nextStateName;
    }

}
