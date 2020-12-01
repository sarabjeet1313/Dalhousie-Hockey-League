package dpl.SimulationManagement.SimulationStateMachine;

import dpl.SystemConfig;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.InitializeModels.IInitializeModelsAbstractFactory;
import dpl.LeagueManagement.InitializeModels.InitializeLeagues;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class ParsingState implements IState {
	private IUserOutput output;
	private IUserInput input;
	private String filePath;
	private ILeaguePersistance leagueDb;
	private League initializedLeague;
	private String stateName;
	private String nextStateName;
	private IGameplayConfigPersistance configDb;
	private ICoachPersistance coachDb;
	private IManagerPersistance managerDb;
	private IStandingsPersistance standingDb;
	private ITradePersistence tradeDb;
	private IInitializeModelsAbstractFactory initializeModels = SystemConfig.getSingleInstance()
			.getInitializeModelsAbstractFactory();
	private ISimulationStateMachineAbstractFactory simulationStateMachineAbstractFactory;

	public ParsingState(IUserInput input, IUserOutput output, String filePath, ILeaguePersistance leagueDb,
			ICoachPersistance coachDb, IGameplayConfigPersistance configDb, IManagerPersistance managerDb,
			ITradePersistence tradeDb, IStandingsPersistance standingDb) {
		this.input = input;
		this.output = output;
		this.filePath = filePath;
		this.leagueDb = leagueDb;
		this.coachDb = coachDb;
		this.configDb = configDb;
		this.managerDb = managerDb;
		this.stateName = "Parsing";
		this.tradeDb = tradeDb;
		this.standingDb = standingDb;
		this.simulationStateMachineAbstractFactory = SystemConfig.getSingleInstance()
				.getSimulationStateMachineAbstractFactory();
	}

	public void nextState(StateContext context) {
		this.nextStateName = "Create Team";
		context.setState(this.simulationStateMachineAbstractFactory.CreateTeamState(input, output, initializedLeague,
				leagueDb, coachDb, configDb, managerDb, tradeDb, standingDb));
	}

	public void doProcessing() {
		output.setOutput("Welcome to the Parsing State. It's time to parse the JSON and initialize your league.");
		output.sendOutput();
		InitializeLeagues leagueModel = initializeModels.InitializeLeagues(filePath, leagueDb, output, input, coachDb,
				configDb, managerDb);
		initializedLeague = leagueModel.parseAndInitializeModels();

		if (initializedLeague == null) {
			System.exit(0);
		}
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}

}
