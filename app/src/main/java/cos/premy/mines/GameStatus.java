package cos.premy.mines;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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

    private boolean useNumbers;
    private boolean hardMode;
    private boolean colored;
    private boolean flood;

    private SharedPreferences sharedPref;

    public GameStatus(Activity activity){
        startTime = new Date();
        gameOver = false;
        hasUserWon = false;
        level = 0;
        levelSwitchListenerVector = new Vector<>();
        gameEndedListenerVector = new Vector<>();

        sharedPref = activity.getSharedPreferences("cos.premy.mines.settings", Context.MODE_PRIVATE);
    }

    public boolean getHardcore(){
        return sharedPref.getBoolean("hardcore", false);
    }

    public boolean getNumberType(){
        return sharedPref.getBoolean("numberType", false);
    }

    public boolean getColored(){
        return sharedPref.getBoolean("colored", false);
    }

    public boolean getFlood() { return sharedPref.getBoolean("flood", false); }

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
