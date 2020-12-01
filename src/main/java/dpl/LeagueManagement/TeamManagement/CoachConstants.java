package dpl.LeagueManagement.TeamManagement;

public enum CoachConstants {

	SUCCESS("success"),
	COACH_NAME("coachName"),
	COACH_SKATING("skatingCoach"),
	COACH_SHOOTING("shootingCoach"),
	COACH_CHECKING("checkingCoach"),
	COACH_SAVING("savingCoach"),
	HEAD_COACH("headCoach"),
	COACHES("coaches"),
	NAME("name"),
	COACH("Coach :"),
	ENTER_COACH("Please enter Coach:"),
	HEAD_COACH_ERROR("Please enter Head Coach name. Null values are not accepted."),
	SKATING_ERROR(" skating value should be between 0 and 1."),
	SHOOTING_ERROR(" shooting value should be between 0 and 1."),
	CHECKING_ERROR(" checking value should be between 0 and 1."),
	SAVING_ERROR(" saving value should be between 0 and 1.");

	private final String coachString;

	CoachConstants(String coachString) {
		this.coachString = coachString;
	}

	public String toString() {
		return coachString;
	}

}
