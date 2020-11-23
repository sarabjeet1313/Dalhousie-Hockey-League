package dpl.LeagueSimulationManagement.TrophySystem.publisher;

import dpl.LeagueSimulationManagement.TrophySystem.constants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.TrophySystem.data.TrophyState;
import dpl.LeagueSimulationManagement.TrophySystem.subscriber.IJackAdamsAward;
import dpl.LeagueSimulationManagement.TrophySystem.subscriber.IMauriceRichardTrophy;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;

public class MauriceRichardTrophyPublisher {
    private final List<IMauriceRichardTrophy> subscribers;
    private static MauriceRichardTrophyPublisher instance;
    private IUserOutput output;

    private MauriceRichardTrophyPublisher() {
        subscribers = new ArrayList<>();
    }

    public static MauriceRichardTrophyPublisher getInstance() {
        if (instance == null) {
            instance = new MauriceRichardTrophyPublisher();
        }
        return instance;
    }
    public void subscribe(IMauriceRichardTrophy subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IMauriceRichardTrophy subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(TrophyState trophy) {
        try {
            if (null == trophy || null == trophy.getAwardedTeam()) {
                throw new IllegalArgumentException();
            }
            for (IMauriceRichardTrophy subscriber : this.subscribers) {
                trophy.setTrophyName(TrophySystemConstants.MAURICE_RICHARD_TROPHY.toString());
                subscriber.updateTrophy(trophy);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + TrophySystemConstants.ARGUMENT_ERROR_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
