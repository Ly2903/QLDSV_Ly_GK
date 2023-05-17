package com.example.qldsv_ly;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class connectionHelper {
    Connection con;
    String username, password, ip, port, db;
    @SuppressLint("NewApi")
    public Connection connectionClass() {
        //ip = "192.168.118.35";
        ip = "10.0.2.2";
        db = "qldiem_PTCUDDD";
        username = "SA";
        password = "123";
        port = "1433";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String connectURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + db + ";user=" + username + ";"+"password=" + password + ";";
            conn = DriverManager.getConnection(connectURL);
            Log.i("Database", "Success");
        }
        catch(Exception e) {
            Log.e("ERROR1", e.getMessage());
        }
        return conn;
    }

}
