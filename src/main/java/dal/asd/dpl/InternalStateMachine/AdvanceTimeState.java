package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.ScheduleConstants;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AdvanceTimeState implements ISimulationState{
    private String stateName;
    private String nextStateName;
    private String currentDate;
    private IUserOutput output;
    private InternalStateContext context;
    private String endDate;
    private Calendar calendar;
    private boolean isALastDay;
    public AdvanceTimeState(String startDate, String endDate, IUserOutput output, InternalStateContext context) {
        this.stateName = StateConstants.ADVANCE_TIME_STATE;
        this.currentDate = startDate;
        this.endDate = endDate;
        this.output = output;
        this.context = context;
        this.isALastDay = false;
        this.calendar = Calendar.getInstance();
    }

    public void nextState(InternalStateContext context) {
        if(isALastDay){
            this.nextStateName = StateConstants.GENERATE_PLAYOFF_SCHEDULE_STATE;
        }
        else {
            this.nextStateName = StateConstants.TRAINING_STATE;
        }
    }

    public void doProcessing() {
        output.setOutput("Advanced the date by one day");
        output.sendOutput();
        incrementCurrentDay();
    }

    public void incrementCurrentDay(){
        this.isALastDay = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ScheduleConstants.DATE_FORMAT);
        this.currentDate = LocalDate.parse(this.currentDate, formatter).plusDays(1).format(formatter).toString();
        if(this.currentDate.equals(this.endDate)) {
            this.isALastDay = true;
        }
    }

    public void setCurrentDate(String date) {
        this.currentDate = date;
    }

    public String getCurrentDate() {
        return this.currentDate;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }

    public boolean isALastDay() {
        return isALastDay;
    }
}
