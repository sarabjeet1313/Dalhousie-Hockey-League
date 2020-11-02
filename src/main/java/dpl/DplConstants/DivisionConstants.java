package dpl.DplConstants;

public enum DivisionConstants {

	DIVISION_NAME("divisionName"),
	DIVISIONS_MODEL("divisions"),
	DIVISIONS_ERROR("Please enter Division name. Null values are not accepted.");

	private final String divisionString;

	private DivisionConstants(String divisionString) {
		this.divisionString = divisionString;
	}

	public String toString() {
		return divisionString;
	}

}
