package dpl.LeagueManagement.TeamManagement;

import java.util.List;

import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;

public class TeamManagementAbstractFactory implements ITeamManagementAbstractFactory {

	@Override
	public Coach Coach() {
		return new Coach();
	}

	@Override
	public Coach CoachWithParameters(String coachName, double skating, double shooting, double checking,
			double saving) {
		return new Coach(coachName, skating, shooting, checking, saving);
	}

	@Override
	public Coach CoachWithDbParameters(String coachName, double skating, double shooting, double checking,
			double saving, ICoachPersistance coachDb) {
		return new Coach(coachName, skating, shooting, checking, saving, coachDb);
	}

	@Override
	public Conference Conference() {
		return new Conference();
	}

	@Override
	public Conference ConferenceWithParameters(String conferenceName, List<Division> divisionList) {
		return new Conference(conferenceName, divisionList);
	}

	@Override
	public Division Division() {
		return new Division();
	}

	@Override
	public Division DivisionWithParameters(String divisionName, List<Team> teamList) {
		return new Division(divisionName, teamList);
	}

	@Override
	public IInjuryManagement InjuryManagement() {
		return new InjuryManagement();
	}

	@Override
	public League League() {
		return new League();
	}

	@Override
	public League LeagueWithParameters(String leagueName, List<Conference> conferenceList, List<Player> freeAgents,
			List<Coach> coaches, List<Manager> managerList, GameplayConfig gameConfig) {
		return new League(leagueName, conferenceList, freeAgents, coaches, managerList, gameConfig);
	}

	@Override
	public League LeagueWithDbParameters(String leagueName, List<Conference> conferenceList, List<Player> freeAgents,
			List<Coach> coaches, List<Manager> managerList, GameplayConfig gameConfig, ILeaguePersistance leagueDb) {
		return new League(leagueName, conferenceList, freeAgents, coaches, managerList, gameConfig, leagueDb);
	}

	@Override
	public Manager Manager() {
		return new Manager();
	}

	@Override
	public Manager ManagerWithParameters(String managerName, String personality) {
		return new Manager(managerName, personality);
	}

	@Override
	public Manager ManagerWithDbParameters(String managerName, String personality ,IManagerPersistance managerDb) {
		return new Manager(managerName, personality ,managerDb);
	}

	@Override
	public Player Player() {
		return new Player();
	}

	@Override
	public Player PlayerWithParameters(String playerName, String position, boolean captain, int age, int skating,
			int shooting, int checking, int saving, boolean isInjured, boolean retireStatus, int daysInjured,
			boolean isActive, int birthDay, int birthMonth, int birthYear, boolean isDraftPlayer) {
		return new Player(playerName, position, captain, age, skating, shooting, checking, saving, isInjured,
				retireStatus, daysInjured, isActive, birthDay, birthMonth, birthYear, isDraftPlayer);
	}

	@Override
	public IRetirementManagement RetirementManagement() {
		return new RetirementManagement();
	}

	@Override
	public ISerialize SerializeLeague() {
		return new SerializeLeague();
	}

	@Override
	public Team Team() {
		return new Team();
	}

	@Override
	public Team TeamWithParameters(String teamName, Manager generalManager, Coach headCoach, List<Player> playerList,
			boolean isNewTeam) {
		return new Team(teamName, generalManager, headCoach, playerList, isNewTeam);
	}

	@Override
	public IRosterManagement RosterManagement() {
		return new RosterManagement();
	}

	@Override
	public IAllStarGameManagement AllStarGameManagement() {
		return new AllStarGameManagement();
	}

	@Override
	public IPlayerDraft PlayerDraft() {
		return new PlayerDraft();
	}
}
