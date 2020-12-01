package dpl.Database;

public enum StoredProcedureConstants {
	
	PERSIST_COACH("spPersistCoach(?, ?, ?, ?, ?, ?, ?, ?)"), 
	PERSIST_MANAGER("spPersistManager(?,?,?,?)"),
	PERSIST_GAMECONFIG("spPersistGameplayConfig(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
	PERSIST_LEAGUE("spPersistLeagueData(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
	LOAD_GAMECONFIG("spLoadGameConfig(?)"),
	LOAD_LEAGUE("spLoadLeagueData(?)"),
	CHECK_LEAGUE_NAME("spCheckLeagueName(?)"),
	UPDATE_RETIRED_PLAYERS_IN_LEAGUE("spRetiredPlayer(?,?,?)"),
	UPDATE_TEAM_WIN("spUpdateStandingsWin(?, ?, ?)"),
	UPDATE_TEAM_LOSS("spUpdateStandingsLose(?, ?, ?)"),
	INSERT_TO_STANDINGS("spInsertToStandings(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
	GET_TEAM_NAME("spGetTeamName(?)"),
	GET_TOP_TEAMS("spGetTopSeededTeams(?, ?)"),
	UPDATE_LEAGUE("spUpdateLeaguePlayers(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
	GET_LOSS("spGetLossPoint()"),
	GET_MAX_PLAYER("spGetMaxPlayersPerTrade()"),
	MAX_PLAYER("maxPlayersPerTrade"),
	GET_RANDOM("spGetRandomTradeOfferChance()"),
	RANDOM_TRADE("randomTradeOfferChance"),
	GET_TRADE_ACCEPT("spGetRandomTradeAcceptChance"),
	TRADE_ACCEPT("randomTradeAcceptChance"),
	LOSS_POINT("losspoint");
	
	private final String spString;

	StoredProcedureConstants(String spString) {
		this.spString = spString;
	}

	public String getSpString() {
		return spString;
	}
	
}
