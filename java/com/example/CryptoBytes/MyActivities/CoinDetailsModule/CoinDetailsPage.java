package com.example.CryptoBytes.MyActivities.CoinDetailsModule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.CryptoBytes.MyModels.HomePageCoinModels.DataMain;
import com.example.CryptoBytes.MyUtils.MyGeneralUtils;
import com.example.CryptoBytes.R;
import com.example.CryptoBytes.databinding.CoinDetailsBinding;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CoinDetailsPage extends AppCompatActivity {
    private String temp;
    private DataMain receivedCoin;
    private DecimalFormat numberFormat = new DecimalFormat("#,###,###.####");
    private CoinDetailsBinding coinDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coinDetailsBinding = CoinDetailsBinding.inflate(getLayoutInflater());
        setContentView(coinDetailsBinding.getRoot());
        receivedCoin = (DataMain) getIntent().getSerializableExtra("coin");
        getData(receivedCoin.getSymbol());
    }

    public void getData(String sym)
    {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/info?symbol="+sym;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jobj=response.getJSONObject("data").getJSONObject(sym);
                    String name =jobj.getString("name");
                    String des=jobj.getString("description");
                    String logo=jobj.getString("logo");
                    coinDetailsBinding.coinName.setText(name);
                    coinDetailsBinding.coinSymbol.setText(sym);

                    coinDetailsBinding.coinDescription.setText(des);

                    temp = numberFormat.format(Math.abs(receivedCoin.getTotalSupply()));
                    coinDetailsBinding.coinTotalSupply.setText(temp);
                    temp = numberFormat.format(Math.abs(receivedCoin.getCirculatingSupply()));
                    coinDetailsBinding.coinCirSupply.setText(temp);
                    temp = numberFormat.format(Math.abs(receivedCoin.getMaxSupply()));
                    coinDetailsBinding.coinMaxSupply.setText(temp);
                    temp = numberFormat.format(Math.abs(receivedCoin.getNumMarketPairs()));
                    coinDetailsBinding.coinMarketPairs.setText(temp);

                    temp = numberFormat.format(Math.abs(receivedCoin.getQuote().getUSD().getMarketCap()));
                    coinDetailsBinding.coinMarketCap.setText(temp);
                    temp = numberFormat.format(Math.abs(receivedCoin.getQuote().getUSD().getPrice()));
                    coinDetailsBinding.coinPrice.setText(temp);
                    temp = numberFormat.format(Math.abs(receivedCoin.getQuote().getUSD().getVolume24h()));
                    coinDetailsBinding.coinVol24.setText(temp);


                    coinDetailsBinding.coinPerChange1hr.setText(MyGeneralUtils.getFloatDecimalFormattedString(receivedCoin.getQuote().getUSD().getPercentChange1h()));
                    coinDetailsBinding.coinPerChange24hr.setText(MyGeneralUtils.getFloatDecimalFormattedString(receivedCoin.getQuote().getUSD().getPercentChange24h()));
                    coinDetailsBinding.coinPerChange7d.setText(MyGeneralUtils.getFloatDecimalFormattedString(receivedCoin.getQuote().getUSD().getPercentChange7d()));
                    coinDetailsBinding.coinPerChange30d.setText(MyGeneralUtils.getFloatDecimalFormattedString(receivedCoin.getQuote().getUSD().getPercentChange30d()));
                    coinDetailsBinding.coinPerChange60d.setText(MyGeneralUtils.getFloatDecimalFormattedString(receivedCoin.getQuote().getUSD().getPercentChange60d()));
                    coinDetailsBinding.coinPerChange90d.setText(MyGeneralUtils.getFloatDecimalFormattedString(receivedCoin.getQuote().getUSD().getPercentChange90d()));

                    Picasso.get().load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+receivedCoin.getId().toString()+".png").into(coinDetailsBinding.coinLogo);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "95f32a77-d85f-43c2-be72-83b0c42bc129");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}