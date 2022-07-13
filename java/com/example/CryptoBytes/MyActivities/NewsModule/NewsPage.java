package com.example.CryptoBytes.MyActivities.NewsModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.CryptoBytes.MyAdapters.NewsPageRVAdapter;
import com.example.CryptoBytes.MyModels.NewsModels.NewsModel;
import com.example.CryptoBytes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class NewsPage extends AppCompatActivity implements NewsPageRVAdapter.NewsItemClickListener {
    private RecyclerView newsRv;
    private List<NewsModel> newsModels;
    private NewsPageRVAdapter newsPageRVAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);
        newsRv=findViewById(R.id.idRVnews);
        swipeRefreshLayout = findViewById(R.id.newsRefresh);
        newsModels=new ArrayList<>();
        getData();
        initRV();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                initRV();
                newsPageRVAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void initRV()
    {
        newsPageRVAdapter = new NewsPageRVAdapter(newsModels, NewsPage.this,
                NewsPage.this);
        newsRv.setLayoutManager(new LinearLayoutManager(NewsPage.this));
        newsRv.setAdapter(newsPageRVAdapter);
    }
    private void getData()
    {
        String url = "https://min-api.cryptocompare.com/data/news/";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        String articleTitle = responseObj.getString("title");
                        String articleURL = responseObj.getString("url");
                        String articleBody = responseObj.getString("body");
                        String imageURL = responseObj.getString("imageurl");
                        String sourceName = responseObj.getString("source");
                        long publishedon = responseObj.getLong("published_on");
                        newsModels.add(new NewsModel(articleTitle,articleURL,articleBody,
                                imageURL,sourceName,publishedon));
                        initRV();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onNewsClick(NewsModel nm) {
        Intent intent = new Intent(NewsPage.this, WebViewActivity.class);
        intent.putExtra("url",nm.getArticleURL());
        intent.putExtra("title",nm.getArticleTitle());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.newsReload:
                finish();
                startActivity(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}