package cos.premy.mines.graphics.animations;

/**
 * Created by premy on 08.11.2017.
 */

public abstract class AbstractLineAnimation implements LineAnimation {
    protected Line startLine;
    protected Line endLine;
    protected long duration;

    protected long startTime;


    public AbstractLineAnimation(Line startLine, Line endLine, int duration){
        this.startLine = startLine;
        this.endLine = endLine;
        this.duration = duration;
        this.startTime = 0;
    }

    @Override
    public Line getStartLine(){
        return startLine;
    }

    @Override
    public void setStartLine(Line line){
        startLine = line;
    }

    @Override
    public Line getEndLine(){
        return endLine;
    }

    @Override
    public void setEndLine(Line line){
        endLine = line;
    }

    @Override
    public void startAnimation(){
        startTime = System.currentTimeMillis();
    }

    protected long getDeltaT(){
        return System.currentTimeMillis() - startTime;
    }

    @Override
    public void finishAnimation(){
        startTime = 0;
    }

    @Override
    public void reverseAnimation(){
        Line oldStartLine = startLine;
        Line oldEndLine = endLine;
        endLine = oldStartLine;
        startLine = oldEndLine;

        long deltaT = getDeltaT();
        if(deltaT > duration){
            startTime = System.currentTimeMillis();
        }
        else{
            startTime = System.currentTimeMillis() - duration + deltaT;
        }
    }

    @Override
    public void moveTo(Line line, long milliseconds){
        startLine = getLine();
        endLine = line;
        duration = milliseconds;
        startAnimation();
    }

    @Override
    public long getAnimationDuration(){
        return duration;
    }

    @Override
    public void setAnimationDuration(long milliseconds){
        duration = milliseconds;
    }

}
