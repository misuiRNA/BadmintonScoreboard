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

    public boolean shouldStartNewGame() {
        if (gameList.size() <= 0) {
            return true;
        }
        return currentGame().isOver();
    }
    
    public Game startNewGame() {
        Game game = null;
        if (shouldStartNewGame() && !isOver()) {
            game = new Game(leftCompetitor, rightCompetitor);
            gameList.add(game);
        }
        return game;
    }

    public boolean isOver() {
        int gameNum = gameList.size();
        if (2 == gameNum) {
             return (shouldStartNewGame() && gameList.get(0).service() == currentGame().service());
        } else if (3 == gameNum) {
            return shouldStartNewGame();
        } else {
            return false;
        }
    }

    public void leftGetPoint() {
        if (isOver()) {
            return;
        }
        currentGame().leftGetPoint();
    }
    
    public void rightGetPoint() {
        if (isOver()) {
            return;
        }
        currentGame().rightGetPoint();
    }

    public String winner() {
        String winner = null;
        if (isOver()) {
            winner = gameList.get(gameList.size() - 1).service();
        }
        return winner;
    }
    
}
