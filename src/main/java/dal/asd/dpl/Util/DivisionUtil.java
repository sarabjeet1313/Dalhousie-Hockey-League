package dal.asd.dpl.Util;

public enum DivisionUtil {

	DIVISION_NAME("divisionName");

	private final String divisionString;

	private DivisionUtil(String divisionString) {
		this.divisionString = divisionString;
	}

	public String toString() {
		return divisionString;
	}

}
