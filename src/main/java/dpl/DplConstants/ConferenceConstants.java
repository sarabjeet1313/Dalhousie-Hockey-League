package dpl.DplConstants;

public enum ConferenceConstants {

	CONFERENCE_NAME("conferenceName"),
	CONFERENCE_ERROR("Please enter Conference name. Null values are not accepted.");

	private final String conferenceString;

	private ConferenceConstants(String conferenceString) {
		this.conferenceString = conferenceString;
	}

	public String toString() {
		return conferenceString;
	}

}
