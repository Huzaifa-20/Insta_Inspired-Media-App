package com.huzaifa.wallahabibi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    SearchView searchView;
    RecyclerView rv;
    RecyclerView.LayoutManager lm;
    MyContactsRvAdapter adapter;
    List<Profile> contacts;
    
    Context c;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_chat,container,false);
        c=container.getContext();
        
        connectViews(v);
        setListeners();
        
        return v;
    }

    private void connectViews(View v) {
        searchView=v.findViewById(R.id.searchView_FC);
        rv=v.findViewById(R.id.rv_FC);

        contacts=new ArrayList<>();
        //GET CONTACTS FROM FIREBASE AND FILL CONTACTS LIST BEFORE SENDING TO ADAPTER//
        contacts.add(new Profile("Salman","https://api.time.com/wp-content/uploads/2017/12/terry-crews-person-of-year-2017-time-magazine-facebook-1.jpg?quality=85"));
        contacts.add(new Profile("Yumna","https://pkimgcdn.peekyou.com/40293972a8174d03a3156f071e6ac2af.jpeg"));
        contacts.add(new Profile("Huzaifa","https://cdn.pixabay.com/photo/2018/08/28/12/41/avatar-3637425_960_720.png"));
        contacts.add(new Profile("Hammad","https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg"));
        //////////////////////////////////////////////////////////////////////////////
        lm= new LinearLayoutManager(c);
        adapter=new MyContactsRvAdapter(contacts,c);

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

    }

    private void setListeners() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
