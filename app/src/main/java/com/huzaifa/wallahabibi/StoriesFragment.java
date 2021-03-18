package com.huzaifa.wallahabibi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class StoriesFragment extends Fragment {


    Context c;

    TextView stories;
    ImageButton search, add;

    private ArrayList<String> images;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_stories,container,false);
        c=container.getContext();

        images=new ArrayList<>();
        images.add("https://wallpaperaccess.com/full/361942.jpg");
        images.add("https://wallpaperaccess.com/full/361854.jpg");
        images.add("https://wallpaperboat.com/wp-content/uploads/2019/09/duck-with-ducklings-photos.jpg");
        images.add("https://miro.medium.com/max/580/0*uyPv5FeFHvOimMKn.jpg");
        images.add("https://rdironworks.com/wp-content/uploads/2017/12/dummy-200x200.png");
        images.add("https://i.redd.it/tpsnoz5bzo501.jpg");

        connectViews(v);
        initRV(v,c);
        setListeners();
        return v;
    }

    private void initRV(View v,Context c){
        RecyclerView rv= v.findViewById(R.id.stories_rv);
        // TODO : use context c below, or simply reference pass "this" as in comment below
//        StoriesRvAdapter sada=new StoriesRvAdapter(this,images )
        StoriesRvAdapter sada=new StoriesRvAdapter(c, images);
        rv.setAdapter(sada);
        // TODO : likewise below, pass c or this ?
        StaggeredGridLayoutManager stag=new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(stag);
    }

    private void setListeners() {
    }

    private void connectViews(View v) {
        stories=v.findViewById(R.id.stories_stories);
        search=v.findViewById(R.id.stories_search);
        add=v.findViewById(R.id.stories_add_btn);
    }
}
