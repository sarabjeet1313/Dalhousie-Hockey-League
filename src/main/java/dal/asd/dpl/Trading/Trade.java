package dal.asd.dpl.Trading;

import dal.asd.dpl.NewsSystem.NewsSubscriber;
import dal.asd.dpl.NewsSystem.TradePublisher;
import dal.asd.dpl.TeamManagement.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

public class Trade implements ITrade {
    private String tradeOfferTeam;
    private String tradeRequestedTeam;
    private List<Player> playerListOfferTeam;
    private List<Player> playerListRequestedTeam;
    private ITradeDB tradeDB;

    public Trade(){

    }
    public Trade(ITradeDB tradeDB){
        this.tradeDB = tradeDB;
    }

    public Trade(String tradeOfferTeam, List<Player> playerListOfferTeam, String tradeRequestedTeam, List<Player> playerListRequestedTeam){
        this.tradeOfferTeam= tradeOfferTeam;
        this.tradeRequestedTeam = tradeRequestedTeam;
        this.playerListOfferTeam = playerListOfferTeam;
        this.playerListRequestedTeam = playerListRequestedTeam;
        TradePublisher.getInstance().subscribe(new NewsSubscriber());
    }

    public String getTradeOfferTeam(){
        return this.tradeOfferTeam;
    }

    public void setTradeOfferTeam(String tradeOfferTeam) {
        this.tradeOfferTeam = tradeOfferTeam;
    }

    public String getTradeRequestedTeam() {
        return tradeRequestedTeam;
    }

    public void setTradeRequestedTeam(String tradeRequestedTeam) {
        this.tradeRequestedTeam = tradeRequestedTeam;
    }

    public List<Player> getPlayerListOfferTeam() {
        return playerListOfferTeam;
    }

    public void setPlayerListOfferTeam(List<Player> playerListOfferTeam) {
        this.playerListOfferTeam = playerListOfferTeam;
    }

    public List<Player> getPlayerListRequestedTeam() {
        return playerListRequestedTeam;
    }

    public void setPlayerListRequestedTeam(List<Player> playerListRequestedTeam) {
        this.playerListRequestedTeam = playerListRequestedTeam;
    }

