package dpl.DplConstants;

public enum TeamManagementConstants {
	
	EMPTY("Empty"),
	TEST_JSON("test.json");
	
	private final String constantString;

	private TeamManagementConstants(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}

}
