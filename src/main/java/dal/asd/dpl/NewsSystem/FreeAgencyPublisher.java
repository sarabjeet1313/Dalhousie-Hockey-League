package dal.asd.dpl.NewsSystem;

public class FreeAgencyPublisher extends AbstractPublisher{

	public void notify(String player, String hiredOrReleased) {
		for(Object subscriber : this.subscribers) {
			((IFreeAgency)subscriber).updateFreeAgency(player, hiredOrReleased);
		}
	}
}
