package dal.asd.dpl.SimulationStateMachine;

import java.util.ArrayList;
import java.util.List;

import dal.asd.dpl.GameplayConfiguration.GameplayConfig;
import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.ILeaguePersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Manager;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.Trading.ITradePersistance;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class LoadTeamState implements IState {
	private IUserInput input;
	private IUserOutput output;
	private String teamName;
	private ILeaguePersistance leagueDb;
	private IGameplayConfigPersistance configDb;
	private ITradePersistance tradeDb;
	private IStandingsPersistance standingsDb;
	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;

	public LoadTeamState(IUserInput input, IUserOutput output, ILeaguePersistance leagueDb,
			IGameplayConfigPersistance configDb, ITradePersistance tradeDb, IStandingsPersistance standingsDb) {
		this.input = input;
		this.output = output;
		this.leagueDb = leagueDb;
		this.stateName = "Load Team";
		this.configDb = configDb;
		this.tradeDb = tradeDb;
		this.standingsDb = standingsDb;
	}

	public void nextState(StateContext context) {
		this.nextStateName = "Simulate";
		context.setState(new SimulateState(input, output, teamName, leagueToSimulate, tradeDb, standingsDb));
	}

	public void doProcessing() {
		output.setOutput("Welcome to Load Team state :-) ");
		output.sendOutput();
		output.setOutput("Please enter the team name and we will load the complete League for you.");
		output.sendOutput();
		input.setInput();
		teamName = input.getInput();
		List<Conference> conferencesList = null;
		List<Player> freeAgents = null;
		List<Coach> coaches = null;
		List<Manager> managers = new ArrayList<Manager>();
		GameplayConfig config = new GameplayConfig(configDb);
		leagueToSimulate = new League("test", conferencesList, freeAgents, coaches, managers, config, leagueDb);
		leagueToSimulate = leagueToSimulate.loadLeague(teamName);
		config = config.loadGameplayConfig(leagueToSimulate);
		leagueToSimulate.setGameConfig(config);
		if (!leagueToSimulate.getLeagueName().equals("test")) {
			output.setOutput("League has been initialized for the team: " + teamName);
			output.sendOutput();
		}
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
