package dal.asd.dpl.Util;

public enum NewsSystemUtil {

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
    TO("to");

    private final String NewsSystemString;

    NewsSystemUtil(String NewsSystemString) {
        this.NewsSystemString = NewsSystemString;
    }

    public String toString() {
        return NewsSystemString;
    }

}
