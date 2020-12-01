package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AwardedTrophy {
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	private void bestTeam(int year) {
		String bestTeam = TeamPoint.getInstance().getBestTeam();
		TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.PRESIDENT_TROPHY.toString(), bestTeam);
		log.log(Level.INFO, TrophySystemConstants.PRESIDENT_TROPHY_LOG.toString());
		output.setOutput(TrophySystemConstants.BEST_TEAM.toString() + bestTeam);
		output.sendOutput();
	}

	private void bestPlayer(int year) {
		Player player = PlayerGoalScore.getInstance().getBestPlayer();
		TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.CALDER_MEMORIAL_TROPHY.toString(), player.getPlayerName());
		log.log(Level.INFO, TrophySystemConstants.CALDER_MEMORIAL_TROPHY_LOG.toString());
		output.setOutput(TrophySystemConstants.BEST_PLAYER.toString() + player.getPlayerName());
		output.sendOutput();
	}

	private void bestGoalie(int year) {
		Player player = GoalSave.getInstance().getBestGoalSaver();
		TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.VEZINA_TROPHY.toString(), player.getPlayerName());
		log.log(Level.INFO, TrophySystemConstants.VEZINA_TROPHY_LOG.toString());
		output.setOutput(TrophySystemConstants.BEST_GOALIE.toString() + player.getPlayerName());
		output.sendOutput();
	}

	private void bestCoach(int year) {
		Coach coach = BestCoachLeague.getInstance().getBestCoach();
		TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.JACK_ADAMS_AWARD.toString(), coach.getCoachName());
		log.log(Level.INFO, TrophySystemConstants.JACK_ADAMS_AWARD_LOG.toString());
		output.setOutput(TrophySystemConstants.BEST_COACH.toString() + coach.getCoachName());
		output.sendOutput();

	}

	private void bestScore(int year) {
		Player player = TopGoalScore.getInstance().getTopGoalScore();
		TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.MAURICE_RICHARD_TROPHY.toString(), player.getPlayerName());
		log.log(Level.INFO, TrophySystemConstants.MAURICE_RICHARD_TROPHY_LOG.toString());
		output.setOutput(TrophySystemConstants.BEST_SCORE.toString() + player.getPlayerName());
		output.sendOutput();
	}

	private void bestDefencemen(int year) {
		Player player = BestDefenceMen.getInstance().getBestDefenceMen();
		TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP.toString(), player.getPlayerName());
		log.log(Level.INFO, TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP_LOG.toString());
		output.setOutput(TrophySystemConstants.BEST_DEFENCEMEN.toString() + player.getPlayerName());
		output.sendOutput();
	}

	private void participationTeam(int year) {
		String participatedTeam = ParticipantAward.getInstance().getTeamWithLowestPoints();
		TrophyHistory.getInstance().addTrophy(year, TrophySystemConstants.PARTICIPATION_AWARD.toString(), participatedTeam);
		log.log(Level.INFO, TrophySystemConstants.PARTICIPATION_AWARD_LOG.toString());
		output.setOutput(TrophySystemConstants.PARTICIPATION_TEAM.toString() + participatedTeam);
		output.sendOutput();
	}

	public void trophyEndOfSeason(int year) {
		bestTeam(year);
		bestPlayer(year);
		bestCoach(year);
		participationTeam(year);
	}

	public void trophyStanleyCup(int year) {
		bestGoalie(year);
		bestScore(year);
		bestDefencemen(year);
	}
}
