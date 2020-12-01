package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.*;
import dpl.LeagueManagement.TrophySystem.*;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrophySystemState implements ISimulationState {
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
	private IInjuryManagement injury;
	private IRetirementManagement retirement;
	private int season;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private static final Logger log = Logger.getLogger(TrophySystemState.class.getName());
	private ITrophySystemAbstractFactory trophySystemAbstractFactory = SystemConfig.getSingleInstance()
			.getTrophySystemAbstractFactory();


	public TrophySystemState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings, IInjuryManagement injury, IRetirementManagement retirement, InternalStateContext context,
							 SeasonCalendar utility, String currentDate, String endDate, int season, IUserOutput output) {
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.injury = injury;
		this.retirement = retirement;
		this.schedule = schedule;
		this.utility = utility;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.output = output;
		this.context = context;
		this.standings = standings;
		this.standingsDb = standingsDb;
		this.season = season;
		this.stateName = StateConstants.TROPHY_STATE;
	}

	static {
		GoalSave.getInstance().attach(TrophySystemAbstractFactory.createObserver(TrophySystemConstants.VEZINA_TROPHY));
		TopGoalScore.getInstance().attach(TrophySystemAbstractFactory.createObserver((TrophySystemConstants.MAURICE_RICHARD_TROPHY)));
		BestDefenceMen.getInstance().attach(TrophySystemAbstractFactory.createObserver(TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP));
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.PLAYER_DRAFT;
		return this.internalStateMachineFactory.PlayerDraftState(leagueToSimulate, schedule, standingsDb, standings, injury, retirement, context,
				utility, currentDate, endDate, season, output);
	}

	public void doProcessing() {
		output.setOutput(StateConstants.TROPHY_STATE);
		output.sendOutput();
		sendUpdatesToTrophy();
		trophySystemAbstractFactory.AwardedTrophy().trophyStanleyCup(season + 1);
		TrophyHistory.getInstance().displayTrophyHistory();
		trophySystemAbstractFactory.AwardedTrophy().trophyEndOfSeason(season + 1);
		TrophyHistory.getInstance().displayTrophyHistory();
		log.log(Level.INFO, StateConstants.TROPHY_STATE);
	}

	private void sendUpdatesToTrophy() {
		List<Conference> conferenceList = leagueToSimulate.getConferenceList();
		for (Conference conference : conferenceList) {
			List<Division> divisionList = conference.getDivisionList();
			for (Division division : divisionList) {
				List<Team> teamList = division.getTeamList();
				for (Team team : teamList) {
					List<Player> playerList = team.getPlayerList();
					for (Player player : playerList) {
						GoalSave.getInstance().notifyPlayerSaveGoal(player);
						TopGoalScore.getInstance().notifyPlayerGoal(player);
						BestDefenceMen.getInstance().notifyWhenPlayerGoal(player);
					}
				}
			}
		}
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
