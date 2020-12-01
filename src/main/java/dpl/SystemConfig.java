package dpl;

import dpl.LeagueManagement.GameplayConfiguration.GameplayConfigurationAbstractFactory;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigurationAbstractFactory;
import dpl.LeagueManagement.InitializeModels.IInitializeModelsAbstractFactory;
import dpl.LeagueManagement.InitializeModels.InitializeModelsAbstractFactory;
import dpl.LeagueManagement.Schedule.IScheduleAbstractFactory;
import dpl.LeagueManagement.Schedule.ScheduleAbstractFactory;
import dpl.LeagueManagement.Standings.IStandingsAbstractFactory;
import dpl.LeagueManagement.Standings.StandingsAbstractFactory;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.TeamManagementAbstractFactory;
import dpl.LeagueManagement.Trading.ITradingAbstractFactory;
import dpl.LeagueManagement.Trading.TradingAbstractFactory;
import dpl.NewsSystem.INewsSystemAbstractFactory;
import dpl.NewsSystem.NewsSystemAbstractFactory;
import dpl.SimulationManagement.InternalStateMachine.IInternalStateMachineAbstractFactory;
import dpl.SimulationManagement.InternalStateMachine.InternalStateMachineAbstractFactory;
import dpl.SimulationManagement.SimulationStateMachine.ISimulationStateMachineAbstractFactory;
import dpl.SimulationManagement.SimulationStateMachine.SimulationStateMachineAbstractFactory;
import dpl.LeagueManagement.TrophySystem.ITrophySystemAbstractFactory;
import dpl.LeagueManagement.TrophySystem.TrophySystemAbstractFactory;
import dpl.UserInputOutput.Parser.IParserAbstractFactory;
import dpl.UserInputOutput.Parser.ParserAbstractFactory;
import dpl.UserInputOutput.UserInput.IUserInputAbstractFactory;
import dpl.UserInputOutput.UserInput.UserInputAbstractFactory;
import dpl.UserInputOutput.UserOutput.IUserOutputAbstractFactory;
import dpl.UserInputOutput.UserOutput.UserOutputAbstractFactory;
import dpl.PersistSerializeDeserialize.ISerializeDeserializeAbstractFactory;
import dpl.PersistSerializeDeserialize.SerializeDeserializeAbstractFactory;

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
	private ITrophySystemAbstractFactory trophySystemAbstractFactory;
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
		trophySystemAbstractFactory = new TrophySystemAbstractFactory();
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

	public INewsSystemAbstractFactory getNewsSystemAbstractFactory() {
		return newsSystemAbstractFactory;
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


	public ITrophySystemAbstractFactory getTrophySystemAbstractFactory() {
		return trophySystemAbstractFactory;
	}


	public ISerializeDeserializeAbstractFactory getSerializeDeserializeAbstractFactory() {
		return serializeDeserializeAbstractFactory;
	}

}
