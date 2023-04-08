package com.example.qldsv_ly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

public class main_giangvien extends AppCompatActivity {
    Button btnQlDiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_giangvien);

        btnQlDiem = findViewById(R.id.btnQLDiem);
        btnQlDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_giangvien.this, nhapdiem.class);
                startActivity(intent);
                finish();
            }
        });
    }
}