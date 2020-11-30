package dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemConstants;
import dpl.LeagueSimulationManagement.TrophySystem.BestCoachLeague;
import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
import dpl.SystemConfig;
import dpl.DplConstants.GameConfigConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Division;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class Training {

    @Expose(serialize = true, deserialize = true)
    private int daysUntilStatIncreaseCheck;
    @Expose(serialize = true, deserialize = true)
    private int trackDays;
    private IUserOutput output;
    private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
            .getTeamManagementAbstractFactory();

    public Training(int daysUntilStatIncreaseCheck, int trackDays) {
        this.daysUntilStatIncreaseCheck = daysUntilStatIncreaseCheck;
        this.trackDays = trackDays;
    }

    static {
        BestCoachLeague.getInstance().attach(TrophySystemAbstractFactory.createObserver(TrophySystemConstants.JACK_ADAMS_AWARD));
    }

    public Training(IUserOutput output) {
        this.output = output;
    }

    public int getDaysUntilStatIncreaseCheck() {
        return daysUntilStatIncreaseCheck;
    }

    public void setDaysUntilStatIncreaseCheck(int daysUntilStatIncreaseCheck) {
        this.daysUntilStatIncreaseCheck = daysUntilStatIncreaseCheck;
    }

    public int getTrackDays() {
        return trackDays;
    }

    public void setTrackDays(int trackDays) {
        this.trackDays = trackDays;
    }

    public double generateRandomValue() {
        return Math.random();
    }

    private void updateStats(Player player, Coach headCoach, League league) {
        int statPlayer = 0;
        double randomValue = generateRandomValue();
        boolean statsUpdated = Boolean.FALSE;
        IInjuryManagement injury = teamManagement.InjuryManagement();
        if (randomValue < headCoach.getSkating()) {
            player.setSkating(player.getSkating() + 1);
            statPlayer += 1;
            statsUpdated = Boolean.TRUE;
        } else {
            player = injury.getPlayerInjuryDays(player, league);
        }
        randomValue = generateRandomValue();
        if (randomValue < headCoach.getShooting() && player.isInjured() == Boolean.FALSE) {
            player.setShooting(player.getShooting() + 1);
            statPlayer += 1;
            statsUpdated = Boolean.TRUE;
        } else {
            if (player.isInjured() == Boolean.FALSE) {
                player = injury.getPlayerInjuryDays(player, league);
            }
        }
        randomValue = generateRandomValue();
        if (randomValue < headCoach.getChecking() && player.isInjured() == Boolean.FALSE) {
            statsUpdated = Boolean.TRUE;
            player.setChecking(player.getChecking() + 1);
            statPlayer += 1;
        } else {
            if (player.isInjured() == Boolean.FALSE) {
                player = injury.getPlayerInjuryDays(player, league);
            }
        }
        randomValue = generateRandomValue();
        if (randomValue < headCoach.getSaving() && player.isInjured() == Boolean.FALSE) {
            player.setShooting(player.getShooting() + 1);
            statPlayer += 1;
            statsUpdated = Boolean.TRUE;
        } else {
            if (player.isInjured() == Boolean.FALSE) {
                player = injury.getPlayerInjuryDays(player, league);
            }
        }
        if (statsUpdated == Boolean.TRUE) {
            output.setOutput(GameConfigConstants.TRAINING_STATS.toString() + player.getPlayerName());
            output.sendOutput();
        }
        BestCoachLeague.getInstance().notifyCoachTraining(headCoach, statPlayer);
    }

    private League playerTraining(League league) {
        League leagueObject = league;
        Coach headCoach;
        List<Conference> conferenceList = league.getConferenceList();
        List<Team> teamList;
        List<Division> divisionList;
        List<Player> playerList = new ArrayList<Player>();
        for (int cIndex = 0; cIndex < conferenceList.size(); cIndex++) {
            divisionList = conferenceList.get(cIndex).getDivisionList();
            for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                teamList = divisionList.get(dIndex).getTeamList();
                for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
                    headCoach = teamList.get(tIndex).getHeadCoach();
                    playerList = teamList.get(tIndex).getPlayerList();
                    for (int pIndex = 0; pIndex < playerList.size(); pIndex++) {
                        updateStats(playerList.get(pIndex), headCoach, league);
                    }
                }
            }
        }
        return leagueObject;
    }

    public League trackDaysForTraining(League league) {
        int days = league.getGameConfig().getTraining().getTrackDays() - 1;
        if (days == 0) {
            league.getGameConfig().getTraining()
                    .setTrackDays(league.getGameConfig().getTraining().getDaysUntilStatIncreaseCheck());
            league = playerTraining(league);
        }
        league.getGameConfig().getTraining().setTrackDays(days);
        return league;
    }
}
