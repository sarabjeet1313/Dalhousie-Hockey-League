package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class AwardedTrophy {
    private IUserOutput output;

    private void bestTeam(int year) {
        String bestTeam = TeamPoint.getInstance().getBestTeam();
        TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.PRESIDENT_TROPHY.toString(), bestTeam);
        output.setOutput(TrophySystemConstants.BEST_TEAM.toString() + bestTeam);
        output.sendOutput();
    }

    private void bestPlayer(int year) {
        Player player = PlayerGoalScore.getInstance().getBestPlayer();
        TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.CALDER_MEMORIAL_TROPHY.toString(), player.getPlayerName());
        output.setOutput(TrophySystemConstants.BEST_PLAYER.toString() + player.getPlayerName());
        output.sendOutput();
    }

    private void bestGoalie(int year) {
        Player player = GoalSave.getInstance().getBestGoalSaver();
        TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.VEZINA_TROPHY.toString(), player.getPlayerName());
        output.setOutput(TrophySystemConstants.BEST_GOALIE.toString() + player.getPlayerName());
        output.sendOutput();
    }

    private void bestCoach(int year) {
        Coach coach = BestCoachLeague.getInstance().getBestCoach();
        TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.JACK_ADAMS_AWARD.toString(), coach.getCoachName());
        output.setOutput(TrophySystemConstants.BEST_COACH.toString() + coach.getCoachName());
        output.sendOutput();

    }

    private void bestScore(int year) {
        Player player = TopGoalScore.getInstance().getTopGoalScore();
        TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.MAURICE_RICHARD_TROPHY.toString(), player.getPlayerName());
        output.setOutput(TrophySystemConstants.BEST_SCORE.toString() + player.getPlayerName());
        output.sendOutput();
    }

    private void bestDefencemen(int year) {
        Player player = BestDefenceMen.getInstance().getBestDefenceMen();
        TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP.toString(), player.getPlayerName());
        output.setOutput(TrophySystemConstants.BEST_DEFENCEMEN.toString() + player.getPlayerName());
        output.sendOutput();
    }

    private void participationTeam(int year) {
        String participatedTeam = ParticipantAward.getInstance().getTeamWithLowestPoints();
        TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.PARTICIPATION_AWARD.toString(), participatedTeam);
        output.setOutput(TrophySystemConstants.PARTICIPATION_TEAM.toString() + participatedTeam);
        output.sendOutput();
    }

    public void trophy(int year) {
        bestTeam(year);
        bestPlayer(year);
        bestGoalie(year);
        bestCoach(year);
        bestScore(year);
        bestDefencemen(year);
        participationTeam(year);
    }

}
