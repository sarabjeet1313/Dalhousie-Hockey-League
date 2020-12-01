package dpl.LeagueManagement.TeamManagement;

public enum ManagerConstants {

	SUCCESS("success"),
	GENERAL_MANAGER_NAME("generalManagerName"),
	NAME("name"),
	PERSONALITY("personality"),
	NORMAL("normal"),
	GENERAL_MANAGER("generalManager"),
	GENERAL_MANAGERS("generalManagers"),
	GENERAL_MANAGER_ERROR("Please enter General Manager name. Null values are not accepted."),
	GENERAL_MANAGER_ERROR_EMPTY("General manager cannot be empty");

	private final String managerString;

	ManagerConstants(String managerString) {
		this.managerString = managerString;
	}

	public String toString() {
		return managerString;
	}
}
