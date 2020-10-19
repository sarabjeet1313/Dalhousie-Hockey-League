package dal.asd.dpl.InternalStateMachine;

public class PersistState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;

    public PersistState (boolean cupWinnerDeclared) {

        this.stateName = "Persist";
        if(cupWinnerDeclared) {
            this.nextStateName = "GenerateRegularSeasonSchedule";
        }
        else {
            this.nextStateName = "AdvanceTime";
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
