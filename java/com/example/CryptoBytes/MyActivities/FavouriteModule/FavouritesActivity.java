package com.example.CryptoBytes.MyActivities.FavouritesModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.CryptoBytes.MyAdapters.FavouritesPageRVAdapter;
import com.example.CryptoBytes.MyAdapters.MainPageRVAdapter;
import com.example.CryptoBytes.MyModels.HomePageCoinModels.DataMain;
import com.example.CryptoBytes.MyActivities.CoinDetailsModule.CoinDetailsPage;
import com.example.CryptoBytes.MyDB.MyDataBaseHelper;
import com.example.CryptoBytes.R;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity implements MainPageRVAdapter.ItemClickListener {
    private RecyclerView favRv;
    public static FavouritesPageRVAdapter favRvadapter=null;
    public static ArrayList<String> favLists = new ArrayList<>();
    public static MyDataBaseHelper dataBaseHelperdataBaseHelper;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        favRv = findViewById(R.id.favRV);
        favLists =MyDataBaseHelper.getInstance(getApplicationContext()).getFavorites().favoriteList;
        favRvadapter = new FavouritesPageRVAdapter(getApplicationContext(), favLists,this);
        favRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        favRv.setAdapter(favRvadapter);
    }


    @Override
    public void onCoinClicked(DataMain sym) {
        Intent intent=new Intent(FavouritesActivity.this, CoinDetailsPage.class);
        intent.putExtra("symbol",sym);
        startActivity(intent);
    }
}