package com.example.qldsv_ly.Objects;


public class ObjectLopTinChiNhapDiem {
    private int id;
    private String maLTC;
    private String tenLTC;



    public ObjectLopTinChiNhapDiem(){
    }


    public ObjectLopTinChiNhapDiem(int id, String maLTC, String tenLTC) {
        this.id = id;
        this.maLTC = maLTC;
        this.tenLTC = tenLTC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaLTC() {
        return maLTC;
    }

    public void setMaLTC(String maLTC) {
        this.maLTC = maLTC;
    }

    public String getTenLTC() {
        return tenLTC;
    }

    public void setTenLTC(String tenLTC) {
        this.tenLTC = tenLTC;
    }
}
