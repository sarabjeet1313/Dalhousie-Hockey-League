package dpl.NewsSystem;

import java.util.ArrayList;

public interface ITradeInfo {
	void updateTrade(String fromTeam, String toTeam, ArrayList<String> fromTeamTrade, ArrayList<String> toTeamTrade);
}
