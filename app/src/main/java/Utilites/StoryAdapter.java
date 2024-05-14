package Utilites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thestar.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import Database.Story;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<Story> strList;

    private OnItemClickListener itemClickListener;


    public StoryAdapter(Context context, ArrayList<Story> strList) {
        this.context = context;
        this.strList = strList;
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
        /*
        if (holder.etRating != null) {
            holder.ratingString = String.valueOf(holder.etRating.getRating());
        } else {
            Log.d("DEBUG", "holder.etRating is null");
        }*/

// HERE CRASH

       // holder.ratingString = String.valueOf(holder.etRating.getRating());
// HERE CRASH
        holder.ratingString = String.valueOf(holder.etRating.getRating());
        holder.etRating.setIsIndicator(true);

        holder.tvName.setText(str.getName());
        holder.tvDescription.setText(str.getDescription());
        // HERE ALSO CRASH
        holder.tvGenre.setText(str.getGenre());
        //
        holder.tvName.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}