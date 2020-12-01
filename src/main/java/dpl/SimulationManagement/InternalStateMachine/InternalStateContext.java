package dpl.SimulationManagement.InternalStateMachine;

import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class InternalStateContext {

	public static ISimulationState currentState;
	public String currentStateName;

	public InternalStateContext(IUserInput input, IUserOutput output) {
		this.currentStateName = "";
	}

	public void doProcessing()  {
		this.currentState.doProcessing();
	}

	public ISimulationState nextState() {
		return this.currentState.nextState(this);
	}

	public void setState(ISimulationState state) {
		this.currentStateName = state.getStateName();
		this.currentState = state;
	}

	public boolean shouldContinue() {
		return this.currentState.shouldContinue();
	}

	public ISimulationState getCurrentstate() {
		return this.currentState;
	}
}
