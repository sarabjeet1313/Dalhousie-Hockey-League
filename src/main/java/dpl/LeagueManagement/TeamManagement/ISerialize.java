package dpl.LeagueManagement.TeamManagement;

import java.io.IOException;

public interface ISerialize {

	public boolean serializeLeagueModel(League league) throws IOException;
}
