package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.util.List;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public interface ITradingAbstractFactory {

	public AiAcceptReject AiAcceptReject();

	public TradeReset TradeReset(ITradePersistence tradeDB);

	public Trade Trade();

	public Trade Trade(ITradePersistence tradeDB);

	public Trade TradeWithDb(ITradePersistence tradeDB);

	public Trade TradeWithParameters(String tradeOfferTeam, List<Player> playerListOfferTeam,
			String tradeRequestedTeam, List<Player> playerListRequestedTeam);
}
