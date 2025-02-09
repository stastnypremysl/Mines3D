package cos.premy.mines;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cos.premy.mines.data.MinesContainer;
import cos.premy.mines.graphics.GameActivity;

public class MainActivity extends AppCompatActivity {

    private Button start5x5;
    private Button start8x8;
    private Button start10x10;
    private Button start12x12;
    private Button start15x15;
    private Button howToPlay;
    private Button options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initActions();

        if(LoadedGame.mainActivity == null){
            ReviewReminder.startPotentialReviewReminding(this);
        }

        LoadedGame.mainActivity = this;

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    private void initComponents(){
        start5x5 = findViewById(R.id.start5x5);
        start8x8 = findViewById(R.id.start8x8);
        start10x10 = findViewById(R.id.start10x10);
        start12x12 = findViewById(R.id.start12x12);
        start15x15 = findViewById(R.id.start15x15);
        howToPlay = findViewById(R.id.howToPlay);
        options = findViewById(R.id.options);
    }

    private void initActions(){
        final Activity thisActivity = this;
        
        start5x5.setOnClickListener(createGameStartListener(thisActivity, 5, 7));
        start8x8.setOnClickListener(createGameStartListener(thisActivity, 8, 16));
        start10x10.setOnClickListener(createGameStartListener(thisActivity, 10, 25));
        start12x12.setOnClickListener(createGameStartListener(thisActivity, 12, 50));
        start15x15.setOnClickListener(createGameStartListener(thisActivity, 15, 90));
        
        options.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(thisActivity, OptionActivity.class);
                startActivity(myIntent);
            }
        });
        
        howToPlay.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoadedGame.mainActivity);
                dlgAlert.setMessage(R.string.how_to_play_hint);
                dlgAlert.setTitle(R.string.how_to_play);
                dlgAlert.create().show();
            }
        });
    }

    private View.OnClickListener createGameStartListener(final Activity activity, final int gridSize, final int baseMines) {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(activity, GameActivity.class);

                LoadedGame.gameStatus = new GameStatus(LoadedGame.mainActivity);
                boolean hardcore = LoadedGame.gameStatus.getHardcore();
                int numLevels = LoadedGame.gameStatus.getNumLevels();
                int mines = (int) (baseMines * (hardcore ? 1.5f : 1) * numLevels / 2);
                assert mines < numLevels * gridSize * gridSize;
                LoadedGame.minesContainer = new MinesContainer(gridSize, gridSize, numLevels, mines);

                startActivity(myIntent);
            }
        };
    }

}

