package dpl.LeagueManagementTest.TrophySystemTest;

public enum TrophySystemTestConstants {
    COACH_TEST("CoachTest"),
    PLAYER_TEST("PlayerTest"),
    TEST_KEY("TestKey"),
    TEST_VALUE("TestValue"),
    TEAM_TEST("TeamTest"),
    TEAM_TEST1("TeamTest1"),
    TEAM1_TEST("Team1Test"),
    STAT_PLAYER("StatPlayer"),
    COACH("Coach"),
    PLAYER("Player"),
    PRESIDENT_TROPHY("President's Trophy"),
    CALDER_MEMORIAL_TROPHY("Calder Memorial Trophy");

    private final String trophySystemTestString;

    TrophySystemTestConstants(String trophySystemTestString) {
        this.trophySystemTestString = trophySystemTestString;
    }

    public String toString() {
        return trophySystemTestString;
    }

}

