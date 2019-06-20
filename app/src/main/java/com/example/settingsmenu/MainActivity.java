package com.example.settingsmenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
// import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
// import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    TextView customText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customText = findViewById(R.id.customText);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        customText.setVisibility(prefs.getBoolean("display", true) ? View.VISIBLE : View.INVISIBLE);
        customText.setTextSize(Float.parseFloat(prefs.getString("size", "15")));
        String pref_color_value = prefs.getString("color", "black");
        try{
            customText.setTextColor(Color.parseColor(pref_color_value));
        }
        catch(Exception e){
            customText.setTextColor(Color.parseColor("black")); // you can use hex (#ff0000) color or color names like "red"
        }
        // Toast.makeText(this, "wow!", Toast.LENGTH_LONG).show();

        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    // Method to set Visibility of Text.
    private void setTextVisible(boolean display) {
        customText.setVisibility(display ? View.VISIBLE : View.INVISIBLE);
    }

    // Method to set Color of Text.
    private void changeTextColor(String pref_color_value) {
        Log.d("Parzival", pref_color_value);
        try{
            customText.setTextColor(Color.parseColor(pref_color_value));
        }
        catch(Exception e){
            customText.setTextColor(Color.parseColor("black")); // you can use hex (#ff0000) color or color names like "red"
        }
    }

    // Method to set Size of Text.
    private void changeTextSize(Float i) {
        customText.setTextSize(i);
    }

    // Method to pass value from SharedPreferences
    private void loadColorFromPreference(SharedPreferences sharedPreferences) {
        Log.d("Parzival",sharedPreferences.getString(getString(R.string.pref_color_key),getString(R.string.pref_color_red_value)));
        changeTextColor(sharedPreferences.getString(getString(R.string.pref_color_key),getString(R.string.pref_color_red_value)));
    }

    private void loadSizeFromPreference(SharedPreferences sharedPreferences) {
        float minSize = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_size_key), "16.0"));
        changeTextSize(minSize);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("display")) {
            setTextVisible(sharedPreferences.getBoolean("display",true));
        } else if (key.equals("color")) {
            loadColorFromPreference(sharedPreferences);
        } else if (key.equals("size"))  {
            loadSizeFromPreference(sharedPreferences);
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