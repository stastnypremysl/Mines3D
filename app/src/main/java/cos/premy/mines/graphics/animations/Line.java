package cos.premy.mines.graphics.animations;

import cos.premy.mines.Utils;

/**
 * Created by premy on 08.11.2017.
 */

public class Line {
    public Point start;
    public Point end;

    public Line(Point start, Point end){
        this.start = start;
        this.end = end;
    }

    public Line(int x1, int x2, int y1, int y2){
        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }

    public void changeLength(double newLength){
        double deltaX = end.X - start.X;
        double deltaY = end.Y - start.Y;
        double oldLength = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double sin = deltaY / oldLength;
        double cos = deltaX / oldLength;

        end = new Point(Utils.dToI(newLength * cos), Utils.dToI(newLength * sin));
    }
}
