package com.bukantkpd.bukabareng.fragments;

/**
 * Created by Ibam on 5/16/2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bukantkpd.bukabareng.R;
import com.bukantkpd.bukabareng.adapters_and_items.MyBuyingsAdapter;
import com.bukantkpd.bukabareng.adapters_and_items.MyBuyingsItem;

import java.util.ArrayList;
import java.util.List;

public class Tab2MyBuyings extends  Fragment{

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MyBuyingsItem> myBuyingsItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_my_buyings, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_buyings_list_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myBuyingsItems = new ArrayList<>();

        String productName = "Nama Barang ke ";
        int productPrice = 10000;
        String boughtQty = "7 Feb 2016";
        int productImage = R.drawable.dummy_loading;

        for (int i = 0; i < 50; i++){
            productPrice += i*5000;
            MyBuyingsItem temp = new MyBuyingsItem(productName + i, "Rp " + productPrice, "Rp "
                    + productPrice, boughtQty, productImage);

            myBuyingsItems.add(temp);
        }

        adapter = new MyBuyingsAdapter(myBuyingsItems, getContext());

        recyclerView.setAdapter(adapter);
        return rootView;
    }


}