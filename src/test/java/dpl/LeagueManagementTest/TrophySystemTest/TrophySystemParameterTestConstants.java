package dpl.LeagueManagementTest.TrophySystemTest;

public enum TrophySystemParameterTestConstants {
    TEST_SAVES(10),
    TEST_GOALS(5),
    TEST_PENALTIES(7),
    TEST_COACH_COUNT(12);
    private final int trophySystemTestInteger;

    TrophySystemParameterTestConstants(int trophySystemTestInteger) {
        this.trophySystemTestInteger = trophySystemTestInteger;
    }

    public int toInteger() {
        return trophySystemTestInteger;
    }
}
