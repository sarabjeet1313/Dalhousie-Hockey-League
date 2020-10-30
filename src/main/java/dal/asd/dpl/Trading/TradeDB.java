package dal.asd.dpl.Trading;

import java.util.List;

public class TradeDB implements ITradeDB{


    @Override
    public List<String> getEligibleTeamName(int lossPoint) {
        return null;
    }

    @Override
    public int getLossPoint() {
        return 0;
    }

    @Override
    public int getMaxPlayersPerTrade() {
        return 0;
    }

    @Override
    public double getRandomTradeOfferChance() {
        return 0;
    }

    @Override
    public double getRandomTradeAcceptChance() {
        return 0;
    }

    @Override
    public String getUserteamName() {
        return null;
    }
}
