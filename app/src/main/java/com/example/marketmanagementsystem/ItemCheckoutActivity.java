package com.example.marketmanagementsystem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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
        et_quantityNumber = findViewById(R.id.et_quantityNumber);
        btn_submitCheckout = findViewById(R.id.btn_submitCheckout);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        String itemID = result.getData().getStringExtra("idScan");
                        item = AppDatabase.getInstant(ItemCheckoutActivity.this)
                                .getItem(itemID);
                        tv_itemIDCheckout.setText(item.getId());
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

        et_quantityNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                int quantity = Integer.parseInt(et_quantityNumber.getText().toString());

                if (quantity > item.getQuantity()) {
                    et_quantityNumber.setText("");
                    Toast.makeText(ItemCheckoutActivity.this, "Not Enough Quantity",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

                Double totalPrice = quantity * item.getPrice();
                tv_totalPrice.setText(String.valueOf(totalPrice));
                return true;
            }
        });

        btn_submitCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_quantityNumber.getText() == null)
                    return;

                int quantity = Integer.parseInt(et_quantityNumber.getText().toString());

                if (!item.checkout(quantity)) {
                    Toast.makeText(ItemCheckoutActivity.this, "Not Enough Quantity",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                AppDatabase.getInstant(ItemCheckoutActivity.this).updateItem(item);
            }
        });

    }
}