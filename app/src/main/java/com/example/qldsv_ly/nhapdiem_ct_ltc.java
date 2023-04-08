package com.example.qldsv_ly;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.qldsv_ly.Objects.ObjectLopTinChiNhapDiem;

import java.sql.ResultSet;
import java.sql.Statement;

public class nhapdiem_ct_ltc extends AppCompatActivity {
    SearchView searchView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhapdiem_ct_ltc);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_dangky, menu);
//
//        //Nút Tìm kiếm
//        MenuItem searchItem = menu.findItem(R.id.menuTimkiem);
//        SearchManager searchManager = (SearchManager) nhapdiem_ct_ltc.this.getSystemService(Context.SEARCH_SERVICE);
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(nhapdiem_ct_ltc.this.getComponentName()));
//        }
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            //Bắt kí tự
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //Toast.makeText(LopTinChiDaDangKy.this, searchView.getQuery(), Toast.LENGTH_SHORT).show();
//
//                if (searchView.getQuery().toString().isEmpty())
//                {
//                    getDSSV();
//                    lopTinChiNhapDiemAdapter.notifyDataSetChanged();
//                }
//                else
//                {
//                    cbHocKy.setSelection(0);
//                    cbNienKhoa.setSelection(0);
//                    timKiemLTCNhapDiem(searchView.getQuery().toString());
//                    lopTinChiNhapDiemAdapter.notifyDataSetChanged();
//                }
//                return false;
//            }
//            //Bắt kí tự sau khi enter (không enter được khi null)
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }

//    private void getDSSV() {
//        try {
//            ConnectionHelper connectionHelper = new ConnectionHelper();
//            connect = connectionHelper.connectionclass();
//
//            if(connect !=null){
//                String query = "select ltc.MaLTC, mh.TenMH from LopTinChi ltc\n" +
//                        "join MonHoc MH on ltc.MaMH = MH.MaMH\n" +
//                        "join day on ltc.MaLTC = day.MaLTC\n" +
//                        "join GiangVien gv on gv.MaGV = day.MaGV\n" +
//                        "where gv.magv = '"+MaGiangVien+"' ";
//
//                if (arrNienKhoa.get(vitriNienKhoa).equals("All") && arrHocKy.get(vitriHocKy).equals("All"))
//                    query = query + " order by cast(substring(ltc.MaLTC,4,10) as int)";
//                else if (arrNienKhoa.get(vitriNienKhoa).equals("All") && !arrHocKy.get(vitriHocKy).equals("All"))
//                    query = query+ " and hocki='"+arrHocKy.get(vitriHocKy)+"' order by cast(substring(ltc.MaLTC,4,10) as int)";
//                else if (!arrNienKhoa.get(vitriNienKhoa).equals("All") && arrHocKy.get(vitriHocKy).equals("All"))
//                    query = query+ " and namhoc='"+arrNienKhoa.get(vitriNienKhoa)+"' order by cast(substring(ltc.MaLTC,4,10) as int)";
//                else
//                    query = query + " and hocki='"+arrHocKy.get(vitriHocKy)+"' and namhoc='"+arrNienKhoa.get(vitriNienKhoa)+"' order by cast(substring(ltc.MaLTC,4,10) as int)";
//
//                arrLopTinChiNhapDiem.clear();
//                Statement st = connect.createStatement();
//                ResultSet rs = st.executeQuery(query);
//                int i=0;
//                while(rs.next())
//                {
//                    objectLopTinChiNhapDiem = new ObjectLopTinChiNhapDiem();
//                    objectLopTinChiNhapDiem.setId(i);
//                    objectLopTinChiNhapDiem.setMaLTC(rs.getString(1));
//                    objectLopTinChiNhapDiem.setTenLTC(rs.getString(2));
//
//
//                    arrLopTinChiNhapDiem.add(objectLopTinChiNhapDiem);
//                }
//                connect.close();
//            }
//            else{
//                connectionResult="Check Connection";
//            }
//        }
//        catch (Exception e){
//            Log.e("",e.getMessage());
//        }
//    }
}