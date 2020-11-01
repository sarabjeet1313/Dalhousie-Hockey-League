package dal.asd.dpl.Util;

public enum ConferenceUtil {

	CONFERENCE_NAME("conferenceName"),
	CONFERENCE_ERROR("Please enter Conference name. Null values are not accepted.");

	private final String conferenceString;

	private ConferenceUtil(String conferenceString) {
		this.conferenceString = conferenceString;
	}

	public String toString() {
		return conferenceString;
	}

}
