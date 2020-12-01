package dpl.NewsSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

public class NewsSubscriber implements ITradeInfo, IGamesPlayedInfo, IInjuryInfo, IFreeAgencyInfo, IRetirementInfo {
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	public NewsSubscriber() {
		super();
	}

	@Override
	public void updateTrade(String fromTeam, String toTeam, ArrayList<String> fromTeamTrade, ArrayList<String> toTeamTrade) {
		Map<String, Map<String, Object>> trades = new LinkedHashMap<>(2);
		trades.put(NewsSystemConstants.FROM.toString(), createTrade(fromTeam, fromTeamTrade));
		trades.put(NewsSystemConstants.TO.toString(), createTrade(toTeam, toTeamTrade));
		output.setOutput(convertToJsonString(trades));
		output.sendOutput();
	}

	@Override
	public void updateGamesPlayed(String winner, String loser, String datePlayed) {
		Map<String, String> gamePlayed = new LinkedHashMap<>(3);
		gamePlayed.put(NewsSystemConstants.WINNER.toString(), winner);
		gamePlayed.put(NewsSystemConstants.LOSER.toString(), loser);
		gamePlayed.put(NewsSystemConstants.DATE.toString(), datePlayed);
		output.setOutput(convertToJsonString(gamePlayed));
		output.sendOutput();
	}

	@Override
	public void updateInjuries(String player, int daysInjured) {
		Map<String, Object> injury = new LinkedHashMap<>(2);
		injury.put(NewsSystemConstants.PLAYER.toString(), player);
		injury.put(NewsSystemConstants.DAYS_INJURED.toString(), daysInjured);
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
		retirement.put(NewsSystemConstants.PLAYER.toString(), player);
		retirement.put(NewsSystemConstants.AGE.toString(), age);
		output.setOutput(convertToJsonString(retirement));
		output.sendOutput();
	}

	private Map<String, Object> createTrade(String team, ArrayList<String> players) {
		String[] p = new String[players.size()];
		int i = 0;
		for (String s : players) {
			p[i] = s;
			i++;
		}
		i = 0;
		Map<String, Object> trade = new LinkedHashMap<>(2);
		trade.put(NewsSystemConstants.TEAM.toString(), team);
		trade.put(NewsSystemConstants.PLAYERS.toString(), Arrays.asList(p));
		return trade;
	}

	private String convertToJsonString(Object gsonName) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String tradesJson = gson.toJson(gsonName);
		return tradesJson;
	}

}