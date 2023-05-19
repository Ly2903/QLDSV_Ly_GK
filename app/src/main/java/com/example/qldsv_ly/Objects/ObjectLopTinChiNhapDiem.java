package com.example.qldsv_ly.Objects;


public class ObjectLopTinChiNhapDiem {

    private String MaLTC;
    private String TenMH;



    public ObjectLopTinChiNhapDiem(){
    }

    public ObjectLopTinChiNhapDiem(String maLTC, String tenMH) {
        this.MaLTC = maLTC;
        this.TenMH = tenMH;
    }

    public String getMaLTC() {
        return MaLTC;
    }

    public void setMaLTC(String maLTC) {
        this.MaLTC = maLTC;
    }

    public String getTenMH() {
        return TenMH;
    }

    public void setTenMH(String tenMH) {
        this.TenMH = tenMH;
    }
}
