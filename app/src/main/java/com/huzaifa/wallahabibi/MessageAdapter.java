package com.huzaifa.wallahabibi;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    List<Chat> mChat;
    Context mContext;
    private String imageUrl;

    FirebaseUser fUser;

    public MessageAdapter(Context c, List<Chat> ls, String imageUrl) {
        this.mContext=c;
        this.mChat=ls;
        this.imageUrl=imageUrl;

    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.MyViewHolder(view);
        }
        else{
            View view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.MyViewHolder(view);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, final int position) {
        Chat chat=mChat.get(position);

        holder.show_message.setText(chat.getMessage());

        if(imageUrl.equals("default")){
            holder.profile_image.setImageResource(R.drawable.profile);
        }
        else{
            Picasso.get().load(imageUrl).into(holder.profile_image);
        }
        if(position==mChat.size()-1){
            if(chat.isSeen()){
                holder.text_seen.setImageResource(R.drawable.ic_seen);
            }
            else{
                holder.text_seen.setImageResource(R.drawable.ic_delivered);
            }
        }
        else{
            holder.text_seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView show_message;
        CircleImageView profile_image;
        ImageView text_seen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message);
            profile_image=itemView.findViewById(R.id.profile_image);
            text_seen=itemView.findViewById(R.id.text_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser= FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }
}
