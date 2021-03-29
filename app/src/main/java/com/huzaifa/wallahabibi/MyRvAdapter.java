package com.huzaifa.wallahabibi;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

    List<ProfilePosts> ls;
    Context c;

    public MyRvAdapter(List<ProfilePosts> ls, Context c) {
        this.c=c;
        this.ls=ls;
    }

    @NonNull
    @Override
    public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.profile_posts_row,parent,false);
        return new MyViewHolder(itemrow);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder holder, final int position) {
        if(ls.get(position).getFirstType().equals("image"))
        {
            holder.firstVideoPost.setVisibility(View.INVISIBLE);
            holder.firstImagePost.setVisibility(View.VISIBLE);
            Picasso.get().load(ls.get(position).getFirstPost()).fit().centerCrop().into(holder.firstImagePost);
        }
        else if(ls.get(position).getFirstType().equals("video"))
        {
            holder.firstVideoPost.setVisibility(View.VISIBLE);
            holder.firstImagePost.setVisibility(View.INVISIBLE);

            holder.firstVideoPost.setVideoPath(ls.get(position).getFirstPost());
            MediaController mediaController=new MediaController(c);
            mediaController.setAnchorView(holder.firstVideoPost);
            holder.firstVideoPost.setMediaController(mediaController);
            holder.firstVideoPost.seekTo( 1 );
        }

        if(ls.get(position).getSecondType()!=null){
            if(ls.get(position).getSecondType().equals("image"))
            {
                holder.secondVideoPost.setVisibility(View.INVISIBLE);
                holder.secondImagePost.setVisibility(View.VISIBLE);
                Picasso.get().load(ls.get(position).getSecondPost()).fit().centerCrop().into(holder.secondImagePost);
            }
            else if(ls.get(position).getSecondType().equals("video"))
            {
                holder.secondVideoPost.setVisibility(View.VISIBLE);
                holder.secondImagePost.setVisibility(View.INVISIBLE);

                holder.secondVideoPost.setVideoPath(ls.get(position).getSecondPost());
                MediaController mediaController=new MediaController(c);
                mediaController.setAnchorView(holder.secondVideoPost);
                holder.secondVideoPost.setMediaController(mediaController);
                holder.secondVideoPost.seekTo( 1 );
            }
        }

        if(ls.get(position).getThirdType()!=null)
        {
            if(ls.get(position).getThirdType().equals("image"))
            {
                holder.thirdVideoPost.setVisibility(View.INVISIBLE);
                holder.thirdImagePost.setVisibility(View.VISIBLE);
                Picasso.get().load(ls.get(position).getThirdPost()).fit().centerCrop().into(holder.thirdImagePost);
            }
            else if(ls.get(position).getThirdType().equals("video"))
            {
                holder.thirdVideoPost.setVisibility(View.VISIBLE);
                holder.thirdImagePost.setVisibility(View.INVISIBLE);

                holder.thirdVideoPost.setVideoPath(ls.get(position).getThirdPost());
                MediaController mediaController=new MediaController(c);
                mediaController.setAnchorView(holder.thirdVideoPost);
                holder.thirdVideoPost.setMediaController(mediaController);
                holder.thirdVideoPost.seekTo( 1 );
            }
        }
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView firstImagePost;
        VideoView firstVideoPost;
        ImageView secondImagePost;
        VideoView secondVideoPost;
        ImageView thirdImagePost;
        VideoView thirdVideoPost;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstImagePost=itemView.findViewById(R.id.image1_PPR);
            secondImagePost=itemView.findViewById(R.id.image2_PPR);
            thirdImagePost=itemView.findViewById(R.id.image3_PPR);
            firstVideoPost=itemView.findViewById(R.id.videoView1_PPR);
            secondVideoPost=itemView.findViewById(R.id.videoView2_PPR);
            thirdVideoPost=itemView.findViewById(R.id.videoView3_PPR);

        }
    }
}

