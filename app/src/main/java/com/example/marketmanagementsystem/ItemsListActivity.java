package com.example.marketmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.lights.LightState;
import android.os.Bundle;

import java.util.List;

public class ItemsListActivity extends AppCompatActivity {

    RecyclerView rv_itemsList;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;

    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        rv_itemsList = findViewById(R.id.rv_itemsList);
        rv_itemsList.setHasFixedSize(true);

        itemList = AppDatabase.getInstant(ItemsListActivity.this).getItemsList(null);

        rvLayoutManager = new LinearLayoutManager(ItemsListActivity.this);
        rv_itemsList.setLayoutManager(rvLayoutManager);
        rvAdapter = new AppRecyclerViewAdapter(ItemsListActivity.this, itemList);
        rv_itemsList.setAdapter(rvAdapter);

    }


}