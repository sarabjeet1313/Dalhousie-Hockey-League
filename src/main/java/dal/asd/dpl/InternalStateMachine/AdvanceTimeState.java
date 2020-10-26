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

        //doProcessing();
    }

    public void nextState(InternalStateContext context) {
        if(isALastDay){
            this.nextStateName = "GeneratePlayoffSchedule";
            context.setState(new GeneratePlayoffScheduleState(leagueToSimulate, utility, currentDate, output, context));
        }
        else {
            this.nextStateName = "Training";
            context.setState(new TrainingState(leagueToSimulate, matchSchedule, utility,  currentDate, output, context));
        }
    }

    public void doProcessing() {
        output.setOutput("Advanced the date by one day");
        output.sendOutput();

        incrementCurrentDay();
       // nextState(this.context);
    }

    public void incrementCurrentDay(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.currentDate = LocalDate.parse(this.currentDate, formatter).plusDays(1).format(formatter).toString();

        if(this.currentDate.equals(this.endDate)) {
            this.isALastDay = true;
        }
    }

    public String getCurrentDate() {return this.currentDate;}

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
