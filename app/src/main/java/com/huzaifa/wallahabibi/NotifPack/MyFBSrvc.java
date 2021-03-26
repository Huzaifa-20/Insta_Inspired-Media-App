package com.huzaifa.wallahabibi.NotifPack;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFBSrvc extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        FirebaseUser fbu= FirebaseAuth.getInstance().getCurrentUser();

        String refreshToken = FirebaseInstanceId.getInstance().getToken();

        if (fbu != null){
            updateToken(refreshToken);
        }
    }

    private void updateToken(String refreshTok){
        FirebaseUser fbu= FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Tokens");

        Token tok=new Token(refreshTok);

        ref.child(fbu.getUid()).setValue(tok);
    }
}
