package cos.premy.mines.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cos.premy.mines.GameStatus;
import cos.premy.mines.LevelSwitchListener;
import cos.premy.mines.data.Mine;
import cos.premy.mines.data.MineStatus;
import cos.premy.mines.graphics.animations.Line;
import cos.premy.mines.graphics.animations.LineAnimation;
import cos.premy.mines.graphics.animations.LinearLineAnimation;
import cos.premy.mines.graphics.animations.Point;

/**
 * Created by premy on 07.11.2017.
 */

public class MineField extends AbstractDrawable {
    private final Grid grid;
    private final Mine data;
    private MineField previousMineField;

    private Line[] crossLines;
    private Line[] minesLines;
    private LineAnimation[] crossLinesAnimation;
    private LineAnimation[] minesLinesAnimation;

    private Point leftTopCorner = null;
    private Point rightTopCorner = null;
    private Point leftBottomCorner = null;
    private Point rightBottomCorner = null;

    private final boolean colored;
    private final boolean numberType;

    private static final int ANIMATION_DURATION = 100;

    private static final Paint WHITE;
    private static final Paint RED;
    private static final Paint BLUE;
    private static final Paint GREEN;
    private static final Paint CYAN;
    private static final Paint MAGENTA;
    private static final Paint YELLOW;


    static {
        WHITE = new Paint();
        WHITE.setAntiAlias(true);
        WHITE.setARGB(255,255,255,255);
        WHITE.setStrokeWidth(3F);

        RED = new Paint();
        RED.setAntiAlias(true);
        RED.setColor(Color.RED);
        RED.setStrokeWidth(3F);

        BLUE = new Paint();
        BLUE.setAntiAlias(true);
        BLUE.setColor(Color.BLUE);
        BLUE.setStrokeWidth(3F);

        GREEN = new Paint();
        GREEN.setAntiAlias(true);
        GREEN.setColor(Color.GREEN);
        GREEN.setStrokeWidth(3F);

        CYAN = new Paint();
        CYAN.setAntiAlias(true);
        CYAN.setColor(Color.CYAN);
        CYAN.setStrokeWidth(3F);

        MAGENTA = new Paint();
        MAGENTA.setAntiAlias(true);
        MAGENTA.setColor(Color.MAGENTA);
        MAGENTA.setStrokeWidth(3F);

        YELLOW = new Paint();
        YELLOW.setAntiAlias(true);
        YELLOW.setColor(Color.YELLOW);
        YELLOW.setStrokeWidth(3F);

    }

    public MineField(Grid grid, Mine data, GameStatus status, final int level){
        super(status);
        this.grid = grid;
        this.data = data;
        colored = status.getColored();
        numberType = status.getNumberType();

        status.addLevelSwitchListener(new LevelSwitchListener() {
            @Override
            public void levelSwitched(GameStatus status) {
                if(status.getLevel() == level){
                    downloadAnimationStartsFromPreviousMineField();
                    refreshAnimations();
                }
            }
        });
    }

    private void initLines(){
        crossLines = new Line[2];
        minesLines = new Line[6];
        refreshLines();
    }

