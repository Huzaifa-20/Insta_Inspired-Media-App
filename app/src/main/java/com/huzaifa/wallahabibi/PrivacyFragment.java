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

public class PrivacyFragment extends Fragment {

    ImageButton backArrow;
    TextView history;
    TextView lastSeen;
    TextView profilePhoto;
    TextView bio;
    TextView status;
    TextView blocked;
    TextView deleteCache;

    Context c;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_privacy,container,false);
        c=container.getContext();

        connectViews(v);
        setListeners();
        return v;
    }

    private void setListeners() {
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment=new SettingsFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_AHS,settingsFragment).commit();
            }
        });

        lastSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastSeen.getText().toString().equals("everyone >")){
                    lastSeen.setText("restricted >");
                }
                else{
                    lastSeen.setText("everyone >");
                }
            }
        });

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profilePhoto.getText().toString().equals("everyone >")){
                    profilePhoto.setText("restricted >");
                }
                else{
                    profilePhoto.setText("everyone >");
                }
            }
        });

        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bio.getText().toString().equals("everyone >")){
                    bio.setText("restricted >");
                }
                else{
                    bio.setText("everyone >");
                }
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastSeen.getText().toString().equals("everyone >")){
                    status.setText("restricted >");
                }
                else{
                    status.setText("everyone >");
                }
            }
        });

        deleteCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Cache Cleared", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FP);
        history=v.findViewById(R.id.searchHistory_FP);
        lastSeen=v.findViewById(R.id.lastSeen_FP);
        profilePhoto=v.findViewById(R.id.profilePhoto_FP);
        bio=v.findViewById(R.id.bio_FP);
        status=v.findViewById(R.id.status_FP);
        blocked=v.findViewById(R.id.blocked_FP);
        deleteCache=v.findViewById(R.id.clearCache_FP);
    }
}
