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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.huzaifa.wallahabibi.NotifPack.Token;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    SearchView searchView;
    RecyclerView rv;
    RecyclerView.LayoutManager lm;
    MyContactsRvAdapter adapter;
    
    Context c;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_chat,container,false);
        c=container.getContext();
        
        connectViews(v);
        setListeners();

        updateTok(FirebaseInstanceId.getInstance().getToken());
        
        return v;
    }

    private void connectViews(View v) {

        searchView=v.findViewById(R.id.searchView_FC);
        rv=v.findViewById(R.id.rv_FC);
        lm= new LinearLayoutManager(c);
        adapter=new MyContactsRvAdapter(MainActivity.chatContacts,c);

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

    private void updateTok(String tok){
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Tokens");
        Token tok1=new Token(tok);
        ref.child(FirebaseAuth.getInstance().getUid()).setValue(tok1);
    }
}
