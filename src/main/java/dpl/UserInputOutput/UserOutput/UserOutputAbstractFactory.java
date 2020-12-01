package dpl.UserInputOutput.UserOutput;

public class UserOutputAbstractFactory implements IUserOutputAbstractFactory {

	@Override
	public IUserOutput CmdUserOutput() {
		return new CmdUserOutput();
	}

}
