package dpl.SimulationStateMachineTest;

public class CustomValidation {
	public boolean isNumber(String input) {
		boolean isValid = false;
		String regex = "[0-9]+";
		if(input.matches(regex)) {
			isValid = true;
		}
		return isValid;
	}
}
