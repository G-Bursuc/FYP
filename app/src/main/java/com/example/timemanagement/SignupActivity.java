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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    boolean success;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView viewStatus = findViewById(R.id.viewStatus);
        viewStatus.setText("");

        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View v){
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPass = findViewById(R.id.editPassword);
        TextView viewStatus = findViewById(R.id.viewStatus);

        String email = editEmail.getText().toString();
        String password = editPass.getText().toString();

        if (validInputs(email, password)) {
            viewStatus.setText("Attempting to sign up...");

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                viewStatus.setText("Sign up successful...");

                                FirebaseUser fbUser = mAuth.getCurrentUser();

                                TaskToDo myTask = new TaskToDo("Default Task",
                                        "This is a default task", "26/10/2023", false);
                                addTaskToRTDB(myTask, fbUser.getUid());
                            } else {
                                viewStatus.setText("");
                                toastMe("Authentication failed.");
                            }
                        }
                    });
        }
    }

    public boolean validInputs(String email, String password){
        return true;
    }

    public void addTaskToRTDB(TaskToDo myTask, String userID) {
        DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference(); // get reference from root

        db.child("Tasks").child(userID).setValue(myTask).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                success = false;
                toastMe("Signup failed");
            }
        });
    }

    public void toastMe(String msg, boolean longLength){
        if (longLength){
            Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void toastMe(String msg){
        toastMe(msg, true);
    }
}