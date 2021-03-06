package com.bukantkpd.bukabareng.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bukantkpd.bukabareng.R;
import com.bukantkpd.bukabareng.adapters_and_items.SearchResultsAdapter;
import com.bukantkpd.bukabareng.api.model.ProductModel;
import com.bukantkpd.bukabareng.api.model.SearchResultListModel;
import com.bukantkpd.bukabareng.api.remote.ApiUtils;
import com.bukantkpd.bukabareng.api.remote.BukBarAPIService;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity implements SearchResultsAdapter.SearchResultsClickListener{

    RecyclerView recyclerView;
    private SearchResultsAdapter adapter;
    private BukBarAPIService bbasService;
    String query, token;
    SharedPreferences sharedPreference;


    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        isLoggedIn();

        bbasService = ApiUtils.getBBASService();


        adapter = new SearchResultsAdapter(this, new ArrayList<ProductModel>(0));

        recyclerView = (RecyclerView) findViewById(R.id.search_results_list_recyclerView);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        recyclerView.setAdapter(adapter);

        sharedPreference = this.getSharedPreferences("bukabareng", Context.MODE_PRIVATE);
        query = getIntent().getStringExtra("searchQuery");
        token = sharedPreference.getString("token", null);
        Log.d("Search Query & token: ", query + " " + token);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search_result);
        toolbar.setTitle("hasil pencarian " + query + "....");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationIcon(R.drawable.arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getProducts(query, token);

    }

    @Override
    public void onBuyButtonClicked(View view, int position) {
        Intent intent = new Intent(this, ProductViewActivity.class);
        startActivity(intent);
    }


    private void isLoggedIn(){
        SharedPreferences sp = this.getSharedPreferences("bukabareng", Context.MODE_PRIVATE);
        boolean isLoggedIn = sp.getBoolean("isLoggedIn", false);

        if(!isLoggedIn){
            Intent intent = new Intent(this, PreLoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void getProducts(String query, String token){
        bbasService.getProductSearch(query, token).enqueue(new Callback<SearchResultListModel>() {
            @Override
            public void onResponse(Call<SearchResultListModel> call, Response<SearchResultListModel> response) {
                if(response.isSuccessful()){
                    String result = new Gson().toJson(response.body().getProductsList());

                    Log.d("JSON RESULT", result);
                    adapter.updateList(response.body().getProductsList());
                    Log.d("Search Result Act", "post loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d("ERROR API", statusCode+"");
                }
            }

            @Override
            public void onFailure(Call<SearchResultListModel> call, Throwable t) {
                t.printStackTrace();
                Log.d("MainActivity", "error loading from API");
                Toast.makeText(SearchResultsActivity.this, "Request timedout, try to search again" +
                        " in a few seconds", Toast
                        .LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void setActionBar(android.widget.Toolbar toolbar) {
        super.setActionBar(toolbar);
    }
}
