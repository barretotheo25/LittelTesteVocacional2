package com.example.litteltestevocacional2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;


public class TelaTeste extends AppCompatActivity {
    CheckBox intro, extrover, introver, socia, ativo, lerdo, lider, gentil, resist, perseve, reser,
            otimi, pessimi, atento, rapido, sincero, educa, encanto, perspi, competi, autocon, ansio, amiga,
            acessi, sensi, frio, silenci, ironia, ciume, confia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_teste);
        getSupportActionBar().hide();
    }

    public void button(){

    }
}