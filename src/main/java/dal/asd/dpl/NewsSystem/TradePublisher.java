package dal.asd.dpl.NewsSystem;

public class TradePublisher {
    private ITrade tradeSubscriber;

    public TradePublisher(ITrade tradeSubscriber) {
        this.tradeSubscriber = tradeSubscriber;
    }

    public void notify(String fromTeam, String toTeam, String[][] playersTraded) {
        this.tradeSubscriber.updateTrade(fromTeam, toTeam, playersTraded);
    }

}
