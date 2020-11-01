package dal.asd.dpl.Util;

public enum StoredProcedureUtil {
	
	PERSIST_COACH("spPersistCoach(?, ?, ?, ?, ?, ?, ?, ?)"), 
	PERSIST_MANAGER("spPersistManager(?,?,?,?)"),
	PERSIST_GAMECONFIG("spPersistGameplayConfig(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
	PERSIST_LEAGUE("spPersistLeagueData(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
	LOAD_GAMECONFIG("spLoadGameConfig(?)"),
	LOAD_LEAGUE("spLoadLeagueData(?)"),
	CHECK_LEAGUE_NAME("spCheckLeagueName(?)"),
	UPDATE_RETIRED_PLAYERS_IN_LEAGUE("spRetiredPlayer(?,?,?)");
	
	
	private final String spString;

	private StoredProcedureUtil(String spString) {
		this.spString = spString;
	}

	public String getSpString() {
		return spString;
	}
	
}
