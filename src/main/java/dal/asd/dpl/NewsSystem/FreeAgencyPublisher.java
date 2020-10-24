package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class FreeAgencyPublisher {
	private List<IFreeAgency> subscribers;

	public FreeAgencyPublisher(){
		subscribers = new ArrayList<IFreeAgency>();
	}

	public void subscribe(IFreeAgency subscriber) {
		this.subscribers.add(subscriber);
	}

	public void unsubscribe(IFreeAgency subscriber) {
		this.subscribers.remove(subscriber);
	}

	public void notify(String player, String hiredOrReleased) {
		for(IFreeAgency subscriber : this.subscribers) {
			subscriber.updateFreeAgency(player, hiredOrReleased);
		}
	}
}
