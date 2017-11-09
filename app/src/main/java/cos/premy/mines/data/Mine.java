package cos.premy.mines.data;

import java.util.Vector;

import cos.premy.mines.GameStatus;
import cos.premy.mines.MyHappyException;

/**
 * Created by premy on 07.11.2017.
 */

public class Mine {
    private boolean isReal;
    private boolean factorized;
    private int neighbors;
    private MineStatus status;
    private Vector<MineStatusChangedListener> mineStatusChangedListenerVector;

    public Mine() {
        this(false);
    }

    public Mine(boolean isReal){
        this.isReal = isReal;
        status = MineStatus.UNBLOCKED;
        neighbors = 0;
        mineStatusChangedListenerVector = new Vector<>();
    }

    public boolean getIsReal(){
        return isReal;
    }

    public void setIsReal(boolean isReal) throws MyHappyException{
        if(factorized){
            throw new MyHappyException(MyHappyException.EX_TYPES.MINE_IS_FACTORIZED);
        }
        this.isReal = isReal;
    }

    public void setFactorized(int neighbors){
        factorized = true;
        this.neighbors = neighbors;
    }

    public int getNeighbors(){
        return neighbors;
    }

    public MineStatus getStatus(){
        return status;
    }

    public void setStatus(MineStatus status) throws MyHappyException{
        if(factorized){
            throw new MyHappyException(MyHappyException.EX_TYPES.MINE_IS_FACTORIZED);
        }
        this.status = status;
    }

    public void setGameStatus(MineStatus status, GameStatus activity) {
        this.status = status;
        switch (status) {
            case UNBLOCKED:
                for (MineStatusChangedListener listener : mineStatusChangedListenerVector) {
                    listener.onUnblock(activity, this);
                }
                break;
            case OPENED:
                for (MineStatusChangedListener listener : mineStatusChangedListenerVector) {
                    listener.onOpen(activity, this);
                }
                break;
            case BLOCKED:
                for (MineStatusChangedListener listener : mineStatusChangedListenerVector) {
                    listener.onBlock(activity, this);
                }
                break;
        }
    }


    public void addMineStatusChangedListener(MineStatusChangedListener listener){
        mineStatusChangedListenerVector.add(listener);
    }

}
