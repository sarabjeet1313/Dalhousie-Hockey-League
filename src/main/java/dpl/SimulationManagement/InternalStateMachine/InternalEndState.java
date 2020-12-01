package dpl.SimulationManagement.InternalStateMachine;

import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InternalEndState implements ISimulationState {
	private static IUserOutput output;
	private static String stateName;
	private static String nextStateName;
	private static final Logger log = Logger.getLogger(InternalEndState.class.getName());

	public InternalEndState(IUserOutput output) {
		this.output = output;
		this.stateName = StateConstants.END_STATE;
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.NO_STATE;
		return null;
	}

	public void doProcessing() {
		output.setOutput(StateConstants.END_STATE_FINISH);
		output.sendOutput();
		log.log(Level.INFO, StateConstants.END_STATE_FINISH);
	}

	public boolean shouldContinue() {
		return true;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
