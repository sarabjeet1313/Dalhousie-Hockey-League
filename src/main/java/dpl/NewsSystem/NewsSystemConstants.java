package dpl.NewsSystem;

public enum NewsSystemConstants {
	ARGUMENT_MESSAGE("Argument passed contains null value"),
	WINNER("winner"),
	LOSER("loser"),
	DATE("date"),
	PLAYER("player"),
	PLAYERS("players"),
	DAYS_INJURED("daysInjured"),
	AGE("age"),
	TEAM("team"),
	FROM("from"),
	EXCEPTION_MESSAGE("NullPointer Exception caught"),
	TO("to");

	private final String NewsSystemString;

	NewsSystemConstants(String NewsSystemString) {
		this.NewsSystemString = NewsSystemString;
	}

	public String toString() {
		return NewsSystemString;
	}
}
