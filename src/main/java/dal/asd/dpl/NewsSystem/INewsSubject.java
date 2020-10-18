package dal.asd.dpl.NewsSystem;

public interface INewsSubject {
    public void subscribe(INewsSubscriber observer);
    public void unSubscribe(INewsSubscriber observer);
    public void notifyNewsSystem();
}
