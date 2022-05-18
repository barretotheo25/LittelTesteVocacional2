package com.example.litteltestevocacional2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaCadastro extends AppCompatActivity {
    EditText nomePessoa, emailCadastro,  senhaCadastro;
    CheckBox mostrarSenhaCadastro, receberNewsletter;
    Button pronto;
    String[] mensagens = {"Preencha todos os campos", "Cadastro realizado"};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        mostrarSenhaCadastro = findViewById(R.id.mostrarSenhaCadastro);
        receberNewsletter = findViewById(R.id.receberNewsletter);
        IniciarComponentes();

        pronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nomePessoa.getText().length() == 0) {
                    nomePessoa.setError("Você precisa inserir o seu nome para se cadastrar!");
                }
                else if (emailCadastro.getText().length() < 5) {
                    emailCadastro.setError("Você precisa inserir um email válido!");
                }
                else if (senhaCadastro.getText().length() < 8) {
                    senhaCadastro.setError("A sua senha deve ter pelo menos 8 caracteres!");
                }
                else{
                    CadastrarUsuario(v);
                }
            }
        });

    }

    public void CadastrarUsuario(View v){

        String email = emailCadastro.getText().toString();
        String senha = senhaCadastro.getText().toString();

        Intent irTelaEscolha = new Intent(this, SairIrTeste.class);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    SalvarDadosUsuario();

                    startActivity(irTelaEscolha);
                }else{
                    String erro;
                    try {
                        throw task.getException();

                    }catch(FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha com no mínimo 6 caracteres!";
                    }catch (FirebaseAuthUserCollisionException e) {
                        erro = "Esta conta de email já está cadastrada!";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email inválido!";
                    }catch (Exception e){
                        erro = "Erro ao cadastrar o usuário";
                    }

                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();


                }
            }
        });
    }

    private void SalvarDadosUsuario(){
        String nome = nomePessoa.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error", "Erro ao salvar os dados" + e.toString());
                    }
                });
    }

    private void IniciarComponentes(){
        nomePessoa = findViewById(R.id.nomePessoa);
        emailCadastro = findViewById(R.id.emailCadastro);
        senhaCadastro = findViewById(R.id.senhaCadastro);
        pronto = findViewById(R.id.pronto);
    }

    public void receberNewsLetter (View r){
        if (receberNewsletter.isChecked()){
            Toast.makeText(this, "A partir de agora você irá receber nossas NewsLetter!", Toast.LENGTH_LONG).show();
        }
    }

    public void mostrarSenha (View m){
        if (mostrarSenhaCadastro.isChecked()){
            senhaCadastro.setInputType(InputType.TYPE_CLASS_TEXT);
        }else{
            senhaCadastro.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }


}