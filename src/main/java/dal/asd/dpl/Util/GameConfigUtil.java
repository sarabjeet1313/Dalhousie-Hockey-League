package dal.asd.dpl.Util;

public enum GameConfigUtil {
	
	AVG_RETIREMENT_AGE("avgRetirementAge"),
	MAX_RETIREMENT_AGE("maxRetirementAge"),
	RANDOM_WIN_CHANCE("randomWinChance"),
	RANDOM_INGURY_CHANCE("randomInjuryChance"),
	INJURY_DAYS_LOW("injuryDaysLow"),
	INJURY_DAYS_HIGH("injuryDaysHigh"),
	STAT_INCREASE_CHECK("daysUntilStatIncreaseCheck"),
	LOSS_POINT("lossPoint"),
	RANDOM_TRADE_OFFER_CHANCE("randomTradeOfferChance"),
	MAX_PLAYERS_PER_TRADE("maxPlayersPerTrade"),
	RANDOM_ACCEPTANCE_CHANCE("randomAcceptanceChance"),
	SUCCESS("success");
	
	private final String comfigString;

	private GameConfigUtil(String comfigString) {
		this.comfigString = comfigString;
	}

	public String toString() {
		return comfigString;
	}

}
