package domain.competition;


public class Game {
    static final boolean SERVICE_LEFT = false;
    static final boolean SERVICE_RIGHT = true;
    
    private boolean inService;
    
    private int leftScore;
    private final String leftCompetitor;
    
    private int rightScore;
    private final String rightCompetitor;

    public Game(String leftCompetitor, String rightCompetitor) {
        this.inService = SERVICE_LEFT;    // TODO initial server by property
        this.leftScore = 0;
        this.leftCompetitor = leftCompetitor;
        this.rightScore = 0;
        this.rightCompetitor = rightCompetitor;
    }

    public String leftCompetiter() {
        return leftCompetitor;
    }

    public String rightCompetiter() {
        return rightCompetitor;
    }

    public int rightScore() {
        return rightScore;
    }
    
    public int leftScore() {
        return leftScore;
    }
    
    public String currentServe() {
        if (inService == SERVICE_LEFT) {
            return leftCompetitor;
        } else {
            return rightCompetitor;
        }
    }

    public boolean isOver() {
        boolean res = false;
        if (inService == SERVICE_LEFT) {
            res = (leftScore >= 21 && leftScore - rightScore >= 2) || leftScore == 30;
        } else {
            res = (rightScore >= 21 && rightScore - leftScore >= 2) || rightScore == 30;
        }
        return res;
    }

    public void serveLeft() {
        if (isOver()) {
            return;
        }
        inService = SERVICE_LEFT;
        leftScore++;
    }
    
    public void serveRight() {
        if (isOver()) {
            return;
        }
        rightScore++;
        inService = SERVICE_RIGHT;
    }

}
