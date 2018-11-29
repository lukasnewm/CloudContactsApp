package com.example.lukas.inclass09;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

//Lukas Newman

public class SignUp extends AppCompatActivity {

    EditText FName;
    EditText LName;
    EditText Email;
    EditText Password;
    EditText Password2;
    Button signUp;
    Button cancel;

    String first, last, email, pass, pass2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        mAuth = FirebaseAuth.getInstance();

        FName = findViewById(R.id.editTextSignupFirstName);
        LName = findViewById(R.id.editTextSignUpLastName);
        Email = findViewById(R.id.editTextSignUpEmail);
        Password = findViewById(R.id.editTextSignUpPassword);
        Password2 = findViewById(R.id.editTextSignUpPasswordConfirm);
        signUp = findViewById(R.id.buttonSignUpSignUp);
        cancel = findViewById(R.id.buttonSignUpCancel);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first = FName.getText().toString().trim();
                last = LName.getText().toString().trim();
                email = Email.getText().toString().trim();
                pass = Password.getText().toString().trim();
                pass2 = Password2.getText().toString().trim();

                if (!(pass.equals(pass2)) || !(pass.length() > 0)) {
                    Toast.makeText(SignUp.this, "Passwords Must Match", Toast.LENGTH_SHORT).show();
                } else if (!(first.length() > 0)) {
                    Toast.makeText(SignUp.this, "Enter A First Name", Toast.LENGTH_SHORT).show();
                } else if (!(last.length() > 0)) {
                    Toast.makeText(SignUp.this, "Enter A Last Name", Toast.LENGTH_SHORT).show();
                } else if (!(email.length() > 0)) {
                    Toast.makeText(SignUp.this, "Enter An Email", Toast.LENGTH_SHORT).show();
                } else {
                    //Succesful
                    signUpUser();
                    //Toast.makeText(SignUp.this, "Created Account", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void signUpUser() {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    User userClass = new User(first, last, email);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "New Account Registered!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Intent signUpToContList = new Intent(SignUp.this, ContactActivity.class);
                    signUpToContList.putExtra("fireUser", user);
                    startActivity(signUpToContList);
                    finish();
                } else {
                    Toast.makeText(SignUp.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    Log.d("FirebaseAppError", task.getException().getMessage().toString());
                }
            }
        });
    }
}
