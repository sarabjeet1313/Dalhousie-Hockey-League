package dal.asd.dpl.SimulationStateMachine;

public interface IState {

    public void nextState(StateContext context);
    public void doProcessing();
}
