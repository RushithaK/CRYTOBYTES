package com.example.CryptoBytes.MyAdapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CryptoBytes.MainActivity;
import com.example.CryptoBytes.MyModels.HomePageCoinModels.DataMain;
import com.example.CryptoBytes.MyModels.HomePageCoinModels.USD;
import com.example.CryptoBytes.R;

import java.util.List;

public class FavouritesPageRVAdapter extends RecyclerView.Adapter<FavouritesPageRVAdapter.FavouriteVH> {
    private Context context;
    private List<String> favList=null;
    private MainPageRVAdapter.ItemClickListener itemClickListener;

    public FavouritesPageRVAdapter(Context context, List<String> favList, MainPageRVAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.context = context;
        this.favList = favList;
    }

    @NonNull
    @Override
    public FavouritesPageRVAdapter.FavouriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item, parent, false);
        return new FavouritesPageRVAdapter.FavouriteVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesPageRVAdapter.FavouriteVH holder, int position) {
        String favString = favList.get(position);
        DataMain modal = MainActivity.getCoinFromSym(favString);
        USD usd= modal.getQuote().getUSD();

        holder.nameTV.setText(modal.getName());
        holder.nameTV.setTextColor(Color.MAGENTA);

        holder.rateTV.setText("$ "+usd.getPrice());

        holder.symbolTV.setText(modal.getSymbol());
        holder.symbolTV.setTextColor(Color.MAGENTA);

        holder.rankTV.setText((Integer.valueOf(modal.getCmcRank())).toString());
//        System.out.println(MainActivity.coinMappedIcon.get(modal.getSymbol()));
//        Log.i("KRI",coinMappedIcon.get(modal.getSymbol()));
//        Picasso.get().load((String)coinMappedIcon.get((String)modal.getSymbol())).into(holder.icon);
        try {
            //Percent_change_1h
            if(usd.getPercentChange1h()<0){
                holder.pc1h.setTextColor(Color.parseColor("#FF0000"));
                holder.pc1h.setText("▼"+Math.abs((usd.getPercentChange1h())));
            }
            else if(usd.getPercentChange1h()>=0) {
                holder.pc1h.setTextColor(Color.parseColor("#32CD32"));
                holder.pc1h.setText("▲"+Math.abs((usd.getPercentChange1h())));
            }
            if(usd.getPercentChange24h()<0){
                holder.pc1d.setTextColor(Color.parseColor("#FF0000"));
                holder.pc1d.setText("▼"+Math.abs((usd.getPercentChange24h())));
            }
            else if(usd.getPercentChange24h()>=0) {
                holder.pc1d.setTextColor(Color.parseColor("#32CD32"));
                holder.pc1d.setText("▲"+Math.abs((usd.getPercentChange24h())));
            }
            if(usd.getPercentChange7d()<0){
                holder.pc7d.setTextColor(Color.parseColor("#FF0000"));
                holder.pc7d.setText("▼"+Math.abs((usd.getPercentChange7d())));
            }
            else if(usd.getPercentChange7d()>=0) {
                holder.pc7d.setTextColor(Color.parseColor("#32CD32"));
                holder.pc7d.setText("▲"+Math.abs((usd.getPercentChange7d())));
            }



        }
        catch(Exception e){
            Log.d("Color_Error",e.getMessage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //itemClickListener.onCoinClicked(favString);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(favList!=null)
            return favList.size();
        return 0;
    }
    public class FavouriteVH extends RecyclerView.ViewHolder{
        private TextView symbolTV, rateTV, nameTV, rankTV, pc1h,pc1d, pc7d;
        private ImageView icon;

        public FavouriteVH(@NonNull View itemView) {
            super(itemView);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVRate);
            nameTV = itemView.findViewById(R.id.idTVName);
            rankTV = itemView.findViewById(R.id.idTVRank);
            pc1h = itemView.findViewById(R.id.idTVpc1h);
//            pc1d = itemView.findViewById(R.id.idTVpc1d);
//            pc7d = itemView.findViewById(R.id.idTVpc7d);
            //icon = itemView.findViewById(R.id.coinIconIV);
        }
    }
}
