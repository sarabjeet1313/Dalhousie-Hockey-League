package dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine;

import dpl.Dpl.ErrorHandling.RetirementManagementException;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class StateContext {
    private static IState currentState;
    private static IUserOutput output;
    private static IUserInput input;
    public String currentStateName;

    public StateContext(IUserInput input, IUserOutput output) {
        this.input = input;
        this.output = output;
        this.currentStateName = "";
    }

    public void nextState() {
        this.currentState.nextState(this);
    }

    public void setState(IState state) {
        this.currentStateName = state.getStateName();
        this.currentState = state;
    }

    public void doProcessing() throws RetirementManagementException {
        this.currentState.doProcessing();
    }
}
