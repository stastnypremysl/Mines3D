package cos.premy.mines.graphics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Vector;
import java.util.logging.Handler;

import cos.premy.mines.GameEndedListener;
import cos.premy.mines.GameStatus;
import cos.premy.mines.LoadedGame;
import cos.premy.mines.R;
import cos.premy.mines.ReviewReminder;
import cos.premy.mines.Utils;
import cos.premy.mines.data.MinesContainer;
import cos.premy.mines.generator.RandomMinesGenerator;

/**
 * Created by premy on 07.11.2017.
 */

public class MinesView extends View {
    private final Vector<IDrawable> drawable;

    private final Grid grid;
    private final SwitchButton switchButton;
    private final GestureDetector detector;

    private final GameStatus gameStatus;
    private final MinesContainer minesContainer;
    private final StatusLabel statusLabel;

    private Runnable refreshRunnable;
    private android.os.Handler handler;

    public MinesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gameStatus = LoadedGame.gameStatus;
        gameStatus.cleanListeners();
        gameStatus.addGameEndedListener(new GameEndedListener() {
            @Override
            public void gameEnded(GameStatus status) {
                doFinalize();

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoadedGame.mainActivity);
                StringBuilder message = new StringBuilder();
                if(status.hasUserWon()){
                    message.append("Congratulations! You have won!\n");
                } else {
                    message.append("You have lost.\n");
                }
                message.append("Correctly placed mines/placed mines/opened fields/number of mines: ");
                message.append(String.format("%d/%d/%d/%d\n", minesContainer.getOkBlockedMines(),
                        minesContainer.getMinesBlocked(), minesContainer.getMinesOpened(), minesContainer.getMinesNumber()));

                message.append("Game time: ");
                double diff = (double)status.getEndTime().getTime() - status.getStartTime().getTime();
                diff = diff/1000;
                message.append(diff);
                message.append(" seconds");

                Activity activity = (Activity)getContext();
                String preferenceName = String.format("cos.premy.mines.%d.%d.%d",
                        minesContainer.getHeight(), minesContainer.getWidth(), minesContainer.getMinesNumber());
                SharedPreferences sharedPref = activity.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
                float lowest = sharedPref.getFloat("lowestTime", -1);
                if(status.hasUserWon() && (lowest == -1 || lowest > diff)){
                    sharedPref.edit().putFloat("lowestTime", (float)diff).commit();
                    ReviewReminder.setReadyForReview(LoadedGame.mainActivity);
                    message.append("\nNew record!");
                }
                if(lowest != -1){
                    message.append("\nThe last record: ");
                    message.append(lowest);
                    message.append(" seconds");
                }

                dlgAlert.setMessage(message.toString());
                dlgAlert.setTitle("Game over");
                dlgAlert.create().show();
            }
        });

        minesContainer = LoadedGame.minesContainer;

        grid = new Grid(minesContainer, gameStatus);
        switchButton = new SwitchButton(gameStatus);
        statusLabel = new StatusLabel(minesContainer, gameStatus);

        drawable = new Vector<>();
        drawable.add(grid);
        drawable.add(switchButton);
        drawable.add(statusLabel);

        detector = new GestureDetector(this.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                for(IDrawable iDrawable : drawable){
                    iDrawable.sendTap(Utils.fToI(e.getX()), Utils.fToI(e.getY()));
                };
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                for(IDrawable iDrawable : drawable){
                    iDrawable.sendLongTap(Utils.fToI(e.getX()), Utils.fToI(e.getY()));
                };
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                for(IDrawable iDrawable : drawable){
                    iDrawable.sendDoubleTap(Utils.fToI(e.getX()), Utils.fToI(e.getY()));
                };
                return true;
            }

        });

        handler = new android.os.Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                postInvalidate();
                handler.postDelayed(refreshRunnable, 17);
            }
        };
        handler.postDelayed(refreshRunnable, 17);
    }

    private void doFinalize() {
        Activity activity = (Activity)getContext();
        activity.finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        for(IDrawable iDrawable : drawable){
            iDrawable.draw(canvas);
        };

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        MinesLayoutComputor layout = new MinesLayoutComputor(h, w);

        grid.setPosition(layout.getGridX(), layout.getGridWidth(), layout.getGridY(), layout.getGridHeight());
        switchButton.setPosition(layout.getButtonX(), layout.getButtonWidth(), layout.getButtonY(), layout.getButtonHeight());
        statusLabel.setPosition(layout.getStatusLabelX(),0,  layout.getStatusLabelY(), layout.getStatusHeight());
    }
}
