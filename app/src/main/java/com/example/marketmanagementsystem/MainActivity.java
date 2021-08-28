package com.example.marketmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_viewAll, btn_addItem, btn_searchItem, btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_addItem = findViewById(R.id.btn_addItem);
        btn_searchItem = findViewById(R.id.btn_searchItem);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        btn_checkout = findViewById(R.id.btn_checkOut);

        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditItemActivity.class);
                startActivity(intent);
            }
        });

        btn_searchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchItemActivity.class);
                startActivity(intent);
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ItemsListActivity.class);
                startActivity(intent);
            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCheckoutActivity.clearItemCheckoutList();
                Intent intent = new Intent(MainActivity.this, ItemCheckoutActivity.class);
                startActivity(intent);
            }
        });
    }
}