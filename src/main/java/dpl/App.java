package dpl;

import dpl.Database.CoachDataDB;
import dpl.Database.GameConfigDB;
import dpl.Database.LeagueDataDB;
import dpl.Database.ManagerDataDB;
import dpl.Database.StandingsDataDb;
import dpl.Database.TradeDataDB;
import dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.SimulationStateMachine.*;
import dpl.Standings.IStandingsPersistance;
import dpl.TeamManagement.ICoachPersistance;
import dpl.TeamManagement.ILeaguePersistance;
import dpl.TeamManagement.IManagerPersistance;
import dpl.Trading.ITradePersistence;
import dpl.UserInput.CmdUserInput;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

public class App {
	
    public static void main( String[] args ) {
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

        if(args == null || args.length == 0) {
            context.setState(new LoadTeamState(input, output, leagueDb, configDb, tradeDb, standingDb));
            context.doProcessing();
            context.nextState(); // Simulate state
            context.doProcessing();
        }
        else {
            String filePath = args[0];
            context.setState(new ParsingState(input, output, filePath, leagueDb, coachDb, configDb, managerDb, tradeDb, standingDb));
            context.doProcessing();
            context.nextState(); // Create Team State
            context.doProcessing();
            context.nextState(); // Simulate state
            context.doProcessing();
        }
    }
}
