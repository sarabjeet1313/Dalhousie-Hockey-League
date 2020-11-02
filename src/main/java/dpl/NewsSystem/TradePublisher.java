package dpl.NewsSystem;
import java.util.ArrayList;
import java.util.List;
import dpl.DplConstants.NewsSystemConstants;
import dpl.UserOutput.IUserOutput;

public class TradePublisher {
    private final List<ITradeInfo> subscribers;
    private static TradePublisher instance;
    private IUserOutput output;

    private TradePublisher() {
        subscribers = new ArrayList<>();
    }

    public static TradePublisher getInstance() {
        if (instance == null) {
            instance = new TradePublisher();
        }
        return instance;
    }

    public void subscribe(ITradeInfo subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(ITradeInfo subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(String fromTeam, String toTeam, String[][] playersTraded) {
        try {
            if (null == fromTeam || null == toTeam || null == playersTraded) {
                throw new IllegalArgumentException();
            }
            for (ITradeInfo subscriber : this.subscribers) {
                subscriber.updateTrade(fromTeam, toTeam, playersTraded);
            }
        } catch (IllegalArgumentException e) {
            output.setOutput(e + NewsSystemConstants.ARGUMENT_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
