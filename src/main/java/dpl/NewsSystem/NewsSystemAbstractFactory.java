package dpl.NewsSystem;

public class NewsSystemAbstractFactory implements INewsSystemAbstractFactory {

	@Override
	public NewsSubscriber NewsSubscriber() {
		return new NewsSubscriber();
	}
}
