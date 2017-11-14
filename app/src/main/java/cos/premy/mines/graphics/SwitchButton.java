package cos.premy.mines.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;

import cos.premy.mines.GameStatus;
import cos.premy.mines.graphics.animations.Line;
import cos.premy.mines.graphics.animations.LineAnimation;
import cos.premy.mines.graphics.animations.LinearLineAnimation;
import cos.premy.mines.graphics.animations.Point;

/**
 * Created by premy on 07.11.2017.
 */

public class SwitchButton extends AbstractDrawable {
    private Paint paintLine;

    private LineAnimation crossLine1 = null;
    private LineAnimation crossLine2 = null;

    private Point leftTopCorner = null;
    private Point rightTopCorner = null;
    private Point leftBottomCorner = null;
    private Point rightBottomCorner = null;

    public SwitchButton(GameStatus gameStatus){
        super(gameStatus);

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setARGB(255,255,255,255);
        paintLine.setStrokeWidth(4F);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x+width, y, paintLine);
        canvas.drawLine(x, y+height, x+width, y+height, paintLine);
        canvas.drawLine(x, y, x, y+height, paintLine);
        canvas.drawLine(x+width, y, x+width, y+height, paintLine);

        Line cross1 = crossLine1.getLine();
        Line cross2 = crossLine2.getLine();
        canvas.drawLine(cross1.start.X, cross1.start.Y, cross1.end.X, cross1.end.Y, paintLine);
        canvas.drawLine(cross2.start.X, cross2.start.Y, cross2.end.X, cross2.end.Y, paintLine);
    }

    @Override
    public void setPosition(int x, int width, int y, int height) {
        super.setPosition(x, width, y, height);

        leftTopCorner = new Point(x, y);
        rightTopCorner = new Point(x + width, y);
        leftBottomCorner = new Point(x, y + height);
        rightBottomCorner = new Point(x + width, y + height);

        if(width > height){
            crossLine1 = new LinearLineAnimation(new Line(leftTopCorner, rightBottomCorner), new Line(leftTopCorner, leftBottomCorner), 200);
            crossLine2 = new LinearLineAnimation(new Line(rightTopCorner, leftBottomCorner), new Line(rightTopCorner, rightBottomCorner), 200);
        } else {
            crossLine1 = new LinearLineAnimation(new Line(leftTopCorner, rightBottomCorner), new Line(leftTopCorner, rightTopCorner), 200);
            crossLine2 = new LinearLineAnimation(new Line(leftBottomCorner, rightTopCorner), new Line(leftBottomCorner, rightBottomCorner), 200);
        }

        if(gameStatus.getLevel() == 1){
            crossLine1.reverseAnimation();
            crossLine1.finishAnimation();
            crossLine2.reverseAnimation();
            crossLine2.finishAnimation();
        }
    }

    @Override
    protected void sendVerifiedTap(int x, int y) {
        crossLine1.reverseAnimation();
        crossLine2.reverseAnimation();
        gameStatus.switchLevel();
    }

    @Override
    public void sendLongTap(int x, int y) {

    }

    @Override
    public void sendDoubleTap(int x, int y) {

    }


    @Override
    protected void sendVerifiedLongTap(int x, int y) {

    }

    @Override
    protected void sendVerifiedDoubleTap(int x, int y) {

    }
}
