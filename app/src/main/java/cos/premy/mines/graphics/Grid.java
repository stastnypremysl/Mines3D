package cos.premy.mines.graphics;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;

import cos.premy.mines.GameStatus;
import cos.premy.mines.data.MinesContainer;
import cos.premy.mines.data.MineCoord;
import cos.premy.mines.generator.RandomMinesGenerator;

/**
 * Created by premy on 07.11.2017.
 */

public class Grid extends AbstractDrawable {
    private final MinesContainer container;
    private final MineField[][][] mineFields;

    private final int N;
    private final int M;

    private final Paint paintLine;

    public Grid(MinesContainer container, GameStatus gameStatus){
        super(gameStatus);
        this.container = container;

        N = container.getHeight();
        M = container.getWidth();
        mineFields = new MineField[gameStatus.getNumLevels()][N][M];

        gameStatus.addLevelSwitchListener(status -> {
            for(int i = 0; i != mineFields.length; i++){;
                for(int ii = 0; ii != N; ii++){
                    for(int iii = 0; iii != M; iii++){
                        MineField prevLevelMine = mineFields[gameStatus.getLastLevel()][ii][iii];
                        mineFields[i][ii][iii].setPreviousMineField(prevLevelMine);
                    }
                }
            }
        });

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setARGB(255,255,255,255);
        paintLine.setStrokeWidth(4F);

        for(int i = 0; i != mineFields.length; i++){
            for(int ii = 0; ii != N; ii++){
                for(int iii = 0; iii != M; iii++){
                    mineFields[i][ii][iii] = new MineField(this, container.getMine(i, ii, iii), gameStatus, i);
                    mineFields[i][ii][iii].setPosition(x + (iii * width) / M, width / M, y + (ii * height) / N, height / N);
                }
            }
        }

    }

    public void autoFlood(Vector<MineCoord> floodTargets) {
        if (gameStatus.getFlood()) {
            for (MineCoord mineCoord: floodTargets) {
                MineField target = mineFields[mineCoord.z][mineCoord.y][mineCoord.x];
                target.sendDoubleTap(target.x, target.y);
            }
        }
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
        super.setPosition(x, width, y, height);
        for(int i = 0; i != mineFields.length; i++){
            for(int ii = 0; ii != N; ii++){
                for(int iii = 0; iii != M; iii++){
                    mineFields[i][ii][iii].setPosition(x + (iii * width) / M, width / M, y + (ii * height) / N, height / N);
                }
            }
        }
    }

    @Override
    public void sendVerifiedTap(int x, int y){
        int level = gameStatus.getLevel();
        for (int i = 0; i != N; i++) {
            for (int ii = 0; ii != M; ii++) {
                mineFields[level][i][ii].sendTap(x, y);
            }
        }
    }

    @Override
    public void sendVerifiedLongTap(int x, int y) {
            int level = gameStatus.getLevel();
            for (int i = 0; i != N; i++) {
                for (int ii = 0; ii != M; ii++) {
                    mineFields[level][i][ii].sendLongTap(x, y);
                }
            }

    }

    @Override
    public void sendVerifiedDoubleTap(int x, int y) {
        if(!container.getFactorized()) {
            int mineX = Math.min(M, (x - this.x) * M / width);
            int mineY = Math.min(N, (y - this.y) * N / height);
            new RandomMinesGenerator().populateNewProblem(container, gameStatus.getLevel(), mineY, mineX);
        }
        int level = gameStatus.getLevel();
        for (int i = 0; i != N; i++) {
            for (int ii = 0; ii != M; ii++) {
                mineFields[level][i][ii].sendDoubleTap(x, y);
            }
        }
    }
}
