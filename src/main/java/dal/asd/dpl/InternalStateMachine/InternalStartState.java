package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalStartState implements ISimulationState {
    private IUserInput input;
    private IUserOutput output;
    private String teamName;
    public int numOfSeasons;
    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private InternalStateContext context;

    public InternalStartState(IUserInput input, IUserOutput output, String teamName, League leagueToSimulate, InternalStateContext context){
        this.input = input;
        this.output = output;
        this.teamName = teamName;
        this.stateName = StateConstants.START_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.context = context;
        this.numOfSeasons = 0;
    }

    public void nextState(InternalStateContext context){
        this.nextStateName = StateConstants.INTERNAL_SIMULATION_STATE;
        context.setState(new InternalSimulationState(input, output, numOfSeasons, teamName, leagueToSimulate, context));
    }

    public void doProcessing(){
        output.setOutput("How many seasons you want to simulate for " + teamName + " ?");
        output.sendOutput();
        input.setInput();
        this.numOfSeasons = Integer.parseInt(input.getInput());
    }

    public String getStateName(){
        return this.stateName;
    }

    public String getNextStateName(){
        return this.nextStateName;
    }
}
