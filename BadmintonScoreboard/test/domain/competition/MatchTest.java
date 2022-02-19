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
        winCurrentGame(leftCompetitor);
        assertEquals(null, match.winner());
    }
    
    public void testNoWinner_When_CompetitorsWinGameEach() {
        winCurrentGame(leftCompetitor);
        winNewGame(rightCompetitor);
        
        assertFalse(match.isOver());
        assertEquals(null, match.winner());
    }

    public void testLeftWinMatch_When_LeftWinTop2Game() {
        winCurrentGame(leftCompetitor);
        winNewGame(leftCompetitor);
        
        assertTrue(match.isOver());
        assertEquals(leftCompetitor, match.winner());
    }
    
    public void testLeftWinMatch_When_LeftWin_1st_and_3rd_Game() {
        winCurrentGame(leftCompetitor);
        winNewGame(rightCompetitor);
        winNewGame(leftCompetitor);
        
        assertTrue(match.isOver());
        assertEquals(leftCompetitor, match.winner());
    }

    public void testLeftWinMatch_When_LeftWin_2nd_and_3rd_Game() {
        winCurrentGame(rightCompetitor);
        winNewGame(leftCompetitor);
        winNewGame(leftCompetitor);
        assertEquals(leftCompetitor, match.winner());
    }
    
    public void testStartNewGameFailed_When_MatchIsOver() {
        winCurrentGame(leftCompetitor);
        assertFalse(match.isOver());
        winNewGame(leftCompetitor);
        assertTrue(match.isOver());
        
        boolean state = match.createNewGame();
        assertFalse(state);
    }
    
    public void testStartNewGameSuccess_When_MatchIsNotOver() {
        winCurrentGame(leftCompetitor);
        assertFalse(match.isOver());
        winNewGame(rightCompetitor);
        assertFalse(match.isOver());
        
        boolean state = match.createNewGame();
        assertTrue(state);
    }
    
    private void winNewGame(String competitor) {
        boolean state = match.createNewGame();
        assertTrue(state);
        winCurrentGame(competitor);
    }
    
    private void winCurrentGame(String competitor) {
        Game newGame = match.currentGame();
        for (int loop = 0; loop < 21 && (!newGame.isOver()); ++loop) {
            if (competitor == leftCompetitor) {
                newGame.serveLeft();
            } else if (competitor == rightCompetitor) {
                newGame.serveRight();
            }
        }
    }

}
