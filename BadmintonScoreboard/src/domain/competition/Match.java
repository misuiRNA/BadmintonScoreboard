package domain.competition;

import java.util.ArrayList;
import java.util.List;

import domain.info.Player;

public class Match {
    private final Player leftPlayer;
    private final Player rightPlayer;
    private List<Game> gameList;

    public Match(Player leftPlayer, Player rightPlayer) {
        this.leftPlayer = leftPlayer;
        this.rightPlayer = rightPlayer;
        gameList = new ArrayList<Game>();
        gameList.add(new Game(leftPlayer, rightPlayer));
    }

    public Player leftPlayer() {
        return leftPlayer;
    }

    public Player rightPlayer() {
        return rightPlayer;
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
            gameList.add(new Game(leftPlayer, rightPlayer));
            createState = true;
        }
        return createState;
    }
    
    public Player winner() {
        Player winner = null;
        if (isOver()) {
            winner = currentGame().currentServe();
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
