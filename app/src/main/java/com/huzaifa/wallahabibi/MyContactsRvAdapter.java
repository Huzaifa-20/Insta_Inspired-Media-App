package com.huzaifa.wallahabibi;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyContactsRvAdapter extends RecyclerView.Adapter<MyContactsRvAdapter.MyViewHolder> implements Filterable {

    List<Profile> ls;
    List<Profile> lsFull;
    Context c;

    String lastMessage;

    public MyContactsRvAdapter(List<Profile> ls, Context c) {
        this.c=c;
        this.ls=ls;
        this.lsFull=new ArrayList<>(ls);
    }

    @NonNull
    @Override
    public MyContactsRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.message_preview_row,parent,false);
        return new MyViewHolder(itemrow);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyContactsRvAdapter.MyViewHolder holder, final int position) {

        lastMessage(ls.get(position).getMyId(),holder.lastMessage);

        holder.lastMessage.setText(lastMessage);
        holder.name.setText(ls.get(position).getName());
        Picasso.get().load(ls.get(position).getProfileImage()).fit().centerCrop().into(holder.profilePicture);

        holder.messagePreviewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c,ChatScreen.class);
                intent.putExtra("contactNumber",position);
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePicture;
        TextView name;
        TextView lastMessage;
        LinearLayout messagePreviewRow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicture=itemView.findViewById(R.id.imageProfile_MP);
            name=itemView.findViewById(R.id.name_MP);
            lastMessage=itemView.findViewById(R.id.lastMessage_MP);
            messagePreviewRow=itemView.findViewById(R.id.messagePreviewRow);
        }
    }

    //Getting last message//
    private void lastMessage(final String userId, final TextView last_message){
        lastMessage="No Message";
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat=snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(firebaseUser.getUid())){
                        lastMessage=chat.getMessage();
                    }
                }
                last_message.setText(lastMessage);
                lastMessage="No message";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Profile> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Profile item : lsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ls.clear();
            ls.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

