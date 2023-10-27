package com.example.timemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

    }

    public void login(View v){
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPass = findViewById(R.id.editPassword);
        TextView viewStatus = findViewById(R.id.viewStatus);

        String email = editEmail.getText().toString();
        String password = editPass.getText().toString();

        if (validInputs(email, password)){
            viewStatus.setText("Attempting to log in...");
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                viewStatus.setText("Login Successful...");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public boolean validInputs(String email, String password){
        return true;
    }

    public void signUp(View v){
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    @Override
    protected void onResume(){
        super.onResume();
        TextView viewStatus = findViewById(R.id.viewStatus);
        viewStatus.setText("");
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPass = findViewById(R.id.editPassword);
        editEmail.setText("");
        editPass.setText("");
    }
}