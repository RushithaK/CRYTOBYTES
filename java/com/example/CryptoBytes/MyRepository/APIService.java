package com.example.CryptoBytes.MyRepository;

import com.example.CryptoBytes.MyModels.HomePageCoinModels.MyJsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIService {

    @Headers("X-CMC_PRO_API_KEY: 95f32a77-d85f-43c2-be72-83b0c42bc129")
    @GET("v1/cryptocurrency/listings/latest")
    Call<MyJsonObject> getCoinList(@Query("limit") String limit);
}