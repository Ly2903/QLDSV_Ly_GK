package com.example.qldsv_ly.api;


import com.example.qldsv_ly.Objects.ObjectDiem;
import com.example.qldsv_ly.Objects.ObjectLopTinChiNhapDiem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/v1/giangVien/getTTGVTheoMaGV")
    Call<String> getTTGVTheoMaGV(@Field("MaGV") String MaGV );

    @FormUrlEncoded
    @POST("api/v1/LTC/getDSLTCTheoMaGV")
    Call<List<ObjectLopTinChiNhapDiem>> getDSLTCTheoMaGV(@Field("MaGV") String MaGV,
                                                         @Field("NamHoc") String NamHoc,
                                                         @Field("HocKi") String HocKi);

    @GET("api/v1/LTC/getDSNamHocTheoLTC")
    Call<List<String>> getDSNamHocTheoLTC();

    @FormUrlEncoded
    @POST("api/v1/dangKi/getDSSVTheoMaLTC")
    Call<List<ObjectDiem>> getDSSVTheoMaLTC(@Field("MaLTC") String MaLTC );


    @FormUrlEncoded
    @POST("api/v1/dangKi/getSVTheoMaSVAndMaLTC")
    Call<ObjectDiem> getSVTheoMaSVAndMaLTC(@Field("MaLTC") String MaLTC,
                                                 @Field("MaSV") String MaSV);

    @FormUrlEncoded
    @POST("api/v1/dangKi/capNhatDiem")
    Call<Object> capNhatDiem(@Field("MaLTC") String MaLTC,
                             @Field("MaSV") String MaSV,
                             @Field("DiemCC") Float DiemCC,
                             @Field("DiemGK") Float DiemGK,
                             @Field("DiemCK") Float DiemCK);
}
