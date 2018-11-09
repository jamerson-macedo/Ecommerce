package com.ecommerce.jmdevelopers.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ecommerce.jmdevelopers.ecommerce.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private ImageView logo;
    private EditText campotelefone;
    private EditText camposenha;
    private ProgressDialog progressDialog;

    private Button botaologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logo = findViewById(R.id.login_logo);
        botaologin = findViewById(R.id.btt_login_activity);
        camposenha = findViewById(R.id.loginsenha);
        campotelefone = findViewById(R.id.logintelefone);
        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation subir = AnimationUtils.loadAnimation(this, R.anim.subir);
        logo.startAnimation(fadein);
        botaologin.startAnimation(subir);
        // muito top essa classe tem que importar la no git
        YoYo.with(Techniques.Hinge).duration(2000).playOn(findViewById(R.id.logintelefone));
        progressDialog = new ProgressDialog(this);
        botaologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logarusuario();
            }
        });


    }

    private void logarusuario() {
        String telefonedigitado = campotelefone.getText().toString();
        String senhadigitado = camposenha.getText().toString();

        if (!TextUtils.isEmpty(telefonedigitado)) {//verifica e-mail
            if (!TextUtils.isEmpty(senhadigitado)) {

                progressDialog.setTitle("Logando conta ");
                progressDialog.setMessage("Aguarde um momento");
                progressDialog.setCanceledOnTouchOutside(false);
                // aquiele chama e quando pega o dismiss ai ele para
                progressDialog.show();
                validarlogin(telefonedigitado, senhadigitado);

            } else {
                Toast.makeText(CadastrarActivity.this,
                        "Preencha a senha!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CadastrarActivity.this,
                    "Preencha o telefone!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void validarlogin(final String telefonedigitado, final String senhadigitado) {

        final DatabaseReference raiz;
        raiz = ConfiguracaoFirebase.getFirebaseDatabase();
        raiz.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("users").child(telefonedigitado).exists()) {

                    User dadosusuario=dataSnapshot.child("users").child(telefonedigitado).getValue(User.class);
                    if(dadosusuario.getTelefone().equals(telefonedigitado)){
                        if(dadosusuario.getSenha().equals(senhadigitado)){
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,
                                    "Logado com sucesso",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,
                                    "Senha Diferente ",
                                    Toast.LENGTH_SHORT).show();

                        }



                    }


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,
                            "Usuario não cadastrado",
                            Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
