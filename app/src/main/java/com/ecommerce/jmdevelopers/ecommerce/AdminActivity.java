package com.ecommerce.jmdevelopers.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toast.makeText(AdminActivity.this, "TELA DE ADM", Toast.LENGTH_SHORT).show();

    }
}
