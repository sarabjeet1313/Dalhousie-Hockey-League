package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigurationAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.IScheduleAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradingAbstractFactory;
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
    private ISimulationState initialState;
    private SeasonCalendar utility;
    private String currentDate;
    private Training training;
    private IRetirementManagement retirement;
    private IInjuryManagement injury;
    private Trade trade;
    private ISchedule schedule;
    private StandingInfo standingsInfo;
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
    private IInternalStateMachineAbstractFactory internalStateMachineFactory;
    private IScheduleAbstractFactory scheduleAbstractFactory;
    private IStandingsAbstractFactory standingsAbstractFactory;
    private IGameplayConfigurationAbstractFactory gameplayConfigurationAbstractFactory;
    private ITradingAbstractFactory tradingAbstractFactory;

    public InternalSimulationState(IUserInput input, IUserOutput output, int seasons, String teamName,
                                   League leagueToSimulate, InternalStateContext context, ITradePersistence tradeDb,
                                   IStandingsPersistance standingsDb) {
        this.training = new Training(output);
        this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
        this.standingsAbstractFactory = SystemConfig.getSingleInstance().getStandingsAbstractFactory();
        this.scheduleAbstractFactory = SystemConfig.getSingleInstance().getScheduleAbstractFactory();
        this.gameplayConfigurationAbstractFactory = SystemConfig.getSingleInstance().getGameplayConfigurationAbstractFactory();
        this.tradingAbstractFactory = SystemConfig.getSingleInstance().getTradingAbstractFactory();
        this.injury = teamManagement.InjuryManagement();
        this.retirement = teamManagement.RetirementManagement();
        this.tradeDb = tradeDb;
        this.trade = tradingAbstractFactory.Trade(tradeDb);
        this.tradeReset = tradingAbstractFactory.TradeReset(tradeDb);
        this.input = input;
        this.output = output;
        this.totalSeasons = seasons;
        this.teamName = teamName;
        this.leagueToSimulate = leagueToSimulate;
        this.stateName = StateConstants.INTERNAL_SIMULATION_STATE;
        this.season = 0;
        this.context = context;
        this.currentDate = "";
        this.standingsDb = standingsDb;
    }

    public ISimulationState nextState(InternalStateContext context) {
        this.nextStateName = "InternalEndState";
        ISimulationState endState = this.internalStateMachineFactory.InternalEndState(output);
        context.setState(endState);
        return endState;
    }

    public void doProcessing() throws RetirementManagementException {

        for (int index = 1; index <= totalSeasons; index++) {
            this.season = index - 1;
            output.setOutput("Season " + index + " is simulating.");
            output.sendOutput();

            standingsDb.setSeason(index);
            standingsInfo = this.standingsAbstractFactory.StandingInfo(leagueToSimulate, season, standingsDb, output);
            utility = this.scheduleAbstractFactory.SeasonCalendar(season, output);
            initialState = this.internalStateMachineFactory.GenerateRegularSeasonScheduleState(leagueToSimulate, this.output, this.season, this.context, standingsDb, standingsInfo, utility);
            context.setState(initialState);

            while(context.shouldContinue()) {
                context.doProcessing();
                ISimulationState state = context.nextState();
                context.setState(state);
            }

            output.setOutput("Season " + index + " winner is : " + utility.getSeasonWinner());
            output.sendOutput();

            standingsInfo.showStats();
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
