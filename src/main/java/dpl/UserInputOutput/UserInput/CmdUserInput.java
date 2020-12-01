package dpl.UserInputOutput.UserInput;

import java.util.Scanner;

public class CmdUserInput implements IUserInput {

	private String inputResponse;

	public CmdUserInput() {
		setInitialValues();
	}

	public void setInitialValues() {
		inputResponse = "";
	}

	public String getInput() {
		return this.inputResponse;
	}

	public void setInput() {
		Scanner scanInput = new Scanner(System.in);
		this.inputResponse = scanInput.nextLine();
	}
}
