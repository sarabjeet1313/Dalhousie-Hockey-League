package dal.asd.dpl.InternalStateMachine;

public class GenerateRegularSeasonScheduleState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;

    public GenerateRegularSeasonScheduleState() {
        this.stateName = "GenerateRegularSeasonSchedule";
        this.nextStateName = "NA";

    }

    public void nextState(InternalStateContext context) {

    }

    public void doProcessing() {

    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }

}
