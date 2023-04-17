package com.example.qldsv_ly.Objects;

public class ObjectDiemSinhVien {
    private int id;
    private String MaSV;

    private Float DiemCC, DiemGK, DiemCK, DiemTK;

    private boolean Huy;

    public ObjectDiemSinhVien() {
    }

    public ObjectDiemSinhVien(int id, String maSV, Float diemCC, Float diemGK, Float diemCK, Float diemTK, boolean huy) {
        this.id = id;
        MaSV = maSV;
        DiemCC = diemCC;
        DiemGK = diemGK;
        DiemCK = diemCK;
        DiemTK = diemTK;
        Huy = huy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Float getDiemTK() {
        return DiemTK;
    }

    public void setDiemTK(Float diemTK) {
        DiemTK = diemTK;
    }

    public boolean isHuy() {
        return Huy;
    }

    public void setHuy(boolean huy) {
        Huy = huy;
    }
}
