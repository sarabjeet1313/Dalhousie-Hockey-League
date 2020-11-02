package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.Trading.ITradePersistance;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalStartState implements ISimulationState {
	private IUserInput input;
	private IUserOutput output;
	private String teamName;
	public int numOfSeasons;
	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private InternalStateContext context;
	private ITradePersistance tradeDb;
	private IStandingsPersistance standingsDb;

	public InternalStartState(IUserInput input, IUserOutput output, String teamName, League leagueToSimulate,
			InternalStateContext context, ITradePersistance tradeDb, IStandingsPersistance standingsDb) {
		this.input = input;
		this.output = output;
		this.teamName = teamName;
		this.stateName = StateConstants.START_STATE;
		this.leagueToSimulate = leagueToSimulate;
		this.context = context;
		this.numOfSeasons = 0;
		this.tradeDb = tradeDb;
		this.standingsDb = standingsDb;
	}

	public void nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.INTERNAL_SIMULATION_STATE;
		context.setState(new InternalSimulationState(input, output, numOfSeasons, teamName, leagueToSimulate, context, tradeDb, standingsDb));
	}

	public void doProcessing() {
		output.setOutput("How many seasons you want to simulate for " + teamName + " ?");
		output.sendOutput();
		input.setInput();
		this.numOfSeasons = Integer.parseInt(input.getInput());
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
