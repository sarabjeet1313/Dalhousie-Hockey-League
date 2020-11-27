package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.TrophySystem.IPresidentsTrophy;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;

public class PresidentsTrophyPublisher extends  Publisher{
    private static PresidentsTrophyPublisher instance;

    private PresidentsTrophyPublisher() {}

    public static PresidentsTrophyPublisher getInstance() {
        if (instance == null) {
            instance = new PresidentsTrophyPublisher();
        }
        return instance;
    }

    public void notifyWinningTeam(Team team) {
        dataMap.put("winner team", team);
        notifyAllObservers();
    }
}
