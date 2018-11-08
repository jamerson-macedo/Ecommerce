package com.ecommerce.jmdevelopers.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CadastrarActivity extends AppCompatActivity {
    private EditText nome,telefone,senha;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        nome=findViewById(R.id.cadastrarnome);
        telefone=findViewById(R.id.cadastrartelefone);
        senha=findViewById(R.id.cadastrarsenha);
        cadastrar=findViewById(R.id.btt_cadastrar_activity);
    }
}
