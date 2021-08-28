package com.example.marketmanagementsystem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemCheckoutActivity extends AppCompatActivity {

    private static List<Item> itemCheckoutList;

    RecyclerView rv_itemCheckoutList;
    RecyclerView.LayoutManager rvLayoutManager;
    RecyclerView.Adapter rvAdapter;
    TextView tv_totalPrice;
    Button btn_scanCheckout, btn_checkoutList;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_checkout);

        rv_itemCheckoutList = findViewById(R.id.rv_itemCheckoutList);
        btn_scanCheckout = findViewById(R.id.btn_scanCheckout);
        btn_checkoutList = findViewById(R.id.btn_checkoutList);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);

        if (itemCheckoutList == null)
            itemCheckoutList = new ArrayList<>();
        else
            tv_totalPrice.setText("Total Price: " + String.valueOf(getTotalPrice()));

        rv_itemCheckoutList.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(ItemCheckoutActivity.this);
        rv_itemCheckoutList.setLayoutManager(rvLayoutManager);
        rvAdapter = new AppRecyclerViewAdapter(ItemCheckoutActivity.this, itemCheckoutList);
        rv_itemCheckoutList.setAdapter(rvAdapter);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        String itemID = result.getData().getStringExtra("idScan");
                        Item item = AppDatabase.getInstant(ItemCheckoutActivity.this)
                                .getItem(itemID);
                        if (item == null) {
                            Toast.makeText(ItemCheckoutActivity.this, "Item Not Available",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        addItemToCheckoutList(item);
                        restartActivity();

                    }
                }
        );

        btn_scanCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemCheckoutActivity.this,
                        ScannerActivity.class);

                activityResultLauncher.launch(intent);
            }
        });

        btn_checkoutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void addItemToCheckoutList(Item item) {
        boolean isFound = false;
        for (Item i: itemCheckoutList) {
            if (i.getId().equals(item.getId()) ) {
                if (i.getQuantity() >= item.getQuantity()) {
                    Toast.makeText(ItemCheckoutActivity.this, "Not Enough Quantity",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                i.setQuantity(i.getQuantity() + 1);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            item.setQuantity(1);
            itemCheckoutList.add(item);
        }
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public static void clearItemCheckoutList() {
        if (itemCheckoutList != null)
            itemCheckoutList.clear();
    }

    private double getTotalPrice() {
        double totalPrice = 0;
        for (Item i : itemCheckoutList) {
            totalPrice += (i.getPrice() * i.getQuantity());
        }
        return totalPrice;
    }
}