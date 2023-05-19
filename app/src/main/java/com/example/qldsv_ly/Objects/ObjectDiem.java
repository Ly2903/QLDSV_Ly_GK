package com.example.qldsv_ly.Objects;

public class ObjectDiem {
    private String  MaSV;

    private Float DiemCC, DiemGK, DiemCK, DiemTK;

    public ObjectDiem() {
    }

    public ObjectDiem( Float diemCC, Float diemGK, Float diemCK) {
        DiemCC = diemCC;
        DiemGK = diemGK;
        DiemCK = diemCK;
    }

    public ObjectDiem(String maSV, Float diemCC, Float diemGK, Float diemCK, Float diemTK) {
        MaSV = maSV;
        DiemCC = diemCC;
        DiemGK = diemGK;
        DiemCK = diemCK;
        DiemTK = diemTK;
    }

    public Float getDiemTK() {
        return DiemTK;
    }

    public void setDiemTK(Float diemTK) {
        DiemTK = diemTK;
    }

    public Float getDiemCC() {
        return DiemCC;
    }

    public void setDiemCC(Float diemCC) {
        DiemCC = diemCC;
    }

    public Float getDiemCK() {
        return DiemCK;
    }

    public void setDiemCK(Float diemCK) {
        DiemCK = diemCK;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public Float getDiemGK() {
        return DiemGK;
    }

    public void setDiemGK(Float diemGK) {
        DiemGK = diemGK;
    }
}
