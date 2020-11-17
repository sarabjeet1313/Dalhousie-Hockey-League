package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

import dpl.LeagueSimulationManagement.NewsSystem.NewsSubscriber;
import dpl.LeagueSimulationManagement.NewsSystem.TradePublisher;
import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.*;

public class Trade implements ITrade {
	private String tradeOfferTeam;
	private String tradeRequestedTeam;
	private List<Player> playerListOfferTeam;
	private List<Player> playerListRequestedTeam;
	private ITradePersistence tradeDB;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();

	static {
		TradePublisher.getInstance().subscribe(new NewsSubscriber());
	}

	public Trade() {

	}

	public Trade(ITradePersistence tradeDB) {
		this.tradeDB = tradeDB;
	}

	public Trade(String tradeOfferTeam, List<Player> playerListOfferTeam, String tradeRequestedTeam,
			List<Player> playerListRequestedTeam) {
		this.tradeOfferTeam = tradeOfferTeam;
		this.tradeRequestedTeam = tradeRequestedTeam;
		this.playerListOfferTeam = playerListOfferTeam;
		this.playerListRequestedTeam = playerListRequestedTeam;

	}

	public String getTradeOfferTeam() {
		return this.tradeOfferTeam;
	}

	public void setTradeOfferTeam(String tradeOfferTeam) {
		this.tradeOfferTeam = tradeOfferTeam;
	}

	public String getTradeRequestedTeam() {
		return tradeRequestedTeam;
	}

	public void setTradeRequestedTeam(String tradeRequestedTeam) {
		this.tradeRequestedTeam = tradeRequestedTeam;
	}

	public List<Player> getPlayerListOfferTeam() {
		return playerListOfferTeam;
	}

	public void setPlayerListOfferTeam(List<Player> playerListOfferTeam) {
		this.playerListOfferTeam = playerListOfferTeam;
	}

	public List<Player> getPlayerListRequestedTeam() {
		return playerListRequestedTeam;
	}

	public void setPlayerListRequestedTeam(List<Player> playerListRequestedTeam) {
		this.playerListRequestedTeam = playerListRequestedTeam;
	}

	public boolean checkRandOfferChance(double randOfferChance) {
		if (Math.random() < randOfferChance) {
			return true;
		} else {
			return false;
		}
	}

	public boolean matchPosition(String s1, String s2) {
		return s1.equals(s2);
	}

	public boolean matchString(String s1, String s2) {
		return s1.equals(s2);
	}

	public boolean sameTeam(String s1, String s2) {
		return s1.equals(s2);
	}

