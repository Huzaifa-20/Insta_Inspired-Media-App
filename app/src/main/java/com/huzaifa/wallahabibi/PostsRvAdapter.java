package com.huzaifa.wallahabibi;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostsRvAdapter extends RecyclerView.Adapter<PostsRvAdapter.ViewHolder> {

    private Context c;
    private ArrayList<Post> postList;

    public PostsRvAdapter(Context c, ArrayList<Post> postList) {
        this.c = c;
        this.postList = postList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.stories_row,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (postList.get(position).getType().equals("image")) {
            Picasso.get().load(postList.get(position).getUrl()).into(holder.postImageView);
        } else if (postList.get(position).getType().equals("video")) {
            Glide
                    .with(c.getApplicationContext())
                    .load(postList.get(position).getUrl())
                    .thumbnail(0.1f)
                    .override(Target.SIZE_ORIGINAL)
                    .into(holder.postImageView);
        }

        holder.postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < MainActivity.allPosts.size(); i++) {
                    if (MainActivity.allPosts.get(i).getUrl().equals(postList.get(position).getUrl())) {
                        if (postList.get(position).getType().equals("image")) {
                            MainActivity.imageIndex = i;
                        } else {
                            MainActivity.videoIndex = i;
                        }

                    }
                }
                trendingSelectedFragment ts = new trendingSelectedFragment();
                FragmentTransaction ft = ((FragmentActivity) c).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_AHS, ts).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView postImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImageView = itemView.findViewById(R.id.imagePost);
        }

    }

}
