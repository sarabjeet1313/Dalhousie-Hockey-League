package dal.asd.dpl.NewsSystem;

public class GamePlayedPublisher extends AbstractPublisher{

	public void notify(String winner, String loser, String datePlayed) {
		for(Object subscriber : this.subscribers) {
			((IGamesPlayed)subscriber).updateGamesPlayed(winner, loser, datePlayed);
		}
	}

}
