package com.example.qldsv_ly;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.qldsv_ly.AdapterCustom.DiemAdapter;
import com.example.qldsv_ly.Objects.ObjectDiem;
import com.example.qldsv_ly.api.ApiManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cap_nhat_diem extends AppCompatActivity {

    TextView maLTC, tenMH, maSV, tenSV;
    EditText diemCC, diemGK, diemCK;

    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_diem);

        setControl();
        setEvent();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Kiểm tra điểm có hợp lệ hay không
                if(!checkDiem(diemCC)) diemCC.setError("Điểm chuyên cần không hợp lệ!");
                else if(!checkDiem(diemGK)) diemGK.setError("Điểm giữa kì không hợp lệ!");
                else if(!checkDiem(diemCK)) diemCK.setError("Điểm cuối kì không hợp lệ!");
                else {
                    Float diemCC_Update,diemGK_Update,diemCK_Update;
                    diemCC_Update = Float.parseFloat(diemCC.getText().toString());
                    diemGK_Update = Float.parseFloat(diemGK.getText().toString());
                    diemCK_Update = Float.parseFloat(diemCK.getText().toString());

                    ApiManager apiManager = ApiManager.getInstance();
                    Call<Object> call = apiManager.getApiService().capNhatDiem(nhapdiem_ct_ltc.maLTC_ct_ltc,nhapdiem_ct_ltc.maSV_ct_ltc, diemCC_Update, diemGK_Update, diemCK_Update);
                    call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if(response.body()!= null && response.isSuccessful()){
                                alertSuccess("Cập nhật điểm cho sinh viên: " + nhapdiem_ct_ltc.maSV_ct_ltc + " thành công! ");
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(cap_nhat_diem.this, "call api CapNhatDiem fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private boolean checkDiem(EditText txt){
        Float diem;
        try {
            diem = Float.parseFloat(txt.getText().toString());
            if(diem > 10 || diem <0) return false;
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    private void setControl(){
        maLTC=findViewById(R.id.maLTC);
        tenMH=findViewById(R.id.tenMH);
        maSV = findViewById(R.id.maSV);
        tenSV = findViewById(R.id.tenSV);
        diemCC = findViewById(R.id.diemCC);
        diemGK = findViewById(R.id.diemGK);
        diemCK = findViewById(R.id.diemCK);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
    private void setEvent(){
        maLTC.setText(nhapdiem_ct_ltc.maLTC_ct_ltc);
        tenMH.setText(nhapdiem_ct_ltc.tenMH_ct_ltc);
        maSV.setText(nhapdiem_ct_ltc.maSV_ct_ltc);
        getDiemSV();
    }

    private void getDiemSV() {
        ApiManager apiManager = ApiManager.getInstance();
        Call<ObjectDiem> call = apiManager.getApiService().getSVTheoMaSVAndMaLTC(nhapdiem_ct_ltc.maLTC_ct_ltc,nhapdiem_ct_ltc.maSV_ct_ltc);
        call.enqueue(new Callback<ObjectDiem>() {
            @Override
            public void onResponse(Call<ObjectDiem> call, Response<ObjectDiem> response) {
                if(response.body()!= null && response.isSuccessful()){
                    //objDiem = response.body();
                    System.out.println("DIEM"+ response.body().getDiemCC() +" "+ response.body().getDiemCK()) ;
                    diemCC.setText(String.valueOf(response.body().getDiemCC()));
                    diemGK.setText(String.valueOf(response.body().getDiemGK()));
                    diemCK.setText(String.valueOf(response.body().getDiemCK()));
                }
            }

            @Override
            public void onFailure(Call<ObjectDiem> call, Throwable t) {
                Toast.makeText(cap_nhat_diem.this, "call api CapNhatDiem fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void alertSuccess(String content) {
        AlertDialog.Builder bulider = new AlertDialog.Builder(cap_nhat_diem.this);
        bulider.setMessage(content);
        bulider.setCancelable(true);
        bulider.setPositiveButton("Đồng ý",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(cap_nhat_diem.this, nhapdiem_ct_ltc.class);
                intent.putExtra("maGiangVien", main_giangvien.maGV);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = bulider.create();
        alert.show();
    }

}