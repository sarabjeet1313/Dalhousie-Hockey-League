package dpl.SimulationManagement.SimulationStateMachine;

import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.SimulationManagement.InternalStateMachine.InternalStartState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class SimulateState implements IState {

	private IUserInput input;
	private IUserOutput output;
	private String teamName;
	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private ITradePersistence tradeDb;
	private IStandingsPersistance standingsDb;

	public SimulateState(IUserInput input, IUserOutput output, String teamName, League leagueToSimulate,
			ITradePersistence tradeDb, IStandingsPersistance standingsDb) {
		this.input = input;
		this.output = output;
		this.teamName = teamName;
		this.stateName = "Simulate";
		this.leagueToSimulate = leagueToSimulate;
		this.tradeDb = tradeDb;
		this.standingsDb = standingsDb;
	}

	public void nextState(StateContext context) {
		this.nextStateName = "None";
		return;
	}

	public void doProcessing() {
		output.setOutput("Welcome to Simulation state :-) ");
		output.sendOutput();

		InternalStateContext stateContext = new InternalStateContext(input, output);
		stateContext.setState(
				new InternalStartState(input, output, teamName, leagueToSimulate, stateContext, tradeDb, standingsDb));
		stateContext.doProcessing();
		stateContext.nextState();
		stateContext.doProcessing();
		stateContext.nextState();
		stateContext.doProcessing();
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
