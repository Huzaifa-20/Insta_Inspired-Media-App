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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.model.MyStory;

public class StoriesFragment extends Fragment implements StoriesRvAdapter.OnStoryListener {


    Context c;
    View v;

    TextView stories;
    ImageButton search, add;

    private ArrayList<String> images;
    private ArrayList<MyStory> myStories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_stories,container,false);
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
        return v;
    }

    private void initRV(View v, Context c){
        RecyclerView rv= v.findViewById(R.id.stories_rv);
        // TODO : use context c below, or simply reference pass "this" as in comment below
//        StoriesRvAdapter sada=new StoriesRvAdapter(this,images )
        StoriesRvAdapter sada=new StoriesRvAdapter(c, images, this);
        rv.setAdapter(sada);
        // TODO : likewise below, pass c or this ?
        StaggeredGridLayoutManager stag=new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(stag);
    }

    private void connectViews(View v) {
        stories=v.findViewById(R.id.stories_stories);
        search=v.findViewById(R.id.stories_search);
        add=v.findViewById(R.id.stories_add_btn);
    }

    @Override
    public void onStoryClick(int pos) {
        initStoryView(v, c, pos);
    }

    private void initStoryView(View v, Context c, int pos) {
        myStories=new ArrayList<>();

        //TODO so our images will be like an obj of a class
        //      where class holds a thumbnail and ref to actual user's stories
        //      currently, the thumbnail itslef is being provided as story but later
        //      implement such that the myStories list is populated with correct stories.

        myStories.add(new MyStory(images.get(pos))); // should become like
        // for (String imgurls : (images.get(pos)){...add imgurls...}
        // where images will actually hold sets of users' stories


        new StoryView.Builder(((FragmentActivity)c).getSupportFragmentManager())
                .setStoriesList(myStories)
                .setStoryDuration(5000)
                .setTitleText("Dummy title")
                .setSubtitleText("Dummy sub title")
                .setTitleLogoUrl(images.get(0))
                .build()
                .show();
    }
}
