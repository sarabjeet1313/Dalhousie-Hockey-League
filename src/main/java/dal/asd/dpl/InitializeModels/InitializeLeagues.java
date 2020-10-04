package dal.asd.dpl.InitializeModels;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dal.asd.dpl.Parser.CmdParseJSON;
import dal.asd.dpl.teammanagement.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InitializeLeagues {
    private static CmdParseJSON parser;
    private List<Conferences> ConferenceList;
    private List<Divisions> DivisionList;
    private Leagues League;
    private List<Players> PlayerList;
    private List<Teams> TeamList;

    public InitializeLeagues(String filePath) {
        parser = new CmdParseJSON(filePath);
        PlayerList = new ArrayList<Players>();
        TeamList = new ArrayList<Teams>();
        DivisionList = new ArrayList<Divisions>();
        ConferenceList = new ArrayList<Conferences>();


        String LeagueName = parser.parse("leagueName");
        League = new Leagues(LeagueName, null, null);

        // Ileague lg = new LeagueDataDB();
       //  bool check = League.isValidLeagueName(LeagueName, lg);


        JsonArray conferences = parser.parseList("conferences");
        Iterator<JsonElement> conferenceList = conferences.iterator();

        while(conferenceList.hasNext()) {
            JsonObject conference = conferenceList.next().getAsJsonObject();
            String ConferenceName = conference.get("conferenceName").toString();

            JsonArray divisions = conference.get("divisions").getAsJsonArray();
            Iterator<JsonElement> divisionList = divisions.iterator();

            while(divisionList.hasNext()) {
                JsonObject division =  divisionList.next().getAsJsonObject();
                String DivisionName = division.get("divisionName").toString();

                JsonArray teams = division.get("teams").getAsJsonArray();
                Iterator<JsonElement> teamList = teams.iterator();

                while(teamList.hasNext()) {
                    JsonObject team = teamList.next().getAsJsonObject();

                    String teamName = team.get("teamName").toString();
                    String genManager = team.get("generalManager").toString();
                    String headCoach = team.get("headCoach").toString();

                    JsonArray players = team.get("players").getAsJsonArray();
                    Iterator<JsonElement> playerList = players.iterator();

                    while(playerList.hasNext()){
                        JsonObject player = playerList.next().getAsJsonObject();

                        String name = player.get("playerName").toString();
                        String position = player.get("position").toString();
                        Boolean captain = player.get("captain").getAsBoolean();

                        PlayerList.add(new Players(name, position, captain));
                    }

                    TeamList.add(new Teams(teamName, genManager, headCoach, PlayerList));
                }

                DivisionList.add(new Divisions(DivisionName, TeamList));
            }

            ConferenceList.add(new Conferences(ConferenceName, DivisionList));
        }
        List<Players> freeAgents =null;
        League = new Leagues(LeagueName, ConferenceList, freeAgents);

        // TODO
        // send the league object to Praneeth.
        // initialize the genManager and headCoach

        // DEBUG

        System.out.println(League.getLeagueName() + " : " + ConferenceList.get(0).getConferenceName() + " : " +
                           DivisionList.get(0).getDivisionName() + " : " + TeamList.get(0).getGeneralManager() +
                           " : " + PlayerList.get(0).getPlayerName());
    }

}
