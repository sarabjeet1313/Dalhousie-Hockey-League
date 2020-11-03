package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.sql.SQLException;

import dpl.DplConstants.GeneratePlayoffConstants;
import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.PlayoffSchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

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
	private InternalStateContext context;
	private ISchedule schedule;

	public GeneratePlayoffScheduleState(League leagueToSimulate, SeasonCalendar seasonCalendar,
			IStandingsPersistance standings, IUserOutput output, InternalStateContext context, int season) {
		this.stateName = StateConstants.GENERATE_PLAYOFF_SCHEDULE_STATE;
		this.season = season;
		this.output = output;
		this.seasonCalendar = seasonCalendar;
		this.standingsDb = standings;
		this.leagueToSimulate = leagueToSimulate;
		this.schedule = new PlayoffSchedule(this.output, this.standingsDb, this.season);
		this.context = context;
		this.startDate = this.seasonCalendar.getPlayoffFirstDay();
		schedule.setFirstDay(startDate);
		schedule.setCurrentDay(startDate);
		this.endDate = this.seasonCalendar.getPlayoffLastDay();
		schedule.setLastDay(endDate);
		seasonCalendar.setLastSeasonDay(this.endDate);
	}

	public void nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.TRAINING_STATE;
	}

	public void doProcessing() {

		output.setOutput(GeneratePlayoffConstants.SCHEDULING_PLAYOFF.toString());
		output.sendOutput();
		try {
			if (null == leagueToSimulate) {
				output.setOutput(GeneratePlayoffConstants.SCHEDULING_ERROR.toString());
				output.sendOutput();
				return;
			}
			schedule.generateSchedule(leagueToSimulate);
			output.setOutput(GeneratePlayoffConstants.PLAYOFF_SUCCESSFUL.toString());
			output.sendOutput();
			schedule.setCurrentDay(this.startDate);
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
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
}