package cos.premy.mines.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import cos.premy.mines.GameStatus;
import cos.premy.mines.data.MinesContainer;

/**
 * Created by premy on 09.11.2017.
 */

public class StatusLabel extends AbstractDrawable {
    private int fontSize = 30;
    private final MinesContainer minesContainer;

    private final Paint paint;

    public StatusLabel(MinesContainer minesContainer, GameStatus status){
        super(status);
        this.minesContainer = minesContainer;

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(fontSize);
        //paint.setTypeface(Typeface.create("Courier", Typeface.NORMAL));
    }

    @Override
    public void draw(Canvas canvas) {
        StringBuilder builder = new StringBuilder();

        builder.append("Time: ");
        long delta = System.currentTimeMillis() - gameStatus.getStartTime().getTime();
        double reducedDelta = delta;
        reducedDelta /= 1000;
        builder.append(reducedDelta);

        String toWrite = builder.toString();
        canvas.drawText(toWrite, x, y, paint);

        builder = new StringBuilder();
        builder.append("Mines blocked: ");
        builder.append(minesContainer.getMinesBlocked());
        builder.append("/");
        builder.append(minesContainer.getMinesNumber());

        toWrite = builder.toString();
        canvas.drawText(toWrite, x, y + fontSize, paint);

    }

    @Override
    public void setPosition(int x, int width, int y, int height) {
        super.setPosition(x, width, y, height);

        this.fontSize = height;
        paint.setTextSize(fontSize);
    }

    @Override
    public void sendTap(int x, int y) {

    }

    @Override
    public void sendLongTap(int x, int y) {

    }

    @Override
    public void sendDoubleTap(int x, int y) {

    }

    @Override
    protected void sendVerifiedTap(int x, int y) {

    }

    @Override
    protected void sendVerifiedLongTap(int x, int y) {

    }

    @Override
    protected void sendVerifiedDoubleTap(int x, int y) {

    }
}
