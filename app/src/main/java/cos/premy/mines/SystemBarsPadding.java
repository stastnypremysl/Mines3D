package cos.premy.mines;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;

/**
 * Keeps the activity content out of the system bars.
 *
 * Since targetSdk 35 every window is laid out edge-to-edge: the status bar and the navigation bar
 * are transparent and the content is drawn behind them. Padding the content view by the system
 * window insets (system bars and display cutout) and painting the uncovered strips black gives the
 * same picture as the black bars the app had before the migration.
 */
public final class SystemBarsPadding implements OnApplyWindowInsetsListener {

    private final Strips strips;

    SystemBarsPadding(Strips strips) {
        this.strips = strips;
    }

    /**
     * Installs the padding on the content view of {@code activity}. Call it after
     * {@link Activity#setContentView(int)}, because that is when AppCompat puts its own content view
     * in place.
     */
    public static void apply(Activity activity) {
        View content = activity.findViewById(android.R.id.content);
        Strips strips = new Strips();
        content.setBackground(strips);
        ViewCompat.setOnApplyWindowInsetsListener(content, new SystemBarsPadding(strips));
    }

    @Override
    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
        int left = insets.getSystemWindowInsetLeft();
        int top = insets.getSystemWindowInsetTop();
        int right = insets.getSystemWindowInsetRight();
        int bottom = insets.getSystemWindowInsetBottom();
        view.setPadding(left, top, right, bottom);
        strips.setInsets(left, top, right, bottom);
        return insets.consumeSystemWindowInsets();
    }

    /**
     * Paints the four strips between the bounds and the insets black and leaves the rest
     * untouched, so the window background shows through where the content is.
     */
    static final class Strips extends Drawable {
        private final Paint paint = new Paint();
        private int left;
        private int top;
        private int right;
        private int bottom;

        Strips() {
            paint.setColor(Color.BLACK);
        }

        void setInsets(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            invalidateSelf();
        }

        @Override
        public void draw(Canvas canvas) {
            Rect b = getBounds();
            int innerTop = b.top + top;
            int innerBottom = b.bottom - bottom;
            canvas.drawRect(b.left, b.top, b.right, innerTop, paint);
            canvas.drawRect(b.left, innerBottom, b.right, b.bottom, paint);
            canvas.drawRect(b.left, innerTop, b.left + left, innerBottom, paint);
            canvas.drawRect(b.right - right, innerTop, b.right, innerBottom, paint);
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            paint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
