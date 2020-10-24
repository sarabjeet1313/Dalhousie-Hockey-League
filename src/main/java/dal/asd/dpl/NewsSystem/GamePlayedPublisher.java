package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class GamePlayedPublisher {
	private List<IGamesPlayedInfo> subscribers;

	public GamePlayedPublisher(){
		subscribers = new ArrayList<IGamesPlayedInfo>();
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
