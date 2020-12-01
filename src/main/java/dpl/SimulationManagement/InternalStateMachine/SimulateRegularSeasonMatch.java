package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.TeamManagement.GeneralConstants;
import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.*;
import dpl.NewsSystem.GamePlayedPublisher;
import dpl.LeagueManagement.TrophySystem.TeamPoint;
import dpl.LeagueManagement.TrophySystem.TrophySystemAbstractFactory;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.*;

public class SimulateRegularSeasonMatch implements ISimulateMatch {

	private int fixedShotsPerTeam = 30;
	private String currentDate;
	private ISchedule schedule;
	private IUserOutput output;
	private String winningTeam;
	private String losingTeam;
	private League leagueToSimulate;
	private double penaltyChance;
	private int checkingValueToPenalty;
	private double shootingValueToGoal;
	private StandingInfo standings;
	private List<Player> firstTeamForwards;
	private List<Player> firstTeamForwardsOnIce;
	private List<Player> firstTeamDefenseMen;
	private List<Player> firstTeamDefenseMenOnIce;
	private List<Player> firstTeamGoalies;
	private List<Player> firstTeamGoaliesOnIce;
	private List<Player> secondTeamForwards;
	private List<Player> secondTeamForwardsOnIce;
	private List<Player> secondTeamDefenseMen;
	private List<Player> secondTeamDefenseMenOnIce;
	private List<Player> secondTeamGoalies;
	private List<Player> secondTeamGoaliesOnIce;
	private int firstTeamSkatingTotal;
	private int secondTeamSkatingTotal;
	private int firstTeamShotsCounter;
	private int secondTeamShotsCounter;
	private Map<String, Integer> teamGoals;
	private Map<String, List<Map<String, String>>> currentSchedule;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();


	public SimulateRegularSeasonMatch(String currentDate, ISchedule schedule, IUserOutput output, League leagueToSimulate, StandingInfo standings) {
		this.currentDate = currentDate;
		this.schedule = schedule;
		this.output = output;
		this.teamGoals = new HashMap<>();
		this.firstTeamForwards = new ArrayList<>();
		this.firstTeamForwardsOnIce = new ArrayList<>();
		this.secondTeamForwards = new ArrayList<>();
		this.secondTeamForwardsOnIce = new ArrayList<>();
		this.firstTeamDefenseMen = new ArrayList<>();
		this.firstTeamDefenseMenOnIce = new ArrayList<>();
		this.secondTeamDefenseMen = new ArrayList<>();
		this.secondTeamDefenseMenOnIce = new ArrayList<>();
		this.firstTeamGoalies = new ArrayList<>();
		this.firstTeamGoaliesOnIce = new ArrayList<>();
		this.secondTeamGoalies = new ArrayList<>();
		this.secondTeamGoaliesOnIce = new ArrayList<>();
		this.teamGoals = new HashMap<>();
		this.firstTeamSkatingTotal = 0;
		this.secondTeamSkatingTotal = 0;
		this.firstTeamShotsCounter = 0;
		this.secondTeamShotsCounter = 0;
		this.penaltyChance = leagueToSimulate.getGameConfig().getPenaltyChance();
		this.checkingValueToPenalty = leagueToSimulate.getGameConfig().getCheckingValue();
		this.shootingValueToGoal = leagueToSimulate.getGameConfig().getShootingValue();
		this.leagueToSimulate = leagueToSimulate;
		this.standings = standings;
		this.currentSchedule = schedule.getFinalSchedule();
	}

	static {
		TeamPoint.getInstance().attach(TrophySystemAbstractFactory.createObserver(TrophySystemConstants.PRESIDENT_TROPHY));
	}

	public void simulateMatch() {
		if (currentSchedule.containsKey(this.currentDate)) {
			List<Map<String, String>> teamsCompetingList = currentSchedule.get(this.currentDate);
			if (teamsCompetingList.size() > 0) {
				Map<String, String> teamsCompeting = teamsCompetingList.get(0);
				for (Map.Entry<String, String> entry : teamsCompeting.entrySet()) {
					String firstTeam = entry.getKey();
					String secondTeam = entry.getValue();
					output.setOutput("Match is going on between " + firstTeam + " vs " + secondTeam);
					output.sendOutput();
					resetGoals(firstTeam, secondTeam);
					playMatch(firstTeam, secondTeam);
					standings.setTotalSeasonMatches(standings.getTotalSeasonMatches() + 1);

					if (teamGoals.get(firstTeam) >= teamGoals.get(secondTeam)) {
						winningTeam = firstTeam;
						losingTeam = secondTeam;
					} else {
						winningTeam = secondTeam;
						losingTeam = firstTeam;
					}
					Team team = teamManagement.Team();
					team.setTeamName(winningTeam);
					TeamPoint.getInstance().notifyTeamWinsTheMatch(team);
					GamePlayedPublisher.getInstance().notify(winningTeam, losingTeam, currentDate);
					standings.updateTeamWinMap(winningTeam);
					standings.updateTeamLoseMap(losingTeam);
				}
				teamsCompetingList.remove(0);
				currentSchedule.put(this.currentDate, teamsCompetingList);
				schedule.setFinalSchedule(currentSchedule);
			}
		}
	}

