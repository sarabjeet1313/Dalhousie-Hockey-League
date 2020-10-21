package dal.asd.dpl.Trading;

import dal.asd.dpl.TeamManagement.Player;

import java.util.List;

public class Trade {
    private String tradeOfferTeam;
    private String tradeRequestedTeam;
    private List<Player> playerListOfferTeam;
    private List<Player> playerListRequestedTeam;

    public Trade(String tradeOfferTeam, List<Player> playerListOfferTeam, String tradeRequestedTeam, List<Player> playerListRequestedTeam){
        this.tradeOfferTeam= tradeOfferTeam;
        this.tradeRequestedTeam = tradeRequestedTeam;
        this.playerListOfferTeam = playerListOfferTeam;
        this.playerListRequestedTeam = playerListRequestedTeam;
    }

    public String getTradeOfferTeam(){
        return this.tradeOfferTeam;
    }

    public void setTradeOfferTeam(String tradeOfferTeam) {
        this.tradeOfferTeam = tradeOfferTeam;
    }

    public String getTradeRequestedTeam() {
        return tradeRequestedTeam;
    }

    public void setTradeRequestedTeam(String tradeRequestedTeam) {
        this.tradeRequestedTeam = tradeRequestedTeam;
    }

    public List<Player> getPlayerListOfferTeam() {
        return playerListOfferTeam;
    }

    public void setPlayerListOfferTeam(List<Player> playerListOfferTeam) {
        this.playerListOfferTeam = playerListOfferTeam;
    }

    public List<Player> getPlayerListRequestedTeam() {
        return playerListRequestedTeam;
    }

    public void setPlayerListRequestedTeam(List<Player> playerListRequestedTeam) {
        this.playerListRequestedTeam = playerListRequestedTeam;
    }


}
