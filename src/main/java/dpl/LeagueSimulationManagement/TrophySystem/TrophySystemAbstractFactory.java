package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;

public class TrophySystemAbstractFactory {
    public static IObserver createObserver(TrophySystemConstants awardType){
        IObserver observer = null;
        switch (awardType) {
            case PRESIDENT_TROPHY :
                observer = new TeamPointObserver();
                break;
            case CALDER_MEMORIAL_TROPHY:
                observer = new PlayerGoalScoreObserver();
                break;
            case JACK_ADAMS_AWARD:
                observer = new BestCoachLeagueObserver();
                break;
            case VEZINA_TROPHY:
                observer = new GoalSaverObserver();
                break;
            case MAURICE_RICHARD_TROPHY:
                observer = new TopGoalScoreObserver();
                break;
            case ROB_HAWKEY_MEMORIAL_CUP:
                observer = new BestDefenceMenObserver();
                break;
            case PARTICIPATION_AWARD:
                observer = new ParticipantAwardObserver();
                break;
            default:
                System.out.println("Please provide the valid award type..!");
                break;
        }

        return observer;
    }
}
