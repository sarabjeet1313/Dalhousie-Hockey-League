package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdvanceTimeState implements ISimulationState{

    private static String stateName;
    private static String nextStateName;
    private static String currentDate;
    private static IUserOutput output;
    private static IUserInput input;
    private static String endDate;
    private static Calendar schedule;

    public AdvanceTimeState(Calendar schedule, String startDate, String endDate, IUserInput input, IUserOutput output) {
        this.stateName = "AdvanceTime";
        this.schedule = schedule;
        this.currentDate = startDate;
        this.endDate = endDate;
        this.input = input;
        this.output = output;
    }

    public void nextState(InternalStateContext context) {

        context.currentDate = this.currentDate;

        if(this.currentDate.equals(this.endDate)){
            this.nextStateName = "GeneratePlayoffSchedule";
            context.setState(new GeneratePlayoffScheduleState());
        }
        else {
            this.nextStateName = "Training";
            context.setState(new TrainingState());
        }

    }

    public void doProcessing() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        try{
            schedule.setTime(dateFormat.parse(currentDate));
        }catch(ParseException e){
            output.setOutput("Exception while getting current date in Advance Time state");
            output.sendOutput();
        }

        // add a day to current date
        schedule.add(Calendar.DAY_OF_MONTH, 1);
        currentDate  = dateFormat.format(schedule.getTime());

    }

    public String getCurrentDate() {return this.currentDate;}

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
