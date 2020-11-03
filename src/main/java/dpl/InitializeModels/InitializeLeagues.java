package dpl.InitializeModels;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dpl.DplConstants.CoachConstants;
import dpl.DplConstants.ConferenceConstants;
import dpl.DplConstants.GeneralConstants;
import dpl.DplConstants.DivisionConstants;
import dpl.DplConstants.GameConfigConstants;
import dpl.DplConstants.InitializeLeaguesConstants;
import dpl.DplConstants.ManagerConstants;
import dpl.DplConstants.PlayerConstants;
import dpl.DplConstants.TeamConstants;
import dpl.GameplayConfiguration.Aging;
import dpl.GameplayConfiguration.GameResolver;
import dpl.GameplayConfiguration.GameplayConfig;
import dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.GameplayConfiguration.Injury;
import dpl.GameplayConfiguration.Trading;
import dpl.GameplayConfiguration.Training;
import dpl.Parser.CmdParseJSON;
import dpl.TeamManagement.Coach;
import dpl.TeamManagement.Conference;
import dpl.TeamManagement.Division;
import dpl.TeamManagement.ICoachPersistance;
import dpl.TeamManagement.ILeaguePersistance;
import dpl.TeamManagement.IManagerPersistance;
import dpl.TeamManagement.League;
import dpl.TeamManagement.Manager;
import dpl.TeamManagement.Player;
import dpl.TeamManagement.Team;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.IUserOutput;

import java.sql.SQLException;
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
			league = new League(leagueName, conferenceList, freeAgents, coaches, managerList, gameConfig, leagueDb);
			boolean check = league.isValidLeagueName(leagueName);

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
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		} catch (NullPointerException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
		return league;
	}

	private List<Conference> loadConferencesInfo() {
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

			Conference conferenceObject = new Conference(conferenceName, bufferDivisionList);
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

				Division divisionObject = new Division(divisionName, bufferTeamList);
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

					String genManager = team.get(ManagerConstants.GENERAL_MANAGER.toString()).toString();
					genManager = truncateString(genManager);

					if (isEmptyString(genManager)) {
						output.setOutput(ManagerConstants.GENERAL_MANAGER_ERROR.toString());
						output.sendOutput();
						return null;
					}
					Manager managerobj = new Manager(genManager, managerDb);

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

					Coach headCoachObj = new Coach(headCoachName, coachSkating, coachShooting, coachChecking,
							coachSaving, coachDb);

					Team teamObject = new Team(teamName, managerobj, headCoachObj, bufferPlayerList, false);
					bufferTeamList.add(teamObject);
					divisionObject.setTeamList(bufferTeamList);
					JsonArray players = team.get(PlayerConstants.PLAYERS.toString()).getAsJsonArray();

					if (players.size() > 20) {
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

						int age = player.get(PlayerConstants.PLAYER_AGE.toString()).getAsInt();

						if (age < 0) {
							output.setOutput(PlayerConstants.PLAYER.toString() + count
									+ PlayerConstants.PLAYER_AGE_ERROR.toString());
							output.sendOutput();
							return null;
						}

						int skating = player.get(PlayerConstants.SKATING.toString()).getAsInt();
						int shooting = player.get(PlayerConstants.SHOOTING.toString()).getAsInt();
						int checking = player.get(PlayerConstants.CHECKING.toString()).getAsInt();
						int saving = player.get(PlayerConstants.SAVING.toString()).getAsInt();

						String returnedValue = isValidPlayer(skating, shooting, checking, saving);

						if (returnedValue.length() > 0) {
							output.setOutput(PlayerConstants.PLAYER.toString() + count + returnedValue);
							output.sendOutput();
							return null;
						}

						Player playerObject = new Player(playerName, position, captain, age, skating, shooting,
								checking, saving, Boolean.FALSE, Boolean.FALSE, 0);
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

				int age = freeAgentObj.get(PlayerConstants.PLAYER_AGE.toString()).getAsInt();
				if (age < 0) {
					output.setOutput(PlayerConstants.FREE_AGENT.toString() + count
							+ PlayerConstants.PLAYER_AGE_ERROR.toString());
					output.sendOutput();
					return null;
				}

				int skating = freeAgentObj.get(PlayerConstants.SKATING.toString()).getAsInt();
				int shooting = freeAgentObj.get(PlayerConstants.SHOOTING.toString()).getAsInt();
				int checking = freeAgentObj.get(PlayerConstants.CHECKING.toString()).getAsInt();
				int saving = freeAgentObj.get(PlayerConstants.SAVING.toString()).getAsInt();

				String freeAgentReturnedValue = isValidPlayer(skating, shooting, checking, saving);

				if (freeAgentReturnedValue.length() > 0) {
					output.setOutput(PlayerConstants.PLAYER.toString() + count + freeAgentReturnedValue);
					output.sendOutput();
					return null;
				}

				freeAgents.add(new Player(agentName, position, captain, age, skating, shooting, checking, saving, false,
						false, 0));
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

				coaches.add(new Coach(coachName, coachSkating, coachShooting, coachChecking, coachSaving, coachDb));
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
				String managerName = managerElement.next().getAsString();
				managerName = truncateString(managerName);
				if (isEmptyString(managerName)) {
					output.setOutput(ManagerConstants.GENERAL_MANAGER_ERROR_EMPTY.toString());
					output.sendOutput();
					return null;
				}
				Manager manager = new Manager(managerName, managerDb);
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
			if (loadAgingInfo(config) == null || loadGameResolverInfo(config) == null
					|| loadInjuriesInfo(config) == null || loadTrainingInfo(config) == null
					|| loadTradingInfo(config) == null) {
				return null;
			}
			gameConfig = new GameplayConfig(aging, resolver, injury, training, trading, configDb);
		} catch (NullPointerException e) {
			throw e;
		}
		return gameConfig;
	}

	private Aging loadAgingInfo(JsonObject config) {

		JsonObject agingObj = config.get(GameConfigConstants.AGING.toString()).getAsJsonObject();
		int avgAge = agingObj.get(GameConfigConstants.AVGERAGE_RETIREMENT_AGE.toString()).getAsInt();
		int maxAge = agingObj.get(GameConfigConstants.MAX_AGE.toString()).getAsInt();
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
		aging = new Aging(avgAge, maxAge);
		return aging;
	}

	private GameResolver loadGameResolverInfo(JsonObject config) throws NullPointerException {
		try {
			JsonObject gameResolverObj = config.get(GameConfigConstants.GAME_RESOLVER.toString()).getAsJsonObject();
			double randomWinChance = gameResolverObj.get(GameConfigConstants.RANDOM_WIN_CHANCE.toString())
					.getAsDouble();
			if (randomWinChance < 0 || randomWinChance > 1) {
				output.setOutput(GameConfigConstants.INVALID_RANDOM_WIN_CHANCE.toString());
				output.sendOutput();
				return null;
			}
			resolver = new GameResolver(randomWinChance);
		} catch (NullPointerException e) {
			throw e;
		}
		return resolver;
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
			trading = new Trading(lossPoint, randomTradeOfferChance, maxPlayersPerTrade, randomAcceptanceChance);
		} catch (NullPointerException e) {
			throw e;
		}
		return trading;
	}
}