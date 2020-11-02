package dal.asd.dpl.Trading;

import java.util.List;

public interface ITradePersistance {

    public List<String> getEligibleTeamName(int lossPoints);
    public int getLossPoint();
    public int getMaxPlayersPerTrade();
    public double getRandomTradeOfferChance();
    public double getRandomTradeAcceptChance();
    public String getUserteamName();

}
