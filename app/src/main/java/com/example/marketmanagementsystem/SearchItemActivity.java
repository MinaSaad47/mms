package com.example.marketmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class SearchItemActivity extends AppCompatActivity {

    EditText et_itemSearch;
    Button btn_search;
    RecyclerView rv_resultItemList;
    RecyclerView.LayoutManager rvLayoutManager;
    RecyclerView.Adapter rvAdapter;

    List<Item> resultItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        et_itemSearch = findViewById(R.id.et_itemSearch);
        btn_search = findViewById(R.id.btn_search);
        rv_resultItemList = findViewById(R.id.rv_resultItemList);

        rv_resultItemList.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(SearchItemActivity.this);
        rv_resultItemList.setLayoutManager(rvLayoutManager);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = et_itemSearch.getText().toString().trim();
                resultItemList = AppDatabase.getInstant(SearchItemActivity.this).getItemsList(itemName);
                rvAdapter = new AppRecyclerViewAdapter(SearchItemActivity.this, resultItemList);
                rv_resultItemList.setAdapter(rvAdapter);
            }
        });


    }
}