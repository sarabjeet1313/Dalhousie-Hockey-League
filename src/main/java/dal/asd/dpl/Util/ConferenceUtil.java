package dal.asd.dpl.Util;

public enum ConferenceUtil {

	CONFERENCE_NAME("conferenceName");

	private final String conferenceString;

	private ConferenceUtil(String conferenceString) {
		this.conferenceString = conferenceString;
	}

	public String toString() {
		return conferenceString;
	}

}
