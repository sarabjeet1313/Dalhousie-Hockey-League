package dpl.LeagueManagement.GameplayConfiguration;

public enum GameConfigConstants {

	AVG_RETIREMENT_AGE("avgRetirementAge"),
	MAX_RETIREMENT_AGE("maxRetirementAge"),
	RANDOM_WIN_CHANCE("randomWinChance"),
	RANDOM_INJURY_CHANCE("randomInjuryChance"),
	INJURY_DAYS_LOW("injuryDaysLow"),
	INJURY_DAYS_HIGH("injuryDaysHigh"),
	GAME_PLAY_CONFIG("gameplayConfig"),
	GMTABLE("gmTable"),
	INJURIES("injuries"),
	AGING("aging"),
	AVGERAGE_RETIREMENT_AGE("averageRetirementAge"),
	MAX_AGE("maximumAge"),
	STAT_DECAY_CHANCE("statDecayChance"),
	INVALID_RETIREMENT_AGE("Invalid Average Retirement Age"),
	GAME_RESOLVER("gameResolver"),
	INVALID_MAX_AGE("Invalid Max Retirement Age"),
	STAT_INCREASE_CHECK("daysUntilStatIncreaseCheck"),
	LOSS_POINT("lossPoint"),
	RANDOM_TRADE_OFFER_CHANCE("randomTradeOfferChance"),
	MAX_PLAYERS_PER_TRADE("maxPlayersPerTrade"),
	RANDOM_ACCEPTANCE_CHANCE("randomAcceptanceChance"),
	SUCCESS("success"),
	INVALID_RANDOM("Invalid random Acceptance Chance value"),
	INVALID_MAX_TRADE("Invalid max Players Per Trade value"),
	INVALID_TRADE_OFFER("Invalid random Trade Offer Chance value"),
	INVALID_RANDOM_INJURY("Invalid random Injury Chance value"),
	INVALID_LOWER_LIMIT("Invalid Injury lower limit value"),
	INVALID_LOSS_POINT("Invalid loss point value"),
	INVALID_RANDOM_WIN_CHANCE("Invalid random Win Chance value"),
	TRADING("trading"),
	TRAINING("training"),
	INVALID_DAYS("Invalid number of days value"),
	TRAINING_STATS("Stats Updated for : ");
	
	private final String comfigString;

	GameConfigConstants(String comfigString) {
		this.comfigString = comfigString;
	}

	public String toString() {
		return comfigString;
	}

}
