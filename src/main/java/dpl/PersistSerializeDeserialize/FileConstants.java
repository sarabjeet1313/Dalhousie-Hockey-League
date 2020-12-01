package dpl.PersistSerializeDeserialize;

public enum FileConstants {

	LEAGUE_SERIALIZATION_FILE("leagueSerialization.json"),
	COACH_SERIALIZATION_FILE("coachSerialization.json"),
	MANAGER_SERIALIZATION_FILE("managerSerialization.json"),
	RETIRED_PLAYER_SERIALIZATION_FILE("retiredPlayerSerialization.json"),
	GAMEPLAY_CONFIG_SERIALIZATION_FILE("gameplayConfigSerialization.json"),
	STANDING_SERIALIZATION_FILE("standingSerialization.json"),
	NEW_LINE("\n");
	

	private final String fileName;

	FileConstants(String fileName) {
		this.fileName = fileName;
	}

	public String toString() {
		return fileName;
	}
}
