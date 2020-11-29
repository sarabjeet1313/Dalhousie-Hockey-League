package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.UserOutputAbstractFactory;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrophyHistory {
    private static final Logger log = Logger.getLogger(TrophyHistory.class.getName());
    private static Map<Integer, List<Map<String, String>>> trophyList;
    private static TrophyHistory instance;
    private IUserOutput output;

    private TrophyHistory() {
        trophyList = new HashMap<>();
        output = (new UserOutputAbstractFactory()).CmdUserOutput();
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
        Collections.reverse(keys);
        for (Integer year : keys) {
            output.setOutput(TrophySystemConstants.YEAR.toString() + year);
            output.sendOutput();
            output.setOutput(TrophySystemConstants.LINE.toString());
            output.sendOutput();
            for (Map<String, String> yearlyTrophies : trophyList.get(year)) {
                for (String trophyName : yearlyTrophies.keySet()) {
                    output.setOutput(trophyName + TrophySystemConstants.ARROW.toString() + yearlyTrophies.get(trophyName));
                    output.sendOutput();
                }
            }
        }
    }
}
