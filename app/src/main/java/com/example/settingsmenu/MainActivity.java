package com.example.settingsmenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
// import android.support.v7.app.AppCompatActivity;
import androidx.preference.Preference.OnPreferenceClickListener;
import androidx.preference.PreferenceCategory;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    TextView visibletxt, colortxt, sizetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visibletxt = findViewById(R.id.visible_text);
        colortxt = findViewById(R.id.color_text);
        sizetxt = findViewById(R.id.size_text);

        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    // Method to set Visibility of Text.
    private void setTextVisible(boolean display_text) {
        if (display_text == true) {
            visibletxt.setVisibility(View.VISIBLE);
        } else {
            visibletxt.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // detect click on option in action bar
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    if(key.equals("text_color")){
            Toast.makeText(MainActivity.this,"color",Toast.LENGTH_LONG).show();
        }
    */

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) { // changing the state of an option (not click)
        if (key.equals("display_text")) {
            setTextVisible(sharedPreferences.getBoolean("display_text", true));
            // openDialog("Most épp egy nagyon fontos döntés előtt állsz. Igen vagy nem?");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*android.support.v7.preference.*/PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    public void openDialog(String text) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(text); // set dialog message
        dialogBuilder.setPositiveButton("igen", // set positive button behaviour
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Toast.makeText(intent,"Az igent választottad!",Toast.LENGTH_LONG).show();
                    }
                }
        );
        dialogBuilder.setNegativeButton("nem", // set negative button behaviour
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // close app
                    }
                }
        );
        AlertDialog dialog = dialogBuilder.create(); // create dialog box
        dialog.show(); // show dialog box
    }

}