package dpl.SimulationManagement.SimulationStateMachine;

import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class InitialState implements IState {
	private static IUserOutput output;
	private static IUserInput input;
	private static String stateName;
	private static String nextStateName;

	public InitialState(IUserInput input, IUserOutput output) {
		this.input = input;
		this.output = output;
		this.stateName = "Initial";
	}

	public void nextState(StateContext context) {
		this.nextStateName = "None";
	}

	public void doProcessing() {
		output.setOutput("Welcome to the Dynasty Mode. It's time to conquer the hockey arena.");
		output.sendOutput();
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
