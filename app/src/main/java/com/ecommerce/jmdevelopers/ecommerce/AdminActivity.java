package com.ecommerce.jmdevelopers.ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    private ImageView camisas,esporte,vestido,sweater,oculos,bolsa,chapeu,tenis,fone,notebook,relogio,telefone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toast.makeText(AdminActivity.this, "TELA DE ADM", Toast.LENGTH_SHORT).show();
        inicializar();
        camisas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","camisas");
                startActivity(intent);
            }
        });
        esporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","esporte");
                startActivity(intent);
            }
        });
        vestido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","vestido");
                startActivity(intent);
            }
        });
        sweater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","sweater");
                startActivity(intent);
            }
        });
        oculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","oculos");
                startActivity(intent);
            }
        });
        bolsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","bolsa");
                startActivity(intent);
            }
        });
        chapeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","chapeu");
                startActivity(intent);
            }
        });
        tenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","tenis");
                startActivity(intent);
            }
        });
        fone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","fone");
                startActivity(intent);
            }
        });
        notebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","notebook");
                startActivity(intent);
            }
        });
        relogio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","relogio");
                startActivity(intent);
            }
        });
        telefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddProductActivity.class);
                intent.putExtra("categoria","celular");
                startActivity(intent);
            }
        });





    }
    public void inicializar(){
        camisas=findViewById(R.id.camisas);
        esporte=findViewById(R.id.esporte);
        vestido=findViewById(R.id.vestido_feminino);
        sweater=findViewById(R.id.sweather);
        oculos=findViewById(R.id.oculos);
        bolsa=findViewById(R.id.bolsas);
        chapeu=findViewById(R.id.chapeu);
        tenis=findViewById(R.id.tenis);
        fone=findViewById(R.id.fones);
        notebook=findViewById(R.id.notebook);
        relogio=findViewById(R.id.relogio);
        telefone=findViewById(R.id.celular);


    }
}
