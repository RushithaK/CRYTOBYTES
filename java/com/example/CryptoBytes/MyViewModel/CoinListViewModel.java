package com.example.CryptoBytes.MyViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.CryptoBytes.MyModels.HomePageCoinModels.MyJsonObject;
import com.example.CryptoBytes.MyRepository.APIService;
import com.example.CryptoBytes.MyRepository.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinListViewModel extends ViewModel {

    private MutableLiveData<MyJsonObject> coinList;

    public CoinListViewModel(){
        this.coinList = new MutableLiveData<>();
    }

    public LiveData<MyJsonObject> getCoinsListObserver() {
        return coinList;
    }

    public void makeApiCall() {
        APIService apiService = RetrofitInstance.getRetroClient().create(APIService.class);
        Call<MyJsonObject> call = apiService.getCoinList("5000");
        call.enqueue(new Callback<MyJsonObject>() {
            @Override
            public void onResponse(Call<MyJsonObject> call, Response<MyJsonObject> response) {
                coinList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MyJsonObject> call, Throwable t) {
                coinList.postValue(null);
            }
        });
    }


}
