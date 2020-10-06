package dal.asd.dpl.InitializeModels;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dal.asd.dpl.Parser.CmdParseJSON;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.teammanagement.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InitializeLeagues {
    private static CmdParseJSON parser;
    private static String filePath;
    private static ILeague leagueDb;
    private static IUserOutput output;
    private static IUserInput input;
    private List<Conferences> conferenceList;
    private List<Divisions> divisionList;
    private Leagues League;
    private List<Players> playerList;
    private List<Players> freeAgents;
    private List<Teams> teamList;

    public InitializeLeagues(String filePath, ILeague leagueDb, IUserOutput output, IUserInput input) {
        this.filePath = filePath;
        this.leagueDb = leagueDb;
        this.input = input;
        this.output = output;
    }

    private boolean isEmptyString(String valueToCheck) {
        if(valueToCheck == "" || valueToCheck == null) {
            return true;
        }
        else
            return false;
    }

    private String truncateString(String inputString) {
        return inputString.replace("\"", "");
    }

    public Leagues parseAndInitializeModels() {
        parser = new CmdParseJSON(this.filePath);
        playerList = new ArrayList<Players>();
        teamList = new ArrayList<Teams>();
        divisionList = new ArrayList<Divisions>();
        conferenceList = new ArrayList<Conferences>();
        freeAgents = new ArrayList<Players>();



        String LeagueName = parser.parse("leagueName");
        if(isEmptyString(LeagueName)) {
            output.setOutput("Please enter League name. Null values are not accepted.");
            output.sendOutput();
            return null;
        }
        if(LeagueName == "Error"){
            return null;
        }

        LeagueName = truncateString(LeagueName);

        League = new Leagues(LeagueName, null, null);
        boolean check = League.isValidLeagueName(LeagueName, leagueDb);
        if(check == false) {
            output.setOutput("Please enter valid League name.");
            output.sendOutput();
            return null;
        }

        JsonArray conferences = parser.parseList("conferences");
        Iterator<JsonElement> conference_List = conferences.iterator();

        while(conference_List.hasNext()) {
            JsonObject conference = conference_List.next().getAsJsonObject();
            String ConferenceName = conference.get("conferenceName").toString();
            if(isEmptyString(ConferenceName)){
                output.setOutput("Please enter Conference name. Null values are not accepted.");
                output.sendOutput();
                return null;
            }
            ConferenceName = truncateString(ConferenceName);

            JsonArray divisions = conference.get("divisions").getAsJsonArray();
            Iterator<JsonElement> division_List = divisions.iterator();

            while(division_List.hasNext()) {
                JsonObject division =  division_List.next().getAsJsonObject();
                String DivisionName = division.get("divisionName").toString();

                if(isEmptyString(DivisionName)){
                    output.setOutput("Please enter Division name. Null values are not accepted.");
                    output.sendOutput();
                    return null;
                }
                DivisionName = truncateString(DivisionName);

                JsonArray teams = division.get("teams").getAsJsonArray();
                Iterator<JsonElement> team_List = teams.iterator();

                while(team_List.hasNext()) {
                    JsonObject team = team_List.next().getAsJsonObject();

                    String teamName = team.get("teamName").toString();
                    if(isEmptyString(teamName)){
                        output.setOutput("Please enter Team name. Null values are not accepted.");
                        output.sendOutput();
                        return null;
                    }
                    teamName = truncateString(teamName);

                    String genManager = team.get("generalManager").toString();
                    if(isEmptyString(genManager)){
                        output.setOutput("Please enter General Manager name. Null values are not accepted.");
                        output.sendOutput();
                        return null;
                    }
                    genManager = truncateString(genManager);

                    String headCoach = team.get("headCoach").toString();
                    if(isEmptyString(headCoach)){
                        output.setOutput("Please enter Head Coach name. Null values are not accepted.");
                        output.sendOutput();
                        return null;
                    }
                    headCoach = truncateString(headCoach);

                    JsonArray players = team.get("players").getAsJsonArray();
                    if(players.size() > 20){
                        output.setOutput("Team cannot have more than 20 players. Please correct the team size.");
                        output.sendOutput();
                        return null;
                    }
                    Iterator<JsonElement> player_List = players.iterator();

                    boolean isCaptainPositionOccupied = false;
                    int count = 0;
                    while(player_List.hasNext()){
                        count++;
                        JsonObject player = player_List.next().getAsJsonObject();

                        String playerName = player.get("playerName").toString();
                        if(isEmptyString(playerName)){
                            output.setOutput("Please enter Player name. Player:" + count + " name is empty.");
                            output.sendOutput();
                            return null;
                        }
                        playerName = truncateString(playerName);

                        String position = player.get("position").toString();
                        if(isEmptyString(position)){
                            output.setOutput("Please enter player:" + count + " position. Null values are not accepted.");
                            output.sendOutput();
                            return null;
                        }
                        position = truncateString(position);

                        if(!(position.contains("forward") || position.contains("goalie") || position.contains("defense"))) {
                            output.setOutput("Player:" + count + " position must be either 'goalie', 'forward', or 'defense'.");
                            output.sendOutput();
                            return null;
                        }

                        Boolean captain = player.get("captain").getAsBoolean();
                        if(captain && isCaptainPositionOccupied) {
                            output.setOutput("A team can only have one captain.");
                            output.sendOutput();
                            return null;
                        }
                        if(captain) {
                            isCaptainPositionOccupied = captain;
                        }

                        playerList.add(new Players(playerName, position, captain));
                    }

                    teamList.add(new Teams(teamName, genManager, headCoach, playerList));
                }

                divisionList.add(new Divisions(DivisionName, teamList));
            }

            conferenceList.add(new Conferences(ConferenceName, divisionList));
        }

        JsonArray freeAgentsArray = parser.parseList("freeAgents");
        Iterator<JsonElement> free_agents = freeAgentsArray.iterator();

        int count = 0;
        while(free_agents.hasNext()) {
            count++;
            JsonObject free_agent = free_agents.next().getAsJsonObject();

            String agentName = free_agent.get("playerName").toString();
            if(isEmptyString(agentName)){
                output.setOutput("Please enter Free Agent:" + count + " name.");
                output.sendOutput();
                return null;
            }
            agentName = truncateString(agentName);

            String position = free_agent.get("position").toString();
            if(isEmptyString(position)){
                output.setOutput("Please enter Free Agent:" + count + " position.");
                output.sendOutput();
                return null;
            }
            position = truncateString(position);

            if(!(position.contains("forward") || position.contains("goalie") || position.contains("defense"))) {
                output.setOutput("Free Agent:" + count + " position must be either 'goalie', 'forward, or 'defense'.");
                output.sendOutput();
                return null;
            }

            Boolean captain = free_agent.get("captain").getAsBoolean();
            if(captain) {
                output.setOutput("A free agent:" + count + " cannot be a captain.");
                output.sendOutput();
                return null;
            }
            freeAgents.add(new Players(agentName, position, captain));
        }

        League = new Leagues(LeagueName, conferenceList, freeAgents);
        return League;

        // DEBUG prints
        /*
        *
        * System.out.println(League.getLeagueName() + " : " + conferenceList.get(0).getConferenceName() + " : " +
        * divisionList.get(0).getDivisionName() + " : " + teamList.get(0).getGeneralManager() +
        * " : " + playerList.get(0).getPlayerName());
        *
        */

    }

}
