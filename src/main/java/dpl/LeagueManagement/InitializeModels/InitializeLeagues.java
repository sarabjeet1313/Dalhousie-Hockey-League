package dpl.LeagueManagement.InitializeModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dpl.SystemConfig;
import dpl.LeagueManagement.TeamManagement.GeneralConstants;
import dpl.LeagueManagement.GameplayConfiguration.Aging;
import dpl.LeagueManagement.GameplayConfiguration.GameConfigConstants;
import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.GameplayConfiguration.Injury;
import dpl.LeagueManagement.GameplayConfiguration.Trading;
import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.CoachConstants;
import dpl.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueManagement.TeamManagement.ConferenceConstants;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.DivisionConstants;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueManagement.TeamManagement.ManagerConstants;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.PlayerConstants;
import dpl.LeagueManagement.TeamManagement.Team;
import dpl.LeagueManagement.TeamManagement.TeamConstants;
import dpl.UserInputOutput.Parser.CmdParseJSON;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

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
	private Injury injury;
	private Training training;
	private Trading trading;
	private IGameplayConfigPersistance configDb;
	private IManagerPersistance managerDb;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	private Logger log = Logger.getLogger(InitializeLeagues.class.getName());

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
		if (null == valueToCheck || valueToCheck.isEmpty()) {
			return true;
		} else
			return false;
	}

	public String truncateString(String inputString) {
		return inputString.replace("\"", "");
	}

	public League parseAndInitializeModels() {
		parser = new CmdParseJSON(this.filePath);
		conferenceList = new ArrayList<>();
		freeAgents = new ArrayList<>();
		coaches = new ArrayList<>();
		managerList = new ArrayList<>();
		gameConfig = null;
		String leagueName = parser.parse(InitializeLeaguesConstants.LEAGUE_NAME.toString());
		try {
			if (isEmptyString(leagueName)) {
				output.setOutput(InitializeLeaguesConstants.NULL_ERROR.toString());
				output.sendOutput();
				return null;
			}

			if (leagueName == InitializeLeaguesConstants.ERROR.toString()) {
				return null;
			}

			leagueName = truncateString(leagueName);
			league = teamManagement.LeagueWithDbParameters(leagueName, conferenceList, freeAgents, coaches, managerList,
					gameConfig, leagueDb);
			boolean check = league.isValidLeagueName(league);

			if (check == Boolean.FALSE) {
				output.setOutput(InitializeLeaguesConstants.VALID_MSG.toString());
				output.sendOutput();
				return null;
			}

			if (loadConferencesInfo() == null) {
				return null;
			}

			if (loadFreeAgentsInfo() == null) {
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
			league.setConferenceList(conferenceList);
			league.setFreeAgents(freeAgents);
			league.setCoaches(coaches);
			league.setManagerList(managerList);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			output.setOutput(e.getMessage());
			output.sendOutput();
			System.exit(1);
		}
		return league;
	}

	private List<Conference> loadConferencesInfo() throws NullPointerException{
		JsonArray conferences = parser.parseList(InitializeLeaguesConstants.CONFERENCES.toString());
		Iterator<JsonElement> conferenceListElement = conferences.iterator();

		while (conferenceListElement.hasNext()) {
			JsonObject conference = conferenceListElement.next().getAsJsonObject();
			String conferenceName = conference.get(ConferenceConstants.CONFERENCE_NAME.toString()).toString();
			List<Division> bufferDivisionList = new ArrayList<>();

			conferenceName = truncateString(conferenceName);

			if (isEmptyString(conferenceName)) {
				output.setOutput(ConferenceConstants.CONFERENCE_ERROR.toString());
				output.sendOutput();
				return null;
			}

			Conference conferenceObject = teamManagement.ConferenceWithParameters(conferenceName, bufferDivisionList);
			conferenceList.add(conferenceObject);
			JsonArray divisions = conference.get(DivisionConstants.DIVISIONS_MODEL.toString()).getAsJsonArray();
			Iterator<JsonElement> divisionListElement = divisions.iterator();

			while (divisionListElement.hasNext()) {
				JsonObject division = divisionListElement.next().getAsJsonObject();
				String divisionName = division.get(DivisionConstants.DIVISION_NAME.toString()).toString();
				List<Team> bufferTeamList = new ArrayList<>();
				divisionName = truncateString(divisionName);

				if (isEmptyString(divisionName)) {
					output.setOutput(DivisionConstants.DIVISIONS_ERROR.toString());
					output.sendOutput();
					return null;
				}

				Division divisionObject = teamManagement.DivisionWithParameters(divisionName, bufferTeamList);
				bufferDivisionList.add(divisionObject);
				conferenceObject.setDivisionList(bufferDivisionList);
				JsonArray teams = division.get(TeamConstants.TEAMS.toString()).getAsJsonArray();
				Iterator<JsonElement> teamListElement = teams.iterator();

				while (teamListElement.hasNext()) {
					JsonObject team = teamListElement.next().getAsJsonObject();
					String teamName = team.get(TeamConstants.TEAM_NAME.toString()).toString();
					List<Player> bufferPlayerList = new ArrayList<>();
					teamName = truncateString(teamName);
					if (isEmptyString(teamName)) {
						output.setOutput(TeamConstants.TEAM_ERROR.toString());
						output.sendOutput();
						return null;
					}

					JsonObject gemManager = team.get(ManagerConstants.GENERAL_MANAGER.toString()).getAsJsonObject();
					String managerName = gemManager.get(ManagerConstants.NAME.toString()).toString();
					managerName = truncateString(managerName);
					String personality = gemManager.get(ManagerConstants.PERSONALITY.toString()).toString();
					personality = truncateString(personality);
					if (isEmptyString(managerName)) {
						output.setOutput(ManagerConstants.GENERAL_MANAGER_ERROR.toString());
						output.sendOutput();
						return null;
					}
					Manager managerObj = teamManagement.ManagerWithDbParameters(managerName, personality, managerDb);

					JsonObject headCoach = team.get(CoachConstants.HEAD_COACH.toString()).getAsJsonObject();
					String headCoachName = headCoach.get(CoachConstants.NAME.toString()).toString();
					headCoachName = truncateString(headCoachName);

					if (isEmptyString(headCoachName)) {
						output.setOutput(CoachConstants.HEAD_COACH_ERROR.toString());
						output.sendOutput();
						return null;
					}

					double coachSkating = headCoach.get(PlayerConstants.SKATING.toString()).getAsDouble();
					double coachShooting = headCoach.get(PlayerConstants.SHOOTING.toString()).getAsDouble();
					double coachChecking = headCoach.get(PlayerConstants.CHECKING.toString()).getAsDouble();
					double coachSaving = headCoach.get(PlayerConstants.SAVING.toString()).getAsDouble();

					String headCoachValue = isValidCoach(coachSkating, coachShooting, coachChecking, coachSaving);

					if (headCoachValue.length() > 0) {
						output.setOutput(CoachConstants.HEAD_COACH.toString() + headCoachValue);
						output.sendOutput();
						return null;
					}

					Coach headCoachObj = teamManagement.CoachWithDbParameters(headCoachName, coachSkating,
							coachShooting, coachChecking, coachSaving, coachDb);

					Team teamObject = teamManagement.TeamWithParameters(teamName, managerObj, headCoachObj,
							bufferPlayerList, false);
					bufferTeamList.add(teamObject);
					divisionObject.setTeamList(bufferTeamList);
					JsonArray players = team.get(PlayerConstants.PLAYERS.toString()).getAsJsonArray();

					if (players.size() > 30) {
						output.setOutput(TeamConstants.TEAM_OVER_FLOW.toString());
						output.sendOutput();
						return null;
					}

					Iterator<JsonElement> player_List = players.iterator();
					boolean isCaptainPositionOccupied = Boolean.FALSE;
					int count = 0;

					while (player_List.hasNext()) {
						count++;
						JsonObject player = player_List.next().getAsJsonObject();
						String playerName = player.get(PlayerConstants.PLAYER_NAME.toString()).toString();
						playerName = truncateString(playerName);

						if (isEmptyString(playerName)) {
							output.setOutput(PlayerConstants.ENTER_DETAILS.toString() + count
									+ PlayerConstants.EMPTY_MSG.toString());
							output.sendOutput();
							return null;
						}

						String position = player.get(PlayerConstants.PLAYER_POSITION.toString()).toString();
						position = truncateString(position);

						if (isEmptyString(position)) {
							output.setOutput(PlayerConstants.ENTER_PLAYER.toString() + count
									+ PlayerConstants.POSITION_ERROR.toString());
							output.sendOutput();
							return null;
						}

						if (!(position.contains(GeneralConstants.FORWARD.toString())
								|| position.contains(GeneralConstants.GOALIE.toString())
								|| position.contains(GeneralConstants.DEFENSE.toString()))) {
							output.setOutput(PlayerConstants.PLAYER.toString() + count
									+ PlayerConstants.POSITION_TYPE_ERROR.toString());
							output.sendOutput();
							return null;
						}

						Boolean captain = player.get(PlayerConstants.PLAYER_CAPTAIN.toString()).getAsBoolean();

						if (captain && isCaptainPositionOccupied) {
							output.setOutput(PlayerConstants.CAPTAIN_ERROR.toString());
							output.sendOutput();
							return null;
						}

						if (captain) {
							isCaptainPositionOccupied = captain;
						}

						int skating = player.get(PlayerConstants.SKATING.toString()).getAsInt();
						int shooting = player.get(PlayerConstants.SHOOTING.toString()).getAsInt();
						int checking = player.get(PlayerConstants.CHECKING.toString()).getAsInt();
						int saving = player.get(PlayerConstants.SAVING.toString()).getAsInt();
						int birthDay = player.get(PlayerConstants.BIRTH_DAY.toString()).getAsInt();
						int birthMonth = player.get(PlayerConstants.BIRTH_MONTH.toString()).getAsInt();
						int birthYear = player.get(PlayerConstants.BIRTH_YEAR.toString()).getAsInt();
						int age = 2020 - birthYear+10;

						String returnedValue = isValidPlayer(skating, shooting, checking, saving);

						if (returnedValue.length() > 0) {
							output.setOutput(PlayerConstants.PLAYER.toString() + count + returnedValue);
							output.sendOutput();
							return null;
						}

						Player playerObject = teamManagement.PlayerWithParameters(playerName, position, captain, age,
								skating, shooting, checking, saving, Boolean.FALSE, Boolean.FALSE, 0, Boolean.FALSE,
								birthDay, birthMonth, birthYear, Boolean.FALSE);
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
			return PlayerConstants.SKATING_ERROR.toString();
		}
		if (shooting < 0 || shooting > 20) {
			return PlayerConstants.SHOOTING_ERROR.toString();
		}
		if (checking < 0 || checking > 20) {
			return PlayerConstants.CHECKING_ERROR.toString();
		}
		if (saving < 0 || saving > 20) {
			return PlayerConstants.SAVING_ERROR.toString();
		}
		return "";
	}

	public String isValidCoach(double skating, double shooting, double checking, double saving) {
		if (skating < 0 || skating > 1) {
			return CoachConstants.SKATING_ERROR.toString();
		}
		if (shooting < 0 || shooting > 1) {
			return CoachConstants.SHOOTING_ERROR.toString();
		}
		if (checking < 0 || checking > 1) {
			return CoachConstants.CHECKING_ERROR.toString();
		}
		if (saving < 0 || saving > 1) {
			return CoachConstants.SAVING_ERROR.toString();
		}
		return "";
	}

	private List<Player> loadFreeAgentsInfo() throws NullPointerException {
		JsonArray freeAgentsArray = parser.parseList(PlayerConstants.FREE_AGENT.toString());
		Iterator<JsonElement> freeAgentElement = freeAgentsArray.iterator();
		int count = 0;
		try {
			while (freeAgentElement.hasNext()) {
				count++;
				JsonObject freeAgentObj = freeAgentElement.next().getAsJsonObject();
				String agentName = freeAgentObj.get(PlayerConstants.PLAYER_NAME.toString()).toString();
				agentName = truncateString(agentName);

				if (isEmptyString(agentName)) {
					output.setOutput(
							PlayerConstants.ENTER_FREE_AGENT.toString() + count + CoachConstants.NAME.toString());
					output.sendOutput();
					return null;
				}

				String position = freeAgentObj.get(PlayerConstants.PLAYER_POSITION.toString()).toString();
				position = truncateString(position);

				if (isEmptyString(position)) {
					output.setOutput(
							PlayerConstants.FREE_AGENT.toString() + count + PlayerConstants.PLAYER_POSITION.toString());
					output.sendOutput();
					return null;
				}

				if (!(position.contains(GeneralConstants.FORWARD.toString())
						|| position.contains(GeneralConstants.GOALIE.toString())
						|| position.contains(GeneralConstants.DEFENSE.toString()))) {
					output.setOutput(PlayerConstants.FREE_AGENT.toString() + count
							+ PlayerConstants.POSITION_TYPE_ERROR.toString());
					output.sendOutput();
					return null;
				}

				Boolean captain = false;

				if (captain) {
					output.setOutput(
							PlayerConstants.FREE_AGENT.toString() + count + PlayerConstants.CANNOT_CAPTAIN.toString());
					output.sendOutput();
					return null;
				}

				int skating = freeAgentObj.get(PlayerConstants.SKATING.toString()).getAsInt();
				int shooting = freeAgentObj.get(PlayerConstants.SHOOTING.toString()).getAsInt();
				int checking = freeAgentObj.get(PlayerConstants.CHECKING.toString()).getAsInt();
				int saving = freeAgentObj.get(PlayerConstants.SAVING.toString()).getAsInt();
				int birthDay = freeAgentObj.get(PlayerConstants.BIRTH_DAY.toString()).getAsInt();
				int birthMonth = freeAgentObj.get(PlayerConstants.BIRTH_MONTH.toString()).getAsInt();
				int birthYear = freeAgentObj.get(PlayerConstants.BIRTH_YEAR.toString()).getAsInt();
				int age = 2020 - birthYear +10;
				String freeAgentReturnedValue = isValidPlayer(skating, shooting, checking, saving);

				if (freeAgentReturnedValue.length() > 0) {
					output.setOutput(PlayerConstants.PLAYER.toString() + count + freeAgentReturnedValue);
					output.sendOutput();
					return null;
				}

				freeAgents.add(teamManagement.PlayerWithParameters(agentName, position, captain, age, skating, shooting,
						checking, saving, false, false, 0, false, birthDay, birthMonth, birthYear, Boolean.FALSE));
			}
		} catch (NullPointerException e) {
			throw e;
		}

		return freeAgents;
	}

	private List<Coach> loadCoachesInfo() throws NullPointerException {
		JsonArray coachesList = parser.parseList(CoachConstants.COACHES.toString());
		Iterator<JsonElement> coachElement = coachesList.iterator();
		int coachCount = 0;
		try {
			while (coachElement.hasNext()) {
				coachCount++;
				JsonObject coachObj = coachElement.next().getAsJsonObject();
				String coachName = coachObj.get(CoachConstants.NAME.toString()).toString();
				coachName = truncateString(coachName);

				if (isEmptyString(coachName)) {
					output.setOutput(
							CoachConstants.ENTER_COACH.toString() + coachCount + CoachConstants.NAME.toString());
					output.sendOutput();
					return null;
				}

				double coachSkating = coachObj.get(PlayerConstants.SKATING.toString()).getAsDouble();
				if (coachSkating < 0 || coachSkating > 1) {
					output.setOutput("Coach:" + coachCount + CoachConstants.SKATING_ERROR.toString());
					output.sendOutput();
					return null;
				}

				double coachShooting = coachObj.get(PlayerConstants.SHOOTING.toString()).getAsDouble();
				if (coachShooting < 0 || coachShooting > 1) {
					output.setOutput(
							CoachConstants.COACH.toString() + coachCount + CoachConstants.SHOOTING_ERROR.toString());
					output.sendOutput();
					return null;
				}

				double coachChecking = coachObj.get(PlayerConstants.CHECKING.toString()).getAsDouble();
				if (coachChecking < 0 || coachChecking > 1) {
					output.setOutput(
							CoachConstants.COACH.toString() + coachCount + CoachConstants.CHECKING_ERROR.toString());
					output.sendOutput();
					return null;
				}

				double coachSaving = coachObj.get(PlayerConstants.SAVING.toString()).getAsDouble();
				if (coachSaving < 0 || coachSaving > 1) {
					output.setOutput(
							CoachConstants.COACH.toString() + coachCount + CoachConstants.SAVING_ERROR.toString());
					output.sendOutput();
					return null;
				}

				coaches.add(teamManagement.CoachWithDbParameters(coachName, coachSkating, coachShooting, coachChecking,
						coachSaving, coachDb));
			}
		} catch (NullPointerException e) {
			throw e;
		}
		return coaches;
	}

	private List<Manager> loadManagerInfo() throws NullPointerException {
		JsonArray managerParseList = parser.parseList(ManagerConstants.GENERAL_MANAGERS.toString());
		Iterator<JsonElement> managerElement = managerParseList.iterator();
		try {
			while (managerElement.hasNext()) {
				JsonObject mangerObj = managerElement.next().getAsJsonObject();
				String managerName = mangerObj.get(ManagerConstants.NAME.toString()).toString();
				managerName = truncateString(managerName);
				String personality = mangerObj.get(ManagerConstants.PERSONALITY.toString()).toString();
				personality = truncateString(personality);
				if (isEmptyString(managerName)) {
					output.setOutput(ManagerConstants.GENERAL_MANAGER_ERROR_EMPTY.toString());
					output.sendOutput();
					return null;
				}
				Manager manager = teamManagement.ManagerWithDbParameters(managerName, personality, managerDb);
				managerList.add(manager);
			}
		} catch (NullPointerException e) {
			throw e;
		}
		return managerList;
	}

	private GameplayConfig loadGameplayConfig() throws NullPointerException {
		try {
			JsonObject config = parser.parseConfig(GameConfigConstants.GAME_PLAY_CONFIG.toString());
			if (loadAgingInfo(config) == null || loadInjuriesInfo(config) == null || loadTrainingInfo(config) == null
					|| loadTradingInfo(config) == null) {
				return null;
			}
			gameConfig = new GameplayConfig(aging, injury, training, trading, configDb);
		} catch (NullPointerException e) {
			throw e;
		}
		return gameConfig;
	}

	private Aging loadAgingInfo(JsonObject config) {

		JsonObject agingObj = config.get(GameConfigConstants.AGING.toString()).getAsJsonObject();
		int avgAge = agingObj.get(GameConfigConstants.AVGERAGE_RETIREMENT_AGE.toString()).getAsInt();
		int maxAge = agingObj.get(GameConfigConstants.MAX_AGE.toString()).getAsInt();
		double statDecayChance = agingObj.get(GameConfigConstants.STAT_DECAY_CHANCE.toString()).getAsDouble();
		if (avgAge < 1) {
			output.setOutput(GameConfigConstants.INVALID_RETIREMENT_AGE.toString());
			output.sendOutput();
			return null;
		}
		if (maxAge < avgAge || maxAge < 1) {
			output.setOutput(GameConfigConstants.INVALID_MAX_AGE.toString());
			output.sendOutput();
			return null;
		}
		aging = new Aging(avgAge, maxAge, statDecayChance);
		return aging;
	}

	private Injury loadInjuriesInfo(JsonObject config) throws NullPointerException {
		try {
			JsonObject injuriesObj = config.get(GameConfigConstants.INJURIES.toString()).getAsJsonObject();
			double randomInjuryChance = injuriesObj.get(GameConfigConstants.RANDOM_INJURY_CHANCE.toString())
					.getAsDouble();
			if (randomInjuryChance < 0 || randomInjuryChance > 1) {
				output.setOutput(GameConfigConstants.INVALID_RANDOM_INJURY.toString());
				output.sendOutput();
				return null;
			}
			int injuryDaysLow = injuriesObj.get(GameConfigConstants.INJURY_DAYS_LOW.toString()).getAsInt();
			if (injuryDaysLow < 0) {
				output.setOutput(GameConfigConstants.INVALID_LOWER_LIMIT.toString());
				output.sendOutput();
				return null;
			}
			int injuryDaysHigh = injuriesObj.get(GameConfigConstants.INJURY_DAYS_HIGH.toString()).getAsInt();
			if (injuryDaysHigh < 0 || injuryDaysHigh <= injuryDaysLow) {
				output.setOutput(GameConfigConstants.INVALID_RANDOM_INJURY.toString());
				output.sendOutput();
				return null;
			}
			injury = new Injury(randomInjuryChance, injuryDaysLow, injuryDaysHigh);
		} catch (NullPointerException e) {
			throw e;
		}
		return injury;
	}

	private Training loadTrainingInfo(JsonObject config) throws NullPointerException {
		try {
			JsonObject trainingObj = config.get(GameConfigConstants.TRAINING.toString()).getAsJsonObject();
			int daysUntilStatIncreaseCheck = trainingObj.get(GameConfigConstants.STAT_INCREASE_CHECK.toString())
					.getAsInt();
			if (daysUntilStatIncreaseCheck < 0) {
				output.setOutput(GameConfigConstants.INVALID_DAYS.toString());
				output.sendOutput();
				return null;
			}
			int trachDays = daysUntilStatIncreaseCheck;
			training = new Training(daysUntilStatIncreaseCheck, trachDays);
		} catch (NullPointerException e) {
			throw e;
		}
		return training;
	}

	private Trading loadTradingInfo(JsonObject config) throws NullPointerException {
		try {
			JsonObject tradingObj = config.get(GameConfigConstants.TRADING.toString()).getAsJsonObject();
			int lossPoint = tradingObj.get(GameConfigConstants.LOSS_POINT.toString()).getAsInt();
			if (lossPoint < 0) {
				output.setOutput(GameConfigConstants.INVALID_LOSS_POINT.toString());
				output.sendOutput();
				return null;
			}
			double randomTradeOfferChance = tradingObj.get(GameConfigConstants.RANDOM_TRADE_OFFER_CHANCE.toString())
					.getAsDouble();
			if (randomTradeOfferChance < 0 || randomTradeOfferChance > 1) {
				output.setOutput(GameConfigConstants.INVALID_TRADE_OFFER.toString());
				output.sendOutput();
				return null;
			}
			int maxPlayersPerTrade = tradingObj.get(GameConfigConstants.MAX_PLAYERS_PER_TRADE.toString()).getAsInt();
			if (maxPlayersPerTrade < 0) {
				output.setOutput(GameConfigConstants.INVALID_MAX_TRADE.toString());
				output.sendOutput();
				return null;
			}
			double randomAcceptanceChance = tradingObj.get(GameConfigConstants.RANDOM_ACCEPTANCE_CHANCE.toString())
					.getAsDouble();
			if (randomTradeOfferChance < 0 || randomTradeOfferChance > 1) {
				output.setOutput(GameConfigConstants.INVALID_RANDOM.toString());
				output.sendOutput();
				return null;
			}

			JsonObject gmTableObject = tradingObj.get(GameConfigConstants.GMTABLE.toString()).getAsJsonObject();
			HashMap<String, Double> gmTable = new HashMap<>();
			gmTableObject.keySet().forEach(keyStr -> {
				gmTable.put(keyStr, gmTableObject.get(keyStr).getAsDouble());
			});
			trading = new Trading(lossPoint, randomTradeOfferChance, maxPlayersPerTrade, randomAcceptanceChance,
					gmTable);
		} catch (NullPointerException e) {
			throw e;
		}
		return trading;
	}
}