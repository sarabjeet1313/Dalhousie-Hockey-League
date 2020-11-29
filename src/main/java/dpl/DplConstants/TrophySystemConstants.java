package dpl.DplConstants;

public enum TrophySystemConstants {
    ARGUMENT_ERROR_MESSAGE("Argument passed contains null value"),
    PRESIDENT_TROPHY("President's Trophy"),
    CALDER_MEMORIAL_TROPHY("Calder Memorial Trophy"),
    JACK_ADAMS_AWARD("Jack Adam's Award"),
    VEZINA_TROPHY("Vezina Trophy"),
    MAURICE_RICHARD_TROPHY("Maurice Richard Trophy"),
    ROB_HAWKEY_MEMORIAL_CUP("Rob Hawkey Memorial Cup"),

    BEST_TEAM("Best Team :"),
    BEST_PLAYER("Best Player :"),
    BEST_GOALIE("Best Goalie :"),
    BEST_COACH("Best Coach :"),
    BEST_SCORE("Best Scorer :"),
    BEST_DEFENCEMEN("Best Defencemen :"),
    PARTICIPATION_TEAM("Participation Team :"),
    COACH("Coach"),
    STAT_PLAYER("StatPlayer"),
    PLAYER("player"),
    TEAM("team"),
    YEAR("year"),
    LINE("=========="),
    ARROW("--->"),
    VALID_AWARD_TYPE("Please provide the valid award type..!"),
    PARTICIPATION_AWARD("Participation Award");


    private final String trophySystemString;

    TrophySystemConstants(String trophySystemString) {
        this.trophySystemString = trophySystemString;
    }

    public String toString() {
        return trophySystemString;
    }
}
