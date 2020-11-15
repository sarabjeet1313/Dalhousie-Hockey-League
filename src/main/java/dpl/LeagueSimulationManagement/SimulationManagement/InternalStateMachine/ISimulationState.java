package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.Dpl.ErrorHandling.RetirementManagementException;

public interface ISimulationState {
    public void nextState(InternalStateContext context);

    public void doProcessing() throws RetirementManagementException;

    public String getStateName();

    public String getNextStateName();
}
