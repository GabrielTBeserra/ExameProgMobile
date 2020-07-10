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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login.class";
    private FirebaseAuth user;
    private ProgressBar progressBar;
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_edit);
        password = findViewById(R.id.pass_edit);

        user = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.login_prog);
        hideProgressBar();
    }


    private void login(String email, String password) {
        user.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            hideProgressBar();
                            Intent intent = new Intent(LoginActivity.this, RecipeListActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            invalidCred();
                        }

                    }
                });
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

    public void logging(View view) {
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

        login(emailM, pass);
    }

    private void invalidCred() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Credenciais incorretas");
        builder.setMessage("Email ou Senha invalidos!");
        builder.create().show();
        hideProgressBar();
        return;
    }

    public void newReg(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
