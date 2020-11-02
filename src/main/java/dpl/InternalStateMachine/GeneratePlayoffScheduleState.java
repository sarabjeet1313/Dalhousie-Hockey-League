package dpl.InternalStateMachine;

import dpl.DplConstants.GeneratePlayoffConstants;
import dpl.DplConstants.StateConstants;
import dpl.Schedule.ISchedule;
import dpl.Schedule.PlayoffSchedule;
import dpl.Schedule.SeasonCalendar;
import dpl.Standings.IStandingsPersistance;
import dpl.TeamManagement.League;
import dpl.UserOutput.IUserOutput;

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

    public GeneratePlayoffScheduleState(League leagueToSimulate, SeasonCalendar seasonCalendar, IStandingsPersistance standings, IUserOutput output, InternalStateContext context, int season) {
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

        if (null == leagueToSimulate) {
            output.setOutput(GeneratePlayoffConstants.SCHEDULING_ERROR.toString());
            output.sendOutput();
            return;
        }

        schedule.generateSchedule(leagueToSimulate);
        output.setOutput(GeneratePlayoffConstants.PLAYOFF_SUCCESSFUL.toString());
        output.sendOutput();
        schedule.setCurrentDay(this.startDate);
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