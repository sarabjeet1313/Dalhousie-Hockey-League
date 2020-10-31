package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.NewsSystem.GamePlayedPublisher;
import dal.asd.dpl.NewsSystem.NewsSubscriber;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.ScheduleConstants;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.Standings.StandingInfo;
import dal.asd.dpl.TeamManagement.ITeamInfo;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Team;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulateGameState implements ISimulationState {
    private static String stateName;
    private static String nextStateName;
    private League leagueToSimulate;
    private StandingInfo standings;
    private ISchedule schedule;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private String currentDate;
    private IUserOutput output;
    private GamePlayedPublisher gamePublisher;
    private ITeamInfo teamInfo;
    private double randomWinChance;
    private Map<String, List<Map<String, String>>> currentSchedule;

    public SimulateGameState(League leagueToSimulate, ISchedule schedule, StandingInfo standings, InternalStateContext context, SeasonCalendar utility, String currentDate, IUserOutput output) {
        this.stateName = StateConstants.SIMULATE_GAME_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.standings = standings;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.currentSchedule = schedule.getFinalSchedule();
        this.randomWinChance = leagueToSimulate.getGameConfig().getGameResolver().getRandomWinChance();
        this.output = output;
        this.teamInfo = new Team();
        this.gamePublisher = new GamePlayedPublisher();
        this.gamePublisher.subscribe(NewsSubscriber.getInstance());
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = StateConstants.INJURY_STATE;
    }

    public void doProcessing() {

        output.setOutput("Inside Match Simulation state");
        output.sendOutput();

        if(schedule.getSeasonType() == ScheduleConstants.REGULAR_SEASON) {
            simulateRegularMatches();
        }

        else if(schedule.getSeasonType() == ScheduleConstants.PLAYOFF_SEASON) {
            simulatePlayoffMatches();
        }
    }

    private void simulateRegularMatches() {
        if (currentSchedule.containsKey(this.currentDate)) {
            List<Map<String, String>> teamsCompetingList = currentSchedule.get(this.currentDate);
            if(teamsCompetingList.size()>0) {
                Map<String, String> teamsCompeting = teamsCompetingList.get(0);
                for (Map.Entry<String, String> entry : teamsCompeting.entrySet()) {
                    String firstTeam = entry.getKey();
                    String secondTeam = entry.getValue();

                    output.setOutput("Match is going on between " + firstTeam + " vs " + secondTeam);
                    output.sendOutput();

                    String winningTeam = "";
                    String losingTeam = "";
                    double team1Strength = teamInfo.getTeamStrength(firstTeam, leagueToSimulate);
                    double team2Strength = teamInfo.getTeamStrength(secondTeam, leagueToSimulate);

                    if(teamInfo.shouldReverseResult(randomWinChance)){
                        if(team1Strength <= team2Strength){
                            winningTeam = firstTeam;
                            losingTeam = secondTeam;
                        }
                        else {
                            winningTeam = secondTeam;
                            losingTeam = firstTeam;
                        }
                    }
                    else {
                        if(team1Strength >= team2Strength){
                            winningTeam = firstTeam;
                            losingTeam = secondTeam;
                        }
                        else {
                            winningTeam = secondTeam;
                            losingTeam = firstTeam;
                        }
                    }

                    gamePublisher.notify(winningTeam, losingTeam, currentDate);
                    standings.updateTeamWinMap(winningTeam);
                    standings.updateTeamLoseMap(losingTeam);
                }
                teamsCompetingList.remove(0);
                currentSchedule.put(this.currentDate, teamsCompetingList);
                schedule.setFinalSchedule(currentSchedule);
            }
        }
    }

    private void simulatePlayoffMatches() {
        if (currentSchedule.containsKey(this.currentDate)) {
            List<Map<String, String>> teamsCompetingList = currentSchedule.get(this.currentDate);

            if(teamsCompetingList.size() > 0) {
                Map<String, String> teamsCompeting = teamsCompetingList.get(0);
                for (Map.Entry<String, String> entry : teamsCompeting.entrySet()) {
                    String firstTeam = entry.getKey();
                    String secondTeam = entry.getValue();

                    int team1Win = 1;
                    int team2Win = 0;
                    // best of seven series
                    for (int i = 0; i < 7; i++) {
                        if(team1Win >= 4 || team2Win >=4) {
                            break;
                        }
                        output.setOutput("Match is going on between " + firstTeam + " vs " + secondTeam);
                        output.sendOutput();

                        String winningTeam = "";
                        String losingTeam = "";
                        double team1Strength = teamInfo.getTeamStrength(firstTeam, leagueToSimulate);
                        double team2Strength = teamInfo.getTeamStrength(secondTeam, leagueToSimulate);
                        if(teamInfo.shouldReverseResult(randomWinChance)){
                            if(team1Strength <= team2Strength){
                                winningTeam = firstTeam;
                                losingTeam = secondTeam;
                                team1Win++;
                            }
                            else {
                                winningTeam = secondTeam;
                                losingTeam = firstTeam;
                                team2Win++;
                            }
                        }
                        else {
                            if(team1Strength >= team2Strength){
                                winningTeam = firstTeam;
                                losingTeam = secondTeam;
                                team1Win++;
                            }
                            else {
                                winningTeam = secondTeam;
                                losingTeam = firstTeam;
                                team2Win++;
                            }
                        }
                        gamePublisher.notify(winningTeam, losingTeam, currentDate);
                        standings.updateTeamWinMap(winningTeam);
                        standings.updateTeamLoseMap(losingTeam);
                    }

                    if (team1Win > team2Win) {
                        generateNextRoundSchedule(firstTeam, secondTeam);
                    }

                    if (team2Win > team1Win) {
                        generateNextRoundSchedule(secondTeam, firstTeam);
                    }
                }
                currentSchedule.remove(this.currentDate);
                schedule.setFinalSchedule(currentSchedule);
            }
        }
    }

    private void generateNextRoundSchedule(String winningTeam, String losingTeam) {
        List<String> teams = schedule.getTeamsToBeScheduled();
        teams.add(winningTeam);
        schedule.setTeamsToBeScheduled(teams);

        List<String> teamsScheduled = schedule.getTeamsScheduled();
        teamsScheduled.remove(winningTeam);
        teamsScheduled.remove(losingTeam);
        schedule.setTeamsScheduled(teamsScheduled);

        if (schedule.getTeamsScheduled().isEmpty()) {
            if (schedule.getTeamsToBeScheduled().size() < 2) {
                utility.setSeasonOverStatus(true);
                utility.setSeasonWinner(schedule.getTeamsToBeScheduled().get(0));
                output.setOutput("Winner is : " + utility.getSeasonWinner());
                output.sendOutput();
                utility.setLastSeasonDay(this.currentDate);
            } else {
                schedule.generateScheduleOnTheFly(schedule.getTeamsToBeScheduled(), this.currentDate);
                List<String> teamsAlreadyScheduled = new ArrayList<String>(schedule.getTeamsToBeScheduled());
                schedule.setTeamsScheduled(teamsAlreadyScheduled);
                List<String> clearTeams = new ArrayList<>();
                schedule.setTeamsToBeScheduled(clearTeams);
            }
        }
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
