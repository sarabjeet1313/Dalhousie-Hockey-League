package dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine;

import dpl.ErrorHandling.RetirementManagementException;

public interface IState {

    public void nextState(StateContext context);

    public void doProcessing() throws RetirementManagementException;

    public String getStateName();

    public String getNextStateName();
}
