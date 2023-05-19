package com.example.qldsv_ly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.qldsv_ly.AdapterCustom.LopTinChiNhapDiemAdapter;
import com.example.qldsv_ly.Objects.ObjectLopTinChiNhapDiem;
import com.example.qldsv_ly.api.ApiManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class main_giangvien extends AppCompatActivity {
    Button btnQlDiem, btnThongBao, btnDangXuat;


    public static String maGV = "GV1";
    public static String tenGV = "";
    TextView txtTentk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_giangvien);



        //      Ánh xạ
        setControl();

        //      Click
        setEvent();
        loadThongTinGV(maGV);

    }

    private void setEvent() {
        btnQlDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_giangvien.this, nhapdiem.class);
                intent.putExtra("maGiangVien", maGV);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setControl() {
        btnQlDiem = findViewById(R.id.btnQLDiem);
        btnThongBao = findViewById(R.id.btnThongBao);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        txtTentk = findViewById(R.id.tentk);
    }
    public void loadThongTinGV(String ma) {
        ApiManager apiManager = ApiManager.getInstance();
        Call<String> call = apiManager.getApiService().getTTGVTheoMaGV(ma);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!= null && response.isSuccessful()){
                    tenGV = response.body();
                    txtTentk.setText("Xin chao " + tenGV);

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(main_giangvien.this, "call api getTTGV fail", Toast.LENGTH_SHORT).show();
            }
        });

    }
}