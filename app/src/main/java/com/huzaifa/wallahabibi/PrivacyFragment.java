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
import androidx.fragment.app.FragmentTransaction;

public class PrivacyFragment extends Fragment {

    ImageButton backArrow;
    TextView history;
    TextView deleteHistory;
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


    }

    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FP);
        history=v.findViewById(R.id.searchHistory_FP);
        deleteHistory=v.findViewById(R.id.deleteSearchHistory_FP);
        deleteCache=v.findViewById(R.id.clearCache_FP);
    }
}
