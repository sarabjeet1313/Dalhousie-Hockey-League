package dpl.Trading;

import java.util.List;

public interface ITradePersistence {

    public List<String> getEligibleTeamName(int lossPoints);
    public int getLossPoint();
    public int getMaxPlayersPerTrade();
    public double getRandomTradeOfferChance();
    public double getRandomTradeAcceptChance();
    public String getUserteamName();

}
