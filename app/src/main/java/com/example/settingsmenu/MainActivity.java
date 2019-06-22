package com.example.settingsmenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
// import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    TextView customText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"tomi.krisko@gmail.com"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "I have found a bug in your app...");
        // emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        emailIntent.putExtra(Intent.EXTRA_PACKAGE_NAME, "com.google.android.gm");
        // You can also attach multiple items by passing an ArrayList of Uris

        startActivity(emailIntent);
        */

        customText = findViewById(R.id.customText);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadTextDisplayFromPref(sharedPreferences);
        loadTextToDisplayFromPref(sharedPreferences);
        loadTextSizeFromPref(sharedPreferences);
        loadTextColorFromPref(sharedPreferences);

        setupSharedPreferences();
    }

    public void btnClick(View view){
        CustomDialogClass cdd = new CustomDialogClass(this);
        cdd.show();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadTextDisplayFromPref(SharedPreferences sharedPreferences) {
        customText.setVisibility(sharedPreferences.getBoolean("key_text_display", Boolean.parseBoolean(getString(R.string.pref_text_display_def))) ? View.VISIBLE : View.INVISIBLE);
    }

    private void loadTextToDisplayFromPref(SharedPreferences sharedPreferences) {
        customText.setText(sharedPreferences.getString("key_text_to_display", getString(R.string.pref_text_to_display_def)));
    }

    private void loadTextSizeFromPref(SharedPreferences sharedPreferences) {
        String pref_size_value = sharedPreferences.getString("key_text_size", getString(R.string.pref_text_size_def));
        try{
            customText.setTextSize(Float.parseFloat(pref_size_value));
        }
        catch(Exception e){
            customText.setTextSize(Float.parseFloat(getString(R.string.pref_text_size_def)));
            Toast.makeText(getApplicationContext(), "The text size has been set to default due to an unexpected exception", Toast.LENGTH_LONG).show();
        }
    }

    private void loadTextColorFromPref(SharedPreferences sharedPreferences) {
        String pref_color_value = sharedPreferences.getString("key_text_color", getString(R.string.pref_text_color_def));
        try{
            customText.setTextColor(Color.parseColor(pref_color_value));
        }
        catch(Exception e){
            customText.setTextColor(Color.parseColor(getString(R.string.pref_text_color_def))); // you can use hex (#ff0000) color or color names like "red"
            Toast.makeText(getApplicationContext(), "The text color has been set to default due to an unexpected exception", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // System.out.println("\nOUTPUT TO CONSOLE\n");
        /*
        Toast toast = Toast.makeText(getApplicationContext(), "Üzenet!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 80, 0);
        toast.show();
        */
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

        if (key.equals("key_text_display")) {
            loadTextDisplayFromPref(sharedPreferences);
        } else if (key.equals("key_text_to_display")) {
            loadTextToDisplayFromPref(sharedPreferences);
        } else if (key.equals("key_text_size"))  {
            loadTextSizeFromPref(sharedPreferences);
        } else if (key.equals("key_text_color")) {
            loadTextColorFromPref(sharedPreferences);
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