package dal.asd.dpl.InternalStateMachine;

public class AdvanceTimeState implements ISimulationState{

    private static String stateName;
    private static String nextStateName;
    private static boolean finalDay;

    public AdvanceTimeState(boolean finalDay) {
        this.stateName = "AdvanceTime";

        if(finalDay)
            this.nextStateName = "GeneratePlayoffSchedule";
        else
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
