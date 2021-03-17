package com.huzaifa.wallahabibi;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

//        Picasso.get().load(ls.get(position).getFirstPost()).into(holder.firstPost);
//        Picasso.get().load(ls.get(position).getSecondPost()).into(holder.secondPost);
//        Picasso.get().load(ls.get(position).getThirdPost()).into(holder.thirdPost);

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView firstPost;
        ImageView secondPost;
        ImageView thirdPost;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstPost=itemView.findViewById(R.id.image1_PPR);
            secondPost=itemView.findViewById(R.id.image2_PPR);
            thirdPost=itemView.findViewById(R.id.image3_PPR);
        }
    }
}

