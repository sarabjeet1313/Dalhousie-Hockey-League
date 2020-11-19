package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.sql.SQLException;

public class EndOfSeasonState implements ISimulationState{
    private String stateName = "SeasonEndState";
    private String nextStateName;
    private IUserOutput output;

    public EndOfSeasonState(IUserOutput output) {
        this.output = output;
    }

    public ISimulationState nextState(InternalStateContext context) {
        this.nextStateName = "None";
        return null;
    }

    public void doProcessing() {
        output.setOutput("Season has been simulated successfully.");
        output.sendOutput();
    }

    public boolean shouldContinue() {
        return false;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
