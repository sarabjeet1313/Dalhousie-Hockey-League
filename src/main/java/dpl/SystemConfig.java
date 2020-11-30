package dpl;

import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.GameplayConfigurationAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigurationAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.InitializeModels.IInitializeModelsAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.InitializeModels.InitializeModelsAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.IScheduleAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ScheduleAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingsAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.TeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradingAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.TradingAbstractFactory;
import dpl.LeagueSimulationManagement.NewsSystem.INewsSystemAbstractFactory;
import dpl.LeagueSimulationManagement.NewsSystem.NewsSystemAbstractFactory;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.IInternalStateMachineAbstractFactory;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStateMachineAbstractFactory;
import dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine.ISimulationStateMachineAbstractFactory;
import dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine.SimulationStateMachineAbstractFactory;
//import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.Parser.IParserAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.Parser.ParserAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInputAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.UserInputAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutputAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.UserOutputAbstractFactory;
import dpl.SerializeDeserialize.ISerializeDeserializeAbstractFactory;
import dpl.SerializeDeserialize.SerializeDeserializeAbstractFactory;

public class SystemConfig {

    private static SystemConfig singleInstance = null;
    private ITeamManagementAbstractFactory teamManagementAbstractFactory;
    private IGameplayConfigurationAbstractFactory gameplayConfigurationAbstractFactory;
    private IInitializeModelsAbstractFactory initializeModelsAbstractFactory;
    private IScheduleAbstractFactory scheduleAbstractFactory;
    private IStandingsAbstractFactory standingsAbstractFactory;
    private ITradingAbstractFactory tradingAbstractFactory;
    private IInternalStateMachineAbstractFactory internalStateMachineAbstractFactory;
    private ISimulationStateMachineAbstractFactory simulationStateMachineAbstractFactory;
    private IParserAbstractFactory parserAbstractFactory;
    private IUserInputAbstractFactory userInputAbstractFactory;
    private IUserOutputAbstractFactory userOutputAbstractFactory;
    private ISerializeDeserializeAbstractFactory serializeDeserializeAbstractFactory;
    private INewsSystemAbstractFactory newsSystemAbstractFactory;

    private SystemConfig() {
        teamManagementAbstractFactory = new TeamManagementAbstractFactory();
        gameplayConfigurationAbstractFactory = new GameplayConfigurationAbstractFactory();
        initializeModelsAbstractFactory = new InitializeModelsAbstractFactory();
        scheduleAbstractFactory = new ScheduleAbstractFactory();
        standingsAbstractFactory = new StandingsAbstractFactory();
        tradingAbstractFactory = new TradingAbstractFactory();
        internalStateMachineAbstractFactory = new InternalStateMachineAbstractFactory();
        simulationStateMachineAbstractFactory = new SimulationStateMachineAbstractFactory();
        parserAbstractFactory = new ParserAbstractFactory();
        userInputAbstractFactory = new UserInputAbstractFactory();
        userOutputAbstractFactory = new UserOutputAbstractFactory();
        serializeDeserializeAbstractFactory = new SerializeDeserializeAbstractFactory();
        newsSystemAbstractFactory = new NewsSystemAbstractFactory();
    }

    public static SystemConfig getSingleInstance() {
        if (null == singleInstance) {
            singleInstance = new SystemConfig();
        }
        return singleInstance;
    }

    public ITeamManagementAbstractFactory getTeamManagementAbstractFactory() {
        return teamManagementAbstractFactory;
    }

    public IGameplayConfigurationAbstractFactory getGameplayConfigurationAbstractFactory() {
        return gameplayConfigurationAbstractFactory;
    }

    public IInitializeModelsAbstractFactory getInitializeModelsAbstractFactory() {
        return initializeModelsAbstractFactory;
    }

    public IScheduleAbstractFactory getScheduleAbstractFactory() {
        return scheduleAbstractFactory;
    }

    public IStandingsAbstractFactory getStandingsAbstractFactory() {
        return standingsAbstractFactory;
    }

    public ITradingAbstractFactory getTradingAbstractFactory() {
        return tradingAbstractFactory;
    }

    public IInternalStateMachineAbstractFactory getInternalStateMachineAbstractFactory() {
        return internalStateMachineAbstractFactory;
    }

    public ISimulationStateMachineAbstractFactory getSimulationStateMachineAbstractFactory() {
        return simulationStateMachineAbstractFactory;
    }

    public IParserAbstractFactory getParserAbstractFactory() {
        return parserAbstractFactory;
    }

    public IUserInputAbstractFactory getUserInputAbstractFactory() {
        return userInputAbstractFactory;
    }

    public IUserOutputAbstractFactory getUserOutputAbstractFactory() {
        return userOutputAbstractFactory;
    }

    public ISerializeDeserializeAbstractFactory getSerializeDeserializeAbstractFactory() {
        return serializeDeserializeAbstractFactory;
    }

    public INewsSystemAbstractFactory getNewsSystemAbstractFactory() {
        return newsSystemAbstractFactory;
    }

}
