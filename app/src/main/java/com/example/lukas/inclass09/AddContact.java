package com.example.lukas.inclass09;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Lukas Newman

public class AddContact extends AppCompatActivity {
    EditText editName;
    EditText editEmail;
    EditText editPhone;
    ImageButton imageButtonAvatar;
    Button buttonSubmit;
    String name;
    String email;
    String phone;
    String dept;
    int avatarID;
    RadioGroup deptSelect;
    boolean pickedAvater = false;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        setTitle("Add Contact");
        //avatarID = R.drawable.select_avatar;

        user = FirebaseAuth.getInstance().getCurrentUser();
        //Log.d("FireBaseUserID", user.getUid());

        ref =  database.getReference("Contacts");
        editEmail = findViewById(R.id.editTextAddContactEmail);
        editName = findViewById(R.id.editTextAddContactName);
        editPhone = findViewById(R.id.editTextAddContactPhone);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        imageButtonAvatar = findViewById(R.id.imageButtonAvatar);
        deptSelect = findViewById(R.id.ragGroupDept);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editName.getText().toString().trim();
                email = editEmail.getText().toString().trim();
                phone = editPhone.getText().toString().trim();
                dept = "Computer Science";

                if (deptSelect.getCheckedRadioButtonId() == R.id.radioButtonSIS) {
                    dept = "SIS";
                } else if (deptSelect.getCheckedRadioButtonId() == R.id.radioButtonCS) {
                    dept = "CS";
                } else if (deptSelect.getCheckedRadioButtonId() == R.id.radioButtonBIO) {
                    dept = "BIO";
                }


                if(!(name.length() > 0)){
                    Toast.makeText(AddContact.this, "Enter a Name", Toast.LENGTH_SHORT).show();
                } else if (!(email.length() > 0)) {
                    Toast.makeText(AddContact.this, "Enter a Email", Toast.LENGTH_SHORT).show();
                } else if (!(phone.length() > 0)) {
                    Toast.makeText(AddContact.this, "Enter a Phone", Toast.LENGTH_SHORT).show();
                } else if (deptSelect.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(AddContact.this, "Select a department", Toast.LENGTH_SHORT).show();
                } else if (pickedAvater == false) {
                    Toast.makeText(AddContact.this, "Select an Avatar", Toast.LENGTH_SHORT).show();
                } else {
                    Contact addContact = new Contact(name, email, phone, dept, avatarID);
                    DatabaseReference addRef = ref.child(user.getUid().toString());

                    //Add value to firebase
                    addRef.push().setValue(addContact);

                    addRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Toast.makeText(AddContact.this, "Added Contact", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(AddContact.this, "Error try again", Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });

        imageButtonAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickAvatar = new Intent(AddContact.this, Avatar.class);
                startActivityForResult(pickAvatar, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(100 == requestCode && resultCode == RESULT_OK)
        {
            avatarID = data.getExtras().getInt("avatarID");
            imageButtonAvatar.setBackgroundResource(avatarID);
            pickedAvater = true;
        }
    }
}
