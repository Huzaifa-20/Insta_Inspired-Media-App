package com.huzaifa.wallahabibi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

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
        setListeners();
        return v;
    }

    //SET ON CLICK LISTENERS//
    private void setListeners() {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment=new SettingsFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_AHS,settingsFragment).commit();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFragment editFragment=new EditFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_AHS,editFragment).commit();
            }
        });
    }

    //FUNCTION THAT CONNECTS XML TO JAVA FILE//
    private void connectViews(View v) {
        settings=v.findViewById(R.id.settings_FP);
        editProfile=v.findViewById(R.id.edit_FP);
        profilePicture=v.findViewById(R.id.profileImage_FP);
        name=v.findViewById(R.id.name_FP);
        bio=v.findViewById(R.id.bio_FP);
        followers=v.findViewById(R.id.followers_FP);
        following=v.findViewById(R.id.following_FP);

        Picasso.get().load(homeScreen.currentUser.getProfileImage()).into(profilePicture);
        name.setText(homeScreen.currentUser.getName());
        bio.setText(homeScreen.currentUser.getBio());

        rv=v.findViewById(R.id.rv_FP);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(c);
        rv.setLayoutManager(lm);
        profilePosts=new ArrayList<>();
        MyRvAdapter adapter = new MyRvAdapter(profilePosts,c);
        rv.setAdapter(adapter);
    }

}
