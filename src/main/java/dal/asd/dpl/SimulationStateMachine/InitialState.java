package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InitialState implements IState {
    private static IUserOutput output;
    private static IUserInput input;

    InitialState(IUserInput input, IUserOutput output) {
        this.input = input;
        this.output = output;
        doProcessing();
    }

    public void nextState(StateContext context){
    }

    public void doProcessing(){
        output.setOutput("Welcome to the Dynasty Mode. It's time to conquer the hockey arena.");
        output.sendOutput();
    }
}
