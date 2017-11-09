package cos.premy.mines;

import java.util.Date;
import java.util.Vector;

/**
 * Created by premy on 07.11.2017.
 */

public class GameStatus {
    private final Date startTime;
    private Date endTime = null;
    private boolean gameOver;
    private boolean hasUserWon;
    private int level;
    private final Vector<LevelSwitchListener> levelSwitchListenerVector;
    private final Vector<GameEndedListener> gameEndedListenerVector;

    public GameStatus(){
        startTime = new Date();
        gameOver = false;
        hasUserWon = false;
        level = 0;
        levelSwitchListenerVector = new Vector<>();
        gameEndedListenerVector = new Vector<>();
    }

    public void endGame(boolean hasUserWon){
        gameOver = true;
        this.hasUserWon = hasUserWon;
        endTime = new Date();
        for(GameEndedListener listener : gameEndedListenerVector){
            listener.gameEnded(this);
        }
    }

    public void switchLevel(){
        level = (level + 1) % 2;
        for(LevelSwitchListener listener : levelSwitchListenerVector){
            listener.levelSwitched(this);
        }
    }

    public int getLevel(){
        return level;
    }

    public boolean stillPlaying() {
        return !gameOver;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void cleanListeners(){
        levelSwitchListenerVector.clear();
    }

    public boolean hasUserWon(){
        return hasUserWon;
    }

    public void addLevelSwitchListener(LevelSwitchListener listener){
        levelSwitchListenerVector.add(listener);
    }

    public void addGameEndedListener(GameEndedListener listener){
        gameEndedListenerVector.add(listener);
    }
}