    private void refreshLines(){
        switch (data.getStatus()){
            case BLOCKED:
                crossLines[0] = new Line(leftTopCorner, rightBottomCorner);
                crossLines[1] = new Line(rightTopCorner, leftBottomCorner);

                for(int i = 0; i != 5; i++){
                    int xL = x + (width*(i+1))/(6);
                    Point top = new Point(xL, y);
                    Point bottom = new Point(xL, y + height);
                    minesLines[i] = new Line(top, bottom);
                }
                for (int i = 5; i != minesLines.length; i++) {
                    minesLines[i] = new Line(rightTopCorner, rightBottomCorner);
                }
                break;


            case OPENED:
                crossLines[0] = new Line(leftTopCorner, rightTopCorner);
                crossLines[1] = new Line(leftBottomCorner, rightBottomCorner);

                if(numberType){
                    if(data.getNeighbors() == 0){
                        for (int i = 0; i != minesLines.length; i++) {
                            minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                        }
                        break;
                    }
                    minesLines[0] = new Line(
                            new Point(this.x + this.width*3/10, this.y + this.height/5),
                            new Point(this.x + this.width*7/10, this.y + this.height/5));
                    minesLines[1] = new Line(
                            new Point(this.x + this.width*3/10, this.y + this.height*4/5),
                            new Point(this.x + this.width*7/10, this.y + this.height*4/5));
                    switch (data.getNeighbors()){
                        case 1:
                            minesLines[2] = new Line(
                                new Point(this.x + this.width/2, this.y + this.height*1/5),
                                new Point(this.x + this.width/2, this.y + this.height*4/5));
                            for (int i = 3; i != minesLines.length; i++) {
                                minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                            }
                            break;
                        case 2:
                            minesLines[2] = new Line(
                                new Point(this.x + this.width/2 - this.width*4/40, this.y + this.height*1/5),
                                new Point(this.x + this.width/2 - this.width*4/40, this.y + this.height*4/5));
                            minesLines[3] = new Line(
                                new Point(this.x + this.width/2 + this.width*4/40, this.y + this.height*1/5),
                                new Point(this.x + this.width/2 + this.width*4/40, this.y + this.height*4/5));
                            for (int i = 4; i != minesLines.length; i++) {
                                minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                            }
                            break;
                        case 3:
                            minesLines[2] = new Line(
                                    new Point(this.x + this.width/2, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2, this.y + this.height*4/5));
                            minesLines[3] = new Line(
                                new Point(this.x + this.width/2 - this.width*4/40, this.y + this.height*1/5),
                                new Point(this.x + this.width/2 - this.width*4/40, this.y + this.height*4/5));
                            minesLines[4] = new Line(
                                new Point(this.x + this.width/2 + this.width*4/40, this.y + this.height*1/5),
                                new Point(this.x + this.width/2 + this.width*4/40, this.y + this.height*4/5));
                            for (int i = 5; i != minesLines.length; i++) {
                                minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                            }
                            break;
                        case 4:
                            minesLines[2] = new Line(
                                    new Point(this.x + this.width/2 - this.width*2/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2 + this.width*2/40, this.y + this.height*4/5));
                            minesLines[3] = new Line(
                                    new Point(this.x + this.width/2 + this.width*6/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2 + this.width*2/40, this.y + this.height*4/5));
                            minesLines[4] = new Line(
                                    new Point(this.x + this.width/2 - this.width*4/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2 - this.width*4/40, this.y + this.height*4/5));
                            for (int i = 5; i != minesLines.length; i++) {
                                minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                            }
                            break;
                        case 5:
                            minesLines[2] = new Line(
                                    new Point(this.x + this.width/2 - this.width*4/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2, this.y + this.height*4/5));
                            minesLines[3] = new Line(
                                    new Point(this.x + this.width/2 + this.width*4/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2, this.y + this.height*4/5));
                            for (int i = 4; i != minesLines.length; i++) {
                                minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                            }
                            break;
                        case 6:
                            minesLines[2] = new Line(
                                    new Point(this.x + this.width/2 - this.width*6/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2 - this.width*2/40, this.y + this.height*4/5));
                            minesLines[3] = new Line(
                                    new Point(this.x + this.width/2 + this.width*2/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2 - this.width*2/40, this.y + this.height*4/5));
                            minesLines[4] = new Line(
                                    new Point(this.x + this.width/2 + this.width*4/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2 + this.width*4/40, this.y + this.height*4/5));
                            for (int i = 5; i != minesLines.length; i++) {
                                minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                            }
                            break;

                    }
                }
                else {
                    for (int i = 0; i != data.getNeighbors(); i++) {
                        int xL = x + (width * (i + 1)) / (data.getNeighbors() + 1);
                        Point top = new Point(xL, y);
                        Point bottom = new Point(xL, y + height);
                        minesLines[i] = new Line(top, bottom);
                    }
                    for (int i = data.getNeighbors(); i != minesLines.length; i++) {
                        minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                    }
                }
                break;

            case UNBLOCKED:
                crossLines[0] = new Line(leftTopCorner, rightBottomCorner);
                crossLines[1] = new Line(rightTopCorner, leftBottomCorner);
                for(int i = 0; i != minesLines.length; i++){
                    minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                }
                break;
        }
    }

