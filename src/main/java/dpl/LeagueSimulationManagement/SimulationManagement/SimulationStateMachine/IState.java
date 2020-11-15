package dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine;

import dpl.Dpl.ErrorHandling.RetirementManagementException;

public interface IState {

    public void nextState(StateContext context);

    public void doProcessing() throws RetirementManagementException;

    public String getStateName();

    public String getNextStateName();
}
