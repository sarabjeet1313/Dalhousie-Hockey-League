package dpl.SimulationManagement.SimulationStateMachine;

import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class SimulationStateMachineAbstractFactory implements ISimulationStateMachineAbstractFactory {

	@Override
	public IState CreateTeamState(IUserInput input, IUserOutput output, League league, ILeaguePersistance leagueDb,
			ICoachPersistance coachDb, IGameplayConfigPersistance configDb, IManagerPersistance managerDb,
			ITradePersistence tradeDb, IStandingsPersistance standingDb) {
		return new CreateTeamState(input, output, league, leagueDb, coachDb, configDb, managerDb, tradeDb, standingDb);
	}

	@Override
	public CustomValidation CustomValidation() {
		return new CustomValidation();
	}

	@Override
	public IState InitialState(IUserInput input, IUserOutput output) {
		return new InitialState(input, output);
	}

	@Override
	public IState LoadTeamState(IUserInput input, IUserOutput output, ILeaguePersistance leagueDb,
			IGameplayConfigPersistance configDb, ITradePersistence tradeDb, IStandingsPersistance standingsDb) {
		return new LoadTeamState(input, output, leagueDb, configDb, tradeDb, standingsDb);
	}

	@Override
	public IState ParsingState(IUserInput input, IUserOutput output, String filePath, ILeaguePersistance leagueDb,
			ICoachPersistance coachDb, IGameplayConfigPersistance configDb, IManagerPersistance managerDb,
			ITradePersistence tradeDb, IStandingsPersistance standingDb) {
		return new ParsingState(input, output, filePath, leagueDb, coachDb, configDb, managerDb, tradeDb, standingDb);
	}

	@Override
	public IState SimulateState(IUserInput input, IUserOutput output, String teamName, League leagueToSimulate,
			ITradePersistence tradeDb, IStandingsPersistance standingsDb) {
		return new SimulateState(input, output, teamName, leagueToSimulate, tradeDb, standingsDb);
	}

	@Override
	public StateContext StateContext(IUserInput input, IUserOutput output) {
		return new StateContext(input, output);
	}

}