	public HashMap<Integer, Double> sortByStrength(HashMap<Integer, Double> hm, boolean descending) {
		List<Map.Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>(hm.entrySet());

		if (descending) {
			Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
				public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
					return (o2.getValue()).compareTo(o1.getValue());
				}
			});
		} else {
			Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
				public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
					return (o1.getValue()).compareTo(o2.getValue());
				}
			});
		}

		HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
		for (Map.Entry<Integer, Double> mapTemp : list) {
			temp.put(mapTemp.getKey(), mapTemp.getValue());
		}
		return temp;

	}

	public List<Player> getPlayersOfSpecificType(String typeOfPlayer, List<Player> playerList) {
		List<Player> p = new ArrayList<Player>();

		for (Player player : playerList) {

			if (matchPosition(typeOfPlayer, player.getPosition())) {
				p.add(player);
			}
		}
		return p;
	}

	public List<Player> getWeakestPlayers(int maxPlayers, String teamName, League league, ITeamInfo iTPInfoObject,
			IPlayerInfo iPInfoObject) {
		List<Player> playersByTeam = iTPInfoObject.getPlayersByTeam(teamName, league);
		List<Player> returnWeakestPlayerList = new ArrayList<Player>();
		double playerStrength;
		String weakestPlayerPosition = null;
		double[] minStrengthArray = new double[maxPlayers];
		int[] playerIndexArray = new int[maxPlayers];
		HashMap<Integer, Double> hmPlayerStrength = new HashMap<Integer, Double>();

		for (int i = 0; i < playersByTeam.size(); i++) {
			playerStrength = iPInfoObject.getPlayerStrength(playersByTeam.get(i));
			hmPlayerStrength.put(i, playerStrength);
		}
		int x = 0;
		Map<Integer, Double> sortedHm = sortByStrength(hmPlayerStrength, false);
		for (Map.Entry<Integer, Double> k : sortedHm.entrySet()) {
			playerIndexArray[x] = k.getKey();
			minStrengthArray[x] = k.getValue();
			x = x + 1;
			if (x == maxPlayers) {
				break;
			}
		}
		if (playerIndexArray.length> 0) {
			weakestPlayerPosition = playersByTeam.get(playerIndexArray[0]).getPosition();
			for (int i = 0; i < playerIndexArray.length; i++) {

				if (matchPosition(weakestPlayerPosition, playersByTeam.get(playerIndexArray[i]).getPosition())) {
					returnWeakestPlayerList.add(playersByTeam.get(playerIndexArray[i]));
				} else if (i + 1 < minStrengthArray.length) {
					if (minStrengthArray[i + 1] > minStrengthArray[i]) {
						break;
					}
				} else {
					break;
				}
			}
		}
		return returnWeakestPlayerList;
	}

	public List<Player> getStrongestPlayers(Trade t, List<String> allTeamNameList, League league,
			ITeamInfo iTPInfoObject, IPlayerInfo iPInfoObject) {
		List<Player> offeredPlayerList = t.getPlayerListOfferTeam();
		List<Player> currentTeamPlayers;

		int totalPlayersNeeded = offeredPlayerList.size();
		double[] maxPlayerStrengthsArray = new double[totalPlayersNeeded];
		int[] currentPlayerIndexArray = new int[totalPlayersNeeded];
		double[] currentPlayerMaxStrength = new double[totalPlayersNeeded];
		List<Player> returnStrongPlayerList = new ArrayList<Player>();
		HashMap<Integer, Double> hmPlayerStrength = new HashMap<Integer, Double>();

		String requiredPlayerType = offeredPlayerList.get(0).getPosition();
		for (int i = 0; i < totalPlayersNeeded; i++) {
			maxPlayerStrengthsArray[i] = iPInfoObject.getPlayerStrength(offeredPlayerList.get(i));
		}
		double offeredPlayersStrength = DoubleStream.of(maxPlayerStrengthsArray).sum();
		double requestedPlayerStrength;
		boolean isSame;
		for (int j = 0; j < allTeamNameList.size(); j++) {
			isSame = sameTeam(t.getTradeOfferTeam(), allTeamNameList.get(j));
			if (isSame == Boolean.FALSE) {
				t.setTradeRequestedTeam(allTeamNameList.get(j));
				currentTeamPlayers = getPlayersOfSpecificType(requiredPlayerType,
						iTPInfoObject.getPlayersByTeam(t.getTradeRequestedTeam(), league));

				for (int i = 0; i < currentTeamPlayers.size(); i++) {
					hmPlayerStrength.put(i, iPInfoObject.getPlayerStrength(currentTeamPlayers.get(i)));
				}
				Map<Integer, Double> sortedDesHm = sortByStrength(hmPlayerStrength, true);
				int g = 0;
				for (Map.Entry<Integer, Double> k : sortedDesHm.entrySet()) {
					currentPlayerMaxStrength[g] = k.getValue();
					currentPlayerIndexArray[g] = k.getKey();
					g = g + 1;
					if (g == totalPlayersNeeded) {
						break;
					}
				}

				requestedPlayerStrength = DoubleStream.of(currentPlayerMaxStrength).sum();
				if (requestedPlayerStrength > offeredPlayersStrength) {
					offeredPlayersStrength = requestedPlayerStrength;
					for (int h = 0; h < currentPlayerIndexArray.length; h++) {
						returnStrongPlayerList.add(currentTeamPlayers.get(currentPlayerIndexArray[h]));
					}
				}

			}
		}
		return returnStrongPlayerList;
	}

	public String[][] prepareToNotify(Trade trade) {
		int totalPlayers;
		totalPlayers = trade.getPlayerListOfferTeam().size();
		String[][] playersTraded = new String[totalPlayers][2];
		for (int i = 0; i < playersTraded.length; i++) {
			for (int j = 0; j < playersTraded[i].length; j++) {

				if (i == 0) {
					if (trade.getPlayerListOfferTeam().size() > 1) {
						playersTraded[i][j] = trade.getPlayerListOfferTeam().get(j).getPlayerName();
					}
				} else {
					if (trade.getPlayerListRequestedTeam().size() > 1) {
						playersTraded[i][j] = trade.getPlayerListRequestedTeam().get(j).getPlayerName();
					}
				}
			}
		}
		return playersTraded;
	}

	@Override
	public League startTrade(League leagueObject) throws SQLException {

		try {
			Trade trade = new Trade();
			List<String> eligibleTeamNameList;
			int maxPlayersPerTrade = leagueObject.getGameConfig().getTrading().getMaxPlayersPerTrade();
			int minLossPoints = leagueObject.getGameConfig().getTrading().getLossPoint();
			double randOfferChance = leagueObject.getGameConfig().getTrading().getRandomTradeOfferChance();
			double randAcceptChance = leagueObject.getGameConfig().getTrading().getRandomAcceptanceChance();

			String[][] playersTraded;
			List<String> allTeamNameList;
			ITeamInfo iTeamInfo = teamManagement.Team();
			IPlayerInfo iPlayerInfo = teamManagement.Player();
			AiAcceptReject aiAcceptReject = new AiAcceptReject();
			TradeReset tradeReset = new TradeReset(tradeDB);
			boolean isUserTeam = false;

			List<Conference> conferenceL = leagueObject.getConferenceList();
			List<Division> divisionL;
			List<Team> teamL;
			List<Player> playerL;

			String userTeamName = iTeamInfo.getUserTeamName(leagueObject);
			eligibleTeamNameList = tradeDB.getEligibleTeamName(minLossPoints);
			allTeamNameList = iTeamInfo.getAllTeamName(leagueObject);
			eligibleTeamNameList.remove(userTeamName);

			for (int i = 0; i < eligibleTeamNameList.size(); i++) {
				if (checkRandOfferChance(randOfferChance)) {
					tradeReset.addToTeamNames(eligibleTeamNameList.get(i));
					trade.setTradeOfferTeam(eligibleTeamNameList.get(i));
					trade.setPlayerListOfferTeam(getWeakestPlayers(maxPlayersPerTrade, eligibleTeamNameList.get(i),
							leagueObject, iTeamInfo, iPlayerInfo));
					trade.setPlayerListRequestedTeam(
							getStrongestPlayers(trade, allTeamNameList, leagueObject, iTeamInfo, iPlayerInfo));

					List<Player> tempPlayerListRequested = new ArrayList<Player>();
					List<Player> tempPlayerListOffer = new ArrayList<Player>();
					tempPlayerListRequested = trade.getPlayerListRequestedTeam();
					tempPlayerListOffer = trade.getPlayerListOfferTeam();

					if (userTeamName.equals(trade.getTradeRequestedTeam())) {
						isUserTeam = true;
					}

					if (aiAcceptReject.isAcceptOrReject(trade, leagueObject, randAcceptChance, isUserTeam, iPlayerInfo,
							iTeamInfo)) {

						for (int cLIndex = 0; cLIndex < conferenceL.size(); cLIndex++) {
							divisionL = conferenceL.get(cLIndex).getDivisionList();

							for (int dLIndex = 0; dLIndex < divisionL.size(); dLIndex++) {
								teamL = divisionL.get(dLIndex).getTeamList();

								for (int tLIndex = 0; tLIndex < teamL.size(); tLIndex++) {
									playerL = teamL.get(tLIndex).getPlayersByTeam(teamL.get(tLIndex).getTeamName(),
											leagueObject);

									if (sameTeam(teamL.get(tLIndex).getTeamName(), trade.getTradeOfferTeam())) {
										if (playerL.containsAll(tempPlayerListOffer)) {
											playerL.removeAll(tempPlayerListOffer);
										}

										for (Player pRequested : tempPlayerListRequested) {
											playerL.add(pRequested);
										}
										teamL.get(tLIndex).setPlayerList(playerL);
									}
									if (sameTeam(teamL.get(tLIndex).getTeamName(), trade.getTradeRequestedTeam())) {

										if (playerL.containsAll(tempPlayerListRequested)) {
											playerL.removeAll(tempPlayerListRequested);
										}
										for (Player pOffer : trade.getPlayerListOfferTeam()) {
											playerL.add(pOffer);
										}
										teamL.get(tLIndex).setPlayerList(playerL);
									}
								}
								divisionL.get(dLIndex).setTeamList(teamL);
							}
							conferenceL.get(cLIndex).setDivisionList(divisionL);
						}
						if (trade.getPlayerListOfferTeam().size() > 1
								&& trade.getPlayerListRequestedTeam().size() > 1) {
							playersTraded = prepareToNotify(trade);
							TradePublisher.getInstance().notify(trade.getTradeOfferTeam(),
									trade.getTradeRequestedTeam(), playersTraded);
							leagueObject.setConferenceList(conferenceL);
						}
					}
				}
			}
		} catch (SQLException e) {
			throw e;
		}
		return leagueObject;
	}
}