    private void initAnimations(){
        crossLinesAnimation = new LineAnimation[2];
        minesLinesAnimation = new LineAnimation[6];
        for(int i = 0; i != crossLinesAnimation.length; i++){
            crossLinesAnimation[i] = new LinearLineAnimation(crossLines[i], crossLines[i], ANIMATION_DURATION);
        }
        for(int i = 0; i != minesLinesAnimation.length; i++){
            minesLinesAnimation[i] = new LinearLineAnimation(minesLines[i], minesLines[i], ANIMATION_DURATION);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint drawPaint = WHITE;
        for(int i = 0; i != crossLinesAnimation.length; i++){
            Line line = crossLinesAnimation[i].getLine();
            canvas.drawLine(line.start.X, line.start.Y, line.end.X, line.end.Y, drawPaint);
        }

        if(data.getStatus() == MineStatus.OPENED && colored){
            switch (data.getNeighbors()){
                case 1:
                    drawPaint = RED;
                    break;
                case 2:
                    drawPaint = BLUE;
                    break;
                case 3:
                    drawPaint = GREEN;
                    break;
                case 4:
                    drawPaint = CYAN;
                    break;
                case 5:
                    drawPaint = MAGENTA;
                    break;
                case 6:
                    drawPaint = YELLOW;
                    break;
            }
        }
        for(int i = 0; i != minesLines.length; i++){
            Line line = minesLinesAnimation[i].getLine();
            if(line.start.X == this.x && line.start.Y == this.y && line.end.X == this.x){
                break;
            }
            canvas.drawLine(line.start.X, line.start.Y, line.end.X, line.end.Y, drawPaint);
        }
    }

    @Override
    public void setPosition(int x, int width, int y, int height) {
        super.setPosition(x, width, y, height);

        leftTopCorner = new Point(x, y);
        rightTopCorner = new Point(x + width, y);
        leftBottomCorner = new Point(x, y + height);
        rightBottomCorner = new Point(x + width, y + height);

        initLines();
        initAnimations();
    }


    @Override
    protected void sendVerifiedLongTap(int x, int y) {
        switch (data.getStatus()) {
            case UNBLOCKED:
                data.setGameStatus(MineStatus.BLOCKED, gameStatus);
                break;
            case BLOCKED:
                data.setGameStatus(MineStatus.UNBLOCKED, gameStatus);
                break;
        }
        refreshLines();
        refreshAnimations();
    }

    @Override
    protected void sendVerifiedDoubleTap(int x, int y) {
        if (data.getStatus() == MineStatus.UNBLOCKED) {
            data.setGameStatus(MineStatus.OPENED, gameStatus);
            refreshLines();
            refreshAnimations();
            if (gameStatus.getFlood() && (data.getNeighbors() == 0)) {
                grid.autoFlood(data.getAllNeighborCoords());
            }
        }
    }

    public void setPreviousMineField(MineField previousMineField){
        this.previousMineField = previousMineField;
    }

    private void refreshAnimations(){
        for(int i = 0; i != crossLinesAnimation.length; i++){
            crossLinesAnimation[i].moveTo(crossLines[i], ANIMATION_DURATION);
        }

        for(int i = 0; i != minesLinesAnimation.length; i++){
            minesLinesAnimation[i].moveTo(minesLines[i], ANIMATION_DURATION);
        }
    }

    private void downloadAnimationStartsFromPreviousMineField(){
        for(int i = 0; i != crossLinesAnimation.length; i++){
            crossLinesAnimation[i].setStartLine(previousMineField.getCrossLinesAnimation()[i].getLine());
            crossLinesAnimation[i].startAnimation();
        }

        for(int i = 0; i != minesLinesAnimation.length; i++){
            minesLinesAnimation[i].setStartLine(previousMineField.getMinesLinesAnimation()[i].getLine());
            minesLinesAnimation[i].startAnimation();
        }
    }

    public LineAnimation[] getCrossLinesAnimation(){
        return crossLinesAnimation;
    }

    public LineAnimation[] getMinesLinesAnimation(){
        return minesLinesAnimation;
    }


    @Override
    public void sendTap(int x, int y) {

    }

    @Override
    public void sendVerifiedTap(int x, int y){

    }

}
