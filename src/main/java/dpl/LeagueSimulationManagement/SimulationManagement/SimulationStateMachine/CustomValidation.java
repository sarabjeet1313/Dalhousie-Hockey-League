package dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine;

public class CustomValidation {

    public static boolean isNumber(String string) {
        return string.matches("^\\d+$");
    }
}
