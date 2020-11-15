package dpl.LeagueSimulationManagement.LeagueManagement.InitializeModels;

import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public interface IInitializeModelsAbstractFactory {

	public InitializeLeagues InitializeLeagues(String filePath, ILeaguePersistance leagueDb, IUserOutput output,
			IUserInput input, ICoachPersistance coachDb, IGameplayConfigPersistance configDb,
			IManagerPersistance managerDb);

}
