package dpl;

import dpl.Database.CoachDataDB;
import dpl.Database.GameConfigDB;
import dpl.Database.LeagueDataDB;
import dpl.Database.ManagerDataDB;
import dpl.Database.StandingsDataDb;
import dpl.Database.TradeDataDB;
import dpl.Dpl.ErrorHandling.RetirementManagementException;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine.*;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class App {

    public static void main(String[] args) throws RetirementManagementException {
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
