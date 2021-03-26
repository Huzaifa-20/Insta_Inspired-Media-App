package com.huzaifa.wallahabibi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class DeviceTokenFetchingSrvc extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
//        super.onTokenRefresh();
        String devTok= FirebaseInstanceId.getInstance().getToken();

    }
}