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

public class StatusLabel implements IDrawable {
    private int fontSize = 30;

    private final MinesContainer minesContainer;
    private final GameStatus status;

    private int x;
    private int y;

    private final Paint paint;

    public StatusLabel(MinesContainer minesContainer, GameStatus status){
        this.minesContainer = minesContainer;
        this.status = status;

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
        long delta = System.currentTimeMillis() - status.getStartTime().getTime();
        double reducedDelta = delta;
        reducedDelta /= 100;
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
        this.x = x;
        this.y = y;

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
}
