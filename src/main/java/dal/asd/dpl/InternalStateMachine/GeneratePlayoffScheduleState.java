package dal.asd.dpl.InternalStateMachine;

public class GeneratePlayoffScheduleState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private static boolean finalDay;

    public GeneratePlayoffScheduleState() {
        this.stateName = "GeneratePlayoffSchedule";
        this.nextStateName = "Training";
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
