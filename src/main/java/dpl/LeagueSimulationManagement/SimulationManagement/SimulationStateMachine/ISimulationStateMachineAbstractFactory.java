package dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine;

import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public interface ISimulationStateMachineAbstractFactory {

	public IState CreateTeamState(IUserInput input, IUserOutput output, League league, ILeaguePersistance leagueDb,
			ICoachPersistance coachDb, IGameplayConfigPersistance configDb, IManagerPersistance managerDb,
			ITradePersistence tradeDb, IStandingsPersistance standingDb);

	public CustomValidation CustomValidation();

	public IState InitialState(IUserInput input, IUserOutput output);

	public IState LoadTeamState(IUserInput input, IUserOutput output, ILeaguePersistance leagueDb,
			IGameplayConfigPersistance configDb, ITradePersistence tradeDb, IStandingsPersistance standingsDb);

	public IState ParsingState(IUserInput input, IUserOutput output, String filePath, ILeaguePersistance leagueDb,
			ICoachPersistance coachDb, IGameplayConfigPersistance configDb, IManagerPersistance managerDb,
			ITradePersistence tradeDb, IStandingsPersistance standingDb);

	public IState SimulateState(IUserInput input, IUserOutput output, String teamName, League leagueToSimulate,
			ITradePersistence tradeDb, IStandingsPersistance standingsDb);

	public StateContext StateContext(IUserInput input, IUserOutput output);

}
