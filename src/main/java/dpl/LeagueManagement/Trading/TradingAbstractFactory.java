package dpl.LeagueManagement.Trading;

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
	public TradeUtility TradeUtility() {
		return new TradeUtility();
	}

	@Override
	public Trade Trade(ITradePersistence tradeDB) {
		return new Trade(tradeDB);
	}

}
