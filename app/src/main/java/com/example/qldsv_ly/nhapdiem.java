package com.example.qldsv_ly;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldsv_ly.Objects.ObjectLopTinChiNhapDiem;
import com.example.qldsv_ly.AdapterCustom.LopTinChiNhapDiemAdapter;
import com.example.qldsv_ly.api.ApiManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class nhapdiem extends AppCompatActivity {
    Spinner cbNienKhoa, cbHocKy;
    List<String> arrNienKhoa = new ArrayList<>();
    ArrayList<String> arrHocKy = new ArrayList<String>();

    RecyclerView rcv_LTC;

    Button btnClickback;
    SearchView searchLTC;
    List<ObjectLopTinChiNhapDiem> arrLopTinChiNhapDiem = new ArrayList<>();

    String NienKhoa="All", HocKy="All";


    public String MaGiangVien="";
    public static String maLTC_NhapDiem="",tenMH_NhapDiem="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhapdiem);

        Intent intent = getIntent();
        MaGiangVien = intent.getStringExtra("maGiangVien");
        System.out.println("magv"+ MaGiangVien);
        setControl();
        setEvent();
        searchMonHoc();

        ArrayAdapter arrayAdapterNienKhoa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrNienKhoa);
        cbNienKhoa.setAdapter(arrayAdapterNienKhoa);
        arrayAdapterNienKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterHocKy = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrHocKy);
        cbHocKy.setAdapter(arrayAdapterHocKy);
        arrayAdapterHocKy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        cbNienKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                NienKhoa = arrNienKhoa.get(i);
                System.out.println("aaaaa"+ arrNienKhoa.get(i));
                getDSLTC();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cbHocKy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HocKy= arrHocKy.get(i);
                getDSLTC();
           //     lopTinChiNhapDiemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnClickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nhapdiem.this, main_giangvien.class);
                intent.putExtra("maGiangVien", MaGiangVien);
                startActivity(intent);
            }

        });
        }
    private void setControl(){
        cbNienKhoa=(Spinner) findViewById(R.id.nien_khoa_spinner);
        cbHocKy=(Spinner) findViewById(R.id.hoc_ky_spinner);
        rcv_LTC = (RecyclerView) findViewById(R.id.rcvListLTC);
        rcv_LTC.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        rcv_LTC.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv_LTC.addItemDecoration(itemDecoration);

        btnClickback = findViewById(R.id.btnClickback);
        searchLTC = findViewById(R.id.searchTaiKhoan);
    }
    private void setEvent(){
        KhoiTaoNienKhoa();
        KhoiTaoHocKi();

        getDSLTC();


    }

    public void searchMonHoc() {
        searchLTC.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty())
                {
                    getDSLTC();
                }
                else {

                    cbHocKy.setSelection(0);
                    cbNienKhoa.setSelection(0);
                    List<ObjectLopTinChiNhapDiem> listLTCSearch = new ArrayList<>();
                    for (int i = 0; i < arrLopTinChiNhapDiem.size(); i++) {
                        if (arrLopTinChiNhapDiem.get(i).getTenMH().toLowerCase().contains(newText.toLowerCase()) || arrLopTinChiNhapDiem.get(i).getMaLTC().toLowerCase().contains(newText.toLowerCase())) {
                            listLTCSearch.add(arrLopTinChiNhapDiem.get(i));

                        }
                    }
                    LopTinChiNhapDiemAdapter ltcAdapter = new LopTinChiNhapDiemAdapter(listLTCSearch, new LopTinChiNhapDiemAdapter.ItemClickListener() {
                        @Override
                        public void OnItemClick(View view, ObjectLopTinChiNhapDiem ltc, int i) {
                            view.setBackground(getDrawable(R.color.background2nd));
                            Toast.makeText(nhapdiem.this, "Item: " + arrLopTinChiNhapDiem.get(i).getMaLTC(), Toast.LENGTH_SHORT).show();
                            maLTC_NhapDiem =arrLopTinChiNhapDiem.get(i).getMaLTC();
                            tenMH_NhapDiem=arrLopTinChiNhapDiem.get(i).getTenMH();
                            Intent intent = new Intent(nhapdiem.this, nhapdiem_ct_ltc.class);
                            intent.putExtra("maGiangVien", MaGiangVien);
                            intent.putExtra("maLTC", arrLopTinChiNhapDiem.get(i).getMaLTC());
                            startActivity(intent);

                        }
                    });
                    rcv_LTC.setAdapter(ltcAdapter);
                }
                return false;
            }
        });
    }
    private void KhoiTaoNienKhoa(){
        arrNienKhoa.clear();
        arrNienKhoa.add("All");
        ApiManager apiManager = ApiManager.getInstance();
        Call<List<String>> call = apiManager.getApiService().getDSNamHocTheoLTC();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.body()!= null && response.isSuccessful()){
                    for(int i = 0 ; i < response.body().size() ; i++){
                        arrNienKhoa.add(response.body().get(i));

                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(nhapdiem.this, "call api fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void KhoiTaoHocKi(){
        arrHocKy.add("All");
        arrHocKy.add("HK1");
        arrHocKy.add("HK2");
    }

    private void getDSLTC() {

        arrLopTinChiNhapDiem.clear();
        ApiManager apiManager = ApiManager.getInstance();
        Call<List<ObjectLopTinChiNhapDiem>> call = apiManager.getApiService().getDSLTCTheoMaGV(MaGiangVien, NienKhoa, HocKy);        call.enqueue(new Callback<List<ObjectLopTinChiNhapDiem>>() {
            @Override
            public void onResponse(Call<List<ObjectLopTinChiNhapDiem>> call, Response<List<ObjectLopTinChiNhapDiem>> response) {
                if(response.body()!= null && response.isSuccessful()){
                    arrLopTinChiNhapDiem = response.body();
                    LopTinChiNhapDiemAdapter ltcAdapter = new LopTinChiNhapDiemAdapter(arrLopTinChiNhapDiem, new LopTinChiNhapDiemAdapter.ItemClickListener() {
                        @Override
                        public void OnItemClick(View view, ObjectLopTinChiNhapDiem ltc, int i) {
                            view.setBackground(getDrawable(R.color.background2nd));
                            Toast.makeText(nhapdiem.this, "Item: " + arrLopTinChiNhapDiem.get(i).getMaLTC(), Toast.LENGTH_SHORT).show();
                            maLTC_NhapDiem =arrLopTinChiNhapDiem.get(i).getMaLTC();
                            tenMH_NhapDiem=arrLopTinChiNhapDiem.get(i).getTenMH();
                            Intent intent = new Intent(nhapdiem.this, nhapdiem_ct_ltc.class);
                            intent.putExtra("maGiangVien", MaGiangVien);
                            intent.putExtra("maLTC", arrLopTinChiNhapDiem.get(i).getMaLTC());
                            startActivity(intent);

                        }
                    });
                    rcv_LTC.setAdapter(ltcAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<ObjectLopTinChiNhapDiem>> call, Throwable t) {
                Toast.makeText(nhapdiem.this, "call api fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

