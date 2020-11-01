package dal.asd.dpl.NewsSystem;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.NewsSystemUtil;

public class NewsSubscriber implements ITradeInfo, IGamesPlayedInfo, IInjuryInfo, IFreeAgencyInfo, IRetirementInfo {
    private IUserOutput output = new CmdUserOutput();

    @Override
    public void updateTrade(String fromTeam, String toTeam, String[][] playersTraded) {
        Map<String, Map<String, Object>> trades = new LinkedHashMap<>(2);
        trades.put(NewsSystemUtil.FROM.toString(), createTrade(fromTeam, playersTraded[0]));
        trades.put(NewsSystemUtil.TO.toString(), createTrade(toTeam, playersTraded[1]));
        output.setOutput(convertToJsonString(trades));
        output.sendOutput();
    }

    @Override
    public void updateGamesPlayed(String winner, String loser, String datePlayed) {
        Map<String, String> gamePlayed = new LinkedHashMap<>(3);
        gamePlayed.put(NewsSystemUtil.WINNER.toString(), winner);
        gamePlayed.put(NewsSystemUtil.LOSER.toString(), loser);
        gamePlayed.put(NewsSystemUtil.DATE.toString(), datePlayed);
        output.setOutput(convertToJsonString(gamePlayed));
        output.sendOutput();
    }

    @Override
    public void updateInjuries(String player, int daysInjured) {
        Map<String, Object> injury = new LinkedHashMap<>(2);
        injury.put(NewsSystemUtil.PLAYER.toString(), player);
        injury.put(NewsSystemUtil.DAYS_INJURED.toString(), daysInjured);
        output.setOutput(convertToJsonString(injury));
        output.sendOutput();
    }

    @Override
    public void updateFreeAgency(String player, String hiredOrReleased) {
        Map<String, String> playersHiredOrRelease = new LinkedHashMap<>(1);
        playersHiredOrRelease.put(hiredOrReleased, player);
        output.setOutput(convertToJsonString(playersHiredOrRelease));
        output.sendOutput();
    }

    @Override
    public void updateRetirement(String player, int age) {
        Map<String, Object> retirement = new LinkedHashMap<>(2);
        retirement.put(NewsSystemUtil.PLAYER.toString(), player);
        retirement.put(NewsSystemUtil.AGE.toString(), age);
        output.setOutput(convertToJsonString(retirement));
        output.sendOutput();
    }

    private Map<String, Object> createTrade(String team, String[] players) {
        Map<String, Object> trade = new LinkedHashMap<>(2);
        trade.put(NewsSystemUtil.TEAM.toString(), team);
        trade.put(NewsSystemUtil.PLAYERS.toString(), Arrays.asList(players));
        return trade;
    }

    private String convertToJsonString(Object gsonName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tradesJson = gson.toJson(gsonName);
        return tradesJson;
    }

}