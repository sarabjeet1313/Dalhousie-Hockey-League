package dpl.SimulationManagement.InternalStateMachine;

public class GameContext {

	private ISimulateMatch match;

	public GameContext(ISimulateMatch match) {
		this.match = match;
	}

	public void changeGame(ISimulateMatch match) {
		this.match = match;
	}

	public void simulateMatch() {
		match.simulateMatch();
	}

	public ISimulateMatch getMatch() {
		return this.match;
	}

}
