package dpl.InternalStateMachine;

import java.util.Calendar;

import dpl.DplConstants.StateConstants;
import dpl.Schedule.ISchedule;
import dpl.Schedule.RegularSeasonSchedule;
import dpl.Schedule.SeasonCalendar;
import dpl.Standings.IStandingsPersistance;
import dpl.Standings.StandingInfo;
import dpl.TeamManagement.League;
import dpl.UserOutput.IUserOutput;

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

    public GenerateRegularSeasonScheduleState(League leagueToSimulate, IUserOutput output, int season, InternalStateContext context, IStandingsPersistance standingsDb) {
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
