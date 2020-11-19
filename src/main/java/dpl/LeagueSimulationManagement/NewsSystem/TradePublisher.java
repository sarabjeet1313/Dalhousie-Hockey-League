package dpl.LeagueSimulationManagement.NewsSystem;
import java.util.ArrayList;
import java.util.List;
import dpl.DplConstants.NewsSystemConstants;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

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

    public void notify(String fromTeam, String toTeam, ArrayList<String> fromTeamTrade, ArrayList<String> toTeamTrade) {
        try {
            if (null == fromTeam || null == toTeam || toTeamTrade.size() < 1 || fromTeamTrade.size() < 1) {
                throw new IllegalArgumentException();
            }
            for (ITradeInfo subscriber : this.subscribers) {
                subscriber.updateTrade(fromTeam, toTeam, fromTeamTrade, toTeamTrade);
            }
        } catch (IllegalArgumentException e) {
            output.setOutput(e + NewsSystemConstants.ARGUMENT_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
