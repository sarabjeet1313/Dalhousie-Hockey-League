package dal.asd.dpl.NewsSystem;

public class TradePublisher extends AbstractPublisher{

    public void notify(String fromTeam, String toTeam, String[][] playersTraded) {
        for(Object subscriber : this.subscribers) {
            ((ITrade)subscriber).updateTrade(fromTeam, toTeam, playersTraded);
        }
    }

}
