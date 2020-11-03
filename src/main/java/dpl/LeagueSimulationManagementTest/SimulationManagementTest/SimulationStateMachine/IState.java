package dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine;

public interface IState {

    public void nextState(StateContext context);

    public void doProcessing();

    public String getStateName();

    public String getNextStateName();
}
