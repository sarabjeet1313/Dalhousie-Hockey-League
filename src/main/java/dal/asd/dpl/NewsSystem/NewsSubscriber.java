package dal.asd.dpl.NewsSystem;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NewsSubscriber implements ITrade, IGamesPlayed, IInjuries, IFreeAgency, IRetirement{

    private static final String WINNER = "winner";
    private static final String LOSER = "loser";
    private static final String DATE = "date";
    private static final String PLAYER = "player";
    private static final String PLAYERS = "players";
    private static final String DAYS_INJURED = "daysInjured";
    private static final String AGE = "age";
    private static final String TEAM = "team";
    private static final String FROM = "from";
    private static final String TO = "to";

    @Override
    public void updateTrade(String fromTeam, String toTeam, String[][] playersTraded) {
        Map<String, Map<String, Object>> trades = new LinkedHashMap<String, Map<String, Object>>(2);
        trades.put(FROM, createTrade(fromTeam, playersTraded[0]));
        trades.put(TO, createTrade(toTeam, playersTraded[1]));

        System.out.println(convertToJsonString(trades));
    }

    @Override
    public void updateGamesPlayed(String winner, String loser, String datePlayed) {
        Map<String, String> gamePlayed = new LinkedHashMap<String, String>(3);
        gamePlayed.put(WINNER, winner);
        gamePlayed.put(LOSER, loser);
        gamePlayed.put(DATE, datePlayed);

        System.out.println(convertToJsonString(gamePlayed));
    }

    @Override
    public void updateInjuries(String player, int daysInjured) {
        Map<String, Object> injury = new LinkedHashMap<String, Object>(2);
        injury.put(PLAYER, player);
        injury.put(DAYS_INJURED, daysInjured);

        System.out.println(convertToJsonString(injury));
    }

    @Override
    public void updateFreeAgency(String player, String hiredOrReleased) {
        Map<String, String> playersHiredOrRelease =  new LinkedHashMap<String, String>(1);
        playersHiredOrRelease.put(hiredOrReleased, player);

        System.out.println(convertToJsonString(playersHiredOrRelease));
    }

    @Override
    public void updateRetirement(String player, int age) {
        Map<String, Object> retirement = new LinkedHashMap<String, Object>(2);
        retirement.put(PLAYER, player);
        retirement.put(AGE, age);

        System.out.println(convertToJsonString(retirement));

    }

    private Map<String, Object> createTrade(String team, String[] players) {
        Map<String, Object> trade = new LinkedHashMap<String, Object>(2);
        trade.put(TEAM, team);
        trade.put(PLAYERS, Arrays.asList(players));
        return trade;
    }

    private String convertToJsonString(Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tradesJson = gson.toJson(object);

        return tradesJson;
    }

}