package cos.premy.mines;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class OptionActivity extends AppCompatActivity {

    private ToggleButton hardcore;
    private SharedPreferences sharedPref;


    public OptionActivity(){
        sharedPref = LoadedGame.mainActivity.getSharedPreferences("cos.premy.mines.settings", Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Options");

        hardcore = findViewById(R.id.mode);
        if(sharedPref.getBoolean("hardcore", false)){
            hardcore.toggle();
        }
        hardcore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hardcore.isChecked()){
                    sharedPref.edit().putBoolean("hardcore", true).commit();
                }
                else {
                    sharedPref.edit().putBoolean("hardcore", false).commit();
                }
            }
        });
    }

}
