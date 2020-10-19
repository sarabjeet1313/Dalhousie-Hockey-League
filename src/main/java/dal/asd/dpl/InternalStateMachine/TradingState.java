package dal.asd.dpl.InternalStateMachine;

public class TradingState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;

    public TradingState () {

        this.stateName = "Trading";
        this.nextStateName = "None";

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
