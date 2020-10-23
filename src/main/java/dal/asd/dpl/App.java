package dal.asd.dpl;

import dal.asd.dpl.Database.LeagueDataDB;
import dal.asd.dpl.NewsSystem.IGamesPlayed;
import dal.asd.dpl.NewsSystem.NewsSystem;
import dal.asd.dpl.SimulationStateMachine.*;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class App {
	
    public static void main( String[] args ) {
        IUserInput input = new CmdUserInput();
        IUserOutput output = new CmdUserOutput();
        ILeague leagueDb = new LeagueDataDB();
        StateContext context = new StateContext(input, output);
        context.setState(new InitialState(input, output));

        context.doProcessing();

        if(args == null || args.length == 0) {
            context.setState(new LoadTeamState(input, output, leagueDb));
            context.doProcessing();
            context.nextState(); // Simulate state
            context.doProcessing();
        }
        else {
            String filePath = args[0];
            context.setState(new ParsingState(input, output, filePath, leagueDb));
            context.doProcessing();
            context.nextState(); // Create Team State
            context.doProcessing();
            context.nextState(); // Simulate state
            context.doProcessing();
        }
    }
}
