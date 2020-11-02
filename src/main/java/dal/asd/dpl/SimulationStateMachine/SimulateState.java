package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.InternalStateMachine.InternalEndState;
import dal.asd.dpl.InternalStateMachine.InternalStartState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.Trading.ITradePersistence;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

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
		stateContext.setState(new InternalStartState(input, output, teamName, leagueToSimulate, stateContext, tradeDb, standingsDb));
		stateContext.doProcessing();
		stateContext.nextState(); // simulation state
		stateContext.doProcessing();
		stateContext.setState(new InternalEndState(output));
		stateContext.doProcessing();
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
