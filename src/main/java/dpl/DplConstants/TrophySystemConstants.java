package dpl.DplConstants;

public enum TrophySystemConstants {
    ARGUMENT_ERROR_MESSAGE("Argument passed contains null value"),
    PRESIDENT_TROPHY("President's Trophy"),
    CALDER_MEMORIAL_TROPHY("Calder Memorial Trophy"),
    JACK_ADAMS_AWARD("Jack Adam's Award"),
    VEZINA_TROPHY("Vezina Trophy"),
    MAURICE_RICHARD_TROPHY("Maurice Richard Trophy"),
    ROB_HAWKEY_MEMORIAL_CUP("Rob Hawkey Memorial Cup"),
    PARTICIPATION_AWARD("Participation Award");


    private final String trophySystemString;

    TrophySystemConstants(String trophySystemString) {
        this.trophySystemString = trophySystemString;
    }

    public String toString() {
        return trophySystemString;
    }
}
