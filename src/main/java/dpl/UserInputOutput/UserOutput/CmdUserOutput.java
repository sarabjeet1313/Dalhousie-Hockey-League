package dpl.UserInputOutput.UserOutput;

public class CmdUserOutput implements IUserOutput {
	private String outputResponse;

	public CmdUserOutput() {
		setInitialValues();
	}

	public void setInitialValues() {
		outputResponse = "";
	}

	public void setOutput(String outputResponse) {
		this.outputResponse = outputResponse;
	}

	public String sendOutput() {
		System.out.println(outputResponse);
		return this.outputResponse;
	}
}
