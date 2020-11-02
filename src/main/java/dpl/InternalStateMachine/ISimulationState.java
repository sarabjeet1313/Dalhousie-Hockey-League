package dpl.InternalStateMachine;

public interface ISimulationState {
    public void nextState(InternalStateContext context);
    public void doProcessing();
    public String getStateName();
    public String getNextStateName();
}
