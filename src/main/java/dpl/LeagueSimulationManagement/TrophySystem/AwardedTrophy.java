package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class AwardedTrophy {

    public void bestTeam(int year){
        String bestTeam = TeamPoints.getInstance().getBestTeam();
        TrophyHistory.getInstance().addTrophy(year, "President's Trophy", bestTeam);
        System.out.println("Best Team :" + bestTeam);
    }

    public void bestPlayer(int year){
        Player player = PlayerGoalScorer.getInstance().getBestPlayer();
        TrophyHistory.getInstance().addTrophy(year, "Calder Memorial Trophy", player.getPlayerName());
        System.out.println("Best Player :" + player.getPlayerName());
    }

    public void bestGoalie(int year){
        Player player = GoalSaver.getInstance().getBestGoalSaver();
        TrophyHistory.getInstance().addTrophy(year, "Vezina Trophy", player.getPlayerName());
        System.out.println("Best Goalie :" + player.getPlayerName());
    }

    public void bestCoach(int year){
        Coach coach = BestCoachLeague.getInstance().getBestCoach();
        TrophyHistory.getInstance().addTrophy(year, "Jack Adam's Award", coach.getCoachName());
        System.out.println("Best Coach :" + coach.getCoachName());
    }

    public void bestScorer(int year){
        Player player = TopGoalScorer.getInstance().getTopGoalScorer();
        TrophyHistory.getInstance().addTrophy(year, "Maurice Richard Trophy", player.getPlayerName());
        System.out.println("Best Scorer :" + player.getPlayerName());
    }

    public void bestDefencemen(int year){
        Player player = BestDefenceMen.getInstance().getBestDefenceMen();
        TrophyHistory.getInstance().addTrophy(year, "Rob Hawkey Memorial Cup", player.getPlayerName());
        System.out.println("Best Defencemen :" + player.getPlayerName());
    }

    public void participationTeam(int year){
        String participatedTeam = ParticipantsAward.getInstance().getTeamWithLowestPoints();
        TrophyHistory.getInstance().addTrophy(year, "Participation Award", participatedTeam);
        System.out.println("Participation Team :" + participatedTeam);
    }

}
