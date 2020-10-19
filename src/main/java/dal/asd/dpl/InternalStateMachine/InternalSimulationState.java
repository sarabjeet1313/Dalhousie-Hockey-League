package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalSimulationState implements ISimulationState{

    private static IUserInput input;
    private static IUserOutput output;
    private static int totalSeasons;
    private static String teamName;
    private static String stateName;
    private static String nextStateName;
    private static Leagues leagueToSimulate;

    public InternalSimulationState(IUserInput input, IUserOutput output, int seasons, String teamName, Leagues leagueToSimulate){
        this.input = input;
        this.output = output;
        this.totalSeasons = seasons;
        this.teamName = teamName;
        this.leagueToSimulate = leagueToSimulate;
        this.stateName = "Simulate";
    }

    public void nextState(InternalStateContext context){
        this.nextStateName = "GenerateRegularSeasonSchedule";
        context.setState(new GenerateRegularSeasonScheduleState(leagueToSimulate, input, output));
    }

    public void doProcessing(){
        for(int i=1; i <= totalSeasons; i++){
            output.setOutput("Season " + i + " simulated for " + teamName + " ..." );
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
