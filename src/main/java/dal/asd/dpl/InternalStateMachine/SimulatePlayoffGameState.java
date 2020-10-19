package dal.asd.dpl.InternalStateMachine;

public class SimulatePlayoffGameState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;

    public SimulatePlayoffGameState () {

        this.stateName = "SimulatePlayoffGame";
        this.nextStateName = "InjuryCheck";

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
