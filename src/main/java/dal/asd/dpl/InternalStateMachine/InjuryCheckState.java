package dal.asd.dpl.InternalStateMachine;

public class InjuryCheckState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;

    public InjuryCheckState (boolean gamesLeft, boolean tradeDeadlinePassed) {

        this.stateName = "InjuryCheck";

        if(gamesLeft)
            this.nextStateName = "SimulatePlayoffGame";
        else
        if(tradeDeadlinePassed)
            this.nextStateName = "Aging";
        else
            this.nextStateName = "Trading";

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
