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
import dal.asd.dpl.Util.CoachUtil;
import dal.asd.dpl.Util.ConferenceUtil;
import dal.asd.dpl.Util.ConstantsUtil;
import dal.asd.dpl.Util.DivisionUtil;
import dal.asd.dpl.Util.GameConfigUtil;
import dal.asd.dpl.Util.InitializeLeaguesUtil;
import dal.asd.dpl.Util.ManagerUtil;
import dal.asd.dpl.Util.PlayerUtil;
import dal.asd.dpl.Util.TeamUtil;

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
		if (null == valueToCheck || valueToCheck.isEmpty() ) {
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
		String leagueName = parser.parse(InitializeLeaguesUtil.LEAGUE_NAME.toString());

		if (isEmptyString(leagueName)) {
			output.setOutput(InitializeLeaguesUtil.NULL_ERROR.toString());
			output.sendOutput();
			return null;
		}

		if (leagueName == InitializeLeaguesUtil.ERROR.toString()) {
			return null;
		}

		leagueName = truncateString(leagueName);
		league = new League(leagueName, conferenceList, freeAgents, coaches, managerList, gameConfig, leagueDb);
		boolean check = league.isValidLeagueName(leagueName, leagueDb);

		if (check == Boolean.FALSE) {
			output.setOutput(InitializeLeaguesUtil.VALID_MSG.toString());
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
		return league;
	}

	private List<Conference> loadConferencesInfo() {
		JsonArray conferences = parser.parseList(InitializeLeaguesUtil.CONFERENCES.toString());
		Iterator<JsonElement> conferenceListElement = conferences.iterator();

		while (conferenceListElement.hasNext()) {
			JsonObject conference = conferenceListElement.next().getAsJsonObject();
			String conferenceName = conference.get(ConferenceUtil.CONFERENCE_NAME.toString()).toString();
			List<Division> bufferDivisionList = new ArrayList<Division>();

			conferenceName = truncateString(conferenceName);

			if (isEmptyString(conferenceName)) {
				output.setOutput(ConferenceUtil.CONFERENCE_ERROR.toString());
				output.sendOutput();
				return null;
			}

			Conference conferenceObject = new Conference(conferenceName, bufferDivisionList);
			conferenceList.add(conferenceObject);
			JsonArray divisions = conference.get(DivisionUtil.DIVISIONS_MODEL.toString()).getAsJsonArray();
			Iterator<JsonElement> divisionListElement = divisions.iterator();

			while (divisionListElement.hasNext()) {
				JsonObject division = divisionListElement.next().getAsJsonObject();
				String divisionName = division.get(DivisionUtil.DIVISION_NAME.toString()).toString();
				List<Team> bufferTeamList = new ArrayList<Team>();
				divisionName = truncateString(divisionName);

				if (isEmptyString(divisionName)) {
					output.setOutput(DivisionUtil.DIVISIONS_ERROR.toString());
					output.sendOutput();
					return null;
				}

				Division divisionObject = new Division(divisionName, bufferTeamList);
				bufferDivisionList.add(divisionObject);
				conferenceObject.setDivisionList(bufferDivisionList);
				JsonArray teams = division.get(TeamUtil.TEAMS.toString()).getAsJsonArray();
				Iterator<JsonElement> teamListElement = teams.iterator();

				while (teamListElement.hasNext()) {
					JsonObject team = teamListElement.next().getAsJsonObject();
					String teamName = team.get(TeamUtil.TEAM_NAME.toString()).toString();
					List<Player> bufferPlayerList = new ArrayList<Player>();
					teamName = truncateString(teamName);
					if (isEmptyString(teamName)) {
						output.setOutput(TeamUtil.TEAM_ERROR.toString());
						output.sendOutput();
						return null;
					}

					String genManager = team.get(ManagerUtil.GENERAL_MANAGER.toString()).toString();
					genManager = truncateString(genManager);

					if (isEmptyString(genManager)) {
						output.setOutput(ManagerUtil.GENERAL_MANAGER_ERROR.toString());
						output.sendOutput();
						return null;
					}
					Manager managerobj = new Manager(genManager, managerDb);

					JsonObject headCoach = team.get(CoachUtil.HEAD_COACH.toString()).getAsJsonObject();
					String headCoachName = headCoach.get(CoachUtil.NAME.toString()).toString();
					headCoachName = truncateString(headCoachName);

					if (isEmptyString(headCoachName)) {
						output.setOutput(CoachUtil.HEAD_COACH_ERROR.toString());
						output.sendOutput();
						return null;
					}

					double coachSkating = headCoach.get(PlayerUtil.SKATING.toString()).getAsDouble();
					double coachShooting = headCoach.get(PlayerUtil.SHOOTING.toString()).getAsDouble();
					double coachChecking = headCoach.get(PlayerUtil.CHECKING.toString()).getAsDouble();
					double coachSaving = headCoach.get(PlayerUtil.SAVING.toString()).getAsDouble();

					String headCoachValue = isValidCoach(coachSkating, coachShooting, coachChecking, coachSaving);

					if (headCoachValue.length() > 0) {
						output.setOutput(CoachUtil.HEAD_COACH.toString() + headCoachValue);
						output.sendOutput();
						return null;
					}

					Coach headCoachObj = new Coach(headCoachName, coachSkating, coachShooting, coachChecking,
							coachSaving, coachDb);

					Team teamObject = new Team(teamName, managerobj, headCoachObj, bufferPlayerList, false);
					bufferTeamList.add(teamObject);
					divisionObject.setTeamList(bufferTeamList);
					JsonArray players = team.get(PlayerUtil.PLAYERS.toString()).getAsJsonArray();

					if (players.size() > 20) {
						output.setOutput(TeamUtil.TEAM_OVER_FLOW.toString());
						output.sendOutput();
						return null;
					}

					Iterator<JsonElement> player_List = players.iterator();
					boolean isCaptainPositionOccupied = Boolean.FALSE;
					int count = 0;

					while (player_List.hasNext()) {
						count++;
						JsonObject player = player_List.next().getAsJsonObject();
						String playerName = player.get(PlayerUtil.PLAYER_NAME.toString()).toString();
						playerName = truncateString(playerName);

						if (isEmptyString(playerName)) {
							output.setOutput(PlayerUtil.ENTER_DETAILS.toString()+ count + PlayerUtil.EMPTY_MSG.toString());
							output.sendOutput();
							return null;
						}

						String position = player.get(PlayerUtil.PLAYER_POSITION.toString()).toString();
						position = truncateString(position);

						if (isEmptyString(position)) {
							output.setOutput(
									PlayerUtil.ENTER_PLAYER.toString() + count + PlayerUtil.POSITION_ERROR.toString());
							output.sendOutput();
							return null;
						}

						if (!(position.contains(ConstantsUtil.FORWARD.toString()) || position.contains(ConstantsUtil.GOALIE.toString())
								|| position.contains(ConstantsUtil.DEFENSE.toString()))) {
							output.setOutput(
									PlayerUtil.PLAYER.toString() + count + PlayerUtil.POSITION_TYPE_ERROR.toString());
							output.sendOutput();
							return null;
						}

						Boolean captain = player.get(PlayerUtil.PLAYER_CAPTAIN.toString()).getAsBoolean();

						if (captain && isCaptainPositionOccupied) {
							output.setOutput(PlayerUtil.CAPTAIN_ERROR.toString());
							output.sendOutput();
							return null;
						}

						if (captain) {
							isCaptainPositionOccupied = captain;
						}

						int age = player.get(PlayerUtil.PLAYER_AGE.toString()).getAsInt();
						
						if (age < 0) {
							output.setOutput(PlayerUtil.PLAYER.toString() + count + PlayerUtil.PLAYER_AGE_ERROR.toString());
							output.sendOutput();
							return null;
						}

						int skating = player.get(PlayerUtil.SKATING.toString()).getAsInt();
						int shooting = player.get(PlayerUtil.SHOOTING.toString()).getAsInt();
						int checking = player.get(PlayerUtil.CHECKING.toString()).getAsInt();
						int saving = player.get(PlayerUtil.SAVING.toString()).getAsInt();

						String returnedValue = isValidPlayer(skating, shooting, checking, saving);

						if (returnedValue.length() > 0) {
							output.setOutput(PlayerUtil.PLAYER.toString() + count + returnedValue);
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
			return PlayerUtil.SKATING_ERROR.toString();
		}
		if (shooting < 0 || shooting > 20) {
			return PlayerUtil.SHOOTING_ERROR.toString();
		}
		if (checking < 0 || checking > 20) {
			return PlayerUtil.CHECKING_ERROR.toString();
		}
		if (saving < 0 || saving > 20) {
			return PlayerUtil.SAVING_ERROR.toString();
		}
		return "";
	}

	public String isValidCoach(double skating, double shooting, double checking, double saving) {
		if (skating < 0 || skating > 1) {
			return CoachUtil.SKATING_ERROR.toString();
		}
		if (shooting < 0 || shooting > 1) {
			return CoachUtil.SHOOTING_ERROR.toString();
		}
		if (checking < 0 || checking > 1) {
			return CoachUtil.CHECKING_ERROR.toString();
		}
		if (saving < 0 || saving > 1) {
			return CoachUtil.SAVING_ERROR.toString();
		}
		return "";
	}

	private List<Player> loadFreeAgentsInfo() {
		JsonArray freeAgentsArray = parser.parseList(PlayerUtil.FREE_AGENT.toString());
		Iterator<JsonElement> freeAgentElement = freeAgentsArray.iterator();
		int count = 0;

		while (freeAgentElement.hasNext()) {
			count++;
			JsonObject freeAgentObj = freeAgentElement.next().getAsJsonObject();
			String agentName = freeAgentObj.get(PlayerUtil.PLAYER_NAME.toString()).toString();
			agentName = truncateString(agentName);

			if (isEmptyString(agentName)) {
				output.setOutput(PlayerUtil.ENTER_FREE_AGENT.toString()+ count + CoachUtil.NAME.toString());
				output.sendOutput();
				return null;
			}

			String position = freeAgentObj.get(PlayerUtil.PLAYER_POSITION.toString()).toString();
			position = truncateString(position);

			if (isEmptyString(position)) {
				output.setOutput(PlayerUtil.FREE_AGENT.toString()+ count + PlayerUtil.PLAYER_POSITION.toString());
				output.sendOutput();
				return null;
			}

			if (!(position.contains(ConstantsUtil.FORWARD.toString()) || position.contains(ConstantsUtil.GOALIE.toString())
					|| position.contains(ConstantsUtil.DEFENSE.toString()))) {
				output.setOutput(PlayerUtil.FREE_AGENT.toString() + count +  PlayerUtil.POSITION_TYPE_ERROR.toString());
				output.sendOutput();
				return null;
			}

			Boolean captain = false;

			if (captain) {
				output.setOutput(PlayerUtil.FREE_AGENT.toString()  + count + PlayerUtil.CANNOT_CAPTAIN.toString());
				output.sendOutput();
				return null;
			}

			int age = freeAgentObj.get(PlayerUtil.PLAYER_AGE.toString()).getAsInt();
			if (age < 0) {
				output.setOutput(PlayerUtil.FREE_AGENT.toString()  + count + PlayerUtil.PLAYER_AGE_ERROR.toString());
				output.sendOutput();
				return null;
			}

			int skating = freeAgentObj.get(PlayerUtil.SKATING.toString()).getAsInt();
			int shooting = freeAgentObj.get(PlayerUtil.SHOOTING.toString()).getAsInt();
			int checking = freeAgentObj.get(PlayerUtil.CHECKING.toString()).getAsInt();
			int saving = freeAgentObj.get(PlayerUtil.SAVING.toString()).getAsInt();

			String freeAgentReturnedValue = isValidPlayer(skating, shooting, checking, saving);

			if (freeAgentReturnedValue.length() > 0) {
				output.setOutput(PlayerUtil.PLAYER.toString() + count + freeAgentReturnedValue);
				output.sendOutput();
				return null;
			}

			freeAgents.add(new Player(agentName, position, captain, age, skating, shooting, checking, saving, false,
					false, 0));
		}

		return freeAgents;
	}

	private List<Coach> loadCoachesInfo() {
		JsonArray coachesList = parser.parseList(CoachUtil.COACHES.toString());
		Iterator<JsonElement> coachElement = coachesList.iterator();
		int coachCount = 0;

		while (coachElement.hasNext()) {
			coachCount++;
			JsonObject coachObj = coachElement.next().getAsJsonObject();
			String coachName = coachObj.get(CoachUtil.NAME.toString()).toString();
			coachName = truncateString(coachName);

			if (isEmptyString(coachName)) {
				output.setOutput(CoachUtil.ENTER_COACH.toString() + coachCount + CoachUtil.NAME.toString());
				output.sendOutput();
				return null;
			}

			double coachSkating = coachObj.get(PlayerUtil.SKATING.toString()).getAsDouble();
			if (coachSkating < 0 || coachSkating > 1) {
				output.setOutput("Coach:" + coachCount + CoachUtil.SKATING_ERROR.toString());
				output.sendOutput();
				return null;
			}

			double coachShooting = coachObj.get(PlayerUtil.SHOOTING.toString()).getAsDouble();
			if (coachShooting < 0 || coachShooting > 1) {
				output.setOutput(CoachUtil.COACH.toString() + coachCount + CoachUtil.SHOOTING_ERROR.toString());
				output.sendOutput();
				return null;
			}

			double coachChecking = coachObj.get(PlayerUtil.CHECKING.toString()).getAsDouble();
			if (coachChecking < 0 || coachChecking > 1) {
				output.setOutput(CoachUtil.COACH.toString() + coachCount + CoachUtil.CHECKING_ERROR.toString());
				output.sendOutput();
				return null;
			}

			double coachSaving = coachObj.get(PlayerUtil.SAVING.toString()).getAsDouble();
			if (coachSaving < 0 || coachSaving > 1) {
				output.setOutput(CoachUtil.COACH.toString() + coachCount + CoachUtil.SAVING_ERROR.toString());
				output.sendOutput();
				return null;
			}

			coaches.add(new Coach(coachName, coachSkating, coachShooting, coachChecking, coachSaving, coachDb));
		}
		return coaches;
	}

	private List<Manager> loadManagerInfo() {
		JsonArray managerParseList = parser.parseList(ManagerUtil.GENERAL_MANAGERS.toString());
		Iterator<JsonElement> managerElement = managerParseList.iterator();
		while (managerElement.hasNext()) {
			String managerName = managerElement.next().getAsString();
			managerName = truncateString(managerName);
			if (isEmptyString(managerName)) {
				output.setOutput(ManagerUtil.GENERAL_MNAGER_ERROR_EMPTY.toString());
				output.sendOutput();
				return null;
			}
			Manager manager = new Manager(managerName, managerDb);
			managerList.add(manager);
		}
		return managerList;
	}

	private GameplayConfig loadGameplayConfig() {
		JsonObject config = parser.parseConfig(GameConfigUtil.GAME_PLAY_CONFIG.toString());
		if (loadAgingInfo(config) == null || loadGameResolverInfo(config) == null || loadInjuriesInfo(config) == null 
				|| loadTrainingInfo(config) == null || loadTradingInfo(config) == null ) {
			return null;
		}
		gameConfig = new GameplayConfig(aging, resolver, injury, training, trading, configDb);
		return gameConfig;
	}

	private Aging loadAgingInfo(JsonObject config) {

		JsonObject agingObj = config.get(GameConfigUtil.AGING.toString()).getAsJsonObject();
		int avgAge = agingObj.get(GameConfigUtil.AVGERAGE_RETIREMENT_AGE.toString()).getAsInt();
		int maxAge = agingObj.get(GameConfigUtil.MAX_AGE.toString()).getAsInt();
		if (avgAge < 1) {
			output.setOutput(GameConfigUtil.INVALID_RETIREMENT_AGE.toString());
			output.sendOutput();
			return null;
		}
		if (maxAge < avgAge || maxAge < 1) {
			output.setOutput(GameConfigUtil.INVALID_MAX_AGE.toString());
			output.sendOutput();
			return null;
		}
		aging = new Aging(avgAge, maxAge);
		return aging;
	}

	private GameResolver loadGameResolverInfo(JsonObject config) {
		JsonObject gameResolverObj = config.get(GameConfigUtil.GAME_RESOLVER.toString()).getAsJsonObject();
		double randomWinChance = gameResolverObj.get(GameConfigUtil.RANDOM_WIN_CHANCE.toString()).getAsDouble();
		if (randomWinChance < 0 || randomWinChance > 1) {
			output.setOutput(GameConfigUtil.INVALID_RANDOM_WIN_CHANCE.toString());
			output.sendOutput();
			return null;
		}
		resolver = new GameResolver(randomWinChance);
		return resolver;
	}

	private Injury loadInjuriesInfo(JsonObject config) {
		JsonObject injuriesObj = config.get(GameConfigUtil.INJURIES.toString()).getAsJsonObject();
		double randomInjuryChance = injuriesObj.get(GameConfigUtil.RANDOM_INJURY_CHANCE.toString()).getAsDouble();
		if (randomInjuryChance < 0 || randomInjuryChance > 1) {
			output.setOutput(GameConfigUtil.INVALID_RANDOM_INJURY.toString());
			output.sendOutput();
			return null;
		}
		int injuryDaysLow = injuriesObj.get(GameConfigUtil.INJURY_DAYS_LOW.toString()).getAsInt();
		if (injuryDaysLow < 0) {
			output.setOutput(GameConfigUtil.INVALID_LOWER_LIMIT.toString());
			output.sendOutput();
			return null;
		}
		int injuryDaysHigh = injuriesObj.get(GameConfigUtil.INJURY_DAYS_LOW.toString()).getAsInt();
		if (injuryDaysHigh < 0 || injuryDaysHigh < injuryDaysLow) {
			output.setOutput(GameConfigUtil.INVALID_RANDOM_INJURY.toString());
			output.sendOutput();
			return null;
		}
		injury = new Injury(randomInjuryChance, injuryDaysLow, injuryDaysHigh);
		return injury;
	}

	private Training loadTrainingInfo(JsonObject config) {
		JsonObject trainingObj = config.get(GameConfigUtil.TRAINING.toString()).getAsJsonObject();
		int daysUntilStatIncreaseCheck = trainingObj.get(GameConfigUtil.STAT_INCREASE_CHECK.toString()).getAsInt();
		if (daysUntilStatIncreaseCheck < 0) {
			output.setOutput(GameConfigUtil.INVALID_DAYS.toString());
			output.sendOutput();
			return null;
		}
		int trachDays = daysUntilStatIncreaseCheck;
		training = new Training(daysUntilStatIncreaseCheck, trachDays);
		return training;
	}

	private Trading loadTradingInfo(JsonObject config) {
		JsonObject tradingObj = config.get(GameConfigUtil.TRADING.toString()).getAsJsonObject();
		int lossPoint = tradingObj.get(GameConfigUtil.LOSS_POINT.toString()).getAsInt();
		if (lossPoint < 0) {
			output.setOutput(GameConfigUtil.INVALID_LOSS_POINT.toString());
			output.sendOutput();
			return null;
		}
		double randomTradeOfferChance = tradingObj.get(GameConfigUtil.RANDOM_TRADE_OFFER_CHANCE.toString()).getAsDouble();
		if (randomTradeOfferChance < 0 || randomTradeOfferChance > 1) {
			output.setOutput(GameConfigUtil.INVALID_TRADE_OFFER.toString());
			output.sendOutput();
			return null;
		}
		int maxPlayersPerTrade = tradingObj.get(GameConfigUtil.MAX_PLAYERS_PER_TRADE.toString()).getAsInt();
		if (maxPlayersPerTrade < 0) {
			output.setOutput(GameConfigUtil.INVALID_MAX_TRADE.toString());
			output.sendOutput();
			return null;
		}
		double randomAcceptanceChance = tradingObj.get(GameConfigUtil.RANDOM_ACCEPTANCE_CHANCE.toString()).getAsDouble();
		if (randomTradeOfferChance < 0 || randomTradeOfferChance > 1) {
			output.setOutput(GameConfigUtil.INVALID_RANDOM.toString());
			output.sendOutput();
			return null;
		}
		trading = new Trading(lossPoint, randomTradeOfferChance, maxPlayersPerTrade, randomAcceptanceChance);
		return trading;
	}
}