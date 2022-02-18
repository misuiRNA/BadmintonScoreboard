package domain.competition;


import domain.competition.Game;
import junit.framework.TestCase;

public class GameTest extends TestCase {
    String leftCompetitor = null;
    String rightCompetitor = null;
    Game game = null;
    
    protected void setUp() throws Exception {
        super.setUp();
        game = new Game("LinDan", "LiChongWei");
        
        leftCompetitor = game.leftCompetiter();
        rightCompetitor = game.rightCompetiter();
    }

    public void testLeftGetPoint() {
        game.leftGetPoint();
        String competitor = game.service();
        assertTrue(competitor.equals(leftCompetitor));
        assertEquals(1,  game.leftScore());
    }

    public void testRightGetPoint() {
        game.rightGetPoint();
        String competitor = game.service();
        assertTrue(competitor.equals(rightCompetitor));
        assertEquals(1,  game.rightScore());
    }
    
    public void testLeftWinGameWhenFirstGet_21_ScoreAndLead_2_More() {
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.leftGetPoint();
        }
        assertFalse(game.isOver());
        assertEquals(20, game.leftScore());
        assertEquals(0, game.rightScore());
        
        game.leftGetPoint();
        assertTrue(game.isOver());
        assertEquals(21, game.leftScore());
        assertEquals(0, game.rightScore());
        
        String winner = game.service();
        assertTrue(winner.equals(leftCompetitor));
    }
    
    public void testRightWinGameWhenFirstGet_21_ScoreAndLead_2_More() {
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.rightGetPoint();
        }
        assertFalse(game.isOver());
        assertEquals(0, game.leftScore());
        assertEquals(20, game.rightScore());
        
        game.rightGetPoint();
        assertTrue(game.isOver());
        assertEquals(0, game.leftScore());
        assertEquals(21, game.rightScore());
        
        String winner = game.service();
        assertTrue(winner.equals(rightCompetitor));
    }
    
    public void testLeftWinGameWhenScoreSurpassed_21_AndLead_2() {
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.leftGetPoint();
        }
        for (int pointNum = 1; pointNum <= 21; pointNum++) {
            game.rightGetPoint();
        }
        
        assertFalse(game.isOver());
        assertEquals(20, game.leftScore());
        assertEquals(21, game.rightScore());
        
        game.leftGetPoint();
        game.leftGetPoint();
        game.leftGetPoint();
        
        assertTrue(game.isOver());
        assertEquals(23, game.leftScore());
        assertEquals(21, game.rightScore());
        
        String winner = game.service();
        assertTrue(winner.equals(leftCompetitor));
    }
    
    public void testLeftWinGameWhenFirstGet_30() {
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.leftGetPoint();
        }
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.rightGetPoint();
        }
        
        assertFalse(game.isOver());
        assertEquals(20, game.leftScore());
        assertEquals(20, game.rightScore());
        
        for (int pointNum = 20; pointNum < 29; pointNum++) {
            game.leftGetPoint();
            game.rightGetPoint();
        }
        
        assertFalse(game.isOver());
        assertEquals(29, game.leftScore());
        assertEquals(29, game.rightScore());
        
        game.leftGetPoint();
        assertTrue(game.isOver());
        assertEquals(30, game.leftScore());
        assertEquals(29, game.rightScore());
        
        String winner = game.service();
        assertTrue(winner.equals(leftCompetitor));
    }
}
