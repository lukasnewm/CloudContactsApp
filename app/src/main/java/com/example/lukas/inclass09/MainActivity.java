package com.example.lukas.inclass09;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//Lukas Newman

public class MainActivity extends AppCompatActivity {

    Button buttonLogIn;
    Button buttonSignUp;
    String email, password;
    EditText editEmail, editPassword;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();;
    DatabaseReference ref;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Log In");
        mAuth = FirebaseAuth.getInstance();
        ref = database.getReference("TestData");

        buttonLogIn = findViewById(R.id.buttonLoginLogin);
        buttonSignUp = findViewById(R.id.buttonLoginSignUp);
        editEmail = findViewById(R.id.editTextlogingEmail);
        editPassword = findViewById(R.id.editTextLoginPassword);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //testSendRecieveData();
                email = editEmail.getText().toString().trim();
                password = editPassword.getText().toString().trim();
                if (email.length() > 0 && password.length() > 0) {
                    signInUser();
                } else {
                    Toast.makeText(MainActivity.this, "Enter a Username and/or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInToSignUp = new Intent (MainActivity.this, SignUp.class);
                startActivity(logInToSignUp);
            }
        });

    }

    public void signInUser() {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent mainToContList = new Intent(MainActivity.this, ContactActivity.class);
                    startActivity(mainToContList);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //This method was a test to send and check data from firebase
    public void testSendRecieveData(){
        //This method is to test that a contact gets sent to firebase and retrieved;
        Contact testContact = new Contact("Jack Deer",  "Jack@Deer.com", "704-867-5309", "Computer Science", R.drawable.awesome);

        //ArrayList<Contact> testContactList = new ArrayList<>();
        //testContactList.add(testContact);
        DatabaseReference testRef = ref.child("TestContacts");
        testRef.setValue(testContact);

        testRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Contact value = dataSnapshot.getValue(Contact.class);
                Toast.makeText(MainActivity.this, value.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseTest", "Error");
            }
        });






    }
}

/**
 * //Declared Globally
 * DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
 * DatabaseReference mConditionRef = mRootRef.child("condition");
 *
 *
 *
 * //inside of onStart()
 * protectected void onStart() {
 * 	super.onStart();
 *
 * 	//this listens to our database
 * 	mConditionRef.addValueEventListener(new ValueEventListener() {
 *
 * 	//whenever the data on the server changes
 *        @Override
 * 	public void onDataChange(DataSnapshot dataSnapshot) {
 *
 *
 * 	}
 *
 * 	//If there is ever an error
 * 	@Override
 * 	public void onCancelled(DatabaseError databaseError) {
 *
 * 	}
 *     });
 *
 *
 * 	mButtonSunny.setOnClickListener(new View.OnClickListener() {
 *
 * 		@Override
 * 		public void onClick(View view) {
 * 			//This updates the value on the database
 * 			mConditionRef.setValue("Sunny");
 * 		}
 * 	});
 *
 * 	mButtonFoggy.setOnClickListener(new View.OnClickListener() {
 *
 * 		@Override
 * 		public void onClick(View view) {
 * 			mConditionRef.setValue("Foggy");
 * 		}
 * 	});
 * }
 */
