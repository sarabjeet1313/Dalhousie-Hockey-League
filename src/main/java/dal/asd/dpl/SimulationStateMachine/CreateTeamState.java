package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class CreateTeamState implements IState {

    private static IUserInput input;
    private static IUserOutput output;

    public CreateTeamState(IUserInput input, IUserOutput output) {
        this.input = input;
        this.output = output;
        doProcessing();
    }

    public void nextState(StateContext context){
        context.setState(new SimulateState(input, output));
    }

    public void doProcessing(){

        output.setOutput("Welcome to the Create Team State. It's time to create and store the team to dB");
        output.sendOutput();

        // TODO
        // need to store the model to database
        // 1. either I can call Praneeth's function with no argument.
        // 2. Or I can call the function with the League object

    }
}
