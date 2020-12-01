package dpl.LeagueManagement.TeamManagement;

import java.util.List;

import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;

public interface ITeamManagementAbstractFactory {

	public Coach Coach();

	public Coach CoachWithParameters(String coachName, double skating, double shooting, double checking, double saving);

	public Coach CoachWithDbParameters(String coachName, double skating, double shooting, double checking,
			double saving, ICoachPersistance coachDb);

	public Conference Conference();

	public Conference ConferenceWithParameters(String conferenceName, List<Division> divisionList);

	public Division Division();

	public Division DivisionWithParameters(String divisionName, List<Team> teamList);

	public IInjuryManagement InjuryManagement();

	public League League();

	public League LeagueWithParameters(String leagueName, List<Conference> conferenceList, List<Player> freeAgents,
			List<Coach> coaches, List<Manager> managerList, GameplayConfig gameConfig);

	public League LeagueWithDbParameters(String leagueName, List<Conference> conferenceList, List<Player> freeAgents,
			List<Coach> coaches, List<Manager> managerList, GameplayConfig gameConfig, ILeaguePersistance leagueDb);

	public Manager Manager();

	public Manager ManagerWithParameters(String managerName, String personality);

	public Manager ManagerWithDbParameters(String managerName, String personality, IManagerPersistance managerDb);
 
	public Player Player();

	public Player PlayerWithParameters(String playerName, String position, boolean captain, int age, int skating,
			int shooting, int checking, int saving, boolean isInjured, boolean retireStatus, int daysInjured,
			boolean isActive, int birthDay, int birthMonth, int birthYear, boolean isDraftPlayer);

	public IRetirementManagement RetirementManagement();

	public ISerialize SerializeLeague();

	public Team Team();

	public Team TeamWithParameters(String teamName, Manager generalManager, Coach headCoach, List<Player> playerList,
			boolean isNewTeam);

	public IRosterManagement RosterManagement();
	
	public IAllStarGameManagement AllStarGameManagement();
	
	public IPlayerDraft PlayerDraft();
	
}

