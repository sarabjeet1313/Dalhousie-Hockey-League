package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.Dpl.ErrorHandling.RetirementManagementException;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class InternalStateContext {
    public static ISimulationState currentState;
    public String currentStateName;

    public InternalStateContext(IUserInput input, IUserOutput output) {
        this.currentStateName = "";
    }

    public void doProcessing() throws RetirementManagementException {
        this.currentState.doProcessing();
    }

    public void nextState() {
        this.currentState.nextState(this);
    }

    public void setState(ISimulationState state) {
        this.currentStateName = state.getStateName();
        this.currentState = state;
    }
}
