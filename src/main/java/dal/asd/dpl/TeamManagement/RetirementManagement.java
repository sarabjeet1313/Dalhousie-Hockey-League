package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		
		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					List<Player> playersByTeam = teamList.get(tIndex).getPlayerList();
					for (Player player : playersByTeam) {
						if(player.isRetireStatus()) {

							Player returnedPlayer = updateRetiredPlayers(player, freeAgentsList);

							if (returnedPlayer.equals(player)) {
								break;
							} else {
								for (Player freeAgent : freeAgentsList) {
									if (returnedPlayer.equals(freeAgent)) {									
										freeAgentsList.remove(freeAgent);
										playersByTeam.remove(player);
										retiredPlayersList.add(player);
										playersByTeam.add(returnedPlayer);
										break;
									}
								}
								break;
							}
						}
					}
				}
			}
		}
		//persist retired players list
		return league;
	}

	public Player updateRetiredPlayers(Player player, List<Player> freeAgentsList) {
		String playerPosition = player.getPosition();

		List<Player> result = new ArrayList<Player>();

		for (Player freeAgentPlayer : freeAgentsList) {
			if (freeAgentPlayer.getPosition().equalsIgnoreCase(playerPosition)) {
				result.add(freeAgentPlayer);
			}
		}

		if (result.size() > 0) {

			Collections.sort(result, new Comparator<Player>() {

				@Override
				public int compare(Player p1, Player p2) {
					if (p1.getPlayerStrength(p1) > p2.getPlayerStrength(p2)) {
						return -1;
					} else if (p1.getPlayerStrength(p1) < p2.getPlayerStrength(p2)) {
						return 1;
					}
					return 0;
				}
			});

			return result.get(0);
		}

		return player;
	}

}
