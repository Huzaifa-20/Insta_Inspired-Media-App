package com.huzaifa.wallahabibi;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.huzaifa.wallahabibi.NotifPack.Data;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class trendingSelectedFragment extends Fragment {

    Context c;
    View v;

    Post currentPost;
    VideoView videoView;
    ImageView imageView;
    CircleImageView dp;
    TextView likes;
    TextView shares;
    TextView userName;
    TextView follower;
    TextView follow;
    CardView followCard;
    Profile user;
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

    private void setListeners() {
        followCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(follow.getText().toString().equals("Follow"))
                {
                    follow.setText("Following");
                    followCard.setBackgroundColor(Color.parseColor("#00AAD4"));
                    updateGlobal();
                }
            }
        });
    }

    private void updateGlobal() {
        MainActivity.following.add(user.getMyId());
        boolean cond=true;
        for(int i=0;i<MainActivity.chatContacts.size();i++)
        {
            if(MainActivity.chatContacts.get(i).getMyId().equals(user.getMyId()))
            {
                cond=false;
            }
        }
        if(cond)
        {
            MainActivity.chatContacts.add(user);
        }


        if(user.getPosts()!=null)
        {
            Iterator it = user.getPosts().entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry)it.next();
                MainActivity.allPosts.add((Post) pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }
        }

        if(user.getStories()!=null)
        {
            Iterator it = user.getStories().entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry)it.next();
                MainActivity.allStories.add((Story) pair.getValue());
                MainActivity.images.add(((Story) pair.getValue()).getStory());
                MainActivity.users.add(((Story) pair.getValue()).getUserName());
                it.remove(); // avoids a ConcurrentModificationException
            }
        }

        updateFirebase();
    }

    private void connectViews(View v) {
        videoView=v.findViewById(R.id.videoView_FTS);
        imageView=v.findViewById(R.id.imageView_FTS);
        dp=v.findViewById(R.id.trendinguser);
        likes=v.findViewById(R.id.likes_FTS);
        shares=v.findViewById(R.id.shares_FTS);
        userName=v.findViewById(R.id.userName_FTS);
        follow=v.findViewById(R.id.follow_FTS);
        follower=v.findViewById(R.id.followers_FTS);
        followCard=v.findViewById(R.id.trendingfollowcard);
        user=new Profile();
        frameLayout=v.findViewById(R.id.videoPreview);
        postRecyclerView=v.findViewById(R.id.trendingRv);
        posts=new ArrayList<>();

        if(MainActivity.videoIndex>=0 && MainActivity.videoIndex<MainActivity.allPosts.size())
        {
            currentPost=new Post(MainActivity.allPosts.get(MainActivity.videoIndex));

            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            videoView.setVideoPath(MainActivity.allPosts.get(MainActivity.videoIndex).getUrl());
            mediaController=new MediaController(c);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.seekTo( 1 );
            videoView.start();
            MainActivity.videoIndex=-1;
        }
        else{
            currentPost=new Post(MainActivity.allPosts.get(MainActivity.imageIndex));

            videoView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(MainActivity.allPosts.get(MainActivity.imageIndex).getUrl()).into(imageView);
            MainActivity.imageIndex=-1;
        }

        setTextViews();
    }

    private void setTextViews()
    {

        likes.setText(currentPost.getLikes());

        shares.setText(currentPost.getShares());

        for(int i=0;i<MainActivity.allChatContacts.size();i++)
        {
            if(MainActivity.allChatContacts.get(i).getMyId().equals(currentPost.getUserId()))
            {
                user=new Profile(MainActivity.allChatContacts.get(i));
            }
        }

        Picasso.get().load(Uri.parse(user.getProfileImage())).into(dp);

        userName.setText(user.getName());
        follower.setText(String.valueOf(user.getTotalFollowers()));

        if(MainActivity.following.contains(user.getMyId()))
        {
            follow.setText("Following");
            followCard.setBackgroundColor(Color.parseColor("#00AAD4"));
        }
        else
        {
            follow.setText("Follow");
            followCard.setBackgroundColor(Color.parseColor("#5D5D5D"));
        }
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

    private void updateFirebase() {
        //-------------FOLLOWING--------------//
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Profiles").child(MainActivity.currentUser.getMyId());

        HashMap<String,String> hashMap=new HashMap<>();
        for(int j=0;j<MainActivity.following.size();j++)
        {
            hashMap.put(MainActivity.following.get(j),MainActivity.following.get(j));
        }
        MainActivity.currentUser.setFollowing(hashMap);
        MainActivity.currentUser.setTotalFollowing((MainActivity.currentUser.getTotalFollowing())+1);
        //-------------FOLLOWERS--------------//
        hashMap=new HashMap<>();
        for(int j=0;j<MainActivity.followers.size();j++)
        {
            hashMap.put(MainActivity.followers.get(j),MainActivity.followers.get(j));
        }
        MainActivity.currentUser.setFollowers(hashMap);
        MainActivity.currentUser.setTotalFollowers(MainActivity.currentUser.getTotalFollowers());

        reference.setValue(MainActivity.currentUser);

        //-------------POSTS--------------//
        DatabaseReference postRef= FirebaseDatabase.getInstance().getReference("Profiles").child(MainActivity.currentUser.getMyId());

        for(int j=0;j<MainActivity.myPosts.size();j++)
        {
            postRef.child("posts").push().setValue( (Post)MainActivity.myPosts.get(j) );
        }

        //-------------STORIES--------------//
        DatabaseReference storyRef= FirebaseDatabase.getInstance().getReference("Profiles").child(MainActivity.currentUser.getMyId());

        for(int j=0;j<MainActivity.allStories.size();j++)
        {
            if(MainActivity.allStories.get(j).getUserName().equals(MainActivity.currentUser.getName()))
            {
                storyRef.child("stories").push().setValue( (Story)MainActivity.allStories.get(j) );
            }
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //-------------FOLLOWERS--------------//
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("Profiles").child(user.getMyId());

        HashMap<String,String> followerHashMap=new HashMap<>();
        if(user.getFollowers()!=null)
        {
            Iterator it = user.getFollowers().entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry)it.next();
                followerHashMap.put(pair.getKey().toString(),pair.getValue().toString());
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        followerHashMap.put(MainActivity.currentUser.getMyId(),MainActivity.currentUser.getMyId());
        user.setFollowers(followerHashMap);
        user.setTotalFollowers((user.getTotalFollowers())+1);
        //-------------FOLLOWINGS--------------//
        HashMap<String,String> followingHashMap=new HashMap<>();
        if(user.getFollowing()!=null)
        {
            Log.d("hope","Im inside USERS Following");
            Iterator it = user.getFollowing().entrySet().iterator();
            while (it.hasNext())
            {
                Log.d("hope","Im inside...");

                Map.Entry pair = (Map.Entry)it.next();
                Log.d("hope","Following: "+pair.getValue().toString());
                followingHashMap.put(pair.getKey().toString(),pair.getValue().toString());
                it.remove(); // avoids a ConcurrentModificationException
            }
            user.setFollowing(followingHashMap);
        }
        myRef.setValue(user);

        //-------------POSTS--------------//
        DatabaseReference myPostsRef= FirebaseDatabase.getInstance().getReference("Profiles").child(user.getMyId());

        for(int j=0;j<MainActivity.allPosts.size();j++)
        {
            if(MainActivity.allPosts.get(j).getUserId().equals(user.getMyId()))
            {
                myPostsRef.child("posts").push().setValue( ((Post)MainActivity.allPosts.get(j)) );
            }
        }

        //-------------STORY--------------//
        DatabaseReference myStoryRef= FirebaseDatabase.getInstance().getReference("Profiles").child(user.getMyId());

        for(int j=0;j<MainActivity.allStories.size();j++)
        {
            if(MainActivity.allStories.get(j).getUserName().equals(user.getName()))
            {
                storyRef.child("stories").push().setValue( (Story)MainActivity.allStories.get(j) );
            }
        }
    }
}
