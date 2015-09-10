package com.hmkcode.android;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.hmkcode.android.fragmentstest.FragmentOne;
import com.hmkcode.android.fragmentstest.FragmentTwo;


public class MainActivity extends AppCompatActivity {

    private EditText titleInput;
    private EditText bandInput;
    private MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MySQLiteHelper(this);
        titleInput = (EditText) findViewById(R.id.titleInput);
        bandInput = (EditText) findViewById(R.id.bandInput);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void saveInputSongToDatabase(View view) {
        db = new MySQLiteHelper(this);
        String songTitle = "";
        String songBand = "";
        if (titleInput.getText().toString().equals("")) {
            titleInput.setError("Please enter a valid song.");
            return;

        } else {
            songTitle = titleInput.getText().toString();

        }
        if (bandInput.getText().toString().equals("")) {
            bandInput.setError("Please enter a valid band, performer or artist.");
            return;

        } else {
            songBand = bandInput.getText().toString();
        }
        if (titleInput.getText().toString().equals("") && bandInput.getText().toString().equals("")) {

        } else {
            db.addSong(new Song(songTitle, songBand));
            titleInput.setText("");
            bandInput.setText("");
        }
    }

    public void selectFrag(View view) {

        Fragment fr;
        if (view == findViewById(R.id.button2)) {
            fr = new FragmentTwo();
        } else {
            fr = new FragmentOne();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();
    }

}
