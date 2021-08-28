package com.example.marketmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.gesture.GestureLibraries;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

public class AppRecyclerViewAdapter extends RecyclerView.Adapter<AppRecyclerViewAdapter.AppViewHolder> {
    List<Item> itemList;
    Context context;

    public AppRecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_item,
                parent, false);

        AppViewHolder appViewHolder = new AppViewHolder(view);

        return appViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        holder.tv_itemId.setText(String.valueOf(itemList.get(position).getId()));
        holder.tv_itemName.setText(itemList.get(position).getName());
        holder.tv_itemPrice.setText(String.valueOf(itemList.get(position).getPrice()));
        holder.tv_itemQuantity.setText(String.valueOf(itemList.get(position).getQuantity()));
        Glide.with(this.context).load(itemList.get(position).getImageURL()).into(holder.iv_itemImage);
        holder.layout_oneLineItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddEditItemActivity.class);
                intent.putExtra("id", itemList.get(position).getId() - 1);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class AppViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_itemImage;
        TextView tv_itemId, tv_itemName, tv_itemPrice, tv_itemQuantity;
        ConstraintLayout layout_oneLineItem;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_itemImage = itemView.findViewById(R.id.iv_itemImage);
            tv_itemId = itemView.findViewById(R.id.tv_itemId);
            tv_itemName = itemView.findViewById(R.id.tv_itemName);
            tv_itemPrice = itemView.findViewById(R.id.tv_itemPrice);
            tv_itemQuantity = itemView.findViewById(R.id.tv_itemQuantity);
            layout_oneLineItem = itemView.findViewById(R.id.layout_oneLineItem);
        }
    }
}
