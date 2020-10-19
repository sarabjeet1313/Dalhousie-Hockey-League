package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NewsSubscriber implements ITrade{

    @Override
    public void updateTrade(String fromTeam, String toTeam, String[][] playersTraded) {
        Trade from = createTrade(fromTeam, playersTraded[0]);
        Trade to = createTrade(toTeam, playersTraded[1]);
        Trades trades = new Trades(from, to);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tradesJson = gson.toJson(trades);
        System.out.println(tradesJson);
    }

    private Trade createTrade(String team, String[] players) {
        Trade trade = new Trade();
        trade.setTeam(team);
        for(String player: players)
            trade.addPlayer(player);
        return trade;
    }

}

class Trade{
    private String team;
    private List<String> players = new ArrayList<String>();

    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    public List<String> getPlayers() {
        return players;
    }
    public void addPlayer(String player) {
        this.players.add(player);
    }
}

class Trades {
    private Trade from;
    private Trade to;

    public Trades(Trade from, Trade to) {
        super();
        this.from = from;
        this.to = to;
    }

    public Trade getFrom() {
        return from;
    }
    public void setFrom(Trade from) {
        this.from = from;
    }
    public Trade getTo() {
        return to;
    }
    public void setTo(Trade to) {
        this.to = to;
    }

}
