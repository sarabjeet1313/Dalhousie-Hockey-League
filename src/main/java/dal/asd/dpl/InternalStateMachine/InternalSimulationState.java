package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalSimulationState implements ISimulationState{

    private IUserInput input;
    private IUserOutput output;
    private int season;
    private int totalSeasons;
    private String teamName;
    private String stateName;
    private String nextStateName;
    private Leagues leagueToSimulate;

    public InternalSimulationState(IUserInput input, IUserOutput output, int seasons, String teamName, Leagues leagueToSimulate){
        this.input = input;
        this.output = output;
        this.totalSeasons = seasons;
        this.teamName = teamName;
        this.leagueToSimulate = leagueToSimulate;
        this.stateName = "Simulate";
        this.season = 0;
    }

    public void nextState(InternalStateContext context){
        this.nextStateName = "GenerateRegularSeasonSchedule";
        context.setState(new GenerateRegularSeasonScheduleState(leagueToSimulate, input, output,season));
    }

    public void doProcessing(){
        for(int i=1; i <= totalSeasons; i++){
            this.season = i-1;
            output.setOutput("Season " + i + " is simulating.");
            output.sendOutput();
        }
    }

    public String getStateName(){
        return this.stateName;
    }

    public String getNextStateName(){
        return this.nextStateName;
    }

}
