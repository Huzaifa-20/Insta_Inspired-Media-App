package com.huzaifa.wallahabibi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.model.MyStory;

public class StoriesRvAdapter extends RecyclerView.Adapter<StoriesRvAdapter.ViewHolder>{

    private Context c;
    private ArrayList<String> images;
    private OnStoryListener onStoryListener;

    public StoriesRvAdapter(Context c, ArrayList<String> images, OnStoryListener onStoryListener) {
        this.c = c;
        this.images = images;
        this.onStoryListener=onStoryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.stories_row, parent, false);
        ViewHolder voh=new ViewHolder(v, onStoryListener);
        return voh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestOptions ro=new RequestOptions().placeholder(R.drawable.frame);

        //TODO fix this ! ! !
        Glide.with(c).load(images.get(position)).apply(ro).into(holder.lt);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        RelativeLayout ll;
//        CardView left;
        ImageView lt;
        OnStoryListener onStoryListener;


        public ViewHolder(@NonNull View itemView, OnStoryListener onStoryListener) {
            super(itemView);
            connectViews(itemView);
            itemView.setOnClickListener(this);
            this.onStoryListener=onStoryListener;
        }

        void connectViews(View v){
//            ll=v.findViewById(R.id.stories_ll);
//            left=v.findViewById(R.id.stories_card_left);
            lt=v.findViewById(R.id.stories_img_left);
        }

        @Override
        public void onClick(View v) {
            onStoryListener.onStoryClick(getAdapterPosition());
        }
    }

    public interface OnStoryListener{
        void onStoryClick(int pos);
    }

}
