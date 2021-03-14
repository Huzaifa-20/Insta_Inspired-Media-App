package com.huzaifa.wallahabibi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    ImageButton backArrow;
    ImageButton settings;
    ImageButton editProfile;
    CircleImageView profilePicture;
    TextView name;
    TextView bio;
    TextView followers;
    TextView following;



    //<-----RECYCLER VIEW VARIABLES----->//
    RecyclerView rv;
    List<ProfilePosts> profilePosts;
    Context c;
    //<-------------------------------->//

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile,container,false);
        c=container.getContext();

        connectViews(v);
        return v;
    }

    //FUNCTION THAT CONNECTS XML TO JAVA FILE//
    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FP);
        settings=v.findViewById(R.id.settings_FP);
        editProfile=v.findViewById(R.id.edit_FP);
        profilePicture=v.findViewById(R.id.profileImage_FP);
        name=v.findViewById(R.id.name_FP);
        bio=v.findViewById(R.id.bio_FP);
        followers=v.findViewById(R.id.followers_FP);
        following=v.findViewById(R.id.following_FP);

        rv=v.findViewById(R.id.rv_FP);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(c);
        rv.setLayoutManager(lm);
        profilePosts=new ArrayList<>();
        MyRvAdapter adapter = new MyRvAdapter(profilePosts,c);
        rv.setAdapter(adapter);
    }
}
