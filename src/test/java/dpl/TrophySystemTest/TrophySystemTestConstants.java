package dpl.TrophySystemTest;

public enum TrophySystemTestConstants {
    COACH_TEST("CoachTest"),
    PLAYER_TEST("PlayerTest"),
    TEST_KEY("TestKey"),
    TEST_VALUE("TestValue"),
    TEAM_TEST("TeamTest"),
    TEAM1_TEST("Team1Test"),
    VEZINA_TROPHY("Vezina Trophy"),
    STAT_PLAYER("StatPlayer"),
    PRESIDENT_TROPHY("President's Trophy"),
    CALDER_MEMORIAL_TROPHY("Calder Memorial Trophy"),
    JACK_ADAMS_AWARD("Jack Adam's Award"),
    MAURICE_RICHARD_TROPHY("Maurice Richard Trophy"),
    ROB_HAWKEY_MEMORIAL_CUP("Rob Hawkey Memorial Cup"),
    PARTICIPATION_AWARD("Participation Award");


    private final String trophySystemTestString;


    TrophySystemTestConstants(String trophySystemTestString) {
        this.trophySystemTestString = trophySystemTestString;
    }




    public String toString() {
        return trophySystemTestString;
    }


}

