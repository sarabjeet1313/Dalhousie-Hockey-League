package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalStateContext {
    public static ISimulationState currentState;
    public String currentStateName;

    public InternalStateContext(IUserInput input, IUserOutput output) {
        this.currentStateName = "";
    }

    public void doProcessing() {
        this.currentState.doProcessing();
    }

    public void nextState() {
        this.currentState.nextState(this);
    }

    public void setState(ISimulationState state) {
        this.currentStateName = state.getStateName();
        this.currentState = state;
    }
}
