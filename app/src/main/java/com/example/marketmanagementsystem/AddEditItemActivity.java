package com.example.marketmanagementsystem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AddEditItemActivity extends AppCompatActivity {

    int index;
    Item item;

    TextView tv_itemID;
    EditText et_itemName, et_itemPrice, et_itemQuantity, et_itemImageURL;
    ImageView iv_itemImagePreview;
    Button btn_submit, btn_scan;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        tv_itemID = findViewById(R.id.tv_itemID);
        et_itemName = findViewById(R.id.et_itemName);
        et_itemPrice = findViewById(R.id.et_itemPrice);
        et_itemQuantity = findViewById(R.id.et_itemQuantity);
        et_itemImageURL = findViewById(R.id.et_imageURL);
        iv_itemImagePreview = findViewById(R.id.iv_itemImagePreview);
        btn_submit = findViewById(R.id.btn_submit);
        btn_scan = findViewById(R.id.btn_scan);

        index = getIntent().getIntExtra("index", -1);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resID = result.getData().getIntExtra("idScan", -1);
                        tv_itemID.setText(String.valueOf(resID));
                    }
                }
        );

        if (index >= 0) {

            btn_scan.setVisibility(View.GONE);

            item = AppDatabase.getInstant(AddEditItemActivity.this).getItemsList(null)
            .get(index);

            tv_itemID.setText(String.valueOf(item.getId()));
            et_itemName.setText(item.getName());
            et_itemPrice.setText(String.valueOf(item.getPrice()));
            et_itemQuantity.setText(String.valueOf(item.getQuantity()));
            et_itemImageURL.setText(item.getImageURL());
            Glide.with(AddEditItemActivity.this).load(item.getImageURL())
                    .into(iv_itemImagePreview);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item == null) {
                    item = new Item();
                    item.setId(Integer.parseInt(tv_itemID.getText().toString()));
                }
                item.setName(et_itemName.getText().toString().trim());
                item.setPrice(Double.valueOf(et_itemPrice.getText().toString().trim()));
                item.setQuantity(Integer.valueOf(et_itemQuantity.getText().toString().trim()));
                item.setImageURL(et_itemImageURL.getText().toString().trim());
                if (index >= 0)
                    AppDatabase.getInstant(AddEditItemActivity.this).updateItem(item);
                else
                    AppDatabase.getInstant(AddEditItemActivity.this).addItem(item);

                // return to main activity
                Intent intent = new Intent(AddEditItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditItemActivity.this, ScannerActivity.class);
                activityResultLauncher.launch(intent);
            }
        });
    }
}