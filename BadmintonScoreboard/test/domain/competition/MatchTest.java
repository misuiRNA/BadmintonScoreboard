package domain.competition;

import junit.framework.TestCase;
import domain.info.Player;

public class MatchTest extends TestCase{
    Player leftPlayer = new Player("LinDan");
    Player rightPlayer = new Player("LiChongWei");
    Match match = null;
    
    public void setUp() throws Exception {
        super.setUp();
        match = new Match(leftPlayer, rightPlayer);
    }

    public void testNoWinner_When_MatchStart() {
        assertEquals(null, match.winner());
    }
    
    public void testNoWinner_When_FirstGameOver() {
        winCurrentGame(leftPlayer);
        assertEquals(null, match.winner());
    }
    
    public void testNoWinner_When_CompetitorsWinGameEach() {
        winCurrentGame(leftPlayer);
        winNewGame(rightPlayer);
        
        assertFalse(match.isOver());
        assertEquals(null, match.winner());
    }

    public void testLeftWinMatch_When_LeftWinTop2Game() {
        winCurrentGame(leftPlayer);
        winNewGame(leftPlayer);
        
        assertTrue(match.isOver());
        assertEquals(leftPlayer, match.winner());
    }
    
    public void testLeftWinMatch_When_LeftWin_1st_and_3rd_Game() {
        winCurrentGame(leftPlayer);
        winNewGame(rightPlayer);
        winNewGame(leftPlayer);
        
        assertTrue(match.isOver());
        assertEquals(leftPlayer, match.winner());
    }

    public void testLeftWinMatch_When_LeftWin_2nd_and_3rd_Game() {
        winCurrentGame(rightPlayer);
        winNewGame(leftPlayer);
        winNewGame(leftPlayer);
        assertEquals(leftPlayer, match.winner());
    }
    
    public void testStartNewGameFailed_When_MatchIsOver() {
        winCurrentGame(leftPlayer);
        assertFalse(match.isOver());
        winNewGame(leftPlayer);
        assertTrue(match.isOver());
        
        boolean state = match.createNewGame();
        assertFalse(state);
    }
    
    public void testStartNewGameSuccess_When_MatchIsNotOver() {
        winCurrentGame(leftPlayer);
        assertFalse(match.isOver());
        winNewGame(rightPlayer);
        assertFalse(match.isOver());
        
        boolean state = match.createNewGame();
        assertTrue(state);
    }

    private void winNewGame(Player player) {
        boolean state = match.createNewGame();
        assertTrue(state);
        winCurrentGame(player);
    }

    private void winCurrentGame(Player player) {
        Game newGame = match.currentGame();
        for (int loop = 0; loop < 21 && (!newGame.isOver()); ++loop) {
            if (player == leftPlayer) {
                newGame.leftWinRound();
            } else if (player == rightPlayer) {
                newGame.rightWinRound();
            }
        }
    }

}
