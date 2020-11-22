package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.util.*;

import dpl.DplConstants.GeneralConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.*;
import dpl.LeagueSimulationManagement.NewsSystem.INewsSystemAbstractFactory;
import dpl.SystemConfig;
import dpl.DplConstants.ScheduleConstants;
import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.NewsSystem.GamePlayedPublisher;
import dpl.LeagueSimulationManagement.NewsSystem.NewsSubscriber;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class SimulateGameState implements ISimulationState {

	private int fixedShotsPerTeam = 30;
	private static String stateName;
	private static String nextStateName;
	private League leagueToSimulate;
	private StandingInfo standings;
	private IStandingsPersistance standingsDb;
	private ISchedule schedule;
	private InternalStateContext context;
	private SeasonCalendar utility;
	private String currentDate;
	private String endDate;
	private String winningTeam;
	private String losingTeam;
	private int season;
	private IUserOutput output;
	private ITeamInfo teamInfo;
	private double penaltyChance;
	private int checkingValueToPenalty;
	private double shootingValueToGoal;
	private IInjuryManagement injury;
	private Map<String, List<Map<String, String>>> currentSchedule;
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
	private GameContext gameContext;
	private ISimulateMatch simulateMatch;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private ITeamManagementAbstractFactory teamManagementAbstractFactory;
	private static INewsSystemAbstractFactory newsSystemAbstractFactory;

	public SimulateGameState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,
			InternalStateContext context, SeasonCalendar utility, String currentDate, String endDate, int season,
			IUserOutput output) {
		this.stateName = StateConstants.SIMULATE_GAME_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.teamManagementAbstractFactory = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.standingsDb = standingsDb;
		this.standings = standings;
		this.schedule = schedule;
		this.context = context;
		this.utility = utility;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.season = season;
		this.currentSchedule = schedule.getFinalSchedule();
		this.injury = teamManagementAbstractFactory.InjuryManagement();
		this.penaltyChance = leagueToSimulate.getGameConfig().getPenaltyChance();
		this.checkingValueToPenalty = leagueToSimulate.getGameConfig().getCheckingValue();
		this.shootingValueToGoal = leagueToSimulate.getGameConfig().getShootingValue();
		this.output = output;
		this.teamInfo = teamManagementAbstractFactory.Team();
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
		this.newsSystemAbstractFactory = SystemConfig.getSingleInstance().getNewsSystemAbstractFactory();
	}

	static {
		GamePlayedPublisher.getInstance().subscribe(SystemConfig.getSingleInstance().getNewsSystemAbstractFactory().NewsSubscriber());
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.INJURY_STATE;
		return this.internalStateMachineFactory.InjuryCheckState(leagueToSimulate, injury, schedule, context, utility, currentDate, endDate, season,
				output, standingsDb, standings);
	}

	public void doProcessing() {
		output.setOutput("Inside Match Simulation state");
		output.sendOutput();
		if (schedule.getSeasonType() == ScheduleConstants.REGULAR_SEASON) {
			this.simulateMatch = internalStateMachineFactory.SimulateRegularSeasonMatch(currentDate, schedule, output, leagueToSimulate, standings);
			this.gameContext = internalStateMachineFactory.GameContext(this.simulateMatch);
			this.gameContext.simulateMatch();
		} else if (schedule.getSeasonType() == ScheduleConstants.PLAYOFF_SEASON) {
			this.simulateMatch = internalStateMachineFactory.SimulatePlayoffSeasonMatch(currentDate, schedule, output, leagueToSimulate, standings, utility);
			this.gameContext = internalStateMachineFactory.GameContext(this.simulateMatch);
			this.gameContext.simulateMatch();
		}
	}

	private void simulateRegularMatches() {
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
					simulateMatch(firstTeam, secondTeam);
					standings.setTotalSeasonMatches(standings.getTotalSeasonMatches() + 1);

					if(teamGoals.get(firstTeam) >= teamGoals.get(secondTeam)) {
						winningTeam = firstTeam;
						losingTeam = secondTeam;
					}
					else {
						winningTeam = secondTeam;
						losingTeam = firstTeam;
					}

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

	private void simulatePlayoffMatches() {
		if (currentSchedule.containsKey(this.currentDate)) {
			List<Map<String, String>> teamsCompetingList = currentSchedule.get(this.currentDate);
			if (teamsCompetingList.size() > 0) {
				Map<String, String> teamsCompeting = teamsCompetingList.get(0);
				for (Map.Entry<String, String> entry : teamsCompeting.entrySet()) {
					String firstTeam = entry.getKey();
					String secondTeam = entry.getValue();
					int firstTeamWin = 1;
					int secondTeamWin = 1;
					for (int index = 0; index < 7; index++) {
						if (firstTeamWin >= 5 || secondTeamWin >= 5) {
							break;
						}
						output.setOutput("Match is going on between " + firstTeam + " vs " + secondTeam);
						output.sendOutput();
						resetGoals(firstTeam, secondTeam);
						standings.setTotalSeasonMatches(standings.getTotalSeasonMatches() + 1);
						simulateMatch(firstTeam, secondTeam);

						if(teamGoals.get(firstTeam) >= teamGoals.get(secondTeam)) {
							winningTeam = firstTeam;
							firstTeamWin++;
							losingTeam = secondTeam;
						}
						else {
							winningTeam = secondTeam;
							secondTeamWin++;
							losingTeam = firstTeam;
						}

						GamePlayedPublisher.getInstance().notify(winningTeam, losingTeam, currentDate);
						standings.updateTeamWinMap(winningTeam);
						standings.updateTeamLoseMap(losingTeam);
					}

					if (firstTeamWin > secondTeamWin) {
						generateNextRoundSchedule(firstTeam, secondTeam);
					}

					if (secondTeamWin > firstTeamWin) {
						generateNextRoundSchedule(secondTeam, firstTeam);
					}
				}
				schedule.setFinalSchedule(currentSchedule);
			}
		}
	}

	private void simulateMatch(String firstTeam, String secondTeam) {

		initializeTeamPlayers(firstTeam, secondTeam);
		int diff = firstTeamSkatingTotal - secondTeamSkatingTotal;

		if(diff < 0) {
			if(Math.abs(diff) <= 6) {
				secondTeamShotsCounter = fixedShotsPerTeam + Math.abs(diff);
			}
			else {
				secondTeamShotsCounter = fixedShotsPerTeam + 6;
			}
			firstTeamShotsCounter = fixedShotsPerTeam;
		}
		else {
			if(diff > 6) {
				firstTeamShotsCounter = fixedShotsPerTeam + 6;
			}
			else {
				firstTeamShotsCounter = fixedShotsPerTeam + diff;
			}
			secondTeamShotsCounter = fixedShotsPerTeam;
		}
		int totalShots = firstTeamShotsCounter + secondTeamShotsCounter;

		for(int i=1; i<=totalShots; i++){
			setFirstTeamPlayersOnIce();
			setSecondTeamPlayersOnIce();
			if(i > 44) {
				firstTeamGoaliesOnIce.add(firstTeamGoalies.get(1));
				secondTeamGoaliesOnIce.add(secondTeamGoalies.get(1));
			}
			else {
				firstTeamGoaliesOnIce.add(firstTeamGoalies.get(0));
				secondTeamGoaliesOnIce.add(secondTeamGoalies.get(0));
			}

			if(Math.random() > 0.5 && firstTeamShotsCounter > 0) {
				firstTeamMakeAShot(firstTeam);
				firstTeamShotsCounter--;
			}
			else {
				if(secondTeamShotsCounter > 0) {
					secondTeamMakeAShot(secondTeam);
					secondTeamShotsCounter--;
				}
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

		if(forwardPlayer.getShooting()*rand1 > (defensePlayer.getChecking()*rand2 + goalie.getSaving()*rand2)+shootingValueToGoal){
			forwardPlayer.setGoals(forwardPlayer.getGoals() + 1);
			standings.setTotalGoalsInSeason(standings.getTotalGoalsInSeason() + 1);
			int goals = teamGoals.get(team);
			teamGoals.put(team, goals+1);
		}
		else if(goalie.getSaving()*rand1 > forwardPlayer.getShooting()*rand2) {
			goalie.setSaves(goalie.getSaves() + 1);
			standings.setTotalSavesInSeason(standings.getTotalSavesInSeason() + 1);
		}
		else {
			if(randomPenalty < penaltyChance && defensePlayer.getChecking() > checkingValueToPenalty) {
				defensePlayer.setPenalties(defensePlayer.getPenalties() + 1);
				standings.setTotalPenaltiesInSeason(standings.getTotalPenaltiesInSeason() + 1);
			}
			defensePlayer.setSaves(defensePlayer.getSaves()+1);
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

		if(forwardPlayer.getShooting()*rand1 > (defensePlayer.getChecking()*rand2 + goalie.getSaving()*rand2)+shootingValueToGoal){
			forwardPlayer.setGoals(forwardPlayer.getGoals() + 1);
			standings.setTotalGoalsInSeason(standings.getTotalGoalsInSeason() + 1);
			int goals = teamGoals.get(team);
			teamGoals.put(team, goals+1);
		}
		else if(goalie.getSaving()*rand1 > forwardPlayer.getShooting()*rand2) {
			goalie.setSaves(goalie.getSaves() + 1);
			standings.setTotalSavesInSeason(standings.getTotalSavesInSeason() + 1);
		}
		else {
			if(randomPenalty < penaltyChance && defensePlayer.getChecking() > checkingValueToPenalty) {
				defensePlayer.setPenalties(defensePlayer.getPenalties() + 1);
				standings.setTotalPenaltiesInSeason(standings.getTotalPenaltiesInSeason() + 1);
			}
			defensePlayer.setSaves(defensePlayer.getSaves()+1);
			standings.setTotalSavesInSeason(standings.getTotalSavesInSeason() + 1);
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

			if(player.getPosition().equals(GeneralConstants.FORWARD.toString())){
				firstTeamForwards.add(player);
				firstTeamSkatingTotal += player.getSkating();
			}
			if(player.getPosition().equals(GeneralConstants.GOALIE.toString())){
				firstTeamGoalies.add(player);
				firstTeamSkatingTotal += player.getSkating();
			}
			if(player.getPosition().equals(GeneralConstants.DEFENSE.toString())){
				firstTeamDefenseMen.add(player);
				firstTeamSkatingTotal += player.getSkating();
			}
		}
	}

	private void initializeSecondTeamPlayers(List<Player> playerList) {
		for (Player player : playerList) {
			if(player.getPosition().equals(GeneralConstants.FORWARD.toString())){
				secondTeamForwards.add(player);
				secondTeamSkatingTotal += player.getSkating();
			}
			if(player.getPosition().equals(GeneralConstants.GOALIE.toString())){
				secondTeamGoalies.add(player);
				secondTeamSkatingTotal += player.getSkating();
			}
			if(player.getPosition().equals(GeneralConstants.DEFENSE.toString())){
				secondTeamDefenseMen.add(player);
				secondTeamSkatingTotal += player.getSkating();
			}
		}
	}

	private void setFirstTeamPlayersOnIce() {
		for(int i = 0; i  < 3; i++){
			Collections.shuffle(firstTeamForwards);
			if(firstTeamForwards.size() > 0) {
				firstTeamForwardsOnIce.add(firstTeamForwards.get(0));
			}
		}

		for(int i = 0; i  < 2; i++){
			Collections.shuffle(firstTeamDefenseMen);
			if(firstTeamDefenseMen.size() > 0) {
				firstTeamDefenseMenOnIce.add(firstTeamDefenseMen.get(0));
			}
		}
	}

	private void setSecondTeamPlayersOnIce() {
		for(int i = 0; i  < 3; i++){
			Collections.shuffle(secondTeamForwards);
			if(secondTeamForwards.size() > 0) {
				secondTeamForwardsOnIce.add(secondTeamForwards.get(0));
			}
		}

		for(int i = 0; i  < 2; i++){
			Collections.shuffle(secondTeamDefenseMen);
			if(secondTeamDefenseMen.size() > 0) {
				secondTeamDefenseMenOnIce.add(secondTeamDefenseMen.get(0));
			}
		}
	}

	private void generateNextRoundSchedule(String winningTeam, String losingTeam) {

		List<String> teams = schedule.getTeamsToBeScheduled();
		teams.add(winningTeam);
		schedule.setTeamsToBeScheduled(teams);

		List<String> teamsScheduled = schedule.getTeamsScheduled();
		teamsScheduled.remove(winningTeam);
		teamsScheduled.remove(losingTeam);
		schedule.setTeamsScheduled(teamsScheduled);

		if (schedule.getTeamsScheduled().isEmpty()) {
			if (schedule.getTeamsToBeScheduled().size() < 2) {
				utility.setSeasonOverStatus(true);
				utility.setSeasonWinner(schedule.getTeamsToBeScheduled().get(0));
				utility.setLastSeasonDay(this.currentDate);
			} else {
				schedule.generateScheduleOnTheFly(schedule.getTeamsToBeScheduled(), this.currentDate);
				List<String> teamsAlreadyScheduled = new ArrayList<String>(schedule.getTeamsToBeScheduled());
				schedule.setTeamsScheduled(teamsAlreadyScheduled);
				List<String> clearTeams = new ArrayList<>();
				schedule.setTeamsToBeScheduled(clearTeams);
			}
		}
	}

	private void resetGoals(String firstTeam, String secondTeam) {
		teamGoals.put(firstTeam, 0);
		teamGoals.put(secondTeam, 0);
	}

	public boolean shouldContinue() {
		return true;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
