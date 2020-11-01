package dal.asd.dpl.Util;

public enum TeamUtil {

	TEAM_NAME("success");

	private final String teamString;

	private TeamUtil(String teamString) {
		this.teamString = teamString;
	}

	public String toString() {
		return teamString;
	}

}
