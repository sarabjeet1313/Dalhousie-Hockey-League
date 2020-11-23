package dpl.LeagueSimulationManagement.TrophySystem.publisher;

import dpl.LeagueSimulationManagement.TrophySystem.constants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.TrophySystem.data.TrophyState;
import dpl.LeagueSimulationManagement.TrophySystem.subscriber.ICalderMemorialTrophy;
import dpl.LeagueSimulationManagement.TrophySystem.subscriber.IPresidentsTrophy;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;

public class CalderMemorialTrophyPublisher {
    private final List<ICalderMemorialTrophy> subscribers;
    private static CalderMemorialTrophyPublisher instance;
    private IUserOutput output;

    private CalderMemorialTrophyPublisher() {
        subscribers = new ArrayList<>();
    }

    public static CalderMemorialTrophyPublisher getInstance() {
        if (instance == null) {
            instance = new CalderMemorialTrophyPublisher();
        }
        return instance;
    }
    public void subscribe(ICalderMemorialTrophy subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(ICalderMemorialTrophy subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(TrophyState trophy) {
        try {
            if (null == trophy || null == trophy.getAwardedTeam()) {
                throw new IllegalArgumentException();
            }
            for (ICalderMemorialTrophy subscriber : this.subscribers) {
                trophy.setTrophyName(TrophySystemConstants.CALDER_MEMORIAL_TROPHY.toString());
                subscriber.updateTrophy(trophy);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + TrophySystemConstants.ARGUMENT_ERROR_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
