package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;

public class VezinaTrophyPublisher {
    private final List<IVezinaTrophy> subscribers;
    private static VezinaTrophyPublisher instance;
    private IUserOutput output;

    private VezinaTrophyPublisher() {
        subscribers = new ArrayList<>();
    }

    public static VezinaTrophyPublisher getInstance() {
        if (instance == null) {
            instance = new VezinaTrophyPublisher();
        }
        return instance;
    }

    public void subscribe(IVezinaTrophy subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IVezinaTrophy subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(TrophyState trophy) {
        try {
            if (null == trophy || null == trophy.getAwardedTeam()) {
                throw new IllegalArgumentException();
            }
            for (IVezinaTrophy subscriber : this.subscribers) {
                trophy.setTrophyName(TrophySystemConstants.VEZINA_TROPHY.toString());
                subscriber.updateTrophy(trophy);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + TrophySystemConstants.ARGUMENT_ERROR_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
