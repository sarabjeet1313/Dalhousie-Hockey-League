package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.util.List;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public interface ITradingAbstractFactory {

    public AiAcceptReject AiAcceptReject();

    public TradeReset TradeReset(ITradePersistence tradeDB);

    public Trade Trade();

    public Trade Trade(ITradePersistence tradeDB);

    public TradeUtility TradeUtility();

}
