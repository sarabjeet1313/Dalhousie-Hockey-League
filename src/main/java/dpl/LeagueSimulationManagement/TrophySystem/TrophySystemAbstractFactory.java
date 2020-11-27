package dpl.LeagueSimulationManagement.TrophySystem;

public class TrophySystemAbstractFactory {

    public static final String PRESIDENTS_TROPHY = "PRESIDENTS_TROPHY";
    public static final String CALDER_MEMORIAL_TROPHY = "CALDER_MEMORIAL_TROPHY";


    public static IObserver createObserver(String awardType){
        IObserver observer = null;

        switch (awardType) {
            case PRESIDENTS_TROPHY :
                observer = new TeamPointsObserver();
                break;
            case CALDER_MEMORIAL_TROPHY:
                observer = new PlayerGoalScorerObserver();
                break;
            default:
                System.out.println("Please provide the valid award type..!");
                break;
        }

        return observer;
    }
}
