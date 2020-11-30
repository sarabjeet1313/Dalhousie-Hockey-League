package dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dpl.SystemConfig;
import dpl.DplConstants.InitializeLeaguesConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.GameplayConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class LoadTeamState implements IState {
	private IUserInput input;
	private IUserOutput output;
	private String teamName;
	private ILeaguePersistance leagueDb;
	private IGameplayConfigPersistance configDb;
	private ITradePersistence tradeDb;
	private IStandingsPersistance standingsDb;
	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	private ISimulationStateMachineAbstractFactory simulationStateMachineAbstractFactory;
	private static final Logger log = Logger.getLogger(LoadTeamState.class.getName());

	public LoadTeamState(IUserInput input, IUserOutput output, ILeaguePersistance leagueDb,
			IGameplayConfigPersistance configDb, ITradePersistence tradeDb, IStandingsPersistance standingsDb) {
		this.input = input;
		this.output = output;
		this.leagueDb = leagueDb;
		this.stateName = "Load Team";
		this.configDb = configDb;
		this.tradeDb = tradeDb;
		this.standingsDb = standingsDb;
		this.simulationStateMachineAbstractFactory = SystemConfig.getSingleInstance().getSimulationStateMachineAbstractFactory();
	}

	public void nextState(StateContext context) {
		this.nextStateName = "Simulate";
		context.setState(this.simulationStateMachineAbstractFactory.SimulateState(input, output, teamName, leagueToSimulate, tradeDb, standingsDb));
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
		leagueToSimulate = teamManagement.LeagueWithDbParameters(InitializeLeaguesConstants.TEST_LEAGUE.toString(), conferencesList, freeAgents,
				coaches, managers, config, leagueDb);
		try {
			leagueToSimulate = leagueToSimulate.loadLeague(teamName);
			config = config.loadGameplayConfig(leagueToSimulate);
			leagueToSimulate.setGameConfig(config);
			if (leagueToSimulate.getLeagueName()
					.equals(InitializeLeaguesConstants.TEST_LEAGUE.toString()) == Boolean.FALSE) {
				output.setOutput("League has been initialized for the team: " + teamName);
				output.sendOutput();
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
			output.setOutput(e.getMessage());
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
