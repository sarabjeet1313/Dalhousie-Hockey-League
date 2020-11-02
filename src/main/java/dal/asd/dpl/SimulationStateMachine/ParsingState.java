package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.InitializeModels.InitializeLeagues;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.TeamManagement.ICoachPersistance;
import dal.asd.dpl.TeamManagement.ILeaguePersistance;
import dal.asd.dpl.TeamManagement.IManagerPersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.Trading.ITradePersistence;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

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
	}

	public void nextState(StateContext context) {
		this.nextStateName = "Create Team";
		context.setState(new CreateTeamState(input, output, initializedLeague, leagueDb, coachDb, configDb, managerDb, tradeDb, standingDb));
	}

	public void doProcessing() {
		output.setOutput("Welcome to the Parsing State. It's time to parse the JSON and initialize your league.");
		output.sendOutput();
		InitializeLeagues leagueModel = new InitializeLeagues(filePath, leagueDb, output, input, coachDb, configDb,
				managerDb);
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
