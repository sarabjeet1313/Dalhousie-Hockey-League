package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import dpl.Database.RetiredPlayersDataDB;
import dpl.DplConstants.ScheduleConstants;
import dpl.DplConstants.TeamManagementConstants;
import dpl.ErrorHandling.RetirementManagementException;
import dpl.LeagueSimulationManagement.NewsSystem.NewsSubscriber;
import dpl.LeagueSimulationManagement.NewsSystem.RetirementPublisher;

public class RetirementManagement implements IRetirementManagement {

	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());

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
	public boolean shouldPlayerRetire(League league, Player player) {
		int maximumAge = league.getGameConfig().getAging().getMaximumAge();
		int likelihoodOfRetirement = getLikelihoodOfRetirement(league, player);
		Random rand = new Random();
		if (rand.nextInt(likelihoodOfRetirement) - 1 == 0 || player.getAge() > maximumAge) {
			log.log(Level.INFO, TeamManagementConstants.SHOULD_PLAYER_RETIRE.toString());
			RetirementPublisher.getInstance().notify(player.getPlayerName(), player.getAge());
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}

	@Override
	public League replaceRetiredPlayers(League league) throws SQLException, RetirementManagementException, IOException {
		List<Conference> conferenceList = league.getConferenceList();
		List<Player> freeAgentsList = league.getFreeAgents();
		IRetiredPlayerPersistance iretiredPlayersObject = new RetiredPlayersDataDB();
		List<Player> tempList = new ArrayList<Player>();
		log.log(Level.INFO, TeamManagementConstants.RETIREMENT_INFO.toString());

		try {

			for (Player freeplayer : freeAgentsList) {

				if (shouldPlayerRetire(league, freeplayer)) {
					tempList.add(freeplayer);
					freeplayer.setRetireStatus(true);
					iretiredPlayersObject.persistRetiredPlayers(freeplayer, null, league);
				}
			}

			freeAgentsList.removeAll(tempList);

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
									Player retiredPlayer = playersByTeam.get(pIndex);

									freeAgentsList.remove(returnedPlayer);
									playersByTeam.remove(playersByTeam.get(pIndex));
									playersByTeam.add(returnedPlayer);

									league.setFreeAgents(freeAgentsList);
									league.getConferenceList().get(index).getDivisionList().get(dIndex).getTeamList()
											.get(tIndex).setPlayerList(playersByTeam);

									iretiredPlayersObject.persistRetiredPlayers(retiredPlayer,
											teamList.get(tIndex).getTeamName(), league);
								}
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, TeamManagementConstants.RETIREMENT_EXCEPTION.toString() + league.getLeagueName());
			throw new RetirementManagementException(
					TeamManagementConstants.RETIREMENT_EXCEPTION.toString() + league.getLeagueName());
		} catch (IOException e) {
			throw e;
		}
		return league;
	}

	public Player statDecayCheck(Player player, League league) {
		double decayValue = league.getGameConfig().getAging().getStatDecayChance();
		double random = new Random().nextDouble();
		if(random < decayValue) {
			player.setSkating(player.getSkating() - 1);
			player.setShooting(player.getShooting() - 1);
			player.setChecking(player.getChecking() - 1);
			player.setSaving(player.getSaving() - 1);
		}
		return player;
	}
	@Override
	public League increaseAge(String currentDate, League league) throws SQLException, RetirementManagementException, IOException, ParseException {
		League tempLeague = null;
		String playerBirthDay = "";
		long dateDiff = 0;
		List<Conference> conferenceList = league.getConferenceList();
		List<Player> freeAgentsList = league.getFreeAgents();
		try {
			for (int index = 0; index < conferenceList.size(); index++) {
				List<Division> divisionList = conferenceList.get(index).getDivisionList();
				for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
					List<Team> teamList = divisionList.get(dIndex).getTeamList();
					for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
						List<Player> playersByTeam = teamList.get(tIndex).getPlayerList();
						for (Player player : playersByTeam) {
							playerBirthDay = player.getBirthDay() + "-" + player.getBirthMonth() + "-" + player.getBirthYear(); 
							Date date = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT.toString()).parse(currentDate);
							Date birthDate = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT.toString()).parse(playerBirthDay);
							dateDiff = birthDate.getTime() - date.getTime();
							if(dateDiff == 0) {
								player.setAge(player.getAge() + 1);
							}
							if (shouldPlayerRetire(league, player)) {
								player.setRetireStatus(true);
							}
							player = statDecayCheck(player, league);
						}
						league.getConferenceList().get(index).getDivisionList().get(dIndex).getTeamList().get(tIndex)
								.setPlayerList(playersByTeam);
					}
				}
			}

			for (Player freeplayer : freeAgentsList) {
				playerBirthDay = freeplayer.getBirthDay() + "-" + freeplayer.getBirthMonth() + "-" + freeplayer.getBirthYear(); 
				Date date = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT.toString()).parse(currentDate);
				Date birthDate = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT.toString()).parse(playerBirthDay);
				dateDiff = birthDate.getTime() - date.getTime();
				if(dateDiff == 0) {
					freeplayer.setAge(freeplayer.getAge() + 1);
				}
				if (shouldPlayerRetire(league, freeplayer)) {
					freeplayer.setRetireStatus(true);
				}
				freeplayer = statDecayCheck(freeplayer, league);
			}
			league.setFreeAgents(freeAgentsList);
			tempLeague = replaceRetiredPlayers(league);
		} catch (SQLException e) {
			throw new RetirementManagementException(
					TeamManagementConstants.RETIREMENT_EXCEPTION.toString() + league.getLeagueName());
		} catch (IOException e) {
			throw e;
		} catch (ParseException e) {
			throw e;
		}
		return tempLeague;
	}
}
