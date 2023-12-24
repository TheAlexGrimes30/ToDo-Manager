package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    /*
    Класс вызова заставки приложения.
    Насследует класс AppCompatActivity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        Этот метод выполняет задержку заставки на 2 секунды,
        а затем переходит с помощью интента к MainActivity.
         */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this , MainActivity.class));
                finish();
            }
        } , 2000);
    }
}