package dal.asd.dpl.NewsSystem;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class NewsSystemTest {


    @Test
    public void newsSystem() {
        NewsSystem news = new NewsSystem();
        String winner = "teamA";
        String loser = "teamB";
        news.notifyGamesPlayed(winner,loser);
        assertEquals("Winner Team is teamA Loser Team is teamB","Winner Team is " + winner+ " Loser Team is " + loser);
    }

}
