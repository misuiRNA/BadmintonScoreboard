package domain.competition;

import domain.info.Player;


public class Game {
    private GameCompetitor leftCompetitor;
    private GameCompetitor rightCompetitor;
    private GameCompetitor inService;
    
    public Game(Player leftPlayer, Player rightPlayer) {
        this.leftCompetitor = new GameCompetitor(leftPlayer);
        this.rightCompetitor = new GameCompetitor(rightPlayer);
        this.inService = this.leftCompetitor;    // TODO initial server by property
    }

    public GameCompetitor leftCompetitor() {
        return leftCompetitor;
    }

    public GameCompetitor rightCompetitor() {
        return rightCompetitor;
    }

    public Player currentServe() {
        return inService.getPlayer();
    }

    public boolean isOver() {
        GameCompetitor winner = inService; 
        GameCompetitor loser = (leftCompetitor == winner) ? rightCompetitor : leftCompetitor;
        
        int winScore = winner.getScore();
        int losScore = loser.getScore();
        boolean res = (winScore >= 21 && winScore - losScore >= 2) || winScore == 30;
        return res;
    }

    public void leftWinRound() {
        if (isOver()) {
            return;
        }
        inService = leftCompetitor;
        increaseServeScore();
    }
    
    public void rightWinRound() {
        if (isOver()) {
            return;
        }
        inService = rightCompetitor;
        increaseServeScore();
    }
    
    private void increaseServeScore() {
        inService.increaseScore();
    }
}
