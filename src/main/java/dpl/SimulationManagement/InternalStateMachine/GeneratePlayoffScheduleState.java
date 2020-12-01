package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.IScheduleAbstractFactory;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.*;
import dpl.SimulationManagement.GeneratePlayoffConstants;
import dpl.SimulationManagement.StateConstants;
import dpl.LeagueManagement.TrophySystem.*;
import dpl.LeagueManagement.TrophySystem.PlayerGoalScore;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePlayoffScheduleState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private String startDate;
	private String endDate;
	private int season;
	private League leagueToSimulate;
	private IUserOutput output;
	private SeasonCalendar seasonCalendar;
	private IStandingsPersistance standingsDb;
	private StandingInfo standings;
	private InternalStateContext context;
	private String currentDate;
	private String lastRegularSeasonDay;
	private ISchedule schedule;
	private Training training;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private IScheduleAbstractFactory scheduleAbstractFactory;
	private ITrophySystemAbstractFactory trophySystemAbstractFactory = SystemConfig.getSingleInstance().getTrophySystemAbstractFactory();

	private static final Logger log = Logger.getLogger(GeneratePlayoffScheduleState.class.getName());

	public GeneratePlayoffScheduleState(League leagueToSimulate, SeasonCalendar seasonCalendar,
										IStandingsPersistance standingsDb, StandingInfo standings, IUserOutput output, InternalStateContext context, int season,
										String currentDate, String endDate) {
		this.stateName = StateConstants.GENERATE_PLAYOFF_SCHEDULE_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.scheduleAbstractFactory = SystemConfig.getSingleInstance().getScheduleAbstractFactory();
		this.season = season;
		this.output = output;
		this.seasonCalendar = seasonCalendar;
		this.standingsDb = standingsDb;
		this.standings = standings;
		this.leagueToSimulate = leagueToSimulate;
		this.schedule = scheduleAbstractFactory.PlayoffSchedule(this.output, this.standingsDb, this.standings, this.season);
		this.context = context;
		this.training = SystemConfig.getSingleInstance().getGameplayConfigurationAbstractFactory().Training();
		this.startDate = this.seasonCalendar.getPlayoffFirstDay();
		schedule.setFirstDay(startDate);
		schedule.setCurrentDay(startDate);
		this.currentDate = currentDate;
		this.lastRegularSeasonDay = endDate;
		this.endDate = this.seasonCalendar.getPlayoffLastDay();
		schedule.setLastDay(endDate);
		seasonCalendar.setLastSeasonDay(this.endDate);
	}
	static {
		PlayerGoalScore.getInstance().attach(TrophySystemAbstractFactory.createObserver(TrophySystemConstants.CALDER_MEMORIAL_TROPHY));
		ParticipantAward.getInstance().attach(TrophySystemAbstractFactory.createObserver(TrophySystemConstants.PARTICIPATION_AWARD));

	}
	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.TRAINING_STATE;
		return this.internalStateMachineFactory.TrainingState(leagueToSimulate, training, schedule, seasonCalendar, currentDate,
				lastRegularSeasonDay, output, context, standingsDb, standings, season);
	}

	public void doProcessing() {
		output.setOutput(GeneratePlayoffConstants.SCHEDULING_PLAYOFF.toString());
		output.sendOutput();
		try {
			if (null == leagueToSimulate) {
				log.log(Level.SEVERE, GeneratePlayoffConstants.SCHEDULING_ERROR.toString());
				return;
			}
			output.setOutput("\nSeason stats after Regular season matches : \n");
			output.sendOutput();
			sendUpdatesToTrophy();
			standings.showStats();
			standings.resetStats();
			schedule.generateSchedule(leagueToSimulate);
			log.log(Level.INFO, GeneratePlayoffConstants.PLAYOFF_SUCCESSFUL.toString());
			schedule.setCurrentDay(this.startDate);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			output.setOutput(e.getMessage());
			output.sendOutput();
			System.exit(1);
		}
		log.log(Level.INFO, GeneratePlayoffConstants.SCHEDULING_PLAYOFF.toString());
	}

	private void sendUpdatesToTrophy() {
		List<Conference> conferenceList = leagueToSimulate.getConferenceList();
		for (Conference conference : conferenceList) {
			List<Division> divisionList = conference.getDivisionList();
			for (Division division : divisionList) {
				List<Team> teamList = division.getTeamList();
				for (Team team : teamList) {
					List<Player> playerList = team.getPlayerList();
					ParticipantAward.getInstance().notifyParticipatedTeam(team);
					for (Player player : playerList) {
						PlayerGoalScore.getInstance().notifyWhenPlayerGoal(player);
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

	public ISchedule getSchedule() {
		return this.schedule;
	}

	public void setSchedule(ISchedule schedule) {
		this.schedule = schedule;
	}
}