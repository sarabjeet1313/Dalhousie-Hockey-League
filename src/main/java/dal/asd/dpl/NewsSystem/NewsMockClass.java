package dal.asd.dpl.NewsSystem;

public class NewsMockClass {

    public void callable(){
        IGamesPlayed game= new NewsSystem();
        game.notifyGamesPlayed(" TeamA ", " Team B ");
    }

}
