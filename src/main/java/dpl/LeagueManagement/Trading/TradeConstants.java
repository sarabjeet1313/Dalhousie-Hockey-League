package dpl.LeagueManagement.Trading;

public enum TradeConstants {

	PARTITION("------------------------------------------ + ----------------------------------------"),
	PLAYERTABLE_HEADER("|	  PlayerName	 |   Position   | Age  | Skating | Shooting | Checking | Saving |"),
	ACCEPT_REJECT("Do you Accept the trade offered? [y/n]"),
	YES_SMALL("y"),
	YES_BIG("Y"),
	REQUESTED_PLAYER_FROM_TEAM("|						 Requested Players from  Your team						 |");

	private final String tradeString;

	TradeConstants(String tradeString) {
		this.tradeString = tradeString;
	}

	public String toString() {
		return tradeString;
	}
}
