package com.ecommerce.jmdevelopers.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ecommerce.jmdevelopers.ecommerce.Assistencia.DadosOnline;
import com.ecommerce.jmdevelopers.ecommerce.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button cadastrar, login;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cadastrar = findViewById(R.id.btt_cadastrar);
        login = findViewById(R.id.btt_login);
        progressDialog = new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CadastrarActivity.class);
                startActivity(i);
            }
        });

        Paper.init(this);
        String userphone = Paper.book().read(DadosOnline.telefonedousuario);
        String usersenha = Paper.book().read(DadosOnline.senhadousuario);
        if (userphone != "" && usersenha != "") {
            if (!TextUtils.isEmpty(userphone) && !TextUtils.isEmpty(usersenha)) {
                progressDialog.setTitle("Logando conta ");
                progressDialog.setMessage("Aguarde um momento");
                progressDialog.setCanceledOnTouchOutside(false);
                // aquiele chama e quando pega o dismiss ai ele para
                //progressDialog.show();
                permitiracesso(userphone, usersenha);

            }


        }
    }

    private void permitiracesso(final String telefonedigitado, final String senhadigitado) {
        final DatabaseReference raiz;
        raiz = ConfiguracaoFirebase.getFirebaseDatabase();
        raiz.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("users").child(telefonedigitado).exists()) {

                    User dadosusuario = dataSnapshot.child("users").child(telefonedigitado).getValue(User.class);
                    if (dadosusuario.getTelefone().equals(telefonedigitado)) {
                        if (dadosusuario.getSenha().equals(senhadigitado)) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,
                                    "Logado com sucesso",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,
                                    "Senha Diferente ",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,
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
