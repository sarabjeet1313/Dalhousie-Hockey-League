package dpl.LeagueSimulationManagement.NewsSystem;

import java.util.ArrayList;
import java.util.List;

import dpl.DplConstants.NewsSystemConstants;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class GamePlayedPublisher {
    private final List<IGamesPlayedInfo> subscribers;
    private static GamePlayedPublisher instance;
    private IUserOutput output;

    private GamePlayedPublisher() {
        subscribers = new ArrayList<>();
    }

    public static GamePlayedPublisher getInstance() {
        if (instance == null) {
            instance = new GamePlayedPublisher();
        }
        return instance;
    }

    public void subscribe(IGamesPlayedInfo subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IGamesPlayedInfo subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(String winner, String loser, String datePlayed) {
        try {
            if (null == winner || null == loser || null == datePlayed) {
                throw new IllegalArgumentException();
            }
            for (IGamesPlayedInfo subscriber : this.subscribers) {
                subscriber.updateGamesPlayed(winner, loser, datePlayed);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + NewsSystemConstants.ARGUMENT_MESSAGE.toString());
            output.sendOutput();
        }

    }

}
