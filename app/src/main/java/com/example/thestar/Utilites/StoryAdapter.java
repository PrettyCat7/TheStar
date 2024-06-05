package com.example.thestar.Utilites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thestar.Database.FirebaseServices;
import com.example.thestar.Database.Story;
import com.example.thestar.Database.User1;
import com.example.thestar.MainActivity;
import com.example.thestar.R;
import com.example.thestar.fragments.StoriesDetails;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<Story> strList;
    private FirebaseServices fbs;

    public StoryAdapter(Context context, ArrayList<Story> strList) {
        this.context = context;
        this.strList = strList;
        this.fbs = fbs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.str_item, parent, false);
        return new StoryAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Story str = strList.get(position);

        holder.ratingString = String.valueOf(holder.etRating.getRating());
        holder.etRating.setIsIndicator(true);

        holder.tvName.setText(str.getName());
        holder.tvDescription.setText(str.getDescription());
        holder.tvGenre.setText(str.getGenre());

        holder.tvName.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putParcelable("story", str); // or use Parcelable for better performance
            StoriesDetails sd = new StoriesDetails();
            sd.setArguments(args);
            FragmentTransaction ft = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout, sd);
            ft.commit();
        });
        if (str.getPhoto() == null || str.getPhoto().isEmpty()) {
            Picasso.get().load(R.drawable.ic_launcher_foreground).into(holder.ivstr);
        } else {
            Picasso.get().load(str.getPhoto()).into(holder.ivstr);
        }
    }

    @Override
    public int getItemCount() {
        return strList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        String ratingString;
        TextView tvName, tvDescription, tvGenre;
        ImageView ivstr;
        RatingBar etRating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvStory);
            tvDescription = itemView.findViewById(R.id.tvDesc);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            etRating = itemView.findViewById(R.id.storyRatingD);
            ivstr = itemView.findViewById(R.id.tvStoryPhoto);


        }
    }
/*
    public interface OnItemClickListener {
        void onItemClick(int position);
    } */

    /*
    public void setOnItemClickListener(StoryAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    } */
}