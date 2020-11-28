package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;

public class TrophySystemAbstractFactory {
    public static IObserver createObserver(TrophySystemConstants awardType){
        IObserver observer = null;
        switch (awardType) {
            case PRESIDENT_TROPHY :
                observer = new TeamPointsObserver();
                break;
            case CALDER_MEMORIAL_TROPHY:
                observer = new PlayerGoalScorerObserver();
                break;
            case JACK_ADAMS_AWARD:
                observer = new BestCoachLeagueObserver();
                break;
            case VEZINA_TROPHY:
                observer = new GoalSaverObserver();
                break;
            case MAURICE_RICHARD_TROPHY:
                observer = new TopGoalScorerObserver();
                break;
            case ROB_HAWKEY_MEMORIAL_CUP:
                observer = new BestDefenceMenObserver();
                break;
            case PARTICIPATION_AWARD:
                observer = new ParticipantsAwardObserver();
                break;
            default:
                System.out.println("Please provide the valid award type..!");
                break;
        }

        return observer;
    }
}
