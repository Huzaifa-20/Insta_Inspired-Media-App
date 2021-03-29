package com.huzaifa.wallahabibi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class trendingSelectedFragment extends Fragment {

    Context c;
    View v;

    VideoView videoView;
    FrameLayout frameLayout;
    MediaController mediaController;
    RecyclerView postRecyclerView;
    StoriesRvAdapter adapter;
    private ArrayList<String> posts;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_trending_selected,container,false);
        c = container.getContext();

        connectViews(v);
        getPostImages();
        setListeners();
        initRV(v, c);
        return v;
    }

    private void connectViews(View v) {
        videoView=v.findViewById(R.id.videoView_FTS);
        frameLayout=v.findViewById(R.id.videoPreview);
        postRecyclerView=v.findViewById(R.id.trendingRv);
        posts=new ArrayList<>();

        videoView.setVideoPath("https://player.vimeo.com/external/214483277.sd.mp4?s=7ed2d96a95d2f83285829d9a64fd30389744de98&profile_id=164&oauth2_token_id=57447761");
        mediaController=new MediaController(c);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.seekTo( 1 );
        videoView.start();
    }

    private void setListeners()
    {

    }

    //Initialise recyclerview//
    private void initRV(View v, Context c) {
        postRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );
        adapter=new StoriesRvAdapter(c,posts);
        postRecyclerView.setAdapter(adapter);
    }

    private void getPostImages() {
        posts.add("https://i.pinimg.com/236x/12/44/d0/1244d0959963cb4a25b4e5570e04ad11--ducks-spaces.jpg");
        posts.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNF8renvz6vJgedh2EzSDgkaW1yPtpqU6FQxcDkSamaKd3-VXkbrXb4j5sNbtXF0lfY1A&usqp=CAU");
        posts.add("https://cdna.artstation.com/p/assets/images/images/002/228/166/large/sebastian-kings-spaceduck.jpg?1458984664");
    }
}
