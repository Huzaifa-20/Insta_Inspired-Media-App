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

public class AboutFragment extends Fragment {

    ImageButton backArrow;
    TextView terms;
    TextView policies;
    TextView ratesReviews;
    TextView report;
    TextView sharing;

    Context c;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_about,container,false);
        c=container.getContext();

        connectViews(v);
        setListeners();

        return v;
    }

    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FAbout);
        terms=v.findViewById(R.id.terms_FAbout);
        policies=v.findViewById(R.id.policies_FAbout);
        ratesReviews=v.findViewById(R.id.rateReviews_FAbout);
        report=v.findViewById(R.id.report_FAbout);
        sharing=v.findViewById(R.id.sharingApp_FAbout);
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

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportFragment reportFragment=new ReportFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_AHS,reportFragment).commit();
            }
        });
    }
}
