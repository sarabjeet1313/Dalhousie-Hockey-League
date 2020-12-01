package dpl.LeagueManagementTest.TradingTest;

public enum TradeTestConstants {

    LEAGUE_NAME("Dalhousie hockey league"),
    FORWARD("forward");

    private final String tradeTestString;

    TradeTestConstants(String tradeString) {
        this.tradeTestString = tradeString;
    }

    public String toString() {
        return tradeTestString;
    }
}
