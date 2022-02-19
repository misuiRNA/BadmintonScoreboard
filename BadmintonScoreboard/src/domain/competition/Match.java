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
        gameList.add(new Game(leftCompetitor, rightCompetitor));
    }

    public String leftCompetiter() {
        return leftCompetitor;
    }

    public String rightCompetiter() {
        return rightCompetitor;
    }

    public Game currentGame() {
        return gameList.get(gameList.size() - 1);
    }
    
    public List<Game> gameList() {
        return gameList;
    }

    public boolean createNewGame() {
        boolean createState = false;
        if (isCurrentGameOver() && !isOver()) {
            gameList.add(new Game(leftCompetitor, rightCompetitor));
            createState = true;
        }
        return createState;
    }
    
    public String winner() {
        String winner = null;
        if (isOver()) {
            winner = gameList.get(gameList.size() - 1).currentServe();
        }
        return winner;
    }

    public boolean isOver() {
        int gameNum = gameList.size();
        if (2 == gameNum) {
             return (isCurrentGameOver() && gameList.get(0).currentServe() == currentGame().currentServe());
        } else if (3 == gameNum) {
            return isCurrentGameOver();
        } else {
            return false;
        }
    }

    private boolean isCurrentGameOver() {
        return currentGame().isOver();
    }
    
}
