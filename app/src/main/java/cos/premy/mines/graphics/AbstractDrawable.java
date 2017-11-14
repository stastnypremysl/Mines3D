package cos.premy.mines.graphics;

import cos.premy.mines.GameStatus;

/**
 * Created by premy on 14.11.2017.
 */

public abstract class AbstractDrawable implements IDrawable {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected final GameStatus gameStatus;

    public AbstractDrawable(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }

    @Override
    public void setPosition(int x, int width, int y, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected boolean verifyTap(int x, int y){
        return this.x <= x && this.width + this.x >= x && this.y <= y && this.height + this.y >= y;
    }

    @Override
    public void sendTap(int x, int y){
        if(verifyTap(x, y)){
            sendVerifiedTap(x, y);
        }
    }

    @Override
    public void sendLongTap(int x, int y){
        if(verifyTap(x, y)){
            sendVerifiedLongTap(x, y);
        }
    }

    @Override
    public void sendDoubleTap(int x, int y){
        if(verifyTap(x, y)){
            sendVerifiedDoubleTap(x, y);
        }
    }

    protected abstract void sendVerifiedTap(int x, int y);
    protected abstract void sendVerifiedLongTap(int x, int y);
    protected abstract void sendVerifiedDoubleTap(int x, int y);
}
