package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.*;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerDraftState implements ISimulationState {
	private String stateName;
	private String nextStateName;
	private String currentDate;
	private IUserOutput output;
	private InternalStateContext context;
	private String endDate;
	private Calendar calendar;
	private SeasonCalendar utility;
	private League leagueToSimulate;
	private IStandingsPersistance standingsDb;
	private StandingInfo standings;
	private ISchedule schedule;
	private Training training;
	private IInjuryManagement injury;
	private IRetirementManagement retirement;
	private int season;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private ITeamManagementAbstractFactory teamManagement;
	private static final Logger log = Logger.getLogger(PlayerDraftState.class.getName());



	public PlayerDraftState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,
							IInjuryManagement injury, IRetirementManagement retirement, InternalStateContext context,
							SeasonCalendar utility, String currentDate, String endDate, int season, IUserOutput output) {
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.teamManagement = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.training = training;
		this.schedule = schedule;
		this.injury = injury;
		this.retirement = retirement;
		this.utility = utility;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.output = output;
		this.context = context;
		this.standings = standings;
		this.standingsDb = standingsDb;
		this.season = season;
		this.stateName = StateConstants.PLAYER_DRAFT;
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.NEXT_SEASON_STATE;
		return this.internalStateMachineFactory.AdvanceToNextSeasonState(leagueToSimulate, schedule, standingsDb, standings, injury, retirement, context,
				utility, currentDate, endDate, season, output);
	}


	public void doProcessing() {
		output.setOutput(StateConstants.PLAYER_DRAFT);
		output.sendOutput();
		IPlayerDraft playerDraft = teamManagement.PlayerDraft();
		List<String> teamNameList = standings.sortMapDraft();
		List<Team> teamList= playerDraft.generateDraftingTeams(teamNameList, leagueToSimulate);
		List<Player> playerList = playerDraft.generateDraftingPlayers(teamList.size());
		List<Team> updatedTeamList = playerDraft.startRoundDraft(teamList, playerList, leagueToSimulate);
		leagueToSimulate = playerDraft.postDrafting(updatedTeamList, leagueToSimulate);

		log.log(Level.INFO, StateConstants.PLAYER_DRAFT);
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
