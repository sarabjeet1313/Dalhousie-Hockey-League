package dal.asd.dpl.Trading;

import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Teams;

import java.util.List;

public interface ITrade {

    public void getTeams();
    public boolean isEligibleToTrade(String teamName);
    public List<Player> generateTradeOffer(String teamName);

}
