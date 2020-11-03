package dpl.InternalStateMachine;

import dpl.DplConstants.StateConstants;
import dpl.Standings.IStandingsPersistance;
import dpl.TeamManagement.League;
import dpl.Trading.ITradePersistence;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.IUserOutput;

public class InternalStartState implements ISimulationState {
    private IUserInput input;
    private IUserOutput output;
    private String teamName;
    public int numOfSeasons;
    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private InternalStateContext context;
    private ITradePersistence tradeDb;
    private IStandingsPersistance standingsDb;

    public InternalStartState(IUserInput input, IUserOutput output, String teamName, League leagueToSimulate,
                              InternalStateContext context, ITradePersistence tradeDb, IStandingsPersistance standingsDb) {
        this.input = input;
        this.output = output;
        this.teamName = teamName;
        this.stateName = StateConstants.START_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.context = context;
        this.numOfSeasons = 0;
        this.tradeDb = tradeDb;
        this.standingsDb = standingsDb;
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = StateConstants.INTERNAL_SIMULATION_STATE;
        context.setState(new InternalSimulationState(input, output, numOfSeasons, teamName, leagueToSimulate, context, tradeDb, standingsDb));
    }

    public void doProcessing() {
        output.setOutput("How many seasons you want to simulate for " + teamName + " ?");
        output.sendOutput();
        input.setInput();
        this.numOfSeasons = Integer.parseInt(input.getInput());
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
