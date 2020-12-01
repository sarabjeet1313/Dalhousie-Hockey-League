package dpl.SimulationManagement.InternalStateMachine;

import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EndOfSeasonState implements ISimulationState {
	private String stateName = StateConstants.END_SEASON_STATE;
	private String nextStateName;
	private IUserOutput output;
	private static final Logger log = Logger.getLogger(EndOfSeasonState.class.getName());

	public EndOfSeasonState(IUserOutput output) {
		this.output = output;
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.NO_STATE;
		return null;
	}

	public void doProcessing() {
		output.setOutput(StateConstants.END_OF_SEASON_STATE_OUTPUT);
		output.sendOutput();
		log.log(Level.INFO, StateConstants.END_OF_SEASON_STATE_OUTPUT);
	}

	public boolean shouldContinue() {
		return false;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
