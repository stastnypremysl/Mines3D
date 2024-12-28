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

    private Line[][] baseLines = null;
    private LineAnimation[][] crossLines = null;
    private LineAnimation[][] inverseCrossLines = null;
    private LineAnimation[] currentAnimatingLines = null;

    private static final int baseSize = 100;

    private static final int numLines = 2;
    private static final Line[] allBaseLines0 = new Line[8];
    private static final Line[] allBaseLines1 = new Line[8];

    static {
        // All base points and lines here are scaled from 0 to buttonBaseSize

        /* Points

        leftTop_____midTop_____rightTop
        |                            |
        leftBot_____midBot_____rightBot

        */

        Point leftTop = new Point(0, 0);
        Point leftBot = new Point(0, baseSize);
        Point midTop = new Point(baseSize / 2, 0);
        Point midBot = new Point(baseSize / 2, baseSize);
        Point rightTop = new Point(baseSize, 0);
        Point rightBot = new Point(baseSize, baseSize);

        /* Lines

        |--------------|      |--------------|      |--------------|
        | \back       /|      | slashLeft  / |      | \  backRight |
        |    \     /   |      |    /      /  |      |  \       \   |
        |       X      |      |   /      /   |      |   \       \  |
        |    /     \   |      |  /      /    |      |    \       \ |
        | /slash      \|      | /  slashRight|      | backLeft    \|
        |--------------|      |--------------|      |--------------|

         */

        Line leftSide = new Line(leftTop, leftBot);
        Line rightSide = new Line(rightTop, rightBot);
        Line slash = new Line(rightTop, leftBot);
        Line slashLeft = new Line(midTop, leftBot);
        Line slashRight = new Line(rightTop, midBot);
        Line back = new Line(leftTop, rightBot);
        Line backLeft = new Line(leftTop, midBot);
        Line backRight = new Line(midTop, rightBot);

        allBaseLines0[0] = leftSide;
        allBaseLines0[1] = back;
        allBaseLines0[2] = leftSide;
        allBaseLines0[3] = backLeft;
        allBaseLines0[4] = slashLeft;
        allBaseLines0[5] = slashLeft;
        allBaseLines0[6] = backLeft;
        allBaseLines0[7] = back;

        allBaseLines1[0] = rightSide;
        allBaseLines1[1] = rightSide;
        allBaseLines1[2] = slash;
        allBaseLines1[3] = slashRight;
        allBaseLines1[4] = backRight;
        allBaseLines1[5] = slashRight;
        allBaseLines1[6] = backRight;
        allBaseLines1[7] = slash;
    }

    public SwitchButton(GameStatus gameStatus){
        super(gameStatus);

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setARGB(255,255,255,255);
        paintLine.setStrokeWidth(4F);
        int numLevels = gameStatus.getNumLevels();
        assert numLevels <= 8 : "Number of levels (z-coordinate) is not supported by this button.";
        baseLines = new Line[numLines][numLevels];
        crossLines = new LineAnimation[numLines][numLevels];
        inverseCrossLines = new LineAnimation[numLines][numLevels];
        currentAnimatingLines = new LineAnimation[numLines];

        for (int i = 0; i < numLevels - 1; i++) {
            baseLines[0][i] = allBaseLines0[i];
            baseLines[1][i] = allBaseLines1[i];
        }
        baseLines[0][numLevels - 1] = allBaseLines0[7];
        baseLines[1][numLevels - 1] = allBaseLines1[7];
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x+width, y, paintLine);
        canvas.drawLine(x, y+height, x+width, y+height, paintLine);
        canvas.drawLine(x, y, x, y+height, paintLine);
        canvas.drawLine(x+width, y, x+width, y+height, paintLine);

        for (LineAnimation crossLine : currentAnimatingLines) {
            if (crossLine == null) continue;
            Line line = crossLine.getLine();
            canvas.drawLine(line.start.X, line.start.Y, line.end.X, line.end.Y, paintLine);
        }
    }

    @Override
    public void setPosition(int x, int width, int y, int height) {
        super.setPosition(x, width, y, height);

        int currentLevel = gameStatus.getLevel();
        Line[][] lines = new Line[baseLines.length][];
        for (int i = 0; i < baseLines.length; i++) {
            lines[i] = new Line[baseLines[i].length];
            for (int j = 0; j < baseLines[i].length; j++) {
                lines[i][j] = scaleToButtonPosition(baseLines[i][j], x, width, y, height);
            }
        }

        for (int i = 0; i < lines.length; i++) {
            int numLevels = lines[i].length;
            for (int j = 0; j < numLevels; j++) {
                crossLines[i][j] = new LinearLineAnimation(lines[i][(j - 1 + numLevels) % numLevels], lines[i][j], 200);
            }
            for (int j = 0; j < numLevels; j++) {
                inverseCrossLines[i][j] = new LinearLineAnimation(lines[i][(j + 1) % numLevels], lines[i][j], 200);
            }
        }

        for (int i = 0; i < lines.length; i++) {
            currentAnimatingLines[i] = crossLines[i][currentLevel];
            currentAnimatingLines[i].finishAnimation();
        }
    }

    @Override
    protected void sendVerifiedTap(int x, int y) {
        double relative;
        if (width > height) {
            relative = (double) (y - this.y) / height;
        }  else {
            relative = (double) (x - this.x) / width;
        }

        if (relative < 0.5) {
            incrementLevel();
        } else {
            decrementLevel();
        }
    }

    private void incrementLevel(){
        gameStatus.incrementLevel();
        int currentLevel = gameStatus.getLevel();

        for (int i = 0; i < crossLines.length; i++) {
            LineAnimation[] crossLine = crossLines[i];
            LineAnimation anim = crossLine[currentLevel];
            anim.startAnimation();
            currentAnimatingLines[i] = anim;
        }
    }

    private void decrementLevel(){
        gameStatus.decrementLevel();
        int currentLevel = gameStatus.getLevel();

        for (int i = 0; i < crossLines.length; i++) {
            LineAnimation[] crossLine = inverseCrossLines[i];
            LineAnimation anim = crossLine[currentLevel];
            anim.startAnimation();
            currentAnimatingLines[i] = anim;
        }
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

    static private Line scaleToButtonPosition(Line baseLine, int x, int width, int y, int height) {
        if (width >= height) {
            return new Line(
                    new Point(
                            (baseLine.start.X * width) / baseSize + x,
                            (baseLine.start.Y * height) / baseSize + y),
                    new Point(
                            (baseLine.end.X * width) / baseSize + x,
                            (baseLine.end.Y * height) / baseSize + y)
            );
        } else {
            return new Line(
                    new Point(
                            (baseLine.start.Y * width) / baseSize + x,
                            -(baseLine.start.X * height) / baseSize + y + height),
                    new Point(
                            (baseLine.end.Y * width) / baseSize + x,
                            -(baseLine.end.X * height) / baseSize + y + height));
        }
    }
}
