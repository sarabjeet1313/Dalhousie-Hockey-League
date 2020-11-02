package dal.asd.dpl.Trading;

import java.util.ArrayList;
import java.util.List;

public class TradeReset {
    private ITradePersistence tradeDB;
    private List<String> teamNames = new ArrayList<>();

    public TradeReset(ITradePersistence tradeDB){
        this.tradeDB = tradeDB;
    }

    public void addToTeamNames (String teamName){
        this.teamNames.add(teamName);
    }

    public void clearList(){
        this.teamNames.clear();
    }

    public void UpdateTrade(){
        boolean isPersisted = Boolean.FALSE;
        for(String teamName: this.teamNames){
            isPersisted = tradeDB.resetTradeLossPoint(teamName);
        }
        if(isPersisted == Boolean.TRUE){
            this.clearList();
        }
    }
}
