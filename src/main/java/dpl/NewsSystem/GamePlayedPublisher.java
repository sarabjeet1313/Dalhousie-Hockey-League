package dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

public class GamePlayedPublisher {
	private final List<IGamesPlayedInfo> subscribers;
	private static GamePlayedPublisher instance;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

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
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(e + NewsSystemConstants.ARGUMENT_MESSAGE.toString());
			output.sendOutput();
		}

	}

}
