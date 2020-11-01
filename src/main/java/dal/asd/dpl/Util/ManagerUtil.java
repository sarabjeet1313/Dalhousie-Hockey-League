package dal.asd.dpl.Util;

public enum ManagerUtil {

	SUCCESS("success"),
	GENERAL_MANAGER_NAME("generalManagerName"),
	GENERAL_MANAGER("generalManager"),
	GENERAL_MANAGERS("generalManagers"),
	GENERAL_MANAGER_ERROR("Please enter General Manager name. Null values are not accepted."),
	GENERAL_MNAGER_ERROR_EMPTY("General manager cannot be empty");

	private final String managerString;

	private ManagerUtil(String managerString) {
		this.managerString = managerString;
	}

	public String toString() {
		return managerString;
	}

}
