package cos.premy.mines.graphics;


import android.graphics.Canvas;
import android.graphics.Paint;

import cos.premy.mines.GameStatus;
import cos.premy.mines.data.MinesContainer;

/**
 * Created by premy on 07.11.2017.
 */

public class Grid implements IDrawable {

    private final MinesContainer container;
    private MineField[][][] mineFields;
    private final GameStatus gameStatus;

    private int x;
    private int y;
    private int width;
    private int height;

    private final int N;
    private final int M;

    private Paint paintLine;

    public Grid(MinesContainer container, GameStatus gameStatus){
        this.container = container;
        this.gameStatus = gameStatus;

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setARGB(255,255,255,255);
        paintLine.setStrokeWidth(3F);

        N = container.getHeight();
        M = container.getWidth();

    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i != N + 1; i++) {
            canvas.drawLine(x, y + (i * height) / N, x + width, y + (i * height) / N, paintLine);
        }

        for(int i = 0; i != M + 1; i++){
            canvas.drawLine(x  + (i * width) / M,y , x + (i * height) / M , y +height, paintLine);
        }

        int level = gameStatus.getLevel();
        for(int i = 0; i != N; i++){
            for(int ii = 0; ii != M; ii++){
                mineFields[level][i][ii].draw(canvas);
            }
        }

    }

    @Override
    public void setPosition(int x, int width, int y, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        mineFields = new MineField[2][][];
        for(int i = 0; i != 2; i++){
            mineFields[i] = new MineField[N][];
            for(int ii = 0; ii != N; ii++){
                mineFields[i][ii] = new MineField[M];
                for(int iii = 0; iii != M; iii++){
                    mineFields[i][ii][iii] = new MineField(container.getMine(i, ii, iii), gameStatus, i);
                    mineFields[i][ii][iii].setPosition(x + (ii * height) / N, height / N, y + (iii * width) / M, width / M);
                }
            }
        }

        for(int i = 0; i != 2; i++){
            for(int ii = 0; ii != N; ii++){
                for(int iii = 0; iii != M; iii++){
                    mineFields[i][ii][iii].setTwin(mineFields[(i+1)%2][ii][iii]);
                }
            }
        }
    }

    @Override
    public void sendTap(int x, int y) {
        if(this.x <= x && this.width + this.x >= x && this.y <= y && this.height + this.y >= y) {
            int level = gameStatus.getLevel();
            for (int i = 0; i != N; i++) {
                for (int ii = 0; ii != M; ii++) {
                    mineFields[level][i][ii].sendTap(x, y);
                }
            }
        }
    }

    @Override
    public void sendLongTap(int x, int y) {
        if(this.x <= x && this.width + this.x >= x && this.y <= y && this.height + this.y >= y) {
            int level = gameStatus.getLevel();
            for (int i = 0; i != N; i++) {
                for (int ii = 0; ii != M; ii++) {
                    mineFields[level][i][ii].sendLongTap(x, y);
                }
            }
        }
    }

    @Override
    public void sendDoubleTap(int x, int y) {
        if(this.x <= x && this.width + this.x >= x && this.y <= y && this.height + this.y >= y) {
            int level = gameStatus.getLevel();
            for (int i = 0; i != N; i++) {
                for (int ii = 0; ii != M; ii++) {
                    mineFields[level][i][ii].sendDoubleTap(x, y);
                }
            }
        }
    }
}
