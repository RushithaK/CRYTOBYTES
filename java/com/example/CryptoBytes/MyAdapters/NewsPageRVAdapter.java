package com.example.CryptoBytes.MyAdapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.CryptoBytes.MyModels.NewsModels.NewsModel;
import com.example.CryptoBytes.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsPageRVAdapter extends RecyclerView.Adapter<NewsPageRVAdapter.NewsViewHolder> {


    private List<NewsModel> newsModals;
    private Context context;
    private NewsPageRVAdapter.NewsItemClickListener newsItemClickListener;

    public NewsPageRVAdapter(List<NewsModel> newsModals, Context context, NewsPageRVAdapter.NewsItemClickListener newsItemClickListener) {
        this.newsItemClickListener = newsItemClickListener;
        this.newsModals = newsModals;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discovery_news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel nm=newsModals.get(position);
        holder.articleTitle.setText(nm.getArticleTitle());
        holder.articleSource.setText(nm.getSourceName());
        Picasso.get().load(nm.getImageURL()).into(holder.articleIcon);
        String publishTimeString = (String) DateUtils.getRelativeTimeSpanString(nm.getPublishedOn() * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        holder.articleAge.setText(publishTimeString);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsItemClickListener.onNewsClick(newsModals.get(holder.getAbsoluteAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(this.newsModals != null) {
            return this.newsModals.size();
        }
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView articleTitle,articleAge,articleSource;
        private ImageView articleIcon;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

//            articleTitle = itemView.findViewById(R.id.articleTitle);
//            articleSource = itemView.findViewById(R.id.sourceName);
//            articleAge = itemView.findViewById(R.id.age);
//            articleIcon = itemView.findViewById(R.id.articleImage);
            articleTitle = itemView.findViewById(R.id.tvHeadlines);
            articleSource = itemView.findViewById(R.id.tvSource);
            articleAge = itemView.findViewById(R.id.tvTimePeriod);
            articleIcon = itemView.findViewById(R.id.ivNewsCover);
        }
    }

    public interface NewsItemClickListener{
        public void onNewsClick(NewsModel nm);
    }
}
