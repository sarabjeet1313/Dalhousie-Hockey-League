package dpl.SimulationManagement.InternalStateMachine;

public interface ISimulationState {

	ISimulationState nextState(InternalStateContext context);

	void doProcessing();

	String getStateName();

	String getNextStateName();

	boolean shouldContinue();

}
