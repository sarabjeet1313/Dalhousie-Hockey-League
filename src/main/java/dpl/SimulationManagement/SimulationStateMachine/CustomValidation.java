package dpl.SimulationManagement.SimulationStateMachine;

public class CustomValidation {
	
	public CustomValidation() {
		super();
	}

	public static boolean isNumber(String string) {
		return string.matches("^\\d+$");
	}
}