	private void resetGoals(String firstTeam, String secondTeam) {
		teamGoals.put(firstTeam, 0);
		teamGoals.put(secondTeam, 0);
	}

	private void playMatch(String firstTeam, String secondTeam) {

		initializeTeamPlayers(firstTeam, secondTeam);
		int diff = firstTeamSkatingTotal - secondTeamSkatingTotal;

		if (diff < 0) {
			if (Math.abs(diff) <= 6) {
				secondTeamShotsCounter = fixedShotsPerTeam + Math.abs(diff);
			} else {
				secondTeamShotsCounter = fixedShotsPerTeam + 6;
			}
			firstTeamShotsCounter = fixedShotsPerTeam;
		} else {
			if (diff > 6) {
				firstTeamShotsCounter = fixedShotsPerTeam + 6;
			} else {
				firstTeamShotsCounter = fixedShotsPerTeam + diff;
			}
			secondTeamShotsCounter = fixedShotsPerTeam;
		}
		int totalShots = firstTeamShotsCounter + secondTeamShotsCounter;

		for (int i = 1; i <= totalShots; i++) {
			setFirstTeamPlayersOnIce();
			setSecondTeamPlayersOnIce();
			if (i > 44) {
				firstTeamGoaliesOnIce.add(firstTeamGoalies.get(1));
				secondTeamGoaliesOnIce.add(secondTeamGoalies.get(1));
			} else {
				firstTeamGoaliesOnIce.add(firstTeamGoalies.get(0));
				secondTeamGoaliesOnIce.add(secondTeamGoalies.get(0));
			}

			if (Math.random() > 0.5 && firstTeamShotsCounter > 0) {
				firstTeamMakeAShot(firstTeam);
				firstTeamShotsCounter--;
			} else {
				if (secondTeamShotsCounter > 0) {
					secondTeamMakeAShot(secondTeam);
					secondTeamShotsCounter--;
				}
			}
		}
	}

	private void initializeTeamPlayers(String firstTeam, String secondTeam) {
		List<Conference> conferenceList = leagueToSimulate.getConferenceList();
		for (Conference conference : conferenceList) {
			List<Division> divisionList = conference.getDivisionList();
			for (Division division : divisionList) {
				List<Team> teamList = division.getTeamList();
				for (Team team : teamList) {
					String teamName = team.getTeamName();

					if (teamName.equals(firstTeam)) {
						List<Player> playerList = team.getPlayerList();
						initializeFirstTeamPlayers(playerList);
					}

					if (teamName.equals(secondTeam)) {
						List<Player> playerList = team.getPlayerList();
						initializeSecondTeamPlayers(playerList);
					}
				}
			}
		}
	}

	private void initializeFirstTeamPlayers(List<Player> playerList) {
		for (Player player : playerList) {

			if (player.getPosition().equals(GeneralConstants.FORWARD.toString())) {
				firstTeamForwards.add(player);
				firstTeamSkatingTotal += player.getSkating();
			}
			if (player.getPosition().equals(GeneralConstants.GOALIE.toString())) {
				firstTeamGoalies.add(player);
				firstTeamSkatingTotal += player.getSkating();
			}
			if (player.getPosition().equals(GeneralConstants.DEFENSE.toString())) {
				firstTeamDefenseMen.add(player);
				firstTeamSkatingTotal += player.getSkating();
			}
		}
	}

	private void initializeSecondTeamPlayers(List<Player> playerList) {
		for (Player player : playerList) {
			if (player.getPosition().equals(GeneralConstants.FORWARD.toString())) {
				secondTeamForwards.add(player);
				secondTeamSkatingTotal += player.getSkating();
			}
			if (player.getPosition().equals(GeneralConstants.GOALIE.toString())) {
				secondTeamGoalies.add(player);
				secondTeamSkatingTotal += player.getSkating();
			}
			if (player.getPosition().equals(GeneralConstants.DEFENSE.toString())) {
				secondTeamDefenseMen.add(player);
				secondTeamSkatingTotal += player.getSkating();
			}
		}
	}

