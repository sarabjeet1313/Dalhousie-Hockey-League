package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
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
    private int year;
    private Calendar calendar;
    private boolean isALastDay;
    private ISchedule matchSchedule;
    private Leagues leagueToSimulate;
    private ScheduleUtlity utility;

    public AdvanceTimeState(ISchedule schedule, Leagues leagueToSimulate, String startDate, String endDate, ScheduleUtlity utlity, IUserOutput output, InternalStateContext context) {
        this.stateName = "AdvanceTime";
        this.currentDate = startDate;
        this.endDate = endDate;
        this.output = output;
        this.utility = utlity;
        this.context = context;
        this.isALastDay = false;
        this.matchSchedule = schedule;
        this.calendar = Calendar.getInstance();
        this.leagueToSimulate = leagueToSimulate;
    }

    public void nextState(InternalStateContext context) {
        if(isALastDay){
            this.nextStateName = "GeneratePlayoffSchedule";
        }
        else {
            this.nextStateName = "Training";
        }
    }

    public void doProcessing() {
        output.setOutput("Advanced the date by one day");
        output.sendOutput();
        incrementCurrentDay();
    }

    public void incrementCurrentDay(){
        this.isALastDay = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
