package com.ecommerce.jmdevelopers.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {
    private String nomecategoria, preco, descricao, nomeproduto, savedataatual, savehoraatual;
    private Button botaoadd;
    private ImageView galeria;
    private EditText inputnome, inputdescricao, inputpreco;
    private static final int PEGARDAGALERIA = 1;
    private Uri imagem;
    private ProgressDialog progressDialog;
    private String chaveprodutoaleatoria, imagemURL;
    private StorageReference imagemref;
    private DatabaseReference produtosref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        botaoadd = findViewById(R.id.botaoadd);
        galeria = findViewById(R.id.galeria);
        inputnome = findViewById(R.id.nomeproduto);
        inputdescricao = findViewById(R.id.descricao);
        inputpreco = findViewById(R.id.precoproduto);

        nomecategoria = getIntent().getExtras().get("categoria").toString();
        imagemref = FirebaseStorage.getInstance().getReference().child("produtos imagens");
        produtosref = FirebaseDatabase.getInstance().getReference().child("produtos");
        progressDialog = new ProgressDialog(this);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirgaleria();
            }
        });
        botaoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validardadosproduto();
            }
        });


    }

    private void validardadosproduto() {
        descricao = inputdescricao.getText().toString();
        nomeproduto = inputnome.getText().toString();
        preco = inputpreco.getText().toString();
        if (imagem != null) {
            if (!TextUtils.isEmpty(descricao)) {//verifica nome
                if (!TextUtils.isEmpty(nomeproduto)) {//verifica e-mail
                    if (!TextUtils.isEmpty(preco)) {


                        salvarinformacoesdoproduto();

                    } else {
                        Toast.makeText(AddProductActivity.this,
                                "Preencha o preço!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddProductActivity.this,
                            "Preencha o nome do produto!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddProductActivity.this,
                        "Preencha a descrição!",
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(AddProductActivity.this,
                    "Insira uma foto!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void salvarinformacoesdoproduto() {
        progressDialog.setTitle("Adicionando Produto ");
        progressDialog.setMessage("Aguarde um momento");
        progressDialog.setCanceledOnTouchOutside(false);
        // aquiele chama e quando pega o dismiss ai ele para
        progressDialog.show();


        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dataatual = new SimpleDateFormat("MMM ddd, yyyy ");
        savedataatual = dataatual.format(calendar.getTime());
        SimpleDateFormat horaaatual = new SimpleDateFormat("HH:mm:ss");
       savehoraatual = horaaatual.format(calendar.getTime());
        chaveprodutoaleatoria = savedataatual + savehoraatual;
        final StorageReference caminhoarquivo = imagemref.child(imagem.getLastPathSegment() + chaveprodutoaleatoria + ".jpg");
        final UploadTask uploadTask = caminhoarquivo.putFile(imagem);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String erro = e.toString();
                Toast.makeText(AddProductActivity.this,
                        "Erro !" + erro,
                        Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddProductActivity.this, "Sucesso ao fazer upload de imagem",
                        Toast.LENGTH_SHORT).show();
                Task<Task<Uri>> uritask = uploadTask.continueWith(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        progressDialog.dismiss();
                        imagemURL = caminhoarquivo.getDownloadUrl().toString();
                        return caminhoarquivo.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Task<Uri>>() {
                    @Override
                    public void onComplete(@NonNull Task<Task<Uri>> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            imagemURL = task.getResult().toString();
                            Toast.makeText(AddProductActivity.this, "Sucesso ao salvar no banco de dados", Toast.LENGTH_LONG).show();
                            salvarprodutonofirebase();
                        }
                    }

                });
            }
        });

    }
    private void abrirgaleria() {

        Intent galeriaintent = new Intent();
        galeriaintent.setAction(Intent.ACTION_GET_CONTENT);
        galeriaintent.setType("image/*");
        startActivityForResult(galeriaintent, PEGARDAGALERIA);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PEGARDAGALERIA && resultCode == RESULT_OK && data != null) {
            imagem = data.getData();
            galeria.setImageURI(imagem);
        }
        else{

            Toast.makeText(this,"Problema ao pegar foto",Toast.LENGTH_LONG).show();
        }
    }
    private void salvarprodutonofirebase() {
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("pid", chaveprodutoaleatoria);
        objectHashMap.put("date", savedataatual);
        objectHashMap.put("time", savehoraatual);
        objectHashMap.put("imagem", imagemURL);
        objectHashMap.put("categoria", nomecategoria);
        objectHashMap.put("preco", preco);
        objectHashMap.put("pnome", nomeproduto);
        produtosref.child(chaveprodutoaleatoria).updateChildren(objectHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddProductActivity.this, "Produto adicionado com sucesso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddProductActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else {
                    String erro = task.getException().toString();
                    Toast.makeText(AddProductActivity.this, "Erro ! " + erro, Toast.LENGTH_LONG).show();

                }

            }
        });


    }
}


