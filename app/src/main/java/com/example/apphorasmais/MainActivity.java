package com.example.apphorasmais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author Thiago Ferreira Assumpção
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                carregaTelaLogin();
            }
        }, 200);
    }

    protected void carregaTelaLogin() {
        Intent i = new Intent(MainActivity.this, Login.class);
        startActivity(i);
        finish();
    }
}