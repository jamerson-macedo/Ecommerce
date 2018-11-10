package com.ecommerce.jmdevelopers.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {
    private String nomecategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        nomecategoria=getIntent().getExtras().get("categoria").toString();
        Toast.makeText(AddProductActivity.this,nomecategoria,Toast.LENGTH_LONG).show();
    }
}
