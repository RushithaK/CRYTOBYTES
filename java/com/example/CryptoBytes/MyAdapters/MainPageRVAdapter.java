package com.example.CryptoBytes.MyAdapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CryptoBytes.MyModels.HomePageCoinModels.DataMain;
import com.example.CryptoBytes.MyModels.HomePageCoinModels.USD;
import com.example.CryptoBytes.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class MainPageRVAdapter extends RecyclerView.Adapter<MainPageRVAdapter.CurrencyViewholder> {
    private List<DataMain> currencyModals;
    private Context context;
    private ItemClickListener itemClickListener;
    private DecimalFormat numberFormat = new DecimalFormat("#,###,###.####");

    public MainPageRVAdapter(List<DataMain> currencyModals, Context context, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.currencyModals = currencyModals;
        this.context = context;
    }

    public void filterList(List<DataMain> filterllist) {
        currencyModals = filterllist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainPageRVAdapter.CurrencyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item, parent, false);
        return new MainPageRVAdapter.CurrencyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPageRVAdapter.CurrencyViewholder holder, @SuppressLint("RecyclerView") int position) {
        DataMain modal =  currencyModals.get(position);
        USD usd = modal.getQuote().getUSD();
        holder.nameTV.setText(modal.getName());
        holder.rateTV.setText("$ "+numberFormat.format(modal.getQuote().getUSD().getPrice()));
        holder.symbolTV.setText(modal.getSymbol());

        holder.rankTV.setText((Integer.valueOf(modal.getCmcRank())).toString());
        Picasso.get().load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+modal.getId().toString()+".png").into(holder.icon);
        try {
            //Percent_change_1h
            String temp = numberFormat.format(Math.abs((usd.getPercentChange1h())));
            if(usd.getPercentChange1h()<0){
                holder.pc1h.setTextColor(Color.parseColor("#FF0000"));
                holder.pc1h.setText("▼"+temp);
            }
            else if(usd.getPercentChange1h()>=0) {
                holder.pc1h.setTextColor(Color.parseColor("#32CD32"));
                holder.pc1h.setText("▲"+temp);
            }
//            temp = numberFormat.format(Math.abs((usd.getPercentChange24h())));
//            if(usd.getPercentChange24h()<0){
//                holder.pc1d.setTextColor(Color.parseColor("#FF0000"));
//                holder.pc1d.setText("▼"+temp);
//            }
//            else if(usd.getPercentChange24h()>=0) {
//                holder.pc1d.setTextColor(Color.parseColor("#32CD32"));
//                holder.pc1d.setText("▲"+temp);
//            }
//            temp = numberFormat.format(Math.abs((usd.getPercentChange7d())));
//            if(usd.getPercentChange7d()<0){
//                holder.pc7d.setTextColor(Color.parseColor("#FF0000"));
//                holder.pc7d.setText("▼"+temp);
//            }
//            else if(usd.getPercentChange7d()>=0) {
//                holder.pc7d.setTextColor(Color.parseColor("#32CD32"));
//                holder.pc7d.setText("▲"+temp);
//            }
        }
        catch(Exception e){
            Log.d("Color_Error",e.getMessage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onCoinClicked(modal);
            }
        });
//        holder.favBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyDataBaseHelper.getInstance(context).getFavorites().favoriteList.add(0, modal.getSymbol());
//                FavouritesActivity.favRvadapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if(this.currencyModals != null) {
            return this.currencyModals.size();
        }
        return 0;
    }

    public class CurrencyViewholder extends RecyclerView.ViewHolder {
        private TextView symbolTV, rateTV, nameTV, rankTV, pc1h,pc1d, pc7d;
        private ImageButton favBt;
        private ImageView icon;

        public CurrencyViewholder(@NonNull View itemView) {
            super(itemView);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVRate);
            nameTV = itemView.findViewById(R.id.idTVName);
            rankTV = itemView.findViewById(R.id.idTVRank);
            pc1h = itemView.findViewById(R.id.idTVpc1h);
//            pc1d = itemView.findViewById(R.id.idTVpc1d);
//            pc7d = itemView.findViewById(R.id.idTVpc7d);
//            favBt = itemView.findViewById(R.id.favButton);
            icon = itemView.findViewById(R.id.coinIconIV);
        }
    }

    public interface ItemClickListener{
        public void onCoinClicked(DataMain coin);
    }
}
