package dpl.SimulationManagement.SimulationStateMachine;

public interface IState {

	public void nextState(StateContext context);

	public void doProcessing();

	public String getStateName();

	public String getNextStateName();
}
