package dal.asd.dpl.InitializeModels;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dal.asd.dpl.Database.LeagueDataDB;
import dal.asd.dpl.Parser.CmdParseJSON;
import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Players;
import dal.asd.dpl.TeamManagement.Teams;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

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
    private Leagues league;
    private List<Players> playerList;
    private List<Players> freeAgents;
    private List<Teams> teamList;

    public InitializeLeagues(String filePath, ILeague leagueDb, IUserOutput output, IUserInput input) {
        this.filePath = filePath;
        this.leagueDb = leagueDb;
        this.input = input;
        this.output = output;
    }

    public boolean isEmptyString(String valueToCheck) {
        if(valueToCheck.isEmpty() || valueToCheck == null) {
            return true;
        }
        else
            return false;
    }

    public String truncateString(String inputString) {
        return inputString.replace("\"", "");
    }

    public Leagues parseAndInitializeModels() {
        parser = new CmdParseJSON(this.filePath);
        playerList = new ArrayList<Players>();
        teamList = new ArrayList<Teams>();
        divisionList = new ArrayList<Divisions>();
        conferenceList = new ArrayList<Conferences>();
        freeAgents = new ArrayList<Players>();

        String leagueName = parser.parse("leagueName");
        if(isEmptyString(leagueName)) {
            output.setOutput("Please enter League name. Null values are not accepted.");
            output.sendOutput();
            return null;
        }
        if(leagueName == "Error"){
            return null;
        }
        leagueName = truncateString(leagueName);

        league = new Leagues(leagueName, null, null);
        boolean check = league.isValidLeagueName(leagueName, leagueDb);
        if(!check) {
            output.setOutput("Please enter valid League name.");
            output.sendOutput();
            return null;
        }

        JsonArray conferences = parser.parseList("conferences");
        Iterator<JsonElement> conferenceListElement = conferences.iterator();

        while(conferenceListElement.hasNext()) {
            JsonObject conference = conferenceListElement.next().getAsJsonObject();
            String conferenceName = conference.get("conferenceName").toString();

            conferenceName = truncateString(conferenceName);
            if(isEmptyString(conferenceName)){
                output.setOutput("Please enter Conference name. Null values are not accepted.");
                output.sendOutput();
                return null;
            }

            JsonArray divisions = conference.get("divisions").getAsJsonArray();
            Iterator<JsonElement> divisionListElement = divisions.iterator();

            while(divisionListElement.hasNext()) {
                JsonObject division =  divisionListElement.next().getAsJsonObject();
                String divisionName = division.get("divisionName").toString();

                divisionName = truncateString(divisionName);
                if(isEmptyString(divisionName)){
                    output.setOutput("Please enter Division name. Null values are not accepted.");
                    output.sendOutput();
                    return null;
                }

                JsonArray teams = division.get("teams").getAsJsonArray();
                Iterator<JsonElement> teamListElement = teams.iterator();

                while(teamListElement.hasNext()) {
                    JsonObject team = teamListElement.next().getAsJsonObject();

                    String teamName = team.get("teamName").toString();
                    teamName = truncateString(teamName);
                    if(isEmptyString(teamName)){
                        output.setOutput("Please enter Team name. Null values are not accepted.");
                        output.sendOutput();
                        return null;
                    }

                    String genManager = team.get("generalManager").toString();
                    genManager = truncateString(genManager);
                    if(isEmptyString(genManager)){
                        output.setOutput("Please enter General Manager name. Null values are not accepted.");
                        output.sendOutput();
                        return null;
                    }

                    String headCoach = team.get("headCoach").toString();
                    headCoach = truncateString(headCoach);
                    if(isEmptyString(headCoach)){
                        output.setOutput("Please enter Head Coach name. Null values are not accepted.");
                        output.sendOutput();
                        return null;
                    }

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
                        playerName = truncateString(playerName);
                        if(isEmptyString(playerName)){
                            output.setOutput("Please enter Player name. Player:" + count + " name is empty.");
                            output.sendOutput();
                            return null;
                        }

                        String position = player.get("position").toString();
                        position = truncateString(position);
                        if(isEmptyString(position)){
                            output.setOutput("Please enter player:" + count + " position. Null values are not accepted.");
                            output.sendOutput();
                            return null;
                        }

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

                divisionList.add(new Divisions(divisionName, teamList));
            }

            conferenceList.add(new Conferences(conferenceName, divisionList));
        }

        JsonArray freeAgentsArray = parser.parseList("freeAgents");
        Iterator<JsonElement> freeAgentElement = freeAgentsArray.iterator();

        int count = 0;
        while(freeAgentElement.hasNext()) {
            count++;
            JsonObject freeAgentObj = freeAgentElement.next().getAsJsonObject();

            String agentName = freeAgentObj.get("playerName").toString();
            agentName = truncateString(agentName);
            if(isEmptyString(agentName)){
                output.setOutput("Please enter Free Agent:" + count + " name.");
                output.sendOutput();
                return null;
            }

            String position = freeAgentObj.get("position").toString();
            position = truncateString(position);
            if(isEmptyString(position)){
                output.setOutput("Please enter Free Agent:" + count + " position.");
                output.sendOutput();
                return null;
            }

            if(!(position.contains("forward") || position.contains("goalie") || position.contains("defense"))) {
                output.setOutput("Free Agent:" + count + " position must be either 'goalie', 'forward, or 'defense'.");
                output.sendOutput();
                return null;
            }

            Boolean captain = freeAgentObj.get("captain").getAsBoolean();
            if(captain) {
                output.setOutput("A free agent:" + count + " cannot be a captain.");
                output.sendOutput();
                return null;
            }
            freeAgents.add(new Players(agentName, position, captain));
        }

        league = new Leagues(leagueName, conferenceList, freeAgents);
        return league;


    }

}
