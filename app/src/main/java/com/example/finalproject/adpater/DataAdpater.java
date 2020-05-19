package com.example.finalproject.adpater;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.display;
import com.example.finalproject.model.Data;

import java.util.List;


public class DataAdpater extends RecyclerView.Adapter<DataAdpater.MyViewHolder> {
    private List<Data> mdataList;

    public DataAdpater(List<Data> dataList) {
        mdataList = dataList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView2);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        final MyViewHolder vh = new MyViewHolder(v);

        vh.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getAdapterPosition();
                Data dataItem = mdataList.get(position);
                if (dataItem.isFavorite() == false) {
                    dataItem.setFavorite(true);
                    mdataList.set(position, dataItem);
                    notifyItemChanged(position);
                } else {
                    dataItem.setFavorite(false);
                    mdataList.set(position, dataItem);
                    notifyItemChanged(position);
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Data dataItem = mdataList.get(position);
        holder.textView.setText(dataItem.getName());
        if (dataItem.isFavorite() == false) {
            holder.imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
        }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), display.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("List", dataItem);
                intent.putExtras(bundle);
                Toast.makeText(v.getContext(), dataItem.getName(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mdataList.size();
    }


}

