package dal.asd.dpl.Util;

public enum CoachUtil {

	SUCCESS("success"),
	COACH_NAME("coachName"),
	COACH_SKATING("skatingCoach"),
	COACH_SHOOTING("shootingCoach"),
	COACH_CHECKING("checkingCoach"),
	COACH_SAVING("savingCoach");

	private final String coachString;

	private CoachUtil(String coachString) {
		this.coachString = coachString;
	}

	public String toString() {
		return coachString	;
	}

}
