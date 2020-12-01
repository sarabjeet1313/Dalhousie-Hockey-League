package dpl;

import dpl.LeagueManagement.Trading.TradeUtility;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.SimulationManagement.SimulationStateMachine.StateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class App {

	private static SystemConfig config = SystemConfig.getSingleInstance();

	public static void main(String[] args) {

		IUserInput input = config.getUserInputAbstractFactory().CmdUserInput();
		IUserOutput output = config.getUserOutputAbstractFactory().CmdUserOutput();
		ILeaguePersistance leagueDb = config.getSerializeDeserializeAbstractFactory().LeagueSerializationDeserialization();
		ICoachPersistance coachDb = config.getSerializeDeserializeAbstractFactory().CoachSerializationDeserialization();
		IGameplayConfigPersistance configDb = config.getSerializeDeserializeAbstractFactory().GameplayConfigSerializationDeserialization();
		IManagerPersistance managerDb = config.getSerializeDeserializeAbstractFactory().ManagerSerializationDeserialization();
		ITradePersistence tradeDb = new TradeUtility();
		IStandingsPersistance standingDb = config.getSerializeDeserializeAbstractFactory().StandingSerializationDeserialization();
		StateContext context = new StateContext(input, output);
		context.setState(config.getSimulationStateMachineAbstractFactory().InitialState(input, output));
		context.doProcessing();

		if (args == null || args.length == 0) {
			context.setState(config.getSimulationStateMachineAbstractFactory().LoadTeamState(input, output, leagueDb, configDb, tradeDb, standingDb));
			context.doProcessing();
			context.nextState();
			context.doProcessing();
		} else {
			String filePath = args[0];
			context.setState(config.getSimulationStateMachineAbstractFactory().ParsingState(input, output, filePath, leagueDb, coachDb, configDb, managerDb, tradeDb, standingDb));
			context.doProcessing();
			context.nextState();
			context.doProcessing();
			context.nextState();
			context.doProcessing();
		}
	}
}