	private void setFirstTeamPlayersOnIce() {
		for (int i = 0; i < 3; i++) {
			Collections.shuffle(firstTeamForwards);
			if (firstTeamForwards.size() > 0) {
				firstTeamForwardsOnIce.add(firstTeamForwards.get(0));
			}
		}

		for (int i = 0; i < 2; i++) {
			Collections.shuffle(firstTeamDefenseMen);
			if (firstTeamDefenseMen.size() > 0) {
				firstTeamDefenseMenOnIce.add(firstTeamDefenseMen.get(0));
			}
		}
	}

	private void setSecondTeamPlayersOnIce() {
		for (int i = 0; i < 3; i++) {
			Collections.shuffle(secondTeamForwards);
			if (secondTeamForwards.size() > 0) {
				secondTeamForwardsOnIce.add(secondTeamForwards.get(0));
			}
		}

		for (int i = 0; i < 2; i++) {
			Collections.shuffle(secondTeamDefenseMen);
			if (secondTeamDefenseMen.size() > 0) {
				secondTeamDefenseMenOnIce.add(secondTeamDefenseMen.get(0));
			}
		}
	}

	private void firstTeamMakeAShot(String team) {

		standings.setTotalShotsInSeason(standings.getTotalShotsInSeason() + 1);
		double rand1 = Math.random();
		double rand2 = Math.random();
		double randomPenalty = Math.random();

		Collections.shuffle(firstTeamForwardsOnIce);
		Player forwardPlayer = firstTeamForwardsOnIce.get(0);

		Collections.shuffle(secondTeamDefenseMenOnIce);
		Player defensePlayer = secondTeamDefenseMenOnIce.get(0);

		Collections.shuffle(secondTeamGoaliesOnIce);
		Player goalie = secondTeamGoaliesOnIce.get(0);

		if (forwardPlayer.getShooting() * rand1 > (defensePlayer.getChecking() * rand2 + goalie.getSaving() * rand2) + shootingValueToGoal) {
			forwardPlayer.setGoals(forwardPlayer.getGoals() + 1);
			standings.setTotalGoalsInSeason(standings.getTotalGoalsInSeason() + 1);
			int goals = teamGoals.get(team);
			teamGoals.put(team, goals + 1);
		} else if (goalie.getSaving() * rand1 > forwardPlayer.getShooting() * rand2) {
			goalie.setSaves(goalie.getSaves() + 1);
			standings.setTotalSavesInSeason(standings.getTotalSavesInSeason() + 1);
		} else {
			if (randomPenalty < penaltyChance && defensePlayer.getChecking() > checkingValueToPenalty) {
				defensePlayer.setPenalties(defensePlayer.getPenalties() + 1);
				standings.setTotalPenaltiesInSeason(standings.getTotalPenaltiesInSeason() + 1);
			}
			defensePlayer.setSaves(defensePlayer.getSaves() + 1);
			standings.setTotalSavesInSeason(standings.getTotalSavesInSeason() + 1);
		}
	}

	private void secondTeamMakeAShot(String team) {

		standings.setTotalShotsInSeason(standings.getTotalShotsInSeason() + 1);
		double rand1 = Math.random();
		double rand2 = Math.random();
		double randomPenalty = Math.random();

		Collections.shuffle(secondTeamForwardsOnIce);
		Player forwardPlayer = secondTeamForwardsOnIce.get(0);

		Collections.shuffle(firstTeamDefenseMenOnIce);
		Player defensePlayer = firstTeamDefenseMenOnIce.get(0);

		Collections.shuffle(firstTeamGoaliesOnIce);
		Player goalie = firstTeamGoaliesOnIce.get(0);

		if (forwardPlayer.getShooting() * rand1 > (defensePlayer.getChecking() * rand2 + goalie.getSaving() * rand2) + shootingValueToGoal) {
			forwardPlayer.setGoals(forwardPlayer.getGoals() + 1);
			standings.setTotalGoalsInSeason(standings.getTotalGoalsInSeason() + 1);
			int goals = teamGoals.get(team);
			teamGoals.put(team, goals + 1);
		} else if (goalie.getSaving() * rand1 > forwardPlayer.getShooting() * rand2) {
			goalie.setSaves(goalie.getSaves() + 1);
			standings.setTotalSavesInSeason(standings.getTotalSavesInSeason() + 1);
		} else {
			if (randomPenalty < penaltyChance && defensePlayer.getChecking() > checkingValueToPenalty) {
				defensePlayer.setPenalties(defensePlayer.getPenalties() + 1);
				standings.setTotalPenaltiesInSeason(standings.getTotalPenaltiesInSeason() + 1);
			}
			defensePlayer.setSaves(defensePlayer.getSaves() + 1);
			standings.setTotalSavesInSeason(standings.getTotalSavesInSeason() + 1);
		}
	}
}
