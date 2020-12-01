package dpl.LeagueManagement.TrophySystem;

public enum TrophySystemConstants {
	NULLPOINTER_MESSAGE("not Found"),
	PRESIDENT_TROPHY("PRESIDENT'S TROPHY"),
	CALDER_MEMORIAL_TROPHY("CALDER MEMORIAL TROPHY"),
	JACK_ADAMS_AWARD("JACK ADAM'S AWARD"),
	VEZINA_TROPHY("VEZINA TROPHY"),
	MAURICE_RICHARD_TROPHY("MAURICE RICHARD TROPHY"),
	ROB_HAWKEY_MEMORIAL_CUP("ROB HAWKEY MEMORIAL CUP"),
	PARTICIPATION_AWARD("PARTICIPATION AWARD"),
	PRESIDENT_TROPHY_LOG("Team selected for President's Trophy "),
	CALDER_MEMORIAL_TROPHY_LOG("Player selected for Calder Memorial Trophy"),
	JACK_ADAMS_AWARD_LOG("Coach selected for Jack Adam's Award"),
	VEZINA_TROPHY_LOG("Goalie selected for Vezina Trophy"),
	MAURICE_RICHARD_TROPHY_LOG("Player selected for Maurice Richard Trophy"),
	ROB_HAWKEY_MEMORIAL_CUP_LOG("Player selected for Rob Hawkey Memorial Cup"),
	PARTICIPATION_AWARD_LOG("Team selected for Participation Award"),
	EXCEPTION_MESSAGE("NullPointer Exception caught"),
	TROPHY_HISTORY("HISTORY OF TROPHIES!"),
	BEST_TEAM("Best Team: "),
	BEST_PLAYER("Best Player: "),
	BEST_GOALIE("Best Goalie: "),
	BEST_COACH("Best Coach: "),
	BEST_SCORE("Best Scorer: "),
	NEXT_LINE("\n"),
	BEST_DEFENCEMEN("Best Defencemen: "),
	PARTICIPATION_TEAM("Participation Team: "),
	COACH("Coach"),
	STAT_PLAYER("StatPlayer"),
	PLAYER("player"),
	TEAM("team"),
	YEAR("Year: "),
	YEAR_TWENTY("2022"),
	YEAR_TEN("2021"),
	LINE("=========="),
	ARROW(" Trophy ---> Name: ");

	private final String trophySystemString;

	TrophySystemConstants(String trophySystemString) {
		this.trophySystemString = trophySystemString;
	}

	public String toString() {
		return trophySystemString;
	}
}
