package dpl;

import dpl.Database.CoachDataDB;
import dpl.Database.GameConfigDB;
import dpl.Database.LeagueDataDB;
import dpl.Database.ManagerDataDB;
import dpl.Database.StandingsDataDb;
import dpl.Database.TradeDataDB;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine.*;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Trading.ITradePersistence;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

public class App {

    public static void main(String[] args) {
        IUserInput input = new CmdUserInput();
        IUserOutput output = new CmdUserOutput();
        ILeaguePersistance leagueDb = new LeagueDataDB();
        ICoachPersistance coachDb = new CoachDataDB();
        IGameplayConfigPersistance configDb = new GameConfigDB();
        IManagerPersistance managerDb = new ManagerDataDB();
        ITradePersistence tradeDb = new TradeDataDB();
        IStandingsPersistance standingDb = new StandingsDataDb();
        StateContext context = new StateContext(input, output);
        context.setState(new InitialState(input, output));


        context.doProcessing();

        if (args == null || args.length == 0) {
            context.setState(new LoadTeamState(input, output, leagueDb, configDb, tradeDb, standingDb));
            context.doProcessing();
            context.nextState();
            context.doProcessing();
        } else {
            String filePath = args[0];
            context.setState(new ParsingState(input, output, filePath, leagueDb, coachDb, configDb, managerDb, tradeDb, standingDb));
            context.doProcessing();
            context.nextState();
            context.doProcessing();
            context.nextState();
            context.doProcessing();
        }
    }
}
