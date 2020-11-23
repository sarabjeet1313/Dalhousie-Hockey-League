package dpl.SerializeDeserialize;

public class SerializeDeserializeAbstractFactory implements ISerializeDeserializeAbstractFactory{

	@Override
	public CoachSerializationDeserialization CoachSerializationDeserialization() {
		return new CoachSerializationDeserialization();
	}

}
