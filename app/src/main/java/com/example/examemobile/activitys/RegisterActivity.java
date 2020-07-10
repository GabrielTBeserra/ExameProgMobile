/*
    Gabriel Teles - 827333
*/
package com.example.examemobile.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.examemobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Register.class";
    private FirebaseAuth user;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = findViewById(R.id.progressBar2);
        email = findViewById(R.id.edit_cad_email);
        password = findViewById(R.id.edit_cad_pass);
        user = FirebaseAuth.getInstance();
        hideProgressBar();
    }

    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void register(String email, String password) {
        user.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            hideProgressBar();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            jaRegistrado();
                        }
                    }
                });
    }

    public void registrar(View view) {
        showProgressBar();
        String emailM = email.getText().toString();
        String pass = password.getText().toString();

        if (emailM.equalsIgnoreCase("") || pass.equalsIgnoreCase("") || emailM == null || pass == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Informacoes Invalidas");
            builder.setMessage("Por favor informe seus dados corretamente");
            builder.create().show();
            hideProgressBar();
            return;
        }


        showProgressBar();

        register(emailM, pass);
    }

    private void jaRegistrado() {
        String title = "Usuario existe";
        String msg = "O usuario informado ja esta registrado";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.create().show();
        hideProgressBar();
    }
}
