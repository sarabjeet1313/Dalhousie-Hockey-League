package dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalEndState;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStartState;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

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
