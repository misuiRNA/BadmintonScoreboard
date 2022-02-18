package domain.competition;

import junit.framework.TestCase;

public class MatchTest extends TestCase{
    String leftCompetitor = null;
    String rightCompetitor = null;
    Match match = null;
    
    public void setUp() throws Exception {
        super.setUp();
        match = new Match("LinDan", "LiChongWei");
        
        leftCompetitor = match.leftCompetiter();
        rightCompetitor = match.rightCompetiter();
    }

    public void testNoWinner_When_MatchStart() {
        assertEquals(null, match.winner());
    }
    
    public void testNoWinner_When_FirstGameOver() {
        winGame(leftCompetitor);
        assertEquals(null, match.winner());
    }
    
    public void testNoWinner_When_CompetitorsWinGameEach() {
        winGame(leftCompetitor);
        winGame(rightCompetitor);
        assertEquals(null, match.winner());
    }

    public void testLeftWinMatch_When_LeftWinTop2Game() {
        winGame(leftCompetitor);
        winGame(leftCompetitor);
        assertEquals(leftCompetitor, match.winner());
    }
    
    public void testLeftWinMatch_When_LeftWin_1st_and_3rd_Game() {
        winGame(leftCompetitor);
        winGame(rightCompetitor);
        winGame(leftCompetitor);
        assertEquals(leftCompetitor, match.winner());
    }

    public void testLeftWinMatch_When_LeftWin_2nd_and_3rd_Game() {
        winGame(rightCompetitor);
        winGame(leftCompetitor);
        winGame(leftCompetitor);
        assertEquals(leftCompetitor, match.winner());
    }
    
    public void testStartNewGame_GetNullOne_When_MatchIsOver() {
        winGame(leftCompetitor);
        assertFalse(match.isOver());
        winGame(leftCompetitor);
        assertTrue(match.isOver());
        
        Game newGame = match.startNewGame();
        assertNull(newGame);
    }
    
    public void testStartNewGame_GetValidOne_When_MatchIsNotOver() {
        winGame(leftCompetitor);
        assertFalse(match.isOver());
        winGame(rightCompetitor);
        assertFalse(match.isOver());
        
        Game newGame = match.startNewGame();
        assertNotNull(newGame);
    }
    
    private void winGame(String competitor) {
        Game newGame = match.startNewGame();
        assertNotNull(newGame);
        
        for (int loop = 0; loop < 21 && (!newGame.isOver()); ++loop) {
            if (competitor == leftCompetitor) {
                newGame.leftGetPoint();
            } else if (competitor == rightCompetitor) {
                newGame.rightGetPoint();
            }
        }
    }

}
