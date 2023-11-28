package com.example.thestar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoryAdaptor extends RecyclerView.Adapter<StoryAdaptor.MyViewHolder> {
    Context context;
    ArrayList<Story> strList;
    private FirebaseServices fbs;

    public StoryAdaptor(Context context, ArrayList<Story> restList) {
        this.context = context;
        this.strList = strList;
        this.fbs = FirebaseServices.getInstance();
    }

    @NonNull
    @Override
    public StoryAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.str_item,parent,false);
        return  new StoryAdaptor.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdaptor.MyViewHolder holder, int position) {
        Story str = strList.get(position);
        holder.tvName.setText(str.getName());
        holder.tvDescription.setText(str.getDescription());
    }

    @Override
    public int getItemCount(){
        return strList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvNameRestItem);
            tvDescription=itemView.findViewById(R.id.tvDescriptionRestItem);

        }
    }
}