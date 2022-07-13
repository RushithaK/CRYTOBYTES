package com.example.CryptoBytes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.CryptoBytes.MyActivities.FeedBackPage;
import com.example.CryptoBytes.MyAdapters.MainPageRVAdapter;
import com.example.CryptoBytes.MyModels.HomePageCoinModels.MyJsonObject;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.CryptoBytes.MyModels.HomePageCoinModels.DataMain;
import com.example.CryptoBytes.MyActivities.CoinDetailsModule.CoinDetailsPage;
import com.example.CryptoBytes.MyActivities.LoginModule.LoginActivity;
import com.example.CryptoBytes.MyActivities.NewsModule.NewsPage;
import com.example.CryptoBytes.MyUtils.MySortingClass;
import com.example.CryptoBytes.MyViewModel.CoinListViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity  implements MainPageRVAdapter.ItemClickListener {

    private RecyclerView currencyRV;
    private EditText searchEdt;
    public static List<DataMain> currencyModalArrayList;
    private Map<Integer,String> idToCoinMap;
    private ProgressBar loadingPB;
    private MainPageRVAdapter currencyRVAdapter;
    private CoinListViewModel mcoinListVM;
    private ImageButton reloadBT;
    public String[] sort_items = {"A-Z","Market Cap (H-L)","Price (H-L)","Volume 24hr (H-L)","Change 1hr (H-L)","Change 24hr (H-L)","Change 7d (H-L)",
            "Z-A","Market Cap (L-H)","Price (L-H)","Volume 24hr (L-H)","Change 1hr (L-H)","Change 24hr (L-H)","Change 7d (L-H)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEdt = findViewById(R.id.idEdtCurrency);
        loadingPB = findViewById(R.id.idPBLoading);
        currencyRV = findViewById(R.id.idRVcurrency);
        reloadBT = findViewById(R.id.refreshButton);
        idToCoinMap = new HashMap<>();
        currencyModalArrayList = new ArrayList<>();

        reloadBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

        currencyRVAdapter = new MainPageRVAdapter(currencyModalArrayList, this,this);
        currencyRV.setLayoutManager(new LinearLayoutManager(this));
        currencyRV.setAdapter(currencyRVAdapter);
        mcoinListVM = new ViewModelProvider(this).get(CoinListViewModel.class);
        mcoinListVM.getCoinsListObserver().observe(this, new Observer<MyJsonObject>() {
            @Override
            public void onChanged(MyJsonObject coins) {
                if(coins!=null)
                {
                    List<DataMain> c=coins.getData();
                    MySortingClass.sortList(c, 1);
                    currencyModalArrayList=c;
                    currencyRVAdapter.filterList(c);
                }
            }
        });
        mcoinListVM.makeApiCall();

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String filter) {
        List<DataMain> filteredlist = new ArrayList<>();

        for (DataMain item : currencyModalArrayList) {
            if (item.getName().toLowerCase().contains(filter.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No currency found..", Toast.LENGTH_SHORT).show();
        } else {
            currencyRVAdapter.filterList(filteredlist);
        }
    }

    public static DataMain getCoinFromSym(String sym)
    {
        for(DataMain dm:currencyModalArrayList)
        {
            if (dm.getSymbol().toString().equals(sym))
                return dm;
        }
        return null;
    }

    @Override
    public void onCoinClicked(DataMain coin) {
        Intent intent=new Intent(this, CoinDetailsPage.class);
        intent.putExtra("coin",coin);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_page_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by:
                displaySortByDialogue();
                return true;
            case R.id.news:
                startActivity(new Intent(MainActivity.this, NewsPage.class));
                return true;
            case R.id.profile:
                Toast.makeText(getApplicationContext(),"Profile Clicked",Toast.LENGTH_SHORT);
                return true;
            case R.id.favMenu:
                Toast.makeText(getApplicationContext(),"Favourites Clicked",Toast.LENGTH_SHORT);
                //startActivity(new Intent(MainActivity.this,FavouritesActivity.class));
                return true;
            case R.id.logout:
                signOut();
                return true;
            case R.id.feedback:
                startActivity(new Intent(MainActivity.this, FeedBackPage.class));
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        Toast.makeText(getApplicationContext(),"User signed Out Successfully!",Toast.LENGTH_SHORT).show();
    }
    private void displaySortByDialogue()
    {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("Sort By");
        alertdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertdialog.setSingleChoiceItems(sort_items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MySortingClass.sortList(currencyModalArrayList,i);
                currencyRVAdapter.notifyDataSetChanged();
            }
        });
        alertdialog.show();
    }
    @Override
    public void onBackPressed() {
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int num = rand.nextInt(1000);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.baseline_logout_24);
        if(num%7==0)
        {
            builder.setMessage("Rate Us?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            startActivity(new Intent(MainActivity.this,FeedBackPage.class));
                        }
                    })
                    .setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
        }else {
            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

}
