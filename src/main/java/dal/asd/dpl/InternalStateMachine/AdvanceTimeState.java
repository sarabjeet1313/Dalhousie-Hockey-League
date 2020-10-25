package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdvanceTimeState implements ISimulationState{

    private String stateName;
    private String nextStateName;
    private String currentDate;
    private IUserOutput output;
    private IUserInput input;
    private String endDate;
    private int year;
    private Calendar calendar;
    private boolean isALastDay;
    private ISchedule matchSchedule;
    private Leagues leagueToSimulate;

    public AdvanceTimeState(ISchedule schedule, Leagues leagueToSimulate, Calendar calendar, String startDate, String endDate, int year, IUserInput input, IUserOutput output) {
        this.stateName = "AdvanceTime";
        this.currentDate = startDate;
        this.endDate = endDate;
        this.input = input;
        this.output = output;
        this.year = year;
        this.isALastDay = false;
        this.matchSchedule = schedule;
        this.calendar = calendar;
        this.leagueToSimulate = leagueToSimulate;
    }

    public void nextState(InternalStateContext context) {
        if(isALastDay){
            this.nextStateName = "GeneratePlayoffSchedule";
            context.setState(new GeneratePlayoffScheduleState(leagueToSimulate, currentDate, input, output, year));
        }
        else {
            this.nextStateName = "Training";
            context.setState(new TrainingState(leagueToSimulate, matchSchedule, currentDate, input, output));
        }
    }

    public void doProcessing() {
        matchSchedule.incrementCurrentDay();
    }

    public void incrementCurrentDay(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            calendar.setTime(dateFormat.parse(currentDate));
        } catch (ParseException e) {
            output.setOutput("Exception while getting current date in Advance Time state");
            output.sendOutput();
        }
        // add a day to current date if it is not last date for the season
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        this.currentDate = dateFormat.format(calendar.getTime());

        if(this.currentDate == this.endDate) {
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
}
