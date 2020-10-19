package dal.asd.dpl.InternalStateMachine;

public class AgingState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;

    public AgingState (boolean cupWinnerDeclared) {

        this.stateName = "Aging";
        if(cupWinnerDeclared) {
            this.nextStateName = "NextSeason";
        }
        else {
            this.nextStateName = "Persist";
        }

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
