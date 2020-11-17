package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.SystemConfig;
import dpl.DplConstants.StateConstants;
import dpl.ErrorHandling.RetirementManagementException;
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

    public ISimulationState nextState(InternalStateContext context) {
        this.nextStateName = "InternalEndState";
        ISimulationState endState = new InternalEndState(output);
        context.setState(endState);
        return endState;
    }

    public void doProcessing() throws RetirementManagementException {

        for (int index = 1; index <= totalSeasons; index++) {
            this.season = index - 1;
            output.setOutput("Season " + index + " is simulating.");
            output.sendOutput();

            standingsDb.setSeason(index);
            standings = new StandingInfo(leagueToSimulate, season, standingsDb, output);
            utility = new SeasonCalendar(season, output);
            GenerateRegularSeasonScheduleState initialState = new GenerateRegularSeasonScheduleState(leagueToSimulate, this.output, this.season, this.context, standingsDb, standings, utility);
            context.setState(initialState);

            while(context.shouldContinue()) {
                context.doProcessing();
                ISimulationState state = context.nextState();
                context.setState(state);
            }

            output.setOutput("Season " + index + " winner is : " + utility.getSeasonWinner());
            output.sendOutput();
        }
    }

    public boolean shouldContinue() {
        return true;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }

}
