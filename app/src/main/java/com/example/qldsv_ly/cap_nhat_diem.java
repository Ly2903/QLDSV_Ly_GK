package com.example.QLDSV;

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

import com.example.Database.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class cap_nhat_diem extends AppCompatActivity {

    TextView maLTC, tenMH, maSV, tenSV;
    EditText diemCC, diemGK, diemCK;

    Button btnUpdate;

    Connection connect;
    String connectionResult="";

    Context context;



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
                    try {
                        connectionHelper connectionHelper = new connectionHelper();
                        connect = connectionHelper.connectionClass();
                        if (connect != null) {
                            String query = "UPDATE DangKi\n" +
                                    "SET DIEMCC = " + diemCC_Update + " , DiemGK = " + diemGK_Update + " , DiemCK =" + diemCK_Update + "\n" +
                                    "WHERE MaLTC='" + nhapdiem_ct_ltc.maLTC_ct_ltc + "' AND MaSV='" + nhapdiem_ct_ltc.maSV_ct_ltc + "'";
                            Statement st = connect.createStatement();
                            st.executeUpdate(query);

                            connect.close();
                            alertSuccess("Cập nhật điểm cho sinh viên: " + nhapdiem_ct_ltc.maSV_ct_ltc + " thành công! ");

                        } else {
                            connectionResult = "Check Connection";
                        }
                    } catch (Exception e) {
                        Log.e("", e.getMessage());
                    }
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
        try {
            connectionHelper connectionHelper = new connectionHelper();
            connect = connectionHelper.connectionClass();
            if(connect !=null){
                String query =
                        "SELECT SV.HoTen, DK.DIEMCC,DK.DiemGK,DK.DiemCK\n" +
                                "FROM SINHVIEN SV, DangKi DK\n" +
                                "WHERE DK.MaLTC='"+nhapdiem_ct_ltc.maLTC_ct_ltc+"' AND DK.MaSV='"+nhapdiem_ct_ltc.maSV_ct_ltc+"' AND SV.MaSV = DK.MaSV";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                int i=0;
                while(rs.next())
                {
                    tenSV.setText(rs.getString(1));
                    diemCC.setText(Float.toString(rs.getFloat(2)));
                    diemGK.setText(Float.toString(rs.getFloat(3)));
                    diemCK.setText(Float.toString(rs.getFloat(4)));
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