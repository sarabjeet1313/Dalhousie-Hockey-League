package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class GamePlayedPublisher {
	private List<IGamesPlayed> subscribers;

	public GamePlayedPublisher(){
		subscribers = new ArrayList<IGamesPlayed>();
	}

	public void subscribe(IGamesPlayed subscriber) {
		this.subscribers.add(subscriber);
	}

	public void unsubscribe(IGamesPlayed subscriber) {
		this.subscribers.remove(subscriber);
	}

	public void notify(String winner, String loser, String datePlayed) {
		for(IGamesPlayed subscriber : this.subscribers) {
			subscriber.updateGamesPlayed(winner, loser, datePlayed);
		}

	}

}
