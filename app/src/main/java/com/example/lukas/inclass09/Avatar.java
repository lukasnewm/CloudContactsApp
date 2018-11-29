package com.example.lukas.inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

//Lukas Newman

public class Avatar extends AppCompatActivity {
    ImageButton f1, f2, f3, m1, m2, m3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        setTitle("Select Avatar");


        f1 = findViewById(R.id.imageButtonf1);
        f2 = findViewById(R.id.imageButtonf2);
        f3 = findViewById(R.id.imageButtonf3);
        m1 = findViewById(R.id.imageButtonm1);
        m2 = findViewById(R.id.imageButtonm2);
        m3 = findViewById(R.id.imageButtonm3);

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("avatarID", R.drawable.avatar_f_1);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("avatarID", R.drawable.avatar_f_2);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("avatarID", R.drawable.avatar_f_3);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("avatarID", R.drawable.avatar_m_1);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("avatarID", R.drawable.avatar_m_2);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("avatarID", R.drawable.avatar_m_3);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
