package com.ecommerce.jmdevelopers.ecommerce;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CadastrarActivity extends AppCompatActivity {
    private EditText nome, telefone, senha;
    private Button botaocadastrar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        nome = findViewById(R.id.cadastrarnome);
        telefone = findViewById(R.id.cadastrartelefone);
        senha = findViewById(R.id.cadastrarsenha);
        botaocadastrar = findViewById(R.id.btt_cadastrar_activity);
        progressDialog = new ProgressDialog(this);
        // CADASTRAR
        botaocadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarconta();
            }
        });
    }

    private void criarconta() {
        // primeiro pegar os dados
        String nomedigitado = nome.getText().toString();
        String telefonedigitado = telefone.getText().toString();
        String senhadigitado = senha.getText().toString();
        if (!TextUtils.isEmpty(nomedigitado)) {//verifica nome
            if (!TextUtils.isEmpty(telefonedigitado)) {//verifica e-mail
                if (!TextUtils.isEmpty(senhadigitado)) {

                    progressDialog.setTitle("Criando conta ");
                    progressDialog.setMessage("Aguarde um momento");
                    progressDialog.setCanceledOnTouchOutside(false);
                    // aquiele chama e quando pega o dismiss ai ele para
                    progressDialog.show();
                    validarusuario(nomedigitado, telefonedigitado, senhadigitado);

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
        } else {
            Toast.makeText(CadastrarActivity.this,
                    "Preencha o nome!",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void validarusuario(final String nomedigitado, final String telefonedigitado, final String senhadigitado) {
        final DatabaseReference raiz;
        raiz = ConfiguracaoFirebase.getFirebaseDatabase();
        raiz.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // se o usuario nao existir...
                if (!dataSnapshot.child("users").child(telefonedigitado).exists()) {

                    HashMap<String, Object> userhashmap = new HashMap<>();
                    userhashmap.put("telefone", telefonedigitado);
                    userhashmap.put("senha", senhadigitado);
                    userhashmap.put("nome", nomedigitado);
                    raiz.child("users").child(telefonedigitado).updateChildren(userhashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CadastrarActivity.this,
                                        "Conta cadastrada com sucesso !",
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();


                            } else {
                                String excecao = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    excecao = "Digite uma senha mais forte!";
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    excecao = "Por favor, digite um e-mail válido";
                                } catch (FirebaseAuthUserCollisionException e) {
                                    excecao = "Este conta já foi cadastrada";
                                } catch (Exception e) {
                                    excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                    e.printStackTrace();
                                }

                                Toast.makeText(CadastrarActivity.this,
                                        excecao,
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                Intent intent = new Intent(CadastrarActivity.this, MainActivity.class);
                                startActivity(intent);


                            }

                        }
                    });
                } else {
                    Toast.makeText(CadastrarActivity.this,
                            "O Numero " + telefonedigitado + "Ja esta cadastrado !",
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Toast.makeText(CadastrarActivity.this,
                            "Tente com outro numero!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastrarActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
