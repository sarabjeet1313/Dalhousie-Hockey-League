package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import dpl.Database.RetiredPlayersDataDB;
import dpl.LeagueSimulationManagementTest.NewsSystem.NewsSubscriber;
import dpl.LeagueSimulationManagementTest.NewsSystem.RetirementPublisher;

public class RetirementManagement implements IRetirementManagement {

	static {
		RetirementPublisher.getInstance().subscribe(new NewsSubscriber());
	}

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
	public boolean shouldPlayerRetire(League league, Player player) throws SQLException {
		int maximumAge = league.getGameConfig().getAging().getMaximumAge();
		int likelihoodOfRetirement = getLikelihoodOfRetirement(league, player);
		Random rand = new Random();
		try {
			if (rand.nextInt(likelihoodOfRetirement) == 0 || player.getAge() > maximumAge) {
				this.replaceRetiredPlayers(league);
				RetirementPublisher.getInstance().notify(player.getPlayerName(), player.getAge());
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (SQLException e) {
			throw e;
		}

	}

	@Override
	public League replaceRetiredPlayers(League league) throws SQLException {
		List<Conference> conferenceList = league.getConferenceList();
		List<Player> freeAgentsList = league.getFreeAgents();
		IRetiredPlayerPersistance iretiredPlayersObject = new RetiredPlayersDataDB();
		int maximumRetirementAge = league.getGameConfig().getAging().getMaximumAge();
		try {
			Iterator<Player> iter = freeAgentsList.iterator();
			while (iter.hasNext()) {
				int years = iter.next().getAge();

				if (years > maximumRetirementAge) {
					iretiredPlayersObject.persistRetiredPlayers(iter.next(), null, league);
					iter.remove();
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
									playersByTeam.remove(playersByTeam.get(pIndex));
									playersByTeam.add(returnedPlayer);
									iretiredPlayersObject.persistRetiredPlayers(playersByTeam.get(pIndex),
											teamList.get(tIndex).getTeamName(), league);

									league.setFreeAgents(freeAgentsList);
									league.getConferenceList().get(index).getDivisionList().get(dIndex).getTeamList()
											.get(tIndex).setPlayerList(playersByTeam);
								}
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw e;
		}
		return league;
	}

	@Override
	public League increaseAge(int days, League league) throws SQLException {
		League tempLeague = null;
		try {
			List<Conference> conferenceList = league.getConferenceList();
			int maximumRetirementAge = league.getGameConfig().getAging().getMaximumAge();
			List<Player> freeAgentsList = league.getFreeAgents();
			for (int index = 0; index < conferenceList.size(); index++) {
				List<Division> divisionList = conferenceList.get(index).getDivisionList();
				for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
					List<Team> teamList = divisionList.get(dIndex).getTeamList();
					for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
						List<Player> playersByTeam = teamList.get(tIndex).getPlayerList();
						for (Player player : playersByTeam) {
							int years = player.getAge();
							player.setAge(years + 1);

							if (player.getAge() >= maximumRetirementAge) {
								player.setRetireStatus(true);
							}
						}
						league.getConferenceList().get(index).getDivisionList().get(dIndex).getTeamList().get(tIndex)
								.setPlayerList(playersByTeam);
					}
				}
			}

			for (Player freeplayer : freeAgentsList) {
				int years = freeplayer.getAge();
				freeplayer.setAge(years + 1);

				if (freeplayer.getAge() > maximumRetirementAge) {
					freeplayer.setRetireStatus(true);
				}
			}

			league.setFreeAgents(freeAgentsList);
			tempLeague = replaceRetiredPlayers(league);
		} catch (SQLException e) {
			throw e;
		}
		return tempLeague;
	}
}
