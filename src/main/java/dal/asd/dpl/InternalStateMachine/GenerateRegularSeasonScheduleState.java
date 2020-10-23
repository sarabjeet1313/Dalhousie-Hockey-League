package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Teams;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateRegularSeasonScheduleState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private String startDate;
    private String endDate;
    private Calendar seasonCalendar;
    private Leagues leagueToSimulate;
    private int currentYear;
    private IUserInput input;
    private IUserOutput output;
    private ISchedule schedule;

    public GenerateRegularSeasonScheduleState(Leagues leagueToSimulate, IUserInput input, IUserOutput output) {
        this.stateName = "GenerateRegularSeasonSchedule";
        this.leagueToSimulate = leagueToSimulate;
        this.seasonCalendar = Calendar.getInstance();
        this.schedule = new RegularSeasonSchedule(seasonCalendar, output);
        this.input = input;
        this.output = output;

        // getting current year
        this.currentYear = this.seasonCalendar.get(Calendar.YEAR);
        String year = String.valueOf(this.currentYear);

        // setting simulation start date to 30th September every year.
        this.startDate = "30-09-" + year;
        schedule.setCurrentDay(startDate);
        schedule.setFirstDay("01-10-" + year);

        // setting final day of regular season
        this.endDate = getFinalDayOfSeason() + "-04-" + String.valueOf(this.currentYear+1);
        schedule.setLastDay(endDate);
    }

    @Override
    public void nextState(InternalStateContext context) {
        this.nextStateName = "AdvanceTime";
        context.setState(new AdvanceTimeState(this.schedule, this.startDate, this.endDate, this.input, this.output));

    }

    public void doProcessing() {

        output.setOutput("Scheduling the regular season for simulation.");
        output.sendOutput();

        if(leagueToSimulate == null) {
            output.setOutput("Error scheduling season, passed league object is null. Please check");
            output.sendOutput();
            return;
        }

        schedule.generateSchedule(leagueToSimulate);
        output.setOutput("Regular season has been scheduled successfully.");
        output.sendOutput();

        String year = String.valueOf(this.currentYear);
        schedule.setCurrentDay("30-09-" + year);
    }

    public String getFinalDayOfSeason() {
        seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        seasonCalendar.set(Calendar.MONTH, Calendar.APRIL);
        seasonCalendar.set(Calendar.YEAR, this.currentYear + 1);
        return String.valueOf(seasonCalendar.get(Calendar.DATE));
    }

    public String getRegularSeasonEndDate() {
        return this.endDate;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }

}
