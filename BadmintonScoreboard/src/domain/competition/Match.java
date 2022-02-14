package domain.competition;

import java.util.ArrayList;
import java.util.List;

public class Match {
    
    private final String leftCompetitor;
    private final String rightCompetitor;
    private List<Game> gameList;

    public Match(String leftCompetitor, String rightCompetitor) {
        this.leftCompetitor = leftCompetitor;
        this.rightCompetitor = rightCompetitor;
        gameList = new ArrayList<Game>();
    }

    public String leftCompetiter() {
        return leftCompetitor;
    }

    public String rightCompetiter() {
        return rightCompetitor;
    }

    public Game currentGame() {
        if (gameList.size() <= 0) {
            startNewGame();
        }
        return gameList.get(gameList.size() - 1);
    }
    
    public List<Game> gameList() {
        return gameList;
    }
    
    public void startNewGame() {
        gameList.add(new Game(leftCompetitor, rightCompetitor));
    }

    public boolean isGameOver() {
        int gameNum = gameList.size();
        if (gameNum == 2) {
             return (currentGame().gameOver() && gameList.get(0).service() == currentGame().service());
        } else if (gameNum == 3) {
            return currentGame().gameOver();
        } else {
            return false;
        }
    }
    
    public String winner() {
        String winner = null;
        if (isGameOver()) {
            winner = gameList.get(gameList.size() - 1).service();
        }
        return winner;
    }
    
}
