package dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine;

import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.InternalEndState;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.InternalStartState;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Trading.ITradePersistence;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

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
