package cos.premy.mines;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;

import org.junit.Test;

public class SystemBarsPaddingTest {

    private static WindowInsetsCompat insets(int left, int top, int right, int bottom) {
        WindowInsetsCompat insets = mock(WindowInsetsCompat.class);
        when(insets.getSystemWindowInsetLeft()).thenReturn(left);
        when(insets.getSystemWindowInsetTop()).thenReturn(top);
        when(insets.getSystemWindowInsetRight()).thenReturn(right);
        when(insets.getSystemWindowInsetBottom()).thenReturn(bottom);
        return insets;
    }

    private static Rect bounds(int width, int height) {
        Rect bounds = new Rect();
        bounds.right = width;
        bounds.bottom = height;
        return bounds;
    }

    @Test
    public void listenerPadsTheViewBySystemWindowInsetsAndConsumesThem() {
        View view = mock(View.class);
        WindowInsetsCompat insets = insets(10, 120, 30, 80);
        WindowInsetsCompat consumed = mock(WindowInsetsCompat.class);
        when(insets.consumeSystemWindowInsets()).thenReturn(consumed);

        WindowInsetsCompat result = new SystemBarsPadding(new SystemBarsPadding.Strips())
                .onApplyWindowInsets(view, insets);

        verify(view).setPadding(10, 120, 30, 80);
        assertSame(consumed, result);
    }

    @Test
    public void listenerRemovesThePaddingWhenThereAreNoInsets() {
        View view = mock(View.class);

        new SystemBarsPadding(new SystemBarsPadding.Strips()).onApplyWindowInsets(view, insets(0, 0, 0, 0));

        verify(view).setPadding(0, 0, 0, 0);
    }

    @Test
    public void listenerHandsTheInsetsToTheStrips() {
        SystemBarsPadding.Strips strips = spy(new SystemBarsPadding.Strips());

        new SystemBarsPadding(strips).onApplyWindowInsets(mock(View.class), insets(10, 120, 30, 80));

        verify(strips).setInsets(10, 120, 30, 80);
    }

    @Test
    public void stripsCoverExactlyTheInsetsOfTheBounds() {
        SystemBarsPadding.Strips strips = spy(new SystemBarsPadding.Strips());
        doReturn(bounds(1080, 2400)).when(strips).getBounds();
        strips.setInsets(10, 120, 30, 80);
        Canvas canvas = mock(Canvas.class);

        strips.draw(canvas);

        verify(canvas).drawRect(eq(0f), eq(0f), eq(1080f), eq(120f), any(Paint.class));
        verify(canvas).drawRect(eq(0f), eq(2320f), eq(1080f), eq(2400f), any(Paint.class));
        verify(canvas).drawRect(eq(0f), eq(120f), eq(10f), eq(2320f), any(Paint.class));
        verify(canvas).drawRect(eq(1050f), eq(120f), eq(1080f), eq(2320f), any(Paint.class));
        verifyNoMoreInteractions(canvas);
    }

    @Test
    public void applyInstallsTheStripsAsTheBackgroundOfTheContentView() {
        Activity activity = mock(Activity.class);
        View content = mock(View.class);
        when(activity.<View>findViewById(android.R.id.content)).thenReturn(content);

        SystemBarsPadding.apply(activity);

        verify(content).setBackground(any(SystemBarsPadding.Strips.class));
    }
}
