package dpl.LeagueManagement.InitializeModels;

import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class InitializeModelsAbstractFactory implements IInitializeModelsAbstractFactory {

	@Override
	public InitializeLeagues InitializeLeagues(String filePath, ILeaguePersistance leagueDb, IUserOutput output,
			IUserInput input, ICoachPersistance coachDb, IGameplayConfigPersistance configDb,
			IManagerPersistance managerDb) {
		return new InitializeLeagues(filePath, leagueDb, output, input, coachDb, configDb, managerDb);
	}

}
