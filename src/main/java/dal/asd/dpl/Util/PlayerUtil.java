package dal.asd.dpl.Util;

public enum PlayerUtil {

	PLAYER_NAME("playerName"),
	PLAYER_POSITION("position"),
	PLAYER_CAPTAIN("captain"),
	PLAYER_AGE("age"),
	SKATING("skating"),
	SHOOTING("shooting"),
	CHECKING("checking"),
	SAVING("saving"),
	IS_INJURED("isInjured"),
	RETIRED_STATUS("retiredStatus"),
	DAYS_INJURED("daysInjured");

	private final String playerString;

	private PlayerUtil(String playerString) {
		this.playerString = playerString;
	}

	public String toString() {
		return playerString	;
	}

}
