package dal.asd.dpl.Trading;

import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Teams;

import java.util.List;

public class Trade implements ITrade  {
    private String team1;
    private String team2;


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
