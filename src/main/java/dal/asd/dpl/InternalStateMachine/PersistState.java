package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserOutput.IUserOutput;

public class PersistState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private Leagues leagueToSimulate;
    private ISchedule schedule;
    private Standings standings;
    private InternalStateContext context;
    private ScheduleUtlity utility;
    private String currentDate;
    private String lastDate;
    private IUserOutput output;

    public PersistState (Leagues leagueToSimulate, ISchedule schedule, Standings standings, InternalStateContext context, ScheduleUtlity utility, String currentDate, IUserOutput output) {
        this.stateName = "Persist";
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.standings = standings;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.lastDate = utility.getRegularSeasonLastDay();
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        if(utility.getSeasonOverStatus()) {
            this.nextStateName = "GenerateRegularSeasonSchedule";
            return;
        }
        else {
            this.nextStateName = "AdvanceTime";
        }
    }

    public void doProcessing() {

        output.setOutput("Inside persist state");
        output.sendOutput();
        //TODO persist data to db

        // Persist calls from Object model classes

//        standings.initializeStandings();
//        standings.updateStandings();

    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
