package dal.asd.dpl.Util;

public enum ManagerUtil {

	SUCCESS("success"),
	GENERAL_MANAGER_NAME("generalManagerName");

	private final String managerString;

	private ManagerUtil(String managerString) {
		this.managerString = managerString;
	}

	public String toString() {
		return managerString;
	}

}
