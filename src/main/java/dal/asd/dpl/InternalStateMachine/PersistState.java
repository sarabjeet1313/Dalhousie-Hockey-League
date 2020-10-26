package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;

public class PersistState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private ISchedule schedule;
    private InternalStateContext context;
    private ScheduleUtlity utility;
    private String currentDate;
    private String lastDate;
    private IUserOutput output;

    public PersistState (League leagueToSimulate, ISchedule schedule, InternalStateContext context, ScheduleUtlity utility, String currentDate, IUserOutput output) {
        this.stateName = "Persist";
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.lastDate = utility.getRegularSeasonLastDay();
        this.output = output;

      //  doProcessing();
    }

    public void nextState(InternalStateContext context) {
        if(/*utility.getSeasonOverStatus()*/ false) {
            this.nextStateName = "GenerateRegularSeasonSchedule";
            return;
        }
        else {
            this.nextStateName = "AdvanceTime";
            context.setState(new AdvanceTimeState(this.schedule, this.leagueToSimulate, this.currentDate, this.lastDate, this.utility, this.output, context));
        }
    }

    public void doProcessing() {

        output.setOutput("Inside persist state");
        output.sendOutput();
        //TODO persist data to db
     //   nextState(this.context);
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
