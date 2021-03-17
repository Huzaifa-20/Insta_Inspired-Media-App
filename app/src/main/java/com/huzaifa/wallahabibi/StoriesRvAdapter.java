package com.huzaifa.wallahabibi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class StoriesRvAdapter extends RecyclerView.Adapter<StoriesRvAdapter.ViewHolder>{

    private Context c;
    private ArrayList<String> images=new ArrayList<>();

    public StoriesRvAdapter(Context c, ArrayList<String> images) {
        this.c = c;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.stories_row, parent, false);
        ViewHolder voh=new ViewHolder(v);
        return voh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestOptions ro=new RequestOptions().placeholder(R.drawable.ic_launcher_background);


        //TODO fix this ! ! !

        Glide.with(c).asBitmap().load(images.get(position)).apply(ro).into(holder.lt);


//        if ((position==(images.size()/2))&& (images.size()%2)==0){
//            holder.rt.setClickable(false);
//            holder.rt.setVisibility(View.INVISIBLE);
//            Glide.with(c).asBitmap().load(images.get((position * 2))).apply(ro).into(holder.lt);
//        }

//        else {
//            if (position==(images.size()/2)){
//                holder.rt.setVisibility(View.VISIBLE);
//                holder.rt.setClickable(true);
//            }
//            Glide.with(c).asBitmap().load(images.get((position * 2))).apply(ro).into(holder.lt);
//            Glide.with(c).asBitmap().load(images.get((position * 2) + 1)).apply(ro).into(holder.rt);
//        }

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout ll;
        CardView left;
        ImageView lt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            connectViews(itemView);

        }

        void connectViews(View v){
            ll=v.findViewById(R.id.stories_ll);
            left=v.findViewById(R.id.stories_card_left);
            lt=v.findViewById(R.id.stories_img_left);
        }
    }

}
