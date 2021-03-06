package com.huzaifa.wallahabibi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.model.MyStory;

public class StoriesRvAdapter extends RecyclerView.Adapter<StoriesRvAdapter.ViewHolder>{

    private Context c;
    private ArrayList<String> images;
    private OnStoryListener onStoryListener;

    public StoriesRvAdapter(Context c, ArrayList<String> images) {
        this.c = c;
        this.images = images;
        this.onStoryListener=null;
    }

    public StoriesRvAdapter(Context c, ArrayList<String> images, OnStoryListener onStoryListener) {
        this.c = c;
        this.images = images;
        this.onStoryListener=onStoryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.stories_row,
                        parent,
                        false
                ),
                onStoryListener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPostImage(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RoundedImageView postImageView;
        OnStoryListener onStoryListener;


        public ViewHolder(@NonNull View itemView, OnStoryListener onStoryListener) {
            super(itemView);
            connectViews(itemView);
            itemView.setOnClickListener(this);
            this.onStoryListener=onStoryListener;
        }

        void connectViews(View v){
            postImageView=itemView.findViewById(R.id.imagePost);
        }

        void setPostImage(String url)
        {
            Picasso.get().load(url).into(postImageView);
        }

        @Override
        public void onClick(View v) {
            if (onStoryListener!=null) {
                onStoryListener.onStoryClick(getAdapterPosition());
            }
        }
    }

    public interface OnStoryListener{
        void onStoryClick(int pos);
    }

}
