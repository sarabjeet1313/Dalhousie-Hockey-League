package dpl;

import dpl.Database.TradeDataDB;
import dpl.ErrorHandling.RetirementManagementException;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine.StateContext;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class App {

    private static SystemConfig config = SystemConfig.getSingleInstance();

    public static void main(String[] args) throws RetirementManagementException {

        IUserInput input = config.getUserInputAbstractFactory().CmdUserInput();
        IUserOutput output = config.getUserOutputAbstractFactory().CmdUserOutput();
        ILeaguePersistance leagueDb = config.getSerializeDeserializeAbstractFactory().LeagueSerializationDeserialization();
        ICoachPersistance coachDb = config.getSerializeDeserializeAbstractFactory().CoachSerializationDeserialization();
        IGameplayConfigPersistance configDb = config.getSerializeDeserializeAbstractFactory().GameplayConfigSerializationDeserialization();
        IManagerPersistance managerDb = config.getSerializeDeserializeAbstractFactory().ManagerSerializationDeserialization();
        ITradePersistence tradeDb = new TradeDataDB();
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
