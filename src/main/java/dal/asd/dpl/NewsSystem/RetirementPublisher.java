package dal.asd.dpl.NewsSystem;

public class RetirementPublisher extends AbstractPublisher{

	public void notify(String player, int age) {
		for(Object subscriber : this.subscribers) {
			((IRetirement)subscriber).updateRetirement(player, age);
		}
	}
}
