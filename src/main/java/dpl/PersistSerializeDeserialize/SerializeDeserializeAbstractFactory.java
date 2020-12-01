package dpl.PersistSerializeDeserialize;

public class SerializeDeserializeAbstractFactory implements ISerializeDeserializeAbstractFactory {

	@Override
	public CoachSerializationDeserialization CoachSerializationDeserialization() {
		return new CoachSerializationDeserialization();
	}

	@Override
	public StandingSerializationDeserialization StandingSerializationDeserialization() {
		return new StandingSerializationDeserialization();
	}

	@Override
	public GameplayConfigSerializationDeserialization GameplayConfigSerializationDeserialization() {
		return new GameplayConfigSerializationDeserialization();
	}

	@Override
	public LeagueSerializationDeserialization LeagueSerializationDeserialization() {
		return new LeagueSerializationDeserialization();
	}

	@Override
	public ManagerSerializationDeserialization ManagerSerializationDeserialization() {
		return new ManagerSerializationDeserialization();
	}

	@Override
	public RetiredPlayerSerializationDeserialization RetiredPlayerSerializationDeserialization() {
		return new RetiredPlayerSerializationDeserialization();
	}

}
