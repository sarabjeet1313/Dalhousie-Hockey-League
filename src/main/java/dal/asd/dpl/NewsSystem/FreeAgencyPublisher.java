package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class FreeAgencyPublisher {
	private List<IFreeAgencyInfo> subscribers;

	public FreeAgencyPublisher(){
		subscribers = new ArrayList<IFreeAgencyInfo>();
	}

	public void subscribe(IFreeAgencyInfo subscriber) {
		this.subscribers.add(subscriber);
	}

	public void unsubscribe(IFreeAgencyInfo subscriber) {
		this.subscribers.remove(subscriber);
	}

	public void notify(String player, String hiredOrReleased) {
		for(IFreeAgencyInfo subscriber : this.subscribers) {
			subscriber.updateFreeAgency(player, hiredOrReleased);
		}
	}
}
