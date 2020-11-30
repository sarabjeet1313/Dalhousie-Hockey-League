package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.util.List;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class TradingAbstractFactory implements ITradingAbstractFactory {

	@Override
	public AiAcceptReject AiAcceptReject() {
		return new AiAcceptReject();
	}

	@Override
	public TradeReset TradeReset(ITradePersistence tradeDB) {
		return new TradeReset(tradeDB);
	}

	@Override
	public Trade Trade() {
		return new Trade();
	}

	@Override
	public Trade Trade(ITradePersistence tradeDB) {
		return new Trade(tradeDB);
	}

	@Override
	public Trade TradeWithDb(ITradePersistence tradeDB) {
		return new Trade(tradeDB);
	}

	@Override
	public Trade TradeWithParameters(String tradeOfferTeam, List<Player> playerListOfferTeam,
			String tradeRequestedTeam, List<Player> playerListRequestedTeam) {
		return new Trade(tradeOfferTeam, playerListOfferTeam, tradeRequestedTeam, playerListRequestedTeam);
	}

}
