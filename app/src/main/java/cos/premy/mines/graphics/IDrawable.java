package cos.premy.mines.graphics;

import android.graphics.Canvas;

/**
 * Created by premy on 07.11.2017.
 */

public interface IDrawable {
    public void draw(Canvas canvas);
    public void setPosition(int x, int width, int y, int height);

    public void sendTap(int x, int y);
    public void sendLongTap(int x, int y);
    public void sendDoubleTap(int x, int y);
}
