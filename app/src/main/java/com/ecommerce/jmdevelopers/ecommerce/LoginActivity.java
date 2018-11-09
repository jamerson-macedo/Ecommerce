package com.ecommerce.jmdevelopers.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class LoginActivity extends AppCompatActivity {
    private ImageView logo;
    private Button botaologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logo=findViewById(R.id.login_logo);
        botaologin=findViewById(R.id.btt_login_activity);
        Animation fadein=AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Animation subir=AnimationUtils.loadAnimation(this,R.anim.subir);
        logo.startAnimation(fadein);
        botaologin.startAnimation(subir);
        // muito top essa classe tem que importar la no git
        YoYo.with(Techniques.StandUp).duration(2000).playOn(findViewById(R.id.logintelefone));

    }
}
