package com.example.marketmanagementsystem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ItemCheckoutActivity extends AppCompatActivity {

    Item item;
    ActivityResultLauncher<Intent> activityResultLauncher;
    TextView tv_itemIDCheckout, tv_itemNameCheckout, tv_itemPriceCheckout, tv_itemQuantityCheckout;
    ImageView iv_itemImageCheckout;
    TextView tv_totalPrice;
    EditText et_quantityNumber;
    Button btn_submitCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_checkout);

        tv_itemIDCheckout = findViewById(R.id.tv_itemIDCheckout);
        tv_itemNameCheckout = findViewById(R.id.tv_itemNameCheckout);
        tv_itemPriceCheckout = findViewById(R.id.tv_itemPriceCheckout);
        tv_itemQuantityCheckout = findViewById(R.id.tv_itemQuantityCheckout);
        iv_itemImageCheckout = findViewById(R.id.iv_itemImageCheckout);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);
        et_quantityNumber = findViewById(R.id.et_itemQuantity);
        btn_submitCheckout = findViewById(R.id.btn_submitCheckout);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int itemID = result.getData().getIntExtra("idScan", -1);
                        item = AppDatabase.getInstant(ItemCheckoutActivity.this)
                                .getItem(itemID);
                        tv_itemIDCheckout.setText(String.valueOf(item.getId()));
                        tv_itemNameCheckout.setText(item.getName());
                        tv_itemPriceCheckout.setText(String.valueOf(item.getPrice()));
                        tv_itemQuantityCheckout.setText(String.valueOf(item.getQuantity()));
                        Glide.with(ItemCheckoutActivity.this)
                                .load(item.getImageURL()).into(iv_itemImageCheckout);
                    }
                }
        );

        Intent intent = new Intent(ItemCheckoutActivity.this, ScannerActivity.class);
        activityResultLauncher.launch(intent);


    }
}