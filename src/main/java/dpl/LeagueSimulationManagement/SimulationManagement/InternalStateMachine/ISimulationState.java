package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.ErrorHandling.RetirementManagementException;

public interface ISimulationState {
    public ISimulationState nextState(InternalStateContext context);

    public void doProcessing() throws RetirementManagementException;

    public String getStateName();

    public String getNextStateName();

    public boolean shouldContinue();

}
