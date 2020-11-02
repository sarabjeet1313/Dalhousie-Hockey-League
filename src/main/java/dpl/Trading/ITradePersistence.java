package dpl.Trading;

import java.util.List;

public interface ITradePersistence {

    public List<String> getEligibleTeamName(int lossPoints);
    public boolean resetTradeLossPoint(String teamName);
}
