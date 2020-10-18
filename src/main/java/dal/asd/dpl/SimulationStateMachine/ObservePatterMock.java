package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.NewsSystem.NewsSystem;

public class ObservePatterMock {
    public static void main(String args[]){
        Trading trades=new Trading();
        NewsSystem news= new NewsSystem("Team A", trades);
        trades.subscribe(news);
        trades.setName("Team B");
        trades.unSubscribe(news);
        NewsSystem news1= new NewsSystem("Team B", trades);
        trades.subscribe(news1);
        trades.setName("Team C");



    }
}
