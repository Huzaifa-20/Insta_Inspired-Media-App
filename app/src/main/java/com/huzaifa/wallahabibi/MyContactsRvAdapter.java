package com.huzaifa.wallahabibi;

import android.content.Context;
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

        holder.name.setText(ls.get(position).getName());

        //GET LAST MESSAGE SOMEHOW//

        lastMessage="No Message";

        //                         //
        holder.lastMessage.setText(lastMessage);
        Picasso.get().load(ls.get(position).getProfileImage()).into(holder.profilePicture);

        holder.messagePreviewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open activity of chat//
                Toast.makeText(c, "open chat activity", Toast.LENGTH_SHORT).show();
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

