package dal.asd.dpl;

import dal.asd.dpl.SimulationStateMachine.LoadTeamState;
import dal.asd.dpl.SimulationStateMachine.ParsingState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class App 
{
    public static void main( String[] args ) {

        IUserInput input = new CmdUserInput();
        IUserOutput output = new CmdUserOutput();

        StateContext context = new StateContext(input, output);

        if(args == null || args.length == 0) {
            context.setState(new LoadTeamState(input, output));
            context.nextState(); // Simulate state
        }
        else {
            String filePath = args[0];
            context.setState(new ParsingState(input, output, filePath));
            context.nextState(); // Create Team State
            context.nextState(); // Simulate state
        }
    }
}
