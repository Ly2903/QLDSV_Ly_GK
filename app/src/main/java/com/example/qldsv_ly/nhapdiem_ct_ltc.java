package com.example.qldsv_ly;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.qldsv_ly.AdapterCustom.DiemSinhVienAdapter;
import com.example.qldsv_ly.AdapterCustom.LopTinChiNhapDiemAdapter;
import com.example.qldsv_ly.Objects.ObjectDiemSinhVien;
import com.example.qldsv_ly.Objects.ObjectLopTinChiNhapDiem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class nhapdiem_ct_ltc extends AppCompatActivity {
    SearchView searchView = null;
    Connection connect;
    String connectionResult="";
    ListView lvDSSV_LTC;
    public static ArrayList<ObjectDiemSinhVien> arrDiemSV;
    ObjectDiemSinhVien objectDiemSinhVien;
    DiemSinhVienAdapter diemSinhVienAdapter;
    Context context;
    TextView maLTC, tenMH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhapdiem_ct_ltc);
        setControl();
        setEvent();

        diemSinhVienAdapter = new DiemSinhVienAdapter(this,R.layout.item_ctltc_nhapdiem,arrDiemSV);
        lvDSSV_LTC.setAdapter(diemSinhVienAdapter);
        lvDSSV_LTC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(nhapdiem_ct_ltc.this, "Item: "+nhapdiem_ct_ltc.arrDiemSV.get(position).getMaSV(), Toast.LENGTH_SHORT).show();
            }
        });

    }


        private void getDSSV() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();

            if(connect !=null){
                String query =
                        "SELECT SV.MaSV, DK.DIEMCC,DK.DiemGK,DK.DiemCK,(MH.HeSoCC*DK.DiemCC + MH.HeSoGK*DK.DiemGK + MH.HeSocK*DK.DiemGK)/100 AS DIEMTK  \n" +
                        "FROM LOPTINCHI LTC, SINHVIEN SV,MONHOC MH, DangKi DK\n" +
                        "WHERE DK.MaLTC='"+nhapdiem.maLTC_NhapDiem+"' AND DK.MaSV=SV.MaSV AND LTC.MaMH=MH.MaMH and DK.MALTC = LTC.MALTC";
                arrDiemSV.clear();
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {

                    objectDiemSinhVien = new ObjectDiemSinhVien();
                    objectDiemSinhVien.setId(i);
                    objectDiemSinhVien.setMaSV(rs.getString(1));
                    objectDiemSinhVien.setDiemCC(rs.getFloat(2));
                    objectDiemSinhVien.setDiemGK(rs.getFloat(3));
                    objectDiemSinhVien.setDiemCK(rs.getFloat(4));
                    objectDiemSinhVien.setDiemTK(rs.getFloat(5));

                    arrDiemSV.add(objectDiemSinhVien);
                }
                Toast.makeText(context, nhapdiem.maLTC_NhapDiem+"", Toast.LENGTH_SHORT).show();

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

    private void setControl(){
        lvDSSV_LTC = (ListView) findViewById(R.id.lvDSSV_LTC);
        maLTC=findViewById(R.id.maLTC);
        tenMH=findViewById(R.id.tenMH);
        arrDiemSV = new ArrayList<>();
    }
    private void setEvent(){
        maLTC.setText(nhapdiem.maLTC_NhapDiem);
        tenMH.setText(nhapdiem.tenMH_NhapDiem);
        getDSSV();
    }


}