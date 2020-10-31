package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GamePlayedPublisher {
	private final List<IGamesPlayedInfo> subscribers;
	private static GamePlayedPublisher instance;

	private GamePlayedPublisher(){
		subscribers = new ArrayList<>();
	}

/*	public List<IGamesPlayedInfo> getSubscribers(){
		return new ArrayList<>(subscribers);
	}*/

	public static GamePlayedPublisher getInstance(){
		if(instance == null){
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
		for(IGamesPlayedInfo subscriber : this.subscribers) {
			subscriber.updateGamesPlayed(winner, loser, datePlayed);
		}

	}

}
