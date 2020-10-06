package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalStateContext {

    ISimulationState currentState;
    String stateName;
    IUserOutput output;
    IUserInput input;

    public InternalStateContext(IUserInput input, IUserOutput output) {
        this.input = input;
        this.output = output;
    }

    public void nextState() {
        this.currentState.nextState(this);
    }

    public void setState(ISimulationState state) {
        this.currentState = state;
    }
}
