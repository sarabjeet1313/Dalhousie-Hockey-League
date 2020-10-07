package dal.asd.dpl.SimulationStateMachine;


import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class StateContext {

    IState currentState;
    IUserOutput output;
    IUserInput input;

    public StateContext(IUserInput input, IUserOutput output) {
        this.input = input;
        this.output = output;
    }

    public void nextState() {
        this.currentState.nextState(this);
    }

    public void setState(IState state) {
        this.currentState = state;
    }

    public void doProcessing() {
        this.currentState.doProcessing();
    }

}
