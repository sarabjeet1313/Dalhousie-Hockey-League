package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.sql.SQLException;
import java.util.List;

public interface ITradePersistence {

    public List<String> getEligibleTeamName(int lossPoints) throws SQLException;

    public boolean resetTradeLossPoint(String teamName) throws SQLException;
}
