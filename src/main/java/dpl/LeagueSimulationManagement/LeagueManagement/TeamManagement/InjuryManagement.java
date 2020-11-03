package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.util.List;
import java.util.Random;

import dpl.LeagueSimulationManagement.NewsSystem.InjuryPublisher;
import dpl.LeagueSimulationManagement.NewsSystem.NewsSubscriber;

public class InjuryManagement implements IInjuryManagement {

    static {
        InjuryPublisher.getInstance().subscribe(new NewsSubscriber());
    }

    @Override
    public League updatePlayerInjuryStatus(int days, League league) {
        List<Conference> conferenceList = league.getConferenceList();
        for (int index = 0; index < conferenceList.size(); index++) {
            List<Division> divisionList = conferenceList.get(index).getDivisionList();
            for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                List<Team> teamList = divisionList.get(dIndex).getTeamList();
                for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
                    List<Player> playersList = teamList.get(tIndex).getPlayerList();
                    for (int pIndex = 0; pIndex < playersList.size(); pIndex++) {
                        int numberOfInjuryDays = playersList.get(pIndex).getDaysInjured();
                        if (playersList.get(pIndex).getDaysInjured() > 0) {
                            playersList.get(pIndex).setDaysInjured(numberOfInjuryDays - days);

                            if (playersList.get(pIndex).getDaysInjured() < 0) {
                                playersList.get(pIndex).setDaysInjured(0);
                            }
                        }
                        if (playersList.get(pIndex).getDaysInjured() == 0) {
                            playersList.get(pIndex).setInjured(Boolean.FALSE);
                        }
                    }
                }
            }
        }
        return league;
    }

    @Override
    public Player getPlayerInjuryDays(Player player, League league) {
        Random random = new Random();
        double randomInjuryChance = league.getGameConfig().getInjury().getRandomInjuryChance() * 100;
        int injuryDaysLow = league.getGameConfig().getInjury().getInjuryDaysLow();
        int injuryDaysHigh = league.getGameConfig().getInjury().getInjuryDaysHigh();
        double randomValue = Math.random() * 100;
        if ((randomValue <= randomInjuryChance) && (player.isInjured() == Boolean.FALSE)) {
            player.setInjured(Boolean.TRUE);
            int injuryDays = random.nextInt(injuryDaysHigh - injuryDaysLow) + injuryDaysLow;
            player.setDaysInjured(injuryDays);
        }
        InjuryPublisher.getInstance().notify(player.getPlayerName(), player.getDaysInjured());
        return player;
    }

    @Override
    public League getInjuryStatusByTeam(String teamName, League league) {
        List<Conference> conferenceList = league.getConferenceList();
        for (int index = 0; index < conferenceList.size(); index++) {
            List<Division> divisionList = conferenceList.get(index).getDivisionList();
            for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                List<Team> teamList = divisionList.get(dIndex).getTeamList();
                for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
                    if (teamList.get(dIndex).getTeamName().equals(teamName)) {
                        List<Player> playersByTeam = teamList.get(tIndex).getPlayerList();
                        for (Player player : playersByTeam) {
                            Player returnedPlayer = getPlayerInjuryDays(player, league);
                            if (returnedPlayer.getDaysInjured() > 0) {
                                player.setInjured(Boolean.TRUE);
                                player.setDaysInjured(returnedPlayer.getDaysInjured());
                            }
                        }

                        league.getConferenceList().get(index).getDivisionList().get(dIndex).getTeamList().get(tIndex)
                                .setPlayerList(playersByTeam);

                        break;
                    }
                }
            }
        }
        return league;
    }

}
