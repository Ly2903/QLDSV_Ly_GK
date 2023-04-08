package com.example.qldsv_ly;

import android.animation.ObjectAnimator;
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

import com.example.qldsv_ly.AdapterCustom.LopTinChiNhapDiemAdapter;
import com.example.qldsv_ly.Objects.ObjectLopTinChiNhapDiem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class nhapdiem extends AppCompatActivity {

    Connection connect;
    String connectionResult="";
    Spinner cbNienKhoa, cbHocKy;
    ArrayList<String> arrNienKhoa = new ArrayList<String>();
    ArrayList<String> arrHocKy = new ArrayList<String>();

    ListView listviewLTC;
    public static ArrayList<ObjectLopTinChiNhapDiem> arrLopTinChiNhapDiem;
    ObjectLopTinChiNhapDiem objectLopTinChiNhapDiem;
    LopTinChiNhapDiemAdapter lopTinChiNhapDiemAdapter;
    int vitri=-1, vitriNienKhoa=0, vitriHocKy=0;

    Context context;
    SearchView searchView = null;
    public static String MaGiangVien="GV1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhapdiem);
        setControl();
        setEvent();

        ArrayAdapter arrayAdapterNienKhoa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrNienKhoa);
        cbNienKhoa.setAdapter(arrayAdapterNienKhoa);
        arrayAdapterNienKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterHocKy = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrHocKy);
        cbHocKy.setAdapter(arrayAdapterHocKy);
        arrayAdapterHocKy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //lopTinChiDaDangKyAdapter = new LopTinChiDaDangKyAdapter(context,arrLopTinChiDaDangKy);
        lopTinChiNhapDiemAdapter = new LopTinChiNhapDiemAdapter(this,R.layout.item_loptinchi_nhapdiem,arrLopTinChiNhapDiem);
        listviewLTC.setAdapter(lopTinChiNhapDiemAdapter);
        listviewLTC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(nhapdiem.this, "Item: "+nhapdiem.arrLopTinChiNhapDiem.get(position).getMaLTC(), Toast.LENGTH_SHORT).show();
            }
        });

        cbNienKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitriNienKhoa=i;
                getDSLTC();
                lopTinChiNhapDiemAdapter.notifyDataSetChanged();
//                searchView.setQuery("", false);
//                searchView.clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cbHocKy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitriHocKy=i;
                getDSLTC();
                lopTinChiNhapDiemAdapter.notifyDataSetChanged();
