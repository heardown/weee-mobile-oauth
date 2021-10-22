package com.sayweee.oauth.demo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sayweee.logger.Logger;
import com.sayweee.oauth.OAuth;
import com.sayweee.oauth.iml.OAuthBean;
import com.sayweee.oauth.iml.OAuthCallback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String client_id = "567937503453";
        String client_secret = "Xm7Kn7Wr2LoAM2c7O9Tn1XLtuTs87i33";
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OAuth.shared().setClientId(client_id).isTest(true)
                        .setOAuthCallback(new OAuthCallback() {
                            @Override
                            public void onResult(boolean success, OAuthBean data) {
                                Logger.json(data);
                                Snackbar.make(view, data.code + " " + data.isSuccess(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        })
                        .doOAuthWeee(MainActivity.this, true, client_secret);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OAuth.shared().onActivityResult(requestCode, resultCode, data);
    }
}