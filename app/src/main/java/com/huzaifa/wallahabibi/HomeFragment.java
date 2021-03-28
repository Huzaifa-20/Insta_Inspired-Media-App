package com.huzaifa.wallahabibi;

import android.content.Context;
import android.os.Bundle;
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

    CardView newVid, shortVid, youTubeVid,desiVid, trendingVid, seeMore;

    private ArrayList<String> posts;

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
        RecyclerView rv = v.findViewById(R.id.trendingRv);

        getPostImages();
        StoriesRvAdapter sada = new StoriesRvAdapter(c, posts);
        rv.setAdapter(sada);

        StaggeredGridLayoutManager stag = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(stag);
    }

    private void getPostImages() {
        posts.add("https://i.pinimg.com/236x/12/44/d0/1244d0959963cb4a25b4e5570e04ad11--ducks-spaces.jpg");
        posts.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNF8renvz6vJgedh2EzSDgkaW1yPtpqU6FQxcDkSamaKd3-VXkbrXb4j5sNbtXF0lfY1A&usqp=CAU");
        posts.add("https://cdna.artstation.com/p/assets/images/images/002/228/166/large/sebastian-kings-spaceduck.jpg?1458984664");
    }
}
