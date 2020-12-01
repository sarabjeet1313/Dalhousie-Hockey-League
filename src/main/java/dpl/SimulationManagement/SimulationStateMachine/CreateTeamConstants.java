package dpl.SimulationManagement.SimulationStateMachine;

public enum CreateTeamConstants {
	
	SELECT_GM("Please select the General manager for "),
	MANAGER_DISPLAY("ManagerID | MANAGER NAME | MANAGER'S PERSONALITY"),
	CHOOSE_MANAGER("Please enter the selected Manager ID"),
	MANAGER_ERROR("Manager ID cannot be string"),
	MANAGER_ERROR_1("Please enter a valid Manager ID"),
	SELECT_COACH("Please select the Head Coach for "),
	COACH_DISPLAY("Coach ID |	COACH NAME	 	| SKATING | SHOOTING | CHECKING | SAVING"),
	CHOOSE_COACH("Please enter the selected coach ID"),
	COACH_ERROR("Coach ID cannot be null"),
	COACH_ERROR_1("Please enter a valid Coach ID");
	
	private final String constantString;

	CreateTeamConstants(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}
}
