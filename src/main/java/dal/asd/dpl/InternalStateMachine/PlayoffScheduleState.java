package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Teams;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PlayoffScheduleState implements ISchedule {

    private Calendar calendar;
    private IUserOutput output;
    private Standings standings;
    private String currentDay;
    private String firstDay;
    private String lastDay;
    private int seasonType;
    private int maxMatches = 105; // for NHL playoffs this is the upper limit of matches.
    private int matchesPerDay;
    private Map<String, List<String>> conferenceTeamList;
    private Map< String /*Date*/, List<Map<String, String> /*Teams Competing*/>> finalSchedule;
   // public Map<Integer/*rounds in playoffs*/, List<String> /*Teams Competing*/> teamsToBeScheduled;
    private List<String> teamsToBeScheduled;
    private List<String> teamsScheduled;

    PlayoffScheduleState(IUserOutput output, Standings standings){
        this.calendar = Calendar.getInstance();
        this.output = output;
        this.standings = standings;
        this.seasonType = 1 /*Playoff Season*/;
        conferenceTeamList = new HashMap<>();
        finalSchedule = new HashMap<>();
        teamsToBeScheduled = new ArrayList<>();
        teamsScheduled = new ArrayList<>();
    }

    public int getSeasonType(){
        return this.seasonType;
    }

    public void setSeasonType(int seasonType) {
        this.seasonType = seasonType;
    }

    public String getFirstDay(){
        return this.firstDay;
    }

    public void setFirstDay(String firstDay){
        this.firstDay = firstDay;
    }

    public String getLastDay() {
        return this.lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public String getCurrentDay(){
        return currentDay;
    }

    public void setCurrentDay(String currentDay){
        this.currentDay = currentDay;
    }

    public void setTeamsToBeScheduled(List<String> teamsToBeScheduled) {
        this.teamsToBeScheduled = teamsToBeScheduled;
    }

    public List<String> getTeamsToBeScheduled() {
        return this.teamsToBeScheduled;
    }

    public void setTeamsScheduled(List<String> teamsScheduled) {
        this.teamsScheduled = teamsScheduled;
    }

    public List<String> getTeamsScheduled() {
        return this.teamsScheduled;
    }

    public void generateSchedule(Leagues leagueToSimulate){
        setMatchesPerDay();
        populateInternalModel(leagueToSimulate);

        for (Map.Entry<String, List<String>> entry : conferenceTeamList.entrySet()) {
            List<String> teamsToCompete = entry.getValue();
            generateScheduleOnTheFly(teamsToCompete, this.currentDay);
        }
    }

    private void populateInternalModel(Leagues leagueToSimulate){
        if(leagueToSimulate == null) {
            return;
        }

        List<Conferences> conferenceList =  leagueToSimulate.getConferenceList();

        for(int index = 0; index < conferenceList.size(); index++) {
            List<Divisions> divisionList = conferenceList.get(index).getDivisionList();
            String conferenceName = conferenceList.get(index).getConferenceName();
            List<String> divisions = new ArrayList<String>();
            for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {

                List<String> teams = new ArrayList<String>();
//                String divisionName = divisionList.get(dIndex).getDivisionName();
//                teams = standings.getTopDivisionTeams(divisionName);

                List<Teams> teamList = divisionList.get(dIndex).getTeamList();


                for(int tIndex = 0; tIndex < teamList.size(); tIndex++) {
                    String teamName = teamList.get(tIndex).getTeamName();
                    teams.add(teamName);
                }

                if(conferenceTeamList.containsKey(conferenceName)) {
                    List<String> alreadyAddedTeams = conferenceTeamList.get(conferenceName);
                    alreadyAddedTeams.addAll(teams);
                    conferenceTeamList.put(conferenceName, alreadyAddedTeams);
                }
                else {
                    conferenceTeamList.put(conferenceName, teams);
                }

                teamsScheduled.addAll(teams);
//                if(teamsToBeScheduled.containsKey(1 /*round-1*/)) {
//                    List<String> teamsParticipating = teamsToBeScheduled.get(1);
//                    teamsParticipating.addAll(teams);
//                    teamsToBeScheduled.put(1, teamsParticipating);
//                }
//                else {
//                    teamsToBeScheduled.put(1, teams);
//                }
            }
        }
    }

    public void generateScheduleOnTheFly(List<String> teamsToCompete, String currentDay) {
        this.currentDay = currentDay;
         incrementCurrentDay();

         int totalTeams = teamsToCompete.size();
         for(int i=0; i < totalTeams/2 ; i++) {
             Map<String, String> teamsCompeting = new HashMap<String, String>();
             teamsCompeting.put(teamsToCompete.get(i), teamsToCompete.get(totalTeams-1-i));
             List<Map<String, String>> matchList = new ArrayList<>();
             matchList.add(teamsCompeting);
             this.finalSchedule.put(this.currentDay, matchList);

             incrementCurrentDay();
         }
    }

    private void setMatchesPerDay(){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date1 = myFormat.parse(firstDay);
            Date date2 = myFormat.parse(lastDay);
            long diff = date2.getTime() - date1.getTime();
            int totalDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            int matchCount = (this.maxMatches)/totalDays;
            if(matchCount < 1){
                matchesPerDay = 1;
            }
            else {
                matchesPerDay = matchCount + 1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean incrementCurrentDay(){

        if(currentDay.equals(lastDay)) {
            return false;
        }

        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                calendar.setTime(dateFormat.parse(currentDay));
            } catch (ParseException e) {
                output.setOutput("Exception while getting current date in Playoff Schedule state");
                output.sendOutput();
            }

            // add a day to current date if it is not last date for the season
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            currentDay = dateFormat.format(calendar.getTime());
            return true;
        }
    }

    public Map< String, List<Map<String, String>>> getFinalSchedule(){
        return this.finalSchedule;
    }

    public void setFinalSchedule(Map< String, List<Map<String, String>>> schedule) {
        this.finalSchedule = schedule;
    }

}

