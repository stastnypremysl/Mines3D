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
    private MineField twin;

    private Line[] crossLines;
    private Line[] minesLines;
    private LineAnimation[] crossLinesAnimation;
    private LineAnimation[] minesLinesAnimation;

    private final int level;

    private static final int ANIMATION_DURATION = 100;

    private Point leftTopCorner = null;
    private Point rightTopCorner = null;
    private Point leftBottomCorner = null;
    private Point rightBottomCorner = null;

    private Paint white;
    private Paint red;
    private Paint blue;
    private Paint green;
    private Paint cyan;
    private Paint magenta;

    private final boolean colored;
    private final boolean numberType;


    public MineField(Grid grid, Mine data, GameStatus status, final int level){
        super(status);
        this.grid = grid;
        this.data = data;
        this.level = level;
        colored = status.getColored();
        numberType = status.getNumberType();

        white = new Paint();
        white.setAntiAlias(true);
        white.setARGB(255,255,255,255);
        white.setStrokeWidth(3F);

        if(colored) {
            red = new Paint();
            red.setAntiAlias(true);
            red.setColor(Color.RED);
            red.setStrokeWidth(3F);

            blue = new Paint();
            blue.setAntiAlias(true);
            blue.setColor(Color.BLUE);
            blue.setStrokeWidth(3F);

            green = new Paint();
            green.setAntiAlias(true);
            green.setColor(Color.GREEN);
            green.setStrokeWidth(3F);

            cyan = new Paint();
            cyan.setAntiAlias(true);
            cyan.setColor(Color.CYAN);
            cyan.setStrokeWidth(3F);

            magenta = new Paint();
            magenta.setAntiAlias(true);
            magenta.setColor(Color.MAGENTA);
            magenta.setStrokeWidth(3F);
        }

        status.addLevelSwitchListener(new LevelSwitchListener() {
            @Override
            public void levelSwitched(GameStatus status) {
                if(status.getLevel() == level){
                    downloadAnimationStartsFromTwin();
                    refreshAnimations();
                }
            }
        });

    }

    private void initLines(){
        crossLines = new Line[2];
        minesLines = new Line[5];
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
                break;
            case OPENED:
                crossLines[0] = new Line(leftTopCorner, rightTopCorner);
                crossLines[1] = new Line(leftBottomCorner, rightBottomCorner);
                if(numberType){
                    if(data.getNeighbors() == 0){
                        for (int i = 0; i != 5; i++) {
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
                            for (int i = 3; i != 5; i++) {
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
                            for (int i = 4; i != 5; i++) {
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
                            break;
                        case 5:
                            minesLines[2] = new Line(
                                    new Point(this.x + this.width/2 - this.width*4/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2, this.y + this.height*4/5));
                            minesLines[3] = new Line(
                                    new Point(this.x + this.width/2 + this.width*4/40, this.y + this.height*1/5),
                                    new Point(this.x + this.width/2, this.y + this.height*4/5));
                            for (int i = 4; i != 5; i++) {
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
                    for (int i = data.getNeighbors(); i != 5; i++) {
                        minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                    }
                }
                break;
            case UNBLOCKED:
                crossLines[0] = new Line(leftTopCorner, rightBottomCorner);
                crossLines[1] = new Line(rightTopCorner, leftBottomCorner);
                for(int i = 0; i != 5; i++){
                    minesLines[i] = new Line(leftTopCorner, leftBottomCorner);
                }
                break;
        }
    }

    private void initAnimations(){
        crossLinesAnimation = new LineAnimation[2];
        minesLinesAnimation = new LineAnimation[5];
        for(int i = 0; i != 2; i++){
            crossLinesAnimation[i] = new LinearLineAnimation(crossLines[i], crossLines[i], ANIMATION_DURATION);
        }
        for(int i = 0; i != 5; i++){
            minesLinesAnimation[i] = new LinearLineAnimation(minesLines[i], minesLines[i], ANIMATION_DURATION);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint drawPaint = white;
        for(int i = 0; i != 2; i++){
            Line line = crossLinesAnimation[i].getLine();
            canvas.drawLine(line.start.X, line.start.Y, line.end.X, line.end.Y, drawPaint);
        }

        if(data.getStatus() == MineStatus.OPENED && colored){
            switch (data.getNeighbors()){
                case 1:
                    drawPaint = red;
                    break;
                case 2:
                    drawPaint = blue;
                    break;
                case 3:
                    drawPaint = green;
                    break;
                case 4:
                    drawPaint = cyan;
                    break;
                case 5:
                    drawPaint = magenta;
                    break;
            }
        }
        for(int i = 0; i != 5; i++){
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
        switch (data.getStatus()) {
            case UNBLOCKED:
            data.setGameStatus(MineStatus.OPENED, gameStatus);
            refreshLines();
            refreshAnimations();
            if (gameStatus.getFlood() && (data.getNeighbors() == 0)) {
                grid.autoFlood(data.getAllNeighborCoords());
            }
            break;
        }
    }

    public void setTwin(MineField twin){
        this.twin = twin;
    }

    private void refreshAnimations(){
        for(int i = 0; i != 2; i++){
            crossLinesAnimation[i].moveTo(crossLines[i], ANIMATION_DURATION);
        }

        for(int i = 0; i != 5; i++){
            minesLinesAnimation[i].moveTo(minesLines[i], ANIMATION_DURATION);
        }
    }

    private void downloadAnimationStartsFromTwin(){
        for(int i = 0; i != 2; i++){
            crossLinesAnimation[i].setStartLine(twin.getCrossLinesAnimation()[i].getLine());
            crossLinesAnimation[i].startAnimation();
        }

        for(int i = 0; i != 5; i++){
            minesLinesAnimation[i].setStartLine(twin.getMinesLinesAnimation()[i].getLine());
            minesLinesAnimation[i].startAnimation();
        }
    }

    public Line[] getCrossLines(){
        return crossLines;
    }

    public Line[] getMinesLines(){
        return minesLines;
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
