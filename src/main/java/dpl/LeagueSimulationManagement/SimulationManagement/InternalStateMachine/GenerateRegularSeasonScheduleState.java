package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.sql.SQLException;
import java.util.Calendar;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.RegularSeasonSchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class GenerateRegularSeasonScheduleState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private String startDate;
	private String endDate;
	private Calendar calendar;
	private League leagueToSimulate;
	private StandingInfo standings;
	private IUserOutput output;
	private InternalStateContext context;
	private ISchedule schedule;
	private SeasonCalendar seasonCalendar;

	public GenerateRegularSeasonScheduleState(League leagueToSimulate, IUserOutput output, int season,
			InternalStateContext context, IStandingsPersistance standingsDb) {
		this.stateName = StateConstants.GENERATE_REGULAR_SEASON_SCHEDULE_STATE;
		this.leagueToSimulate = leagueToSimulate;
		this.standings = new StandingInfo(leagueToSimulate, season, standingsDb);
		this.calendar = Calendar.getInstance();
		this.schedule = new RegularSeasonSchedule(calendar, output);
		this.seasonCalendar = new SeasonCalendar(season, output);
		this.output = output;
		this.context = context;
		this.startDate = seasonCalendar.getRegularSeasonStartDay();
		schedule.setCurrentDay(this.startDate);
		schedule.setFirstDay(seasonCalendar.getRegularSeasonFirstDay());
		this.endDate = seasonCalendar.getRegularSeasonLastDay();
		schedule.setLastDay(this.endDate);
		seasonCalendar.setLastSeasonDay(this.endDate);
	}

	public void nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.ADVANCE_TIME_STATE;
	}

	public void doProcessing() {
		output.setOutput("Scheduling the regular season for simulation.");
		output.sendOutput();
		try {
			standings.initializeStandings();
			if (null == leagueToSimulate) {
				output.setOutput("Error scheduling season, passed league object is null. Please check");
				output.sendOutput();
				return;
			}
			schedule.generateSchedule(leagueToSimulate);
			output.setOutput("Regular season has been scheduled successfully.");
			output.sendOutput();
			schedule.setCurrentDay(seasonCalendar.getRegularSeasonStartDay());
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

	public String getRegularSeasonEndDate() {
		return this.endDate;
	}

	public String getRegularSeasonStartDate() {
		return this.startDate;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}

	public ISchedule getSchedule() {
		return schedule;
	}
}
