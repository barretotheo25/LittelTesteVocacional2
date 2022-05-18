package com.example.litteltestevocacional2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SairIrTeste extends AppCompatActivity {
    Button irTeste, saiApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sair_ir_teste);
        irTeste = findViewById(R.id.irTeste);
        saiApp = findViewById(R.id.sairApp);
    }

    public void irTeste(View p){
        Intent irTeste = new Intent(this, TelaTeste.class);
        startActivity(irTeste);
    }
    public void sairDoApp(View a){
        finishAffinity();
    }
}