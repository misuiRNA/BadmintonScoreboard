package domain.competition;


public class Game {
    static final boolean SERVICE_LEFT = false;
    static final boolean SERVICE_RIGHT = true;
    
    private boolean gameOver;
    private boolean service;
    
    private int leftScore;
    private final String leftCompetitor;
    
    private int rightScore;
    private final String rightCompetitor;

    public Game(String leftCompetitor, String rightCompetitor) {
        this.gameOver = false;
        this.service = SERVICE_LEFT;    // TODO initial server by property
        this.leftScore = 0;
        this.leftCompetitor = leftCompetitor;
        this.rightScore = 0;
        this.rightCompetitor = rightCompetitor;
    }

    public boolean gameOver() {
        return gameOver;
    }
    
    public int rightScore() {
        return rightScore;
    }
    
    public int leftScore() {
        return leftScore;
    }
    
    public String service() {
        if (service == SERVICE_LEFT) {
            return leftCompetitor;
        } else {
            return rightCompetitor;
        }
    }
    
    public void leftGetPoint() {
        if (gameOver) {
            return;
        }
        service = SERVICE_LEFT;
        leftScore++;
        if ((leftScore >= 21 && leftScore - rightScore >= 2) || leftScore == 30){
            gameOver = true;
        }
    }
    
    public void rightGetPoint() {
        if (gameOver) {
            return;
        }
        rightScore++;
        service = SERVICE_RIGHT;
        if ((rightScore >= 21 && rightScore - leftScore >= 2) || rightScore == 30){
            gameOver = true;
        }
    }

}
