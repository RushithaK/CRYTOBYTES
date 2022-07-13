package com.example.CryptoBytes.MyModels;
import java.util.ArrayList;
import java.util.HashMap;

public class MyFavouritesModel {
    public ArrayList<String> favoriteList;
    public HashMap<String, String> favoritesMap;

    public MyFavouritesModel(ArrayList<String> favoriteList, HashMap<String, String> favoritesMap) {
        this.favoriteList = favoriteList;
        this.favoritesMap = favoritesMap;
    }
}
