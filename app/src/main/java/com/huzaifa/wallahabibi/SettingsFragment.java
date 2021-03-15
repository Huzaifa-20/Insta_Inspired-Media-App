package com.huzaifa.wallahabibi;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    ImageButton backArrow;
    LinearLayout privacy;
    LinearLayout appSettings;
    LinearLayout about;
    TextView logOut;

    Context c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_settings,container,false);
        c=container.getContext();

        connectViews(v);
        setListeners();
        return v;
    }

    private void setListeners() {
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment=new ProfileFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_AHS,profileFragment).commit();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Bye Bye", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FS);
        privacy=v.findViewById(R.id.privacySettings_FS);
        appSettings=v.findViewById(R.id.appSettings_FS);
        about=v.findViewById(R.id.aboutSettings_FS);
        logOut=v.findViewById(R.id.logOut_FS);
    }
}
