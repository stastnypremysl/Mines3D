package cos.premy.mines.graphics.animations;

import cos.premy.mines.Utils;

/**
 * Created by premy on 08.11.2017.
 */

public class LinearLineAnimation extends AbstractLineAnimation {

    public LinearLineAnimation(Line startLine, Line endLine, int duration){
        super(startLine, endLine, duration);
    }

    @Override
    public Line getLine() {
        long deltaT = getDeltaT();
        if(deltaT >= duration){
            return endLine;
        }

        double K = (double)deltaT / (double)duration;
        Point pointBegin = new Point(startLine.start.X + Utils.dToI((double)(endLine.start.X - startLine.start.X) * K),
                startLine.start.Y + Utils.dToI((double)(endLine.start.Y - startLine.start.Y) * K));
        Point pointEnd = new Point(startLine.end.X + Utils.dToI((double)(endLine.end.X - startLine.end.X) * K),
                startLine.end.Y + Utils.dToI((double)(endLine.end.Y - startLine.end.Y) * K));

        return new Line(pointBegin, pointEnd);
    }
}
