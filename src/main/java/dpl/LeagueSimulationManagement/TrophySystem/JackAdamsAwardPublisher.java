package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;

public class JackAdamsAwardPublisher {
    private final List<IJackAdamsAward> subscribers;
    private static JackAdamsAwardPublisher instance;
    private IUserOutput output;

    private JackAdamsAwardPublisher() {
        subscribers = new ArrayList<>();
    }

    public static JackAdamsAwardPublisher getInstance() {
        if (instance == null) {
            instance = new JackAdamsAwardPublisher();
        }
        return instance;
    }

    public void subscribe(IJackAdamsAward subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IJackAdamsAward subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(TrophyState trophy) {
        try {
            if (null == trophy || null == trophy.getAwardedTeam()) {
                throw new IllegalArgumentException();
            }
            for (IJackAdamsAward subscriber : this.subscribers) {
                trophy.setTrophyName(TrophySystemConstants.JACK_ADAMS_AWARD.toString());
                subscriber.updateTrophy(trophy);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + TrophySystemConstants.ARGUMENT_ERROR_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
