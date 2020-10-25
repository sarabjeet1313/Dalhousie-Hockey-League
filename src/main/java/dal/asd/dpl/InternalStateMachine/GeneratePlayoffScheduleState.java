package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.util.Calendar;

public class GeneratePlayoffScheduleState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private boolean finalDay;
    private String startDate;
    private String endDate;
    private String currentDate;
    private Calendar seasonCalendar;
    private Leagues leagueToSimulate;
    private int currentYear;
    private IUserInput input;
    private IUserOutput output;
    private ISchedule schedule;

    public GeneratePlayoffScheduleState(Leagues leagueToSimulate, String currentDate, IUserInput input, IUserOutput output, int year) {
        this.stateName = "GeneratePlayoffSchedule";
        this.nextStateName = "Training";
        this.input = input;
        this.output = output;
        this.currentYear = year;
        this.currentDate = currentDate;
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = new PlayoffScheduleState(seasonCalendar, output);

        // setting first day to 2nd Wed in April
        this.startDate = getFirstDayOfPlayoff() + "-04-" + Integer.toString(this.currentYear+1);
        schedule.setFirstDay(startDate);

        schedule.setCurrentDay(startDate);

        // setting last day to 1st June
        this.endDate = "01-06-" + Integer.toString(this.currentYear+1);
        schedule.setLastDay(endDate);

    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = "Training";
        context.setState(new TrainingState(leagueToSimulate, schedule, currentDate, input, output));
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

    public String getFirstDayOfPlayoff() {
        seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
        seasonCalendar.set(Calendar.MONTH, Calendar.APRIL);
        seasonCalendar.set(Calendar.YEAR, this.currentYear+1);
        return String.valueOf(seasonCalendar.get(Calendar.DATE));
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
