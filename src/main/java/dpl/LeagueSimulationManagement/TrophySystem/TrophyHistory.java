package dpl.LeagueSimulationManagement.TrophySystem;

import java.util.*;

public class TrophyHistory {
    private static Map<Integer, List<Map<String, String>>> trophyList;
    private static TrophyHistory instance;

    private TrophyHistory() {
        trophyList = new HashMap<>();
    }

    public static TrophyHistory getInstance() {
        if (instance == null) {
            instance = new TrophyHistory();
        }
        return instance;
    }

    public void addTrophy(int year, String award, String awardedTo){
        Map<String, String> trophy = new HashMap<>();
        trophy.put(award, awardedTo);
        List<Map<String, String>> list = (trophyList.containsKey(year)) ? trophyList.get(year) : new ArrayList<>();
        list.add(trophy);
        trophyList.put(year, list);
    }

    public Map<Integer, List<Map<String, String>>> getTrophyHistory(){
        Map<Integer, List<Map<String, String>>> history = new HashMap<>();
        Set<Integer> keys = trophyList.keySet();
        for(Integer year: keys){
            List<Map<String, String>> list = new ArrayList<>();
            for(Map<String, String> yearlyTrophies: trophyList.get(year)){
                Map<String, String> trophy = new HashMap<>();
                for(String trophyName: yearlyTrophies.keySet()){
                    trophy.put(trophyName, yearlyTrophies.get(trophyName));
                }
                list.add(trophy);
            }
            history.put(year, list);
        }
        return history;
    }

    public void displayTrophyHistory(){
        List<Integer> keys = new ArrayList<>(trophyList.keySet());
        Collections.sort(keys);
        Collections.reverse(keys);
        for(Integer year: keys){
            System.out.println("Year " + year);
            System.out.println("==========");
            for(Map<String, String> yearlyTrophies: trophyList.get(year)){
                for(String trophyName: yearlyTrophies.keySet()){
                    System.out.println(trophyName + "===>" + yearlyTrophies.get(trophyName));
                }
            }
        }
    }

}
