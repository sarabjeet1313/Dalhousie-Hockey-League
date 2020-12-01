package dpl.LeagueManagement.TrophySystem;

import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.*;

public class TrophyHistory {
	private static Map<Integer, List<Map<String, String>>> trophyList;
	private static TrophyHistory instance;
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	private TrophyHistory() {
		trophyList = new HashMap<>();
	}

	public static TrophyHistory getInstance() {
		if (instance == null) {
			instance = new TrophyHistory();
		}
		return instance;
	}

	public void addTrophy(int year, String award, String awardedTo) {
		Map<String, String> trophy = new HashMap<>();
		trophy.put(award, awardedTo);
		List<Map<String, String>> list = (trophyList.containsKey(year)) ? trophyList.get(year) : new ArrayList<>();
		list.add(trophy);
		trophyList.put(year, list);
	}

	public Map<Integer, List<Map<String, String>>> getTrophyHistory() {
		Map<Integer, List<Map<String, String>>> history = new HashMap<>();
		Set<Integer> keys = trophyList.keySet();
		for (Integer year : keys) {
			List<Map<String, String>> list = new ArrayList<>();
			for (Map<String, String> yearlyTrophies : trophyList.get(year)) {
				Map<String, String> trophy = new HashMap<>();
				for (String trophyName : yearlyTrophies.keySet()) {
					trophy.put(trophyName, yearlyTrophies.get(trophyName));
				}
				list.add(trophy);
			}
			history.put(year, list);
		}
		return history;
	}

	public void displayTrophyHistory() {
		List<Integer> keys = new ArrayList<>(trophyList.keySet());
		Collections.sort(keys);
		String yearNum;
		Collections.reverse(keys);
		for (Integer year : keys) {
			if (year == 2) {
				yearNum = TrophySystemConstants.YEAR_TWENTY.toString();
			} else {
				yearNum = TrophySystemConstants.YEAR_TEN.toString();
			}
			output.setOutput(TrophySystemConstants.NEXT_LINE.toString());
			output.sendOutput();
			output.setOutput(TrophySystemConstants.TROPHY_HISTORY.toString());
			output.sendOutput();
			output.setOutput(TrophySystemConstants.YEAR.toString() + yearNum);
			output.sendOutput();
			output.setOutput(TrophySystemConstants.LINE.toString());
			output.sendOutput();
			for (Map<String, String> yearlyTrophies : trophyList.get(year)) {
				for (String trophyName : yearlyTrophies.keySet()) {
					output.setOutput(trophyName + TrophySystemConstants.ARROW.toString() + yearlyTrophies.get(trophyName));
					output.sendOutput();
				}
			}
			output.setOutput(TrophySystemConstants.NEXT_LINE.toString());
			output.sendOutput();
		}
	}
}