    public boolean checkRandOfferChance(double randOfferChance){
        if(Math.random() < randOfferChance ){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean matchPosition(String s1, String s2){
        return s1.equals(s2);
    }
    public boolean matchString(String s1, String s2){
        return s1.equals(s2);
    }
    public boolean sameTeam(String s1, String s2){
        return s1.equals(s2);
    }

    public  HashMap<Integer,Double> sortByStrength(HashMap<Integer, Double> hm, boolean descending){
        List<Map.Entry<Integer,Double> > list = new LinkedList<Map.Entry<Integer,Double> >(hm.entrySet());

        if (descending){
            Collections.sort(list, new Comparator<Map.Entry<Integer,Double> >() {
                public int compare(Map.Entry<Integer,Double> o1,
                                   Map.Entry<Integer, Double> o2)
                {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });
        }
        else{
            Collections.sort(list, new Comparator<Map.Entry<Integer,Double> >() {
                public int compare(Map.Entry<Integer,Double> o1,
                                   Map.Entry<Integer, Double> o2)
                {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });
        }

        HashMap<Integer, Double> temp = new LinkedHashMap<Integer,Double>();
        for (Map.Entry<Integer,Double> mapTemp : list) {
            temp.put(mapTemp.getKey(), mapTemp.getValue());
        }
        return temp;

    }

    public List<Player> getPlayersOfSpecificType(String typeOfPlayer ,List<Player> playerList ){
        List<Player> p = new ArrayList<Player>();

        for (Player player : playerList) {

            if (matchPosition(typeOfPlayer, player.getPosition())) {
                p.add(player);
            }
        }
        return p;
    }

    public List<Player> getWeakestPlayers(int maxPlayers, String teamName, League league,
                                          ITeamInfo iTPInfoObject, IPlayerInfo iPInfoObject){
        List<Player> p = iTPInfoObject.getPlayersByTeam(teamName,league);
        List<Player> returnWeakestPlayerList = new ArrayList<Player>();
        double playerStrength;
        String weakestPlayerPosition;
        double[] minStrengthArray = new double[maxPlayers];
        int[] playerIndexArray = new int[maxPlayers];
        HashMap<Integer, Double> hmPlayerStrength = new HashMap<Integer, Double>();

        for (int i= 0 ; i< p.size(); i++){
            playerStrength = iPInfoObject.getPlayerStrength(p.get(i));
            hmPlayerStrength.put(i,playerStrength);
        }
        int x =0;
        Map<Integer,Double> sortedHm = sortByStrength(hmPlayerStrength,false);
        for (Map.Entry<Integer, Double> k : sortedHm.entrySet()) {
            playerIndexArray[x] = k.getKey();
            minStrengthArray[x] = k.getValue();
            x=x+1;
            if(x == maxPlayers){
                break;
            }
        }
        weakestPlayerPosition = p.get(playerIndexArray[0]).getPosition();
        for (int i = 0; i < playerIndexArray.length; i++){

            if(matchPosition(weakestPlayerPosition, p.get(playerIndexArray[i]).getPosition())){
                returnWeakestPlayerList.add(p.get(playerIndexArray[i]));
            }
            else if (i+1< minStrengthArray.length){
                if(minStrengthArray[i+1] > minStrengthArray[i]){
                    break;
                }
            }
            else{
                break;
            }
        }
        return returnWeakestPlayerList;
    }

    public List<Player> getStrongestPlayers(Trade t, List<String> allTeamNameList ,League league,
                                            ITeamInfo iTPInfoObject, IPlayerInfo iPInfoObject ){
        List<Player> offeredPlayerList = t.getPlayerListOfferTeam();
        List<Player> currentTeamPlayers;

        int totalPlayersNeeded = offeredPlayerList.size();
        double [] maxPlayerStrengthsArray = new double[totalPlayersNeeded];
        int [] currentPlayerIndexArray = new int[totalPlayersNeeded];
        double[] currentPlayerMaxStrength = new double[totalPlayersNeeded];
        List<Player> returnStrongPlayerList = new ArrayList<Player>();
        HashMap<Integer, Double> hmPlayerStrength = new HashMap<Integer, Double>();

        String requiredPlayerType = offeredPlayerList.get(0).getPosition();
        for(int i = 0; i < totalPlayersNeeded; i++){
            maxPlayerStrengthsArray[i] = iPInfoObject.getPlayerStrength(offeredPlayerList.get(i));
        }
        double offeredPlayersStrength = DoubleStream.of(maxPlayerStrengthsArray).sum();
        double requestedPlayerStrength;
        boolean isSame;
        for (int j = 0; j< allTeamNameList.size(); j++) {
            isSame = sameTeam(t.getTradeOfferTeam(), allTeamNameList.get(j));
            if (isSame == false) {
                // Here
                t.setTradeRequestedTeam(allTeamNameList.get(j));

                currentTeamPlayers = getPlayersOfSpecificType(requiredPlayerType, iTPInfoObject.getPlayersByTeam(t.getTradeRequestedTeam(), league));

                for (int i = 0; i < currentTeamPlayers.size(); i++) {
                    hmPlayerStrength.put(i, iPInfoObject.getPlayerStrength(currentTeamPlayers.get(i)));
                }
                Map<Integer, Double> sortedDesHm = sortByStrength(hmPlayerStrength, true);
                int g = 0;
                for (Map.Entry<Integer, Double> k : sortedDesHm.entrySet()) {
                    currentPlayerMaxStrength[g] = k.getValue();
                    currentPlayerIndexArray[g] = k.getKey();
                    g = g + 1;
                    if (g == totalPlayersNeeded) {
                        break;
                    }
                }

                requestedPlayerStrength = DoubleStream.of(currentPlayerMaxStrength).sum();
                if (requestedPlayerStrength > offeredPlayersStrength) {
                    offeredPlayersStrength = requestedPlayerStrength;
                    for (int h = 0; h < currentPlayerIndexArray.length; h++) {
                        returnStrongPlayerList.add(currentTeamPlayers.get(currentPlayerIndexArray[h]));
                    }
                }

            }
        }
        return returnStrongPlayerList;
    }

    public String[][] prepareToNotify(Trade trade){
        int totalPlayers;
        totalPlayers   = trade.getPlayerListOfferTeam().size();
        String [][] playersTraded = new String[totalPlayers][2];
        for(int i=0; i< playersTraded.length; i++ ){
            for (int j=0; j< playersTraded[i].length;j++){
                if(i==0){
                    for (Player p: trade.getPlayerListOfferTeam()){
                        playersTraded[i][j] = p.getPlayerName();
                    }
                }else{
                    for (Player p: trade.getPlayerListRequestedTeam()){
                        playersTraded[i][j] = p.getPlayerName();
                    }
                }
            }
        }
        return playersTraded;
    }

    @Override
    public League startTrade(League leagueObject) {
//        TradeDB tradeDB = new TradeDB();
        Trade trade = new Trade();
        List<String> eligibleTeamNameList = new ArrayList<String>();
        int maxPlayersPerTrade = tradeDB.getMaxPlayersPerTrade();
        int minLossPoints = tradeDB.getLossPoint();
        double randOfferChance = tradeDB.getRandomTradeOfferChance();
        double randAcceptChance = tradeDB.getRandomTradeAcceptChance();
        String userTeamName = tradeDB.getUserteamName();
        eligibleTeamNameList = tradeDB.getEligibleTeamName(minLossPoints);

        eligibleTeamNameList.remove(userTeamName);

        List<Conference> conferenceL = leagueObject.getConferenceList();
        List<Division> divisionL;
        List<Team> teamL;
        List<Player> playerL;
        List<String> allTeamNameList = new ArrayList<String>();

        //getTeam name write in Teams

        for(int i = 0; i < conferenceL.size(); i++){
            divisionL = conferenceL.get(i).getDivisionList();

            for (int j = 0; j < divisionL.size(); j++){
                teamL = divisionL.get(j).getTeamList();

                for (int k = 0; k < teamL.size(); k++){
                    allTeamNameList.add(teamL.get(k).getTeamName());

                }
            }
        }
        // rename to some logical
        ITeamInfo ty = new Team();
        IPlayerInfo py = new Player();
        ITeamInfo ti = new Team();
        AiAcceptReject ar = new AiAcceptReject();

        for(int i = 0; i<eligibleTeamNameList.size() ; i++ ){
            if (checkRandOfferChance(randOfferChance)){
                // reset tradeLoss point for eli[i]
                trade.setTradeOfferTeam(eligibleTeamNameList.get(i));
                trade.setPlayerListOfferTeam(getWeakestPlayers(maxPlayersPerTrade, eligibleTeamNameList.get(i), leagueObject, ty, py));
                trade.setPlayerListRequestedTeam(getStrongestPlayers(trade,allTeamNameList,leagueObject,ty,py));

                // based on team name get is User team in Teams
                //trade.getTradeRequestedTeam();
                if(ar.isAcceptOrReject(trade,leagueObject,randAcceptChance,true, py,ti)){
                    //iterate to teamPlayer list and make Swap 100% easier if directly changed to database.

                    for(int f = 0; f < conferenceL.size(); f++){
                        divisionL = conferenceL.get(f).getDivisionList();

                        for (int g = 0; g < divisionL.size(); g++){
                            teamL = divisionL.get(g).getTeamList();

                            for (int h = 0; h < teamL.size(); h++){
                                playerL = teamL.get(h).getPlayersByTeam(teamL.get(h).getTeamName(),leagueObject);
                                if(sameTeam(teamL.get(h).getTeamName(),trade.getTradeOfferTeam())){

                                   // remove offered player from team offering them
                                    for(Player p : playerL){
                                        for(Player pOffer: trade.getPlayerListOfferTeam()){

                                            if(matchString(p.getPlayerName(), pOffer.getPlayerName())){
                                                playerL.remove(p);
                                            }
                                        }
                                    }
                                    // add new requested players
                                    for(Player pRequested: trade.getPlayerListRequestedTeam()){
                                        playerL.add(pRequested);
                                    }
                                    teamL.get(h).setPlayerList(playerL);
//                                    conferenceL.get(f).getDivisionList().get(g).setTeamList(teamL);


                                }
                                if(sameTeam(teamL.get(h).getTeamName(), getTradeRequestedTeam())){

                                    // remove offered player from team offering them
                                    for(Player p : playerL){
                                        for(Player pRequested: trade.getPlayerListRequestedTeam()){

                                            if(matchString(p.getPlayerName(), pRequested.getPlayerName())){
                                                playerL.remove(p);
                                            }
                                        }
                                    }
                                    // add new requested players
                                    for(Player pOffer: trade.getPlayerListOfferTeam()){
                                        playerL.add(pOffer);
                                    }
                                    teamL.get(h).setPlayerList(playerL);
                                }

                            }
                            divisionL.get(g).setTeamList(teamL);
                        }
                        conferenceL.get(f).setDivisionList(divisionL);
                    }
                    // call notify
                    TradePublisher.getInstance().notify(trade.getTradeOfferTeam(), trade.getTradeRequestedTeam(),prepareToNotify(trade));
                    leagueObject.setConferenceList(conferenceL);
                }
            }

        }
        return leagueObject;
    }
}
