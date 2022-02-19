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

    public void testLeftInServe() {
        game.serveLeft();
        String competitor = game.currentServe();
        assertTrue(competitor.equals(leftCompetitor));
        assertEquals(1,  game.leftScore());
    }

    public void testRightInServe() {
        game.serveRight();
        String competitor = game.currentServe();
        assertTrue(competitor.equals(rightCompetitor));
        assertEquals(1,  game.rightScore());
    }
    
    public void testLeftWinGameWhenFirstGet_21_and_Lead_2_More() {
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.serveLeft();
        }
        assertFalse(game.isOver());
        assertEquals(20, game.leftScore());
        assertEquals(0, game.rightScore());
        
        game.serveLeft();
        assertTrue(game.isOver());
        assertEquals(21, game.leftScore());
        assertEquals(0, game.rightScore());
        
        String winner = game.currentServe();
        assertTrue(winner.equals(leftCompetitor));
    }
    
    public void testRightWinGameWhenFirstGet_21_and_Lead_2_More() {
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.serveRight();
        }
        assertFalse(game.isOver());
        assertEquals(0, game.leftScore());
        assertEquals(20, game.rightScore());
        
        game.serveRight();
        assertTrue(game.isOver());
        assertEquals(0, game.leftScore());
        assertEquals(21, game.rightScore());
        
        String winner = game.currentServe();
        assertTrue(winner.equals(rightCompetitor));
    }
    
    public void testLeftWinGameWhenScoreSurpassed_21_and_Lead_2() {
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.serveLeft();
        }
        for (int pointNum = 1; pointNum <= 21; pointNum++) {
            game.serveRight();
        }
        
        assertFalse(game.isOver());
        assertEquals(20, game.leftScore());
        assertEquals(21, game.rightScore());
        
        game.serveLeft();
        game.serveLeft();
        game.serveLeft();
        
        assertTrue(game.isOver());
        assertEquals(23, game.leftScore());
        assertEquals(21, game.rightScore());
        
        String winner = game.currentServe();
        assertTrue(winner.equals(leftCompetitor));
    }
    
    public void testLeftWinGameWhenFirstGet_30() {
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.serveLeft();
        }
        for (int pointNum = 1; pointNum <= 20; pointNum++) {
            game.serveRight();
        }
        
        assertFalse(game.isOver());
        assertEquals(20, game.leftScore());
        assertEquals(20, game.rightScore());
        
        for (int pointNum = 20; pointNum < 29; pointNum++) {
            game.serveLeft();
            game.serveRight();
        }
        
        assertFalse(game.isOver());
        assertEquals(29, game.leftScore());
        assertEquals(29, game.rightScore());
        
        game.serveLeft();
        assertTrue(game.isOver());
        assertEquals(30, game.leftScore());
        assertEquals(29, game.rightScore());
        
        String winner = game.currentServe();
        assertTrue(winner.equals(leftCompetitor));
    }
}
