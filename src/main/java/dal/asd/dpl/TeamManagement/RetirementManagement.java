package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RetirementManagement implements IRetirementManager {

	@Override
	public int getLikelihoodOfRetirement(League league, Player player) {
		int averageRetirementAge = league.getGameConfig().getAging().getAverageRetirementAge();
		int maximumAge = league.getGameConfig().getAging().getMaximumAge() + 50;
		int age = player.getAge();
		int likelihoodOfRetirement = 0;

		if (age <= averageRetirementAge) {
			likelihoodOfRetirement = maximumAge - age;
		} else {
			likelihoodOfRetirement = (int) ((maximumAge - age) / 3);
		}

		return likelihoodOfRetirement;
	}

	@Override
	public boolean shouldPlayerRetire(League league, Player player) {
		int maximumAge = league.getGameConfig().getAging().getMaximumAge();
		int likelihoodOfRetirement = getLikelihoodOfRetirement(league, player);
		Random rand = new Random();

		if (rand.nextInt(likelihoodOfRetirement) == 0 || player.getAge() > maximumAge) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public League replaceRetiredPlayers(League league) {
		List<Conference> conferenceList = league.getConferenceList();
		List<Player> freeAgentsList = league.getFreeAgents();
		List<Player> retiredPlayersList = new ArrayList<Player>();
		int maximumRetirementAge = league.getGameConfig().getAging().getMaximumAge();

		for (Player freeplayer : freeAgentsList) {
			int years = freeplayer.getAge();

			if (years > maximumRetirementAge) {
				freeAgentsList.remove(freeplayer);
				retiredPlayersList.add(freeplayer);
			}
		}

		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					List<Player> playersByTeam = teamList.get(tIndex).getPlayerList();
					for (int pIndex = 0; pIndex < playersByTeam.size(); pIndex++) {
						if (playersByTeam.get(pIndex).isRetireStatus()) {
							int selectedIndex = 0;
							double max = 0;

							if (freeAgentsList.size() > 0) {

								for (int findex = 0; findex < freeAgentsList.size(); findex++) {
									Player freeAgent = freeAgentsList.get(findex);
									if (freeAgent.getPosition().equals(playersByTeam.get(pIndex).getPosition())) {
										if (max < freeAgent.getPlayerStrength(freeAgent)) {
											max = freeAgent.getPlayerStrength(freeAgent);
											selectedIndex = findex;
										}
									}
								}

								Player returnedPlayer = freeAgentsList.get(selectedIndex);

								freeAgentsList.remove(returnedPlayer);
								retiredPlayersList.add(playersByTeam.get(pIndex));
								playersByTeam.remove(playersByTeam.get(pIndex));
								playersByTeam.add(returnedPlayer);

								league.setFreeAgents(freeAgentsList);
								league.getConferenceList().get(index).getDivisionList().get(dIndex).getTeamList()
										.get(tIndex).setPlayerList(playersByTeam);
							}
						}
					}
				}
			}
		}

		// persist retired players list
		return league;
	}

}
