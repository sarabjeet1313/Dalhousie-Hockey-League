package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

public interface ISimulationState {
    public ISimulationState nextState(InternalStateContext context);

    public void doProcessing();

    public String getStateName();

    public String getNextStateName();

    public boolean shouldContinue();

}
