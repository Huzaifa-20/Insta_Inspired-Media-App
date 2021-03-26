package com.huzaifa.wallahabibi;

import com.huzaifa.wallahabibi.NotifPack.MyResponse;
import com.huzaifa.wallahabibi.NotifPack.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIServiceInterface {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA77IIJBo:APA91bFDsmKjDibv_osoOV4EXaEinDZAC9RQstG_l_NY3u_Wgo3E1Nze1XLjDsZjM188gya8Vr0LntDOSXT3yYrgqWVcWBIn3g-MngRdoVjqDjk2Qn3bI8w-7hNk0lpoQmSVOJKfJVMi"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
