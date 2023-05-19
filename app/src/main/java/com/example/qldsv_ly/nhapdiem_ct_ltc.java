package com.example.qldsv_ly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldsv_ly.AdapterCustom.DiemAdapter;
import com.example.qldsv_ly.AdapterCustom.LopTinChiNhapDiemAdapter;
import com.example.qldsv_ly.Objects.ObjectDiem;
import com.example.qldsv_ly.Objects.ObjectLopTinChiNhapDiem;
import com.example.qldsv_ly.api.ApiManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class nhapdiem_ct_ltc extends AppCompatActivity {
    SearchView searchSV;
    Connection connect;


    RecyclerView rcvDSSV_LTC;
   List<ObjectDiem> arrDiemSV = new ArrayList<>();

    Context context;
    TextView maLTC, tenMH;

    Button btnClickback;

    String MaGiangVien = "";

    public static String maLTC_ct_ltc="", maSV_ct_ltc="", tenMH_ct_ltc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhapdiem_ct_ltc);

        Intent intent = getIntent();
        MaGiangVien = intent.getStringExtra("maGiangVien");

        setControl();
        setEvent();
        searchTaiKhoan();


        btnClickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nhapdiem_ct_ltc.this, nhapdiem.class);
                intent.putExtra("maGiangVien", MaGiangVien);
                startActivity(intent);
            }

        });
    }


        private void getDSSV(String ma) {
            arrDiemSV.clear();
            ApiManager apiManager = ApiManager.getInstance();
            Call<List<ObjectDiem>> call = apiManager.getApiService().getDSSVTheoMaLTC(ma);
            call.enqueue(new Callback<List<ObjectDiem>>() {
                @Override
                public void onResponse(Call<List<ObjectDiem>> call, Response<List<ObjectDiem>> response) {
                    if(response.body()!= null && response.isSuccessful()){
                        arrDiemSV = response.body();
                        DiemAdapter diemAdapter = new DiemAdapter(arrDiemSV, new DiemAdapter.ItemClickListener() {
                            @Override
                            public void OnItemClick(View view, ObjectDiem diem, int i) {
                                view.setBackground(getDrawable(R.color.background2nd));
                                Toast.makeText(nhapdiem_ct_ltc.this, "Item: "+arrDiemSV.get(i).getMaSV(), Toast.LENGTH_SHORT).show();
                                maLTC_ct_ltc = nhapdiem.maLTC_NhapDiem;
                                tenMH_ct_ltc = nhapdiem.tenMH_NhapDiem;
                                maSV_ct_ltc =arrDiemSV.get(i).getMaSV();
                                Intent intent = new Intent(nhapdiem_ct_ltc.this, cap_nhat_diem.class);
                                startActivity(intent);

                            }
                        });
                        rcvDSSV_LTC.setAdapter(diemAdapter);

                    }
                }

                @Override
                public void onFailure(Call<List<ObjectDiem>> call, Throwable t) {
                    Toast.makeText(nhapdiem_ct_ltc.this, "call api fail", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void setControl(){
        rcvDSSV_LTC = (RecyclerView) findViewById(R.id.rcvDSSV_LTC);
        rcvDSSV_LTC.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        rcvDSSV_LTC.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvDSSV_LTC.addItemDecoration(itemDecoration);
        maLTC=findViewById(R.id.maLTC);
        tenMH=findViewById(R.id.tenMH);
        searchSV = findViewById(R.id.searchTaiKhoan);
        btnClickback = findViewById(R.id.btnClickback);

    }
    private void setEvent(){
        maLTC.setText(nhapdiem.maLTC_NhapDiem);
        tenMH.setText(nhapdiem.tenMH_NhapDiem);
        getDSSV(nhapdiem.maLTC_NhapDiem);
    }
    public void timKiemMaSV(String kitu){

    }

    public void searchTaiKhoan() {
        searchSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty())
                {
                    getDSSV(nhapdiem.maLTC_NhapDiem);

                }
                else {
                    List<ObjectDiem> listSearchDiem = new ArrayList<>();
                    for (int i = 0; i < arrDiemSV.size(); i++) {
                        if (arrDiemSV.get(i).getMaSV().toLowerCase().contains(newText.toLowerCase())) {
                            listSearchDiem.add(arrDiemSV.get(i));

                        }
                        DiemAdapter diemAdapter = new DiemAdapter(listSearchDiem, new DiemAdapter.ItemClickListener() {
                            @Override
                            public void OnItemClick(View view, ObjectDiem diem, int i) {
                                view.setBackground(getDrawable(R.color.background2nd));
                                Toast.makeText(nhapdiem_ct_ltc.this, "Item: " + arrDiemSV.get(i).getMaSV(), Toast.LENGTH_SHORT).show();
                                maLTC_ct_ltc = nhapdiem.maLTC_NhapDiem;
                                tenMH_ct_ltc = nhapdiem.tenMH_NhapDiem;
                                maSV_ct_ltc = arrDiemSV.get(i).getMaSV();
                                Intent intent = new Intent(nhapdiem_ct_ltc.this, cap_nhat_diem.class);
                                startActivity(intent);

                            }
                        });
                        rcvDSSV_LTC.setAdapter(diemAdapter);

                    }
                }
                    return false;
                }
        });
    }


}