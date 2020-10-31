package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class FreeAgencyPublisher {
	private final List<IFreeAgencyInfo> subscribers;
	private static FreeAgencyPublisher instance;

	private FreeAgencyPublisher(){
		subscribers = new ArrayList<>();
	}
	/*public List<IFreeAgencyInfo> getSubscribers(){
		return new ArrayList<>(subscribers);
	}*/

	public static FreeAgencyPublisher getInstance(){
		if(instance == null){
			instance = new FreeAgencyPublisher();
		}
		return instance;
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
