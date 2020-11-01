package dal.asd.dpl.util;

public enum TeamManagementUtil {
	
	EMPTY("Empty"),
	TEST_JSON("test.json");
	
	private final String constantString;

	private TeamManagementUtil(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}

}
