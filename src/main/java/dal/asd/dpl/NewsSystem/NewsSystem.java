package dal.asd.dpl.NewsSystem;

public class NewsSystem implements IGamesPlayed {



    @Override
    public void notifyGamesPlayed(String winner, String loser) {
        System.out.println("Winner Team is " + winner+ " Loser Team is " + loser );

    }
}
