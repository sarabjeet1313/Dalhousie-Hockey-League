package dpl.SimulationStateMachine;

import dpl.InternalStateMachine.InternalEndState;
import dpl.InternalStateMachine.InternalStartState;
import dpl.InternalStateMachine.InternalStateContext;
import dpl.Standings.IStandingsPersistance;
import dpl.TeamManagement.League;
import dpl.Trading.ITradePersistence;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.IUserOutput;

public class SimulateState implements IState {

    private IUserInput input;
    private IUserOutput output;
    private String teamName;
    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private ITradePersistence tradeDb;
    private IStandingsPersistance standingsDb;

    public SimulateState(IUserInput input, IUserOutput output, String teamName, League leagueToSimulate,
                         ITradePersistence tradeDb, IStandingsPersistance standingsDb) {
        this.input = input;
        this.output = output;
        this.teamName = teamName;
        this.stateName = "Simulate";
        this.leagueToSimulate = leagueToSimulate;
        this.tradeDb = tradeDb;
        this.standingsDb = standingsDb;
    }

    public void nextState(StateContext context) {
        this.nextStateName = "None";
        return;
    }

    public void doProcessing() {
        output.setOutput("Welcome to Simulation state :-) ");
        output.sendOutput();

        InternalStateContext stateContext = new InternalStateContext(input, output);
        stateContext.setState(new InternalStartState(input, output, teamName, leagueToSimulate, stateContext, tradeDb, standingsDb));
        stateContext.doProcessing();
        stateContext.nextState(); // simulation state
        stateContext.doProcessing();
        stateContext.setState(new InternalEndState(output));
        stateContext.doProcessing();
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
