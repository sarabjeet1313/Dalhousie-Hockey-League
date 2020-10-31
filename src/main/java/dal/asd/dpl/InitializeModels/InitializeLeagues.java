package dal.asd.dpl.InitializeModels;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dal.asd.dpl.GameplayConfiguration.Aging;
import dal.asd.dpl.GameplayConfiguration.GameResolver;
import dal.asd.dpl.GameplayConfiguration.GameplayConfig;
import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.GameplayConfiguration.Injury;
import dal.asd.dpl.GameplayConfiguration.Trading;
import dal.asd.dpl.GameplayConfiguration.Training;
import dal.asd.dpl.Parser.CmdParseJSON;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.Division;
import dal.asd.dpl.TeamManagement.ICoachPersistance;
import dal.asd.dpl.TeamManagement.ILeaguePersistance;
import dal.asd.dpl.TeamManagement.IManagerPersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Manager;
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
	private ILeaguePersistance leagueDb;
	private ICoachPersistance coachDb;
	private IUserOutput output;
	private List<Conference> conferenceList;
	private League league;
	private List<Player> freeAgents;
	private List<Coach> coaches;
	private List<Manager> managerList;
	private GameplayConfig gameConfig;
	private Aging aging;
	private GameResolver resolver;
	private Injury injury;
	private Training training;
	private Trading trading;
	private IGameplayConfigPersistance configDb;
	private IManagerPersistance managerDb;

	public InitializeLeagues(String filePath, ILeaguePersistance leagueDb, IUserOutput output, IUserInput input,
			ICoachPersistance coachDb, IGameplayConfigPersistance configDb, IManagerPersistance managerDb) {
		this.filePath = filePath;
		this.leagueDb = leagueDb;
		this.output = output;
		this.coachDb = coachDb;
		this.configDb = configDb;
		this.managerDb = managerDb;
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
		managerList = new ArrayList<Manager>();
		gameConfig = null;
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
		league = new League(leagueName, conferenceList, freeAgents, coaches, managerList, gameConfig, leagueDb);
		boolean check = league.isValidLeagueName(leagueName, leagueDb);

		if (!check) {
			output.setOutput("Please enter valid League name.");
			output.sendOutput();
			return null;
		}

		if(loadConferencesInfo() == null) {
			return null;
		}

		if(loadFreeAgentsInfo() == null) {
			return null;
		}

		if (loadCoachesInfo() == null) {
			return null;
		}

		if (loadManagerInfo() == null) {
			return null;
		}

		if (loadGameplayConfig() == null) {
			return null;
		}
		league.setGameConfig(gameConfig);
//		gameConfig.saveGameplayConfig(league);
		league.setConferenceList(conferenceList);
		league.setFreeAgents(freeAgents);
		league.setCoaches(coaches);
		league.setManagerList(managerList);
		return league;
	}
	
	private List<Conference> loadConferencesInfo() {
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
					Manager managerobj = new Manager(genManager, managerDb); 
					
					JsonObject headCoach = team.get("headCoach").getAsJsonObject();
					String headCoachName = headCoach.get("name").toString();
					headCoachName = truncateString(headCoachName);

					if (isEmptyString(headCoachName)) {
						output.setOutput("Please enter Head Coach name. Null values are not accepted.");
						output.sendOutput();
						return null;
					}

					double coachSkating = headCoach.get("skating").getAsDouble();
					double coachShooting = headCoach.get("shooting").getAsDouble();
					double coachChecking = headCoach.get("checking").getAsDouble();
					double coachSaving = headCoach.get("saving").getAsDouble();

					String headCoachValue = isValidCoach(coachSkating, coachShooting, coachChecking, coachSaving);

					if (headCoachValue.length() > 0) {
						output.setOutput("Head Coach:" + headCoachValue);
						output.sendOutput();
						return null;
					}

					Coach headCoachObj = new Coach(headCoachName, coachSkating, coachShooting, coachChecking,
							coachSaving, coachDb);

					Team teamObject = new Team(teamName, managerobj, headCoachObj, bufferPlayerList);
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
						int shooting = player.get("shooting").getAsInt();
						int checking = player.get("checking").getAsInt();
						int saving = player.get("saving").getAsInt();

						String returnedValue = isValidPlayer(skating, shooting, checking, saving);

						if (returnedValue.length() > 0) {
							output.setOutput("Player:" + count + returnedValue);
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
		return conferenceList;
	}

	public String isValidPlayer(int skating, int shooting, int checking, int saving) {
		if (skating < 0 || skating > 20) {
			return " skating value should be between 0 and 20.";
		}
		if (shooting < 0 || shooting > 20) {
			return " shooting value should be between 0 and 20.";
		}
		if (checking < 0 || checking > 20) {
			return " checking value should be between 0 and 20.";
		}
		if (saving < 0 || saving > 20) {
			return " saving value should be between 0 and 20.";
		}
		return "";
	}
	
	public String isValidCoach(double skating, double shooting, double checking, double saving) {
		if (skating < 0 || skating > 1) {
			return " skating value should be between 0 and 1.";
		}
		if (shooting < 0 || shooting > 1) {
			return " shooting value should be between 0 and 1.";
		}
		if (checking < 0 || checking > 1) {
			return " checking value should be between 0 and 1.";
		}
		if (saving < 0 || saving > 1) {
			return " saving value should be between 0 and 1.";
		}
		return "";
	}
	
	private List<Player> loadFreeAgentsInfo() {
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
			int shooting = freeAgentObj.get("shooting").getAsInt();
			int checking = freeAgentObj.get("checking").getAsInt();
			int saving = freeAgentObj.get("saving").getAsInt();

			String freeAgentReturnedValue = isValidPlayer(skating, shooting, checking, saving);

			if (freeAgentReturnedValue.length() > 0) {
				output.setOutput("Player:" + count + freeAgentReturnedValue);
				output.sendOutput();
				return null;
			}

			freeAgents.add(new Player(agentName, position, captain, age, skating, shooting, checking, saving, false,
					false, 0));
		}
		
		return freeAgents;
	}

	private List<Coach> loadCoachesInfo() {
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

			coaches.add(new Coach(coachName, coachSkating, coachShooting, coachChecking, coachSaving, coachDb));
		}
		return coaches;
	}

	private List<Manager> loadManagerInfo() {
		JsonArray managerParseList = parser.parseList("generalManagers");
		Iterator<JsonElement> managerElement = managerParseList.iterator();
		while (managerElement.hasNext()) {
			String managerName = managerElement.next().getAsString();
			managerName = truncateString(managerName);
			if (isEmptyString(managerName)) {
				output.setOutput("General manager cannot be empty");
				output.sendOutput();
				return null;
			}
			Manager manager = new Manager(managerName, managerDb);
			managerList.add(manager);
		}
		return managerList;
	}

	private GameplayConfig loadGameplayConfig() {
		JsonObject config = parser.parseConfig("gameplayConfig");
		if (loadAgingInfo(config) == null || loadGameResolverInfo(config) == null || loadInjuriesInfo(config) == null
				|| loadTrainingInfo(config) == null || loadTradingInfo(config) == null) {
			return null;
		}
		gameConfig = new GameplayConfig(aging, resolver, injury, training, trading, configDb);
		return gameConfig;
	}

	private Aging loadAgingInfo(JsonObject config) {

		JsonObject agingObj = config.get("aging").getAsJsonObject();
		int avgAge = agingObj.get("averageRetirementAge").getAsInt();
		int maxAge = agingObj.get("maximumAge").getAsInt();
		if (avgAge < 1) {
			output.setOutput("Invalid Average Retirement Age");
			output.sendOutput();
			return null;
		}
		if (maxAge < avgAge || maxAge < 1) {
			output.setOutput("Invalid Max Retirement Age");
			output.sendOutput();
			return null;
		}
		aging = new Aging(avgAge, maxAge);
		return aging;
	}

	private GameResolver loadGameResolverInfo(JsonObject config) {
		JsonObject gameResolverObj = config.get("gameResolver").getAsJsonObject();
		double randomWinChance = gameResolverObj.get("randomWinChance").getAsDouble();
		if (randomWinChance < 0 || randomWinChance > 1) {
			output.setOutput("Invalid random Win Chance value");
			output.sendOutput();
			return null;
		}
		resolver = new GameResolver(randomWinChance);
		return resolver;
	}

	private Injury loadInjuriesInfo(JsonObject config) {
		JsonObject injuriesObj = config.get("injuries").getAsJsonObject();
		double randomInjuryChance = injuriesObj.get("randomInjuryChance").getAsDouble();
		if (randomInjuryChance < 0 || randomInjuryChance > 1) {
			output.setOutput("Invalid random Injury Chance value");
			output.sendOutput();
			return null;
		}
		int injuryDaysLow = injuriesObj.get("injuryDaysLow").getAsInt();
		if (injuryDaysLow < 0) {
			output.setOutput("Invalid Injury lower limit value");
			output.sendOutput();
			return null;
		}
		int injuryDaysHigh = injuriesObj.get("injuryDaysLow").getAsInt();
		if (injuryDaysHigh < 0 || injuryDaysHigh < injuryDaysLow) {
			output.setOutput("Invalid random Injury Chance value");
			output.sendOutput();
			return null;
		}
		injury = new Injury(randomInjuryChance, injuryDaysLow, injuryDaysHigh);
		return injury;
	}

	private Training loadTrainingInfo(JsonObject config) {
		JsonObject trainingObj = config.get("training").getAsJsonObject();
		int daysUntilStatIncreaseCheck = trainingObj.get("daysUntilStatIncreaseCheck").getAsInt();
		if (daysUntilStatIncreaseCheck < 0) {
			output.setOutput("Invalid number of days value");
			output.sendOutput();
			return null;
		}
		training = new Training(daysUntilStatIncreaseCheck);
		return training;
	}

	private Trading loadTradingInfo(JsonObject config) {
		JsonObject tradingObj = config.get("trading").getAsJsonObject();
		int lossPoint = tradingObj.get("lossPoint").getAsInt();
		if (lossPoint < 0) {
			output.setOutput("Invalid loss point value");
			output.sendOutput();
			return null;
		}
		double randomTradeOfferChance = tradingObj.get("randomTradeOfferChance").getAsDouble();
		if (randomTradeOfferChance < 0 || randomTradeOfferChance > 1) {
			output.setOutput("Invalid random Trade Offer Chance value");
			output.sendOutput();
			return null;
		}
		int maxPlayersPerTrade = tradingObj.get("maxPlayersPerTrade").getAsInt();
		if (maxPlayersPerTrade < 0) {
			output.setOutput("Invalid max Players Per Trade value");
			output.sendOutput();
			return null;
		}
		double randomAcceptanceChance = tradingObj.get("randomAcceptanceChance").getAsDouble();
		if (randomTradeOfferChance < 0 || randomTradeOfferChance > 1) {
			output.setOutput("Invalid random Acceptance Chance value");
			output.sendOutput();
			return null;
		}
		trading = new Trading(lossPoint, randomTradeOfferChance, maxPlayersPerTrade, randomAcceptanceChance);
		return trading;
	}
}