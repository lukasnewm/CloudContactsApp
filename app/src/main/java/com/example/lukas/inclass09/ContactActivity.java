package com.example.lukas.inclass09;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//Lukas Newman

public class ContactActivity extends AppCompatActivity {

    Button logOut;
    Button createContact;
    ListView contactList;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    //DatabaseReference userRef;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setTitle("Contacts");
        contactList = findViewById(R.id.listViewContacts);
        logOut = findViewById(R.id.buttonLogOut);
        createContact = findViewById(R.id.buttonCreateContact);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = database.getReference("/Contacts/" + user.getUid());


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent contactToMain = new Intent(ContactActivity.this, MainActivity.class);
                startActivity(contactToMain);
                finish();
            }
        });

        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addContact = new Intent(ContactActivity.this, AddContact.class);
                startActivity(addContact);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(ContactActivity.this, dataSnapshot.toString(), Toast.LENGTH_LONG).show();
                //Log.d("CheckData", dataSnapshot.toString());
                final ArrayList<Contact> conList = new ArrayList<>();
                final ArrayList<String> conID = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    conList.add(snapshot.getValue(Contact.class));
                    conID.add(snapshot.getKey());
                }
                //GenericTypeIndicator<ArrayList<Contact>> t = new GenericTypeIndicator<ArrayList<Contact>>() {};
                //ArrayList<Contact> conList = dataSnapshot.getValue(t);
                ContactsAdaptor contactsAdaptor = new ContactsAdaptor(ContactActivity.this, R.layout.display_contact, conList);
                contactList.setAdapter(contactsAdaptor);

                contactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ref.child(conID.get(i)).removeValue();
                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
