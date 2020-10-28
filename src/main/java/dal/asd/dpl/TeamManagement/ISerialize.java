package dal.asd.dpl.TeamManagement;

public interface ISerialize {
	public  boolean serializeLeagueModel(League league);
	public League deSerializeLeagueModel(String filename);
}
