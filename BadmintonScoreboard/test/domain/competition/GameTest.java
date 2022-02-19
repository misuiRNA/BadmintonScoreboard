package domain.competition;


import domain.competition.Game;
import domain.info.Player;
import junit.framework.TestCase;

public class GameTest extends TestCase {
    Player leftPlayer = new Player("LinDan");
    Player rightPlayer = new Player("LiChongWei");
    Game game = null;
    
    protected void setUp() throws Exception {
        super.setUp();
        game = new Game(leftPlayer, rightPlayer);
    }

    public void testLeftInServe() {
        game.leftWinRound();
        Player player = game.currentServe();
        assertEquals(leftPlayer, player);
        assertEquals(1,  game.leftCompetitor().getScore());
    }

    public void testRightInServe() {
        game.rightWinRound();
        assertEquals(rightPlayer, game.currentServe());
        assertEquals(1,  game.rightCompetitor().getScore());
    }
    
    public void testLeftWinGameWhenFirstGet_21_and_Lead_2_More() {
        leftWinRounds(20);
        
        assertFalse(game.isOver());
        assertEquals(20, game.leftCompetitor().getScore());
        assertEquals(0, game.rightCompetitor().getScore());
        
        game.leftWinRound();
        assertTrue(game.isOver());
        assertEquals(21, game.leftCompetitor().getScore());
        assertEquals(0, game.rightCompetitor().getScore());
        
        Player winner = game.currentServe();
        assertEquals(leftPlayer, winner);
    }

    public void testRightWinGameWhenFirstGet_21_and_Lead_2_More() {
        rightWinRounds(20);
        assertFalse(game.isOver());
        assertEquals(0, game.leftCompetitor().getScore());
        assertEquals(20, game.rightCompetitor().getScore());
        
        game.rightWinRound();
        assertTrue(game.isOver());
        assertEquals(0, game.leftCompetitor().getScore());
        assertEquals(21, game.rightCompetitor().getScore());
        
        Player winner = game.currentServe();
        assertEquals(rightPlayer, winner);
    }
    
    public void testLeftWinGameWhenScoreSurpassed_21_and_Lead_2() {
        leftWinRounds(20);
        rightWinRounds(21);
        assertFalse(game.isOver());
        assertEquals(20, game.leftCompetitor().getScore());
        assertEquals(21, game.rightCompetitor().getScore());
        
        leftWinRounds(3);
        assertTrue(game.isOver());
        assertEquals(23, game.leftCompetitor().getScore());
        assertEquals(21, game.rightCompetitor().getScore());
        
        Player winner = game.currentServe();
        assertEquals(leftPlayer, winner);
    }
    
    public void testLeftWinGameWhenFirstGet_30() {
        leftWinRounds(20);
        rightWinRounds(20);
        assertFalse(game.isOver());
        assertEquals(20, game.leftCompetitor().getScore());
        assertEquals(20, game.rightCompetitor().getScore());

        for (int pointNum = 20; pointNum < 29; pointNum++) {
            game.leftWinRound();
            game.rightWinRound();
        }
        
        assertFalse(game.isOver());
        assertEquals(29, game.leftCompetitor().getScore());
        assertEquals(29, game.rightCompetitor().getScore());
        
        game.leftWinRound();
        assertTrue(game.isOver());
        assertEquals(30, game.leftCompetitor().getScore());
        assertEquals(29, game.rightCompetitor().getScore());
        
        Player winner = game.currentServe();
        assertEquals(leftPlayer, winner);
    }

    private void leftWinRounds(int winTime) {
        for (int loop = 1; loop <= winTime; loop++) {
            game.leftWinRound();
        }
    }

    private void rightWinRounds(int winTime) {
        for (int loop = 1; loop <= winTime; loop++) {
            game.rightWinRound();
        }
    }
    
}