//                searchView.setQuery("", false);
//                searchView.clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        }
    private void setControl(){
        cbNienKhoa=(Spinner) findViewById(R.id.nien_khoa_spinner);
        cbHocKy=(Spinner) findViewById(R.id.hoc_ky_spinner);
        listviewLTC = (ListView) findViewById(R.id.listviewDSLTC);
        arrLopTinChiNhapDiem = new ArrayList<>();
    }
    private void setEvent(){
        KhoiTaoNienKhoa();
        KhoiTaoHocKi();

        getDSLTC();
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dangky, menu);

        //Nút Tìm kiếm
        MenuItem searchItem = menu.findItem(R.id.menuTimkiem);
        SearchManager searchManager = (SearchManager) nhapdiem.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(nhapdiem.this.getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Bắt kí tự
            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(LopTinChiDaDangKy.this, searchView.getQuery(), Toast.LENGTH_SHORT).show();

                if (searchView.getQuery().toString().isEmpty())
                {
                    getDSLTC();
                    lopTinChiNhapDiemAdapter.notifyDataSetChanged();
                }
                else
                {
                    cbHocKy.setSelection(0);
                    cbNienKhoa.setSelection(0);
                    timKiemLTCNhapDiem(searchView.getQuery().toString());
                    lopTinChiNhapDiemAdapter.notifyDataSetChanged();
                }
                return false;
            }
            //Bắt kí tự sau khi enter (không enter được khi null)
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void KhoiTaoNienKhoa(){
        try
        {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String query = "select distinct namhoc, cast(substring(NamHoc,0,5) as int)\n" +
                        "from LopTinChi\n" +
                        "group by namhoc\n" +
                        "order by cast(substring(NamHoc,0,5) as int) DESC, NamHoc";
                arrNienKhoa.clear();
                arrNienKhoa.add("All");
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    arrNienKhoa.add(rs.getString(1));
                }
                connect.close();
            }
            else{
                connectionResult="Check Connection";
            }
        }
        catch (Exception e){
            Log.e("",e.getMessage());
        }
    }
    private void KhoiTaoHocKi(){
        arrHocKy.add("All");
        arrHocKy.add("HK1");
        arrHocKy.add("HK2");
    }

    private void getDSLTC() {
    try {
        ConnectionHelper connectionHelper = new ConnectionHelper();
        connect = connectionHelper.connectionclass();

        if(connect !=null){
            String query = "select ltc.MaLTC, mh.TenMH from LopTinChi ltc\n" +
                    "join MonHoc MH on ltc.MaMH = MH.MaMH\n" +
                    "join day on ltc.MaLTC = day.MaLTC\n" +
                    "join GiangVien gv on gv.MaGV = day.MaGV\n" +
                    "where gv.magv = '"+MaGiangVien+"' ";

            if (arrNienKhoa.get(vitriNienKhoa).equals("All") && arrHocKy.get(vitriHocKy).equals("All"))
                query = query + " order by cast(substring(ltc.MaLTC,4,10) as int)";
            else if (arrNienKhoa.get(vitriNienKhoa).equals("All") && !arrHocKy.get(vitriHocKy).equals("All"))
                query = query+ " and hocki='"+arrHocKy.get(vitriHocKy)+"' order by cast(substring(ltc.MaLTC,4,10) as int)";
            else if (!arrNienKhoa.get(vitriNienKhoa).equals("All") && arrHocKy.get(vitriHocKy).equals("All"))
                query = query+ " and namhoc='"+arrNienKhoa.get(vitriNienKhoa)+"' order by cast(substring(ltc.MaLTC,4,10) as int)";
            else
                query = query + " and hocki='"+arrHocKy.get(vitriHocKy)+"' and namhoc='"+arrNienKhoa.get(vitriNienKhoa)+"' order by cast(substring(ltc.MaLTC,4,10) as int)";

            arrLopTinChiNhapDiem.clear();
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);
            int i=0;
            while(rs.next())
            {
                objectLopTinChiNhapDiem = new ObjectLopTinChiNhapDiem();
                objectLopTinChiNhapDiem.setId(i);
                objectLopTinChiNhapDiem.setMaLTC(rs.getString(1));
                objectLopTinChiNhapDiem.setTenLTC(rs.getString(2));


                arrLopTinChiNhapDiem.add(objectLopTinChiNhapDiem);
            }
            connect.close();
        }
        else{
            connectionResult="Check Connection";
        }
    }
    catch (Exception e){
        Log.e("",e.getMessage());
    }
    }

    public void timKiemLTCNhapDiem(String kitu){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            String query = "select ltc.MaLTC, mh.TenMH from LopTinChi ltc\n" +
                    "join MonHoc MH on ltc.MaMH = MH.MaMH\n" +
                    "join day on ltc.MaLTC = day.MaLTC\n" +
                    "join GiangVien gv on gv.MaGV = day.MaGV\n" +
                    "where gv.magv = '"+MaGiangVien+"' ";
            if (connect != null) {
                query = query + " and (ltc.maltc like N'%"+kitu+"%' or mh.TenMH like N'%"+kitu+"%')";
                arrLopTinChiNhapDiem.clear();
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    objectLopTinChiNhapDiem = new ObjectLopTinChiNhapDiem();
                    objectLopTinChiNhapDiem.setId(i);
                    objectLopTinChiNhapDiem.setMaLTC(rs.getString(1));
                    objectLopTinChiNhapDiem.setTenLTC(rs.getString(2));

                    arrLopTinChiNhapDiem.add(objectLopTinChiNhapDiem);
                }
                connect.close();
            }
            else{
                connectionResult="Check Connection";
            }

        }
        catch (Exception e){
            Log.e("",e.getMessage());
        }
    }
}

