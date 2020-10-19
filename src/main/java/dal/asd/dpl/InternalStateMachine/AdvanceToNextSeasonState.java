package dal.asd.dpl.InternalStateMachine;

public class AdvanceToNextSeasonState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private static boolean cupWinnerDeclared;

    public AdvanceToNextSeasonState (boolean cupWinnerDeclared) {

        this.stateName = "NextSeason";
        this.nextStateName = "Persist";

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
