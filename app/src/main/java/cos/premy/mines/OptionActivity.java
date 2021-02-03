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
    private ToggleButton color;
    private ToggleButton numberType;
    private ToggleButton flood;
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
        color = findViewById(R.id.color);
        numberType = findViewById(R.id.numberType);
        flood = findViewById(R.id.flood);

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

        if(sharedPref.getBoolean("colored", false)){
            color.toggle();
        }
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color.isChecked()){
                    sharedPref.edit().putBoolean("colored", true).commit();
                }
                else {
                    sharedPref.edit().putBoolean("colored", false).commit();
                }
            }
        });

        if(sharedPref.getBoolean("numberType", false)){
            numberType.toggle();
        }
        numberType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberType.isChecked()){
                    sharedPref.edit().putBoolean("numberType", true).commit();
                }
                else {
                    sharedPref.edit().putBoolean("numberType", false).commit();
                }
            }
        });

        if(sharedPref.getBoolean("flood", false)){
            flood.toggle();
        }
        flood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flood.isChecked()){
                    sharedPref.edit().putBoolean("flood", true).commit();
                }
                else {
                    sharedPref.edit().putBoolean("flood", false).commit();
                }
            }
        });

    }

}
