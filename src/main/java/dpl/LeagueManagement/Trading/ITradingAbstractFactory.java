package dpl.LeagueManagement.Trading;

public interface ITradingAbstractFactory {

	public AiAcceptReject AiAcceptReject();

	public TradeReset TradeReset(ITradePersistence tradeDB);

	public Trade Trade();

	public Trade Trade(ITradePersistence tradeDB);

	public TradeUtility TradeUtility();

}
