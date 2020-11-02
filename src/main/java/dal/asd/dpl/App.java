package dal.asd.dpl;

import dal.asd.dpl.Database.CoachDataDB;
import dal.asd.dpl.Database.GameConfigDB;
import dal.asd.dpl.Database.LeagueDataDB;
import dal.asd.dpl.Database.ManagerDataDB;
import dal.asd.dpl.Database.StandingsDataDb;
import dal.asd.dpl.Database.TradeDataDB;
import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.SimulationStateMachine.*;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.TeamManagement.ICoachPersistance;
import dal.asd.dpl.TeamManagement.ILeaguePersistance;
import dal.asd.dpl.TeamManagement.IManagerPersistance;
import dal.asd.dpl.Trading.ITradePersistance;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class App {
	
    public static void main( String[] args ) {
        IUserInput input = new CmdUserInput();
        IUserOutput output = new CmdUserOutput();
        ILeaguePersistance leagueDb = new LeagueDataDB();
        ICoachPersistance coachDb = new CoachDataDB();
        IGameplayConfigPersistance configDb = new GameConfigDB();
        IManagerPersistance managerDb = new ManagerDataDB();
        ITradePersistance tradeDb = new TradeDataDB();
        IStandingsPersistance standingDb = new StandingsDataDb(0);
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
