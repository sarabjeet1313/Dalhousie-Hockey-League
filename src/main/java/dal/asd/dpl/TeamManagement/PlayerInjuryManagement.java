package dal.asd.dpl.TeamManagement;

import java.util.List;

public class PlayerInjuryManagement implements IPlayerInjuryManagement {

	@Override
	public League updatePlayerInjuryStatus(League league) {
		List<Conference> conferenceList = league.getConferenceList();
		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					List<Player> playersList = teamList.get(tIndex).getPlayerList();
					for (int pIndex = 0; pIndex < playersList.size(); pIndex++) {
						int numberOfInjuryDays = playersList.get(pIndex).getDaysInjured();
						if(playersList.get(pIndex).getDaysInjured() > 0) {
							playersList.get(pIndex).setDaysInjured(numberOfInjuryDays - 1);
						}
						if(playersList.get(pIndex).getDaysInjured() == 0) {
							playersList.get(pIndex).setInjured(false);
						}
					}
				}
			}
		}
		return league;
	}

}
