package dpl.SerializeDeserialize;

public enum FileConstants {

    STANDING_SERIALIZATION_FILE("standingSerialization.json");

    private final String fileName;

    FileConstants(String fileName) {
        this.fileName = fileName;
    }

    public String toString() {
        return fileName;
    }
}
