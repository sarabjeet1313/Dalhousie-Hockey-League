package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalStateContext {

    public static ISimulationState currentState;
    public String currentStateName;
    private static IUserOutput output;
    private static IUserInput input;

    public InternalStateContext(IUserInput input, IUserOutput output) {
        this.currentStateName = "";
        this.input = input;
        this.output = output;
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
