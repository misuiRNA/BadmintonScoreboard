package domain.competition;

import domain.info.Player;

public class GameCompetitor {
    private final Player player;
    private int score;
    
    public GameCompetitor(Player player) {
        this.player = player;
        score = 0;
    }

    public Player getPlayer() {
        return player;
    }
    
    public int getScore() {
        return score;
    }
    
    public void increaseScore() {
        ++score;
    }
    
}
