package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.UserOutputAbstractFactory;
import dpl.SystemConfig;

public class TrophySystemAbstractFactory {
    private static IUserOutput output;

    private TrophySystemAbstractFactory()
    {
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
    }

    public static IObserver createObserver(TrophySystemConstants awardType) {
        IObserver observer = null;
        switch (awardType) {
            case PRESIDENT_TROPHY:
                observer = new TeamPointObserver();
                break;
            case CALDER_MEMORIAL_TROPHY:
                observer = new PlayerGoalScoreObserver();
                break;
            case JACK_ADAMS_AWARD:
                observer = new BestCoachLeagueObserver();
                break;
            case VEZINA_TROPHY:
                observer = new GoalSaveObserver();
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
                output.setOutput(TrophySystemConstants.ARROW.toString());
                output.sendOutput();
                break;
        }

        return observer;
    }
}
