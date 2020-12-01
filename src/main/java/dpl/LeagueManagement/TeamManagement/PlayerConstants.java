package dpl.LeagueManagement.TeamManagement;

public enum PlayerConstants {

	PLAYER_NAME("playerName"),
	FREE_AGENT("freeAgents"),
	PLAYERS("players"),
	PLAYER("Player:"),
	PLAYER_POSITION("position"),
	PLAYER_CAPTAIN("captain"),
	PLAYER_AGE("age"),
	SKATING("skating"),
	SHOOTING("shooting"),
	CHECKING("checking"),
	SAVING("saving"),
	IS_INJURED("isInjured"),
	RETIRED_STATUS("retiredStatus"),
	DAYS_INJURED("daysInjured"),
	IS_ACTIVE("isActive"),
	ENTER_DETAILS("Please enter Player name. Player:" ),
	ENTER_PLAYER("Please enter player:"),
	ENTER_FREE_AGENT("Please enter Free Agent:" ),
	EMPTY_MSG( " name is empty."),
	POSITION_ERROR(" position. Null values are not accepted."),
	CAPTAIN_ERROR("A team can only have one captain."),
	CANNOT_CAPTAIN(" cannot be a captain."),
	POSITION_TYPE_ERROR(" position must be either 'goalie', 'forward', or 'defense'."),
	PLAYER_AGE_ERROR( " age should be integer and greater than 0."),
	SKATING_ERROR(" skating value should be between 0 and 20."),
	SHOOTING_ERROR(" shooting value should be between 0 and 20."),
	CHECKING_ERROR(" checking value should be between 0 and 20."),
	SAVING_ERROR(" saving value should be between 0 and 20."),
	BIRTH_DAY("birthDay"),
	BIRTH_MONTH("birthMonth"),
	BIRTH_YEAR("birthYear"),
	GOALIE("goalie"),
	FORWARD("forward"),
	DEFENCE("defense");
	

	private final String playerString;

	PlayerConstants(String playerString) {
		this.playerString = playerString;
	}

	public String toString() {
		return playerString	;
	}
}
