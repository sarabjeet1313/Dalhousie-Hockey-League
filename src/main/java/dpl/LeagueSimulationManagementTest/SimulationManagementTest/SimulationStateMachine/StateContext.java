package dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine;

import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

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

    public void doProcessing() {
        this.currentState.doProcessing();
    }
}
