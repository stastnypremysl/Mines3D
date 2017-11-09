package cos.premy.mines.graphics.animations;

/**
 * Created by premy on 08.11.2017.
 */

public interface LineAnimation {
    public Line getStartLine();
    public void setStartLine(Line line);

    public Line getEndLine();
    public void setEndLine(Line line);

    public Line getLine();

    public long getAnimationDuration();
    public void setAnimationDuration(long milliseconds);

    public void startAnimation();
    public void reverseAnimation();
    public void finishAnimation();

    public void moveTo(Line line, long milliseconds);

}
