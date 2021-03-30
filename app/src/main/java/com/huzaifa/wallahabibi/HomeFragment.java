package com.huzaifa.wallahabibi;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Context c;
    View v;

    EditText search;
    ImageButton searchBtn;
    String urlClicked;

    CardView newVid, shortVid, youTubeVid,desiVid, trendingVid, seeMore;

    private ArrayList<Post> posts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_home,container,false);

        c = container.getContext();

        connectViews(v);
        setListeners();
        initRV(v, c);
        return v;
    }

    private void connectViews(View v) {
        posts=new ArrayList<>();
        urlClicked="";

        search=v.findViewById(R.id.trendingsearch);
        searchBtn=v.findViewById(R.id.trendingbtn);

        newVid=v.findViewById(R.id.newvid);
        shortVid=v.findViewById(R.id.shortvids);
        youTubeVid=v.findViewById(R.id.youtubevids);
        desiVid=v.findViewById(R.id.desivideos);
        trendingVid=v.findViewById(R.id.trendingvids);
        seeMore=v.findViewById(R.id.seemore);

    }

    private void setListeners()
    {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRV(View v, Context c) {
        getPostImages();
        RecyclerView rv = v.findViewById(R.id.trendingRv);
        PostsRvAdapter sada = new PostsRvAdapter(c, posts);
        rv.setAdapter(sada);

        StaggeredGridLayoutManager stag = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(stag);
    }

    private void getPostImages() {
        posts.clear();
        for(int i=0;i<MainActivity.allPosts.size();i++)
        {
            posts.add(new Post(MainActivity.allPosts.get(i)));
        }
    }
}
