package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;
import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;

public class PresidentsTrophyPublisher {
    private final List<IPresidentsTrophy> subscribers;
    private static PresidentsTrophyPublisher instance;
    private IUserOutput output;

    private PresidentsTrophyPublisher() {
        subscribers = new ArrayList<>();
    }

    public static PresidentsTrophyPublisher getInstance() {
        if (instance == null) {
            instance = new PresidentsTrophyPublisher();
        }
        return instance;
    }

    public void subscribe(IPresidentsTrophy subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IPresidentsTrophy subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(TrophyState trophy) {
        try {
            if (null == trophy || null == trophy.getAwardedTeam()) {
                throw new IllegalArgumentException();
            }
            for (IPresidentsTrophy subscriber : this.subscribers) {
                trophy.setTrophyName(TrophySystemConstants.PRESIDENT_TROPHY.toString());
                subscriber.updateTrophy(trophy);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + TrophySystemConstants.ARGUMENT_ERROR_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
