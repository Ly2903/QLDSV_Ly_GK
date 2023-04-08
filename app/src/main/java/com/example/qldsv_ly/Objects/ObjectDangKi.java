package com.example.qldsv_ly.Objects;

public class ObjectDangKi {
    private String MaLTC, MaSV;

    private Float DiemCC, DiemGK, DiemCK;

    private boolean Huy;

    public ObjectDangKi() {
    }

    public ObjectDangKi(String maLTC, String maSV, Float diemCC, Float diemGK, Float diemCK, boolean huy) {
        MaLTC = maLTC;
        MaSV = maSV;
        DiemCC = diemCC;
        DiemGK = diemGK;
        DiemCK = diemCK;
        Huy = huy;
    }

    public String getMaLTC() {
        return MaLTC;
    }

    public void setMaLTC(String maLTC) {
        MaLTC = maLTC;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public Float getDiemCC() {
        return DiemCC;
    }

    public void setDiemCC(Float diemCC) {
        DiemCC = diemCC;
    }

    public Float getDiemGK() {
        return DiemGK;
    }

    public void setDiemGK(Float diemGK) {
        DiemGK = diemGK;
    }

    public Float getDiemCK() {
        return DiemCK;
    }

    public void setDiemCK(Float diemCK) {
        DiemCK = diemCK;
    }

    public boolean isHuy() {
        return Huy;
    }

    public void setHuy(boolean huy) {
        Huy = huy;
    }
}
