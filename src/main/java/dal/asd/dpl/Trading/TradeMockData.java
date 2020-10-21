package dal.asd.dpl.Trading;

import dal.asd.dpl.TeamManagement.Player;

import java.util.List;

public class TradeMockData implements ITrade {
    //TODO Tsst
    //TODO Create mock
    //TODO override methods

    @Override
    public void getTeams() {

    }

    @Override
    public boolean isEligibleToTrade(String teamName) {
        return false;
    }

    @Override
    public List<Player> generateTradeOffer(String teamName) {
        return null;
    }

}
