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
    private int lastLevel;
    private final Vector<LevelSwitchListener> levelSwitchListenerVector;
    private final int numLevels;
    private final Vector<GameEndedListener> gameEndedListenerVector;

    private SharedPreferences sharedPref;

    public GameStatus(Activity activity){
        startTime = new Date();
        gameOver = false;
        hasUserWon = false;
        level = 0;
        lastLevel = level;
        levelSwitchListenerVector = new Vector<>();
        gameEndedListenerVector = new Vector<>();

        sharedPref = activity.getSharedPreferences("cos.premy.mines.settings", Context.MODE_PRIVATE);
        this.numLevels = sharedPref.getInt("numLevels", 2);
    }

    public int getNumLevels(){
        return numLevels;
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

    public void incrementLevel(){
        lastLevel = level;
        level = (level + 1) % numLevels;
        for(LevelSwitchListener listener : levelSwitchListenerVector){
            listener.levelSwitched(this);
        }
    }

    public void decrementLevel(){
        lastLevel = level;
        level = (numLevels + level - 1) % numLevels;
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

    public int getLastLevel() {
        return lastLevel;
    }
}
