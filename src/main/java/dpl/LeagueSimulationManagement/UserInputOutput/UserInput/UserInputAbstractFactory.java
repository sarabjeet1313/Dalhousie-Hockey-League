package dpl.LeagueSimulationManagement.UserInputOutput.UserInput;

public class UserInputAbstractFactory implements IUserInputAbstractFactory {

	@Override
	public IUserInput CmdUserInput() {
		return new CmdUserInput();
	}

}
