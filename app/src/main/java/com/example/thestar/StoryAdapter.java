package com.example.thestar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<Story> strList;
    private FirebaseServices fbs;
    private OnItemClickListener itemClickListener;

    public StoryAdapter(Context context, ArrayList<Story> strList) {
        this.context = context;
        this.strList = strList;
        this.fbs = FirebaseServices.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.str_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Story str = strList.get(position);

        holder.tvName.setText(str.getName());
        holder.tvDescription.setText(str.getDescription());
        holder.tvRating.setText(str.getRating());
        holder.tvGenre.setText(str.getGenre());
        holder.tvName.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });
        if (str.getImage() == null || str.getImage().isEmpty()) {
            Picasso.get().load(R.drawable.ic_launcher_foreground).into(holder.ivstr);
        } else {
            Picasso.get().load(str.getImage()).into(holder.ivstr);
        }
    }

    @Override
    public int getItemCount() {
        return strList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription, tvGenre, tvRating;
        ImageView ivstr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameRestItem);
            tvDescription = itemView.findViewById(R.id.tvDescriptionRestItem);
            tvGenre = itemView.findViewById(R.id.etgenadd);
            tvRating = itemView.findViewById(R.id.etrating);
            ivstr = itemView.findViewById(R.id.IVstr);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}