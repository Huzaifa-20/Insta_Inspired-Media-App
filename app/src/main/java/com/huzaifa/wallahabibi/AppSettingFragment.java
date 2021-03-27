package com.huzaifa.wallahabibi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AppSettingFragment extends Fragment {

    ImageButton backArrow;
    Switch pushNotifications;
    Switch cameraPermission;
    Switch micPermission;

    Context c;
    int MY_CAMERA_REQUEST_CODE=01;
    int MY_MIC_REQUEST_CODE=00;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_appsetting,container,false);
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

        cameraPermission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cameraPermission.isChecked()==true){
                    MainActivity.allowCamera=true;
                    Toast.makeText(c, "Camera permission granted", Toast.LENGTH_LONG).show();
                }
                else{
                    MainActivity.allowCamera=false;
                    Toast.makeText(c, "camera permission denied", Toast.LENGTH_LONG).show();
                }
            }
        });

        micPermission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(micPermission.isChecked()==true){
                    MainActivity.allowMicrophone=true;
                    Toast.makeText(c, "Mic permission granted", Toast.LENGTH_LONG).show();
                }
                else{
                    MainActivity.allowMicrophone=false;
                    Toast.makeText(c, "Mic permission denied", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void connectViews(View v) {
        backArrow=v.findViewById(R.id.back_arrow_FAS);
        pushNotifications=v.findViewById(R.id.pushNotifications_FAS);
        cameraPermission=v.findViewById(R.id.cameraPermission_FAS);
        micPermission=v.findViewById(R.id.microphonePermission_FAS);

        if(MainActivity.allowCamera)
        {
            cameraPermission.setChecked(true);
        }
        else
        {
            cameraPermission.setChecked(false);
        }

        if(MainActivity.allowMicrophone)
        {
            micPermission.setChecked(true);
        }
        else
        {
            micPermission.setChecked(false);
        }
    }
}
