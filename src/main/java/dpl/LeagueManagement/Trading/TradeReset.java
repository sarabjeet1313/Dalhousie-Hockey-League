package dpl.LeagueManagement.Trading;

import dpl.LeagueManagement.Standings.StandingInfo;

import java.util.ArrayList;
import java.util.List;

public class TradeReset {
	
	private ITradePersistence tradeDB;
	private List<String> teamNames = new ArrayList<>();

	public TradeReset(ITradePersistence tradeDB) {
		this.tradeDB = tradeDB;
	}

	public void addToTeamNames(String teamName) {
		this.teamNames.add(teamName);
	}

	public void clearList() {
		this.teamNames.clear();
	}

	public void UpdateTrade(StandingInfo standingInfo){
		boolean isPersisted = Boolean.FALSE;
		if(this.teamNames.size() > 1){
			for(String teamName: this.teamNames){
				isPersisted = tradeDB.resetTradeLossPoint(teamName, standingInfo);
			}
		}
		if(isPersisted == Boolean.TRUE){
			this.clearList();
		}

	}

}
