package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.PlayoffSchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;

public class GeneratePlayoffScheduleState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private String startDate;
    private String endDate;
    private String currentDate;
    private int season;
    private League leagueToSimulate;
    private IUserOutput output;
    private SeasonCalendar seasonCalendar;
    private IStandingsPersistance standingsDb;
    private InternalStateContext context;
    private ISchedule schedule;

    public GeneratePlayoffScheduleState(League leagueToSimulate, SeasonCalendar seasonCalendar, IStandingsPersistance standings, String currentDate, IUserOutput output, InternalStateContext context, int season) {
        this.stateName = StateConstants.GENERATE_PLAYOFF_SCHEDULE_STATE;
        this.season = season;
        this.output = output;
        this.seasonCalendar = seasonCalendar;
        this.currentDate = currentDate;
        this.standingsDb = standings;
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = new PlayoffSchedule(output, standings, season);
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

        output.setOutput("Scheduling the playoff season round-1 for simulation.");
        output.sendOutput();

        if(leagueToSimulate == null) {
            output.setOutput("Error scheduling season, passed league object is null. Please check");
            output.sendOutput();
            return;
        }

        schedule.generateSchedule(leagueToSimulate);
        output.setOutput("Playoff season has been scheduled successfully.");
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
