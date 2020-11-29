package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class AwardedTrophy {

    private void bestTeam(int year) {
        String bestTeam = TeamPoint.getInstance().getBestTeam();
        TrophyHistory.getInstance().addTrophy(year, "President's Trophy", bestTeam);
        System.out.println("Best Team :" + bestTeam);
    }

    private void bestPlayer(int year) {
        Player player = PlayerGoalScore.getInstance().getBestPlayer();
        TrophyHistory.getInstance().addTrophy(year, "Calder Memorial Trophy", player.getPlayerName());
        System.out.println("Best Player :" + player.getPlayerName());
    }

    private void bestGoalie(int year) {
        Player player = GoalSave.getInstance().getBestGoalSaver();
        TrophyHistory.getInstance().addTrophy(year, "Vezina Trophy", player.getPlayerName());
        System.out.println("Best Goalie :" + player.getPlayerName());
    }

    private void bestCoach(int year) {
        Coach coach = BestCoachLeague.getInstance().getBestCoach();
        TrophyHistory.getInstance().addTrophy(year, "Jack Adam's Award", coach.getCoachName());
        System.out.println("Best Coach :" + coach.getCoachName());
    }

    private void bestScorer(int year) {
        Player player = TopGoalScore.getInstance().getTopGoalScore();
        TrophyHistory.getInstance().addTrophy(year, "Maurice Richard Trophy", player.getPlayerName());
        System.out.println("Best Scorer :" + player.getPlayerName());
    }

    private void bestDefencemen(int year) {
        Player player = BestDefenceMen.getInstance().getBestDefenceMen();
        TrophyHistory.getInstance().addTrophy(year, "Rob Hawkey Memorial Cup", player.getPlayerName());
        System.out.println("Best Defencemen :" + player.getPlayerName());
    }

    private void participationTeam(int year) {
        String participatedTeam = ParticipantAward.getInstance().getTeamWithLowestPoints();
        TrophyHistory.getInstance().addTrophy(year, "Participation Award", participatedTeam);
        System.out.println("Participation Team :" + participatedTeam);
    }

    public void trophy(int year) {
        bestTeam(year);
        bestPlayer(year);
        bestGoalie(year);
        bestCoach(year);
        bestScorer(year);
        bestDefencemen(year);
        participationTeam(year);
    }

}
