package cos.premy.mines;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cos.premy.mines.generator.RandomMinesGenerator;
import cos.premy.mines.graphics.GameActivity;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Button start5x5;
    private Button start8x8;
    private Button start10x10;
    private Button start12x12;
    private Button start15x15;
    private Button howToPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initActions();

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
    }

    private void initActions(){
        final Activity thisActivity = this;
        start5x5.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(thisActivity, GameActivity.class);

                LoadedGame.gameStatus = new GameStatus();
                LoadedGame.minesContainer = new RandomMinesGenerator().getNewProblem(5,5,7);

                startActivity(myIntent);
            }
        });
        start8x8.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(thisActivity, GameActivity.class);

                LoadedGame.gameStatus = new GameStatus();
                LoadedGame.minesContainer = new RandomMinesGenerator().getNewProblem(8,8,16);

                startActivity(myIntent);
            }
        });
        start10x10.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(thisActivity, GameActivity.class);

                LoadedGame.gameStatus = new GameStatus();
                LoadedGame.minesContainer = new RandomMinesGenerator().getNewProblem(10,10,25);

                startActivity(myIntent);
            }
        });
        start12x12.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(thisActivity, GameActivity.class);

                LoadedGame.gameStatus = new GameStatus();
                LoadedGame.minesContainer = new RandomMinesGenerator().getNewProblem(12,12,50);

                startActivity(myIntent);
            }
        });
        start15x15.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(thisActivity, GameActivity.class);

                LoadedGame.gameStatus = new GameStatus();
                LoadedGame.minesContainer = new RandomMinesGenerator().getNewProblem(15,15,90);

                startActivity(myIntent);
            }
        });
        howToPlay.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoadedGame.mainActivity);
                StringBuilder message = new StringBuilder();
                dlgAlert.setMessage("Nobody knows! (Přemysl Šťastný) \n \nPS: You can try using double tap and long press.");
                dlgAlert.setTitle("How to play?");
                dlgAlert.create().show();
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
