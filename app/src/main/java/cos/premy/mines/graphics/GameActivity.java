package cos.premy.mines.graphics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cos.premy.mines.R;
import cos.premy.mines.SystemBarsPadding;

public class GameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        SystemBarsPadding.apply(this);
    }

}
