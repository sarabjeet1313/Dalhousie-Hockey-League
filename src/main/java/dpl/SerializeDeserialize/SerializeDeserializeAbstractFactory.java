package dpl.SerializeDeserialize;

public class SerializeDeserializeAbstractFactory implements ISerializeDeserializeAbstractFactory {
    @Override
    public StandingSerializationDeserialization StandingSerializationDeserialization() {
        return new StandingSerializationDeserialization();
    }

}
