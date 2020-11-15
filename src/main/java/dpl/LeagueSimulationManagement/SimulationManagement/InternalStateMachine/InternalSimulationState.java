package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.SystemConfig;
import dpl.Dpl.ErrorHandling.RetirementManagementException;
import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.*;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.Trade;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.TradeReset;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class InternalSimulationState implements ISimulationState {

    private IUserInput input;
    private IUserOutput output;
    private int season;
    private int totalSeasons;
    private String teamName;
    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private String currentDate;
    private Training training;
    private IRetirementManagement retirement;
    private IInjuryManagement injury;
    private Trade trade;
    private ISchedule schedule;
    private StandingInfo standings;
    private IStandingsPersistance standingsDb;
    private TrainingState trainingState;
    private GeneratePlayoffScheduleState playoffScheduleState;
    private AdvanceTimeState advanceTimeState;
    private SimulateGameState simulateGame;
    private AgingState agingState;
    private TradingState tradingState;
    private InjuryCheckState injuryCheck;
    private AdvanceToNextSeasonState advanceToNextSeason;
    private PersistState persistState;
    private ITradePersistence tradeDb;
    private TradeReset tradeReset;
    private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();

    public InternalSimulationState(IUserInput input, IUserOutput output, int seasons, String teamName,
                                   League leagueToSimulate, InternalStateContext context, ITradePersistence tradeDb,
                                   IStandingsPersistance standingsDb) {
        this.training = new Training(output);
        this.injury = teamManagement.InjuryManagement();
        this.retirement = teamManagement.RetirementManagement();
        this.trade = new Trade(tradeDb);
        this.tradeReset = new TradeReset(tradeDb);
        this.input = input;
        this.output = output;
        this.totalSeasons = seasons;
        this.teamName = teamName;
        this.leagueToSimulate = leagueToSimulate;
        this.stateName = StateConstants.INTERNAL_SIMULATION_STATE;
        this.season = 0;
        this.context = context;
        this.currentDate = "";
        this.tradeDb = tradeDb;
        this.standingsDb = standingsDb;
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = "InternalEndState";
        context.setState(new InternalEndState(output));
    }

    public void doProcessing() throws RetirementManagementException {

        for (int index = 1; index <= totalSeasons; index++) {
            this.season = index - 1;
            output.setOutput("Season " + index + " is simulating.");
            output.sendOutput();

            standingsDb.setSeason(index);
            standings = new StandingInfo(leagueToSimulate, season, standingsDb);
            GenerateRegularSeasonScheduleState initialState = new GenerateRegularSeasonScheduleState(leagueToSimulate, this.output, this.season, this.context, standingsDb);
            initialState.doProcessing();

            this.schedule = initialState.getSchedule();
            String startDate = initialState.getRegularSeasonStartDate();
            String endDate = initialState.getRegularSeasonEndDate();

            this.currentDate = startDate;

            boolean seasonPending = true;
            do {
                utility = new SeasonCalendar(season, output);

                advanceTimeState = new AdvanceTimeState(currentDate, endDate, output, context);
                advanceTimeState.doProcessing();
                this.currentDate = advanceTimeState.getCurrentDate();

                boolean isLastDayForSeason = advanceTimeState.isALastDay();

                if (isLastDayForSeason) {
                    playoffScheduleState = new GeneratePlayoffScheduleState(leagueToSimulate, utility, standingsDb, output, context, season);
                    playoffScheduleState.doProcessing();
                    schedule = playoffScheduleState.getSchedule();
                    trainingState = new TrainingState(leagueToSimulate, training, schedule, utility, currentDate,
                            output, context);
                } else {
                    trainingState = new TrainingState(leagueToSimulate, training, schedule, utility, currentDate,
                            output, context);
                }

                trainingState.doProcessing();
                boolean anyUnplayedGames = schedule.anyUnplayedGame(currentDate);

                while (anyUnplayedGames) {
                    simulateGame = new SimulateGameState(leagueToSimulate, schedule, standings, context, utility,
                            currentDate, output);
                    simulateGame.doProcessing();

                    injuryCheck = new InjuryCheckState(leagueToSimulate, injury, schedule, context, utility,
                            currentDate, output);
                    injuryCheck.doProcessing();
                    anyUnplayedGames = schedule.anyUnplayedGame(currentDate);
                }

                if (utility.isTradeDeadlinePending(this.currentDate)) {
                    tradingState = new TradingState(leagueToSimulate, trade, context, output);
                    tradingState.doProcessing();
                }

                agingState = new AgingState(leagueToSimulate, injury, context, utility, currentDate, output);
                agingState.doProcessing();

                if (utility.getSeasonOverStatus() | utility.isLastDayOfSeason(currentDate)) {
                    advanceToNextSeason = new AdvanceToNextSeasonState(leagueToSimulate, injury, retirement, context,
                            utility, currentDate, output);
                    advanceToNextSeason.doProcessing();
                    seasonPending = false;
                }
                persistState = new PersistState(leagueToSimulate, schedule, standings, tradeReset, context, utility, currentDate,
                        output);
                persistState.doProcessing();

            } while (seasonPending);

            output.setOutput("Season " + index + " winner is : " + utility.getSeasonWinner());
            output.sendOutput();
        }
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }

}
