package com.anthony.deltec.gestor;

import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Init extends AppCompatActivity {
    private LottieAnimationView animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_init);
        getSupportActionBar().hide();
        //PRIMER LAYOUT INFLADO CON UNA ANIMACIÓN LOTTI
        animation = findViewById(R.id.mySplash);
        startAnimation();
    }

    private void startAnimation() {
        animation.setAnimation(R.raw.loading);
        animation.playAnimation();
        Intent intent = new Intent(this,MainActivity.class);
        //EVENTO AL FINALIZAR LA ANIMACION
        new Handler().postDelayed(new Runnable(){
            public void run(){

                startActivity(intent);
                finish();
            }
        }, 2200);
    }
}