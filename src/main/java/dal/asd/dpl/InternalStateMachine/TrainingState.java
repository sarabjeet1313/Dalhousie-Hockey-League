package dal.asd.dpl.InternalStateMachine;

public class TrainingState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private static boolean finalDay;

    public TrainingState () {
        this.stateName = "Training";

        if(/*gamesLeft*/ true)
            this.nextStateName = "SimulatePlayoffGame";
        else
            if(/*tradeDeadlinePassed*/ true)
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
