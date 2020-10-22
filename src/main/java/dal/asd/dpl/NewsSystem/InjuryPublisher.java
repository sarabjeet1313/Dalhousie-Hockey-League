package dal.asd.dpl.NewsSystem;

public class InjuryPublisher extends AbstractPublisher{

	public void notify(String player, int daysInjured) {
		for(Object subscriber : this.subscribers) {
			((IInjuries)subscriber).updateInjuries(player, daysInjured);
		}
	}
}
