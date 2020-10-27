package dal.asd.dpl.InitializeModels;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dal.asd.dpl.GameplayConfiguration.GameplayConfig;
import dal.asd.dpl.Parser.CmdParseJSON;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.Division;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Team;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InitializeLeagues implements IInitializeLeagues {
	private CmdParseJSON parser;
	private String filePath;
	private ILeague leagueDb;
	private IUserOutput output;
	private List<Conference> conferenceList;
	private League league;
	private List<Player> freeAgents;
	private List<Coach> coaches;
	private List<String> managers;
	private GameplayConfig config;

	public InitializeLeagues(String filePath, ILeague leagueDb, IUserOutput output, IUserInput input) {
		this.filePath = filePath;
		this.leagueDb = leagueDb;
		this.output = output;
	}

	public boolean isEmptyString(String valueToCheck) {
		if (valueToCheck.isEmpty() || valueToCheck == null) {
			return true;
		} else
			return false;
	}

	public String truncateString(String inputString) {
		return inputString.replace("\"", "");
	}

	public League parseAndInitializeModels() {
		parser = new CmdParseJSON(this.filePath);
		conferenceList = new ArrayList<Conference>();
		freeAgents = new ArrayList<Player>();
		coaches = new ArrayList<Coach>();
		managers = new ArrayList<String>();
		config = null;
		String leagueName = parser.parse("leagueName");

		if (isEmptyString(leagueName)) {
			output.setOutput("Please enter League name. Null values are not accepted.");
			output.sendOutput();
			return null;
		}

		if (leagueName == "Error") {
			return null;
		}

		leagueName = truncateString(leagueName);
		league = new League(leagueName, conferenceList, freeAgents, coaches, managers, config);
		boolean check = league.isValidLeagueName(leagueName, leagueDb);

		if (!check) {
			output.setOutput("Please enter valid League name.");
			output.sendOutput();
			return null;
		}

		JsonArray conferences = parser.parseList("conferences");
		Iterator<JsonElement> conferenceListElement = conferences.iterator();

		while (conferenceListElement.hasNext()) {
			JsonObject conference = conferenceListElement.next().getAsJsonObject();
			String conferenceName = conference.get("conferenceName").toString();
			List<Division> bufferDivisionList = new ArrayList<Division>();

			conferenceName = truncateString(conferenceName);

			if (isEmptyString(conferenceName)) {
				output.setOutput("Please enter Conference name. Null values are not accepted.");
				output.sendOutput();
				return null;
			}

			Conference conferenceObject = new Conference(conferenceName, bufferDivisionList);
			conferenceList.add(conferenceObject);
			JsonArray divisions = conference.get("divisions").getAsJsonArray();
			Iterator<JsonElement> divisionListElement = divisions.iterator();

			while (divisionListElement.hasNext()) {
				JsonObject division = divisionListElement.next().getAsJsonObject();
				String divisionName = division.get("divisionName").toString();
				List<Team> bufferTeamList = new ArrayList<Team>();
				divisionName = truncateString(divisionName);

				if (isEmptyString(divisionName)) {
					output.setOutput("Please enter Division name. Null values are not accepted.");
					output.sendOutput();
					return null;
				}

				Division divisionObject = new Division(divisionName, bufferTeamList);
				bufferDivisionList.add(divisionObject);
				conferenceObject.setDivisionList(bufferDivisionList);
				JsonArray teams = division.get("teams").getAsJsonArray();
				Iterator<JsonElement> teamListElement = teams.iterator();

				while (teamListElement.hasNext()) {
					JsonObject team = teamListElement.next().getAsJsonObject();
					String teamName = team.get("teamName").toString();
					List<Player> bufferPlayerList = new ArrayList<Player>();
					teamName = truncateString(teamName);
					if (isEmptyString(teamName)) {
						output.setOutput("Please enter Team name. Null values are not accepted.");
						output.sendOutput();
						return null;
					}

					String genManager = team.get("generalManager").toString();
					genManager = truncateString(genManager);

					if (isEmptyString(genManager)) {
						output.setOutput("Please enter General Manager name. Null values are not accepted.");
						output.sendOutput();
						return null;
					}

					JsonObject headCoach = team.get("headCoach").getAsJsonObject();
					String headCoachName = headCoach.get("name").toString();
					headCoachName = truncateString(headCoachName);

					if (isEmptyString(headCoachName)) {
						output.setOutput("Please enter Head Coach name. Null values are not accepted.");
						output.sendOutput();
						return null;
					}

					double coachSkating = headCoach.get("skating").getAsDouble();
					if (coachSkating < 0 || coachSkating > 1) {
						output.setOutput("HeadCoach:" + headCoachName + " skating value should be between 0 and 1.");
						output.sendOutput();
						return null;
					}

					double coachShooting = headCoach.get("shooting").getAsDouble();
					if (coachShooting < 0 || coachShooting > 1) {
						output.setOutput("HeadCoach:" + headCoachName + " shooting value should be between 0 and 1.");
						output.sendOutput();
						return null;
					}

					double coachChecking = headCoach.get("checking").getAsDouble();
					if (coachChecking < 0 || coachChecking > 1) {
						output.setOutput("HeadCoach:" + headCoachName + " checking value should be between 0 and 1.");
						output.sendOutput();
						return null;
					}

					double coachSaving = headCoach.get("saving").getAsDouble();
					if (coachSaving < 0 || coachSaving > 1) {
						output.setOutput("HeadCoach:" + headCoachName + " saving value should be between 0 and 1.");
						output.sendOutput();
						return null;
					}

					Coach headCoachObj = new Coach(headCoachName, coachSkating, coachShooting, coachChecking,
							coachSaving);

					Team teamObject = new Team(teamName, genManager, headCoachObj, bufferPlayerList);
					bufferTeamList.add(teamObject);
					divisionObject.setTeamList(bufferTeamList);
					JsonArray players = team.get("players").getAsJsonArray();

					if (players.size() > 20) {
						output.setOutput("Team cannot have more than 20 players. Please correct the team size.");
						output.sendOutput();
						return null;
					}

					Iterator<JsonElement> player_List = players.iterator();
					boolean isCaptainPositionOccupied = false;
					int count = 0;

					while (player_List.hasNext()) {
						count++;
						JsonObject player = player_List.next().getAsJsonObject();
						String playerName = player.get("playerName").toString();
						playerName = truncateString(playerName);

						if (isEmptyString(playerName)) {
							output.setOutput("Please enter Player name. Player:" + count + " name is empty.");
							output.sendOutput();
							return null;
						}

						String position = player.get("position").toString();
						position = truncateString(position);

						if (isEmptyString(position)) {
							output.setOutput(
									"Please enter player:" + count + " position. Null values are not accepted.");
							output.sendOutput();
							return null;
						}

						if (!(position.contains("forward") || position.contains("goalie")
								|| position.contains("defense"))) {
							output.setOutput(
									"Player:" + count + " position must be either 'goalie', 'forward', or 'defense'.");
							output.sendOutput();
							return null;
						}

						Boolean captain = player.get("captain").getAsBoolean();

						if (captain && isCaptainPositionOccupied) {
							output.setOutput("A team can only have one captain.");
							output.sendOutput();
							return null;
						}

						if (captain) {
							isCaptainPositionOccupied = captain;
						}

						int age = player.get("age").getAsInt();
						if (age < 0) {
							output.setOutput("Player:" + count + " age should be integer and greater than 0.");
							output.sendOutput();
							return null;
						}

						int skating = player.get("skating").getAsInt();
						if (skating < 0 || skating > 20) {
							output.setOutput("Player:" + count + " skating value should be between 0 and 20.");
							output.sendOutput();
							return null;
						}

						int shooting = player.get("shooting").getAsInt();
						if (shooting < 0 || shooting > 20) {
							output.setOutput("Player:" + count + " shooting value should be between 0 and 20.");
							output.sendOutput();
							return null;
						}

						int checking = player.get("checking").getAsInt();
						if (checking < 0 || checking > 20) {
							output.setOutput("Player:" + count + " checking value should be between 0 and 20.");
							output.sendOutput();
							return null;
						}

						int saving = player.get("saving").getAsInt();
						if (saving < 0 || saving > 20) {
							output.setOutput("Player:" + count + " saving value should be between 0 and 20.");
							output.sendOutput();
							return null;
						}

						Player playerObject = new Player(playerName, position, captain, age, skating, shooting,
								checking, saving, false, false, 0);
						bufferPlayerList.add(playerObject);
						teamObject.setPlayerList(bufferPlayerList);
					}
				}
			}
		}

		JsonArray freeAgentsArray = parser.parseList("freeAgents");
		Iterator<JsonElement> freeAgentElement = freeAgentsArray.iterator();
		int count = 0;

		while (freeAgentElement.hasNext()) {
			count++;
			JsonObject freeAgentObj = freeAgentElement.next().getAsJsonObject();
			String agentName = freeAgentObj.get("playerName").toString();
			agentName = truncateString(agentName);

			if (isEmptyString(agentName)) {
				output.setOutput("Please enter Free Agent:" + count + " name.");
				output.sendOutput();
				return null;
			}

			String position = freeAgentObj.get("position").toString();
			position = truncateString(position);

			if (isEmptyString(position)) {
				output.setOutput("Please enter Free Agent:" + count + " position.");
				output.sendOutput();
				return null;
			}

			if (!(position.contains("forward") || position.contains("goalie") || position.contains("defense"))) {
				output.setOutput("Free Agent:" + count + " position must be either 'goalie', 'forward, or 'defense'.");
				output.sendOutput();
				return null;
			}

			Boolean captain = false;

			if (captain) {
				output.setOutput("A free agent:" + count + " cannot be a captain.");
				output.sendOutput();
				return null;
			}

			int age = freeAgentObj.get("age").getAsInt();
			if (age < 0) {
				output.setOutput("Player:" + count + " age should be integer and greater than 0.");
				output.sendOutput();
				return null;
			}

			int skating = freeAgentObj.get("skating").getAsInt();
			if (skating < 0 || skating > 20) {
				output.setOutput("Player:" + count + " skating value should be between 0 and 20.");
				output.sendOutput();
				return null;
			}

			int shooting = freeAgentObj.get("shooting").getAsInt();
			if (shooting < 0 || shooting > 20) {
				output.setOutput("Player:" + count + " shooting value should be between 0 and 20.");
				output.sendOutput();
				return null;
			}

			int checking = freeAgentObj.get("checking").getAsInt();
			if (checking < 0 || checking > 20) {
				output.setOutput("Player:" + count + " checking value should be between 0 and 20.");
				output.sendOutput();
				return null;
			}

			int saving = freeAgentObj.get("saving").getAsInt();
			if (saving < 0 || saving > 20) {
				output.setOutput("Player:" + count + " saving value should be between 0 and 20.");
				output.sendOutput();
				return null;
			}

			freeAgents.add(new Player(agentName, position, captain, age, skating, shooting, checking, saving, false, false, 0));
		}

		JsonArray coachesList = parser.parseList("coaches");
		Iterator<JsonElement> coachElement = coachesList.iterator();
		int coachCount = 0;

		while (coachElement.hasNext()) {
			coachCount++;
			JsonObject coachObj = coachElement.next().getAsJsonObject();
			String coachName = coachObj.get("name").toString();
			coachName = truncateString(coachName);

			if (isEmptyString(coachName)) {
				output.setOutput("Please enter Coach:" + coachCount + " name.");
				output.sendOutput();
				return null;
			}

			double coachSkating = coachObj.get("skating").getAsDouble();
			if (coachSkating < 0 || coachSkating > 1) {
				output.setOutput("Coach:" + coachCount + " skating value should be between 0 and 1.");
				output.sendOutput();
				return null;
			}

			double coachShooting = coachObj.get("shooting").getAsDouble();
			if (coachShooting < 0 || coachShooting > 1) {
				output.setOutput("Coach:" + coachCount + " shooting value should be between 0 and 1.");
				output.sendOutput();
				return null;
			}

			double coachChecking = coachObj.get("checking").getAsDouble();
			if (coachChecking < 0 || coachChecking > 1) {
				output.setOutput("Coach:" + coachCount + " checking value should be between 0 and 1.");
				output.sendOutput();
				return null;
			}

			double coachSaving = coachObj.get("saving").getAsDouble();
			if (coachSaving < 0 || coachSaving > 1) {
				output.setOutput("Coach:" + coachCount + " saving value should be between 0 and 1.");
				output.sendOutput();
				return null;
			}

			coaches.add(new Coach(coachName, coachSkating, coachShooting, coachChecking, coachSaving));
		}

		JsonArray managerList = parser.parseList("generalManagers");
		Iterator<JsonElement> managerElement = managerList.iterator();
		int managerCount = 0;

		while (managerElement.hasNext()) {
			managerCount++;
			String managerName = managerElement.next().getAsString();
			managerName = truncateString(managerName);
			if (isEmptyString(managerName)) {
				output.setOutput("General manager cannot be empty");
				output.sendOutput();
				return null;
			}
			managers.add(managerName);
		}
		league.setConferenceList(conferenceList);
		league.setFreeAgents(freeAgents);
		league.setCoaches(coaches);
		league.setGeneralManager(managers);
		return league;
	}
}
