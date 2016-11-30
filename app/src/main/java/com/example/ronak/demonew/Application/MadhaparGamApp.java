package com.example.ronak.demonew.Application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.mpt.storage.SharedPreferenceUtil;

/**
 * Created by smartsense on 22/09/16.
 */

public class MadhaparGamApp extends Application {
    private RequestQueue reuestQueque;
    private static MadhaparGamApp appInstnace;
    private static final String TAG = "MADHAPARGAM_APP";

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceUtil.init(this);
        appInstnace = this;
    }
    public RequestQueue getRequestQueue() {
        if (reuestQueque == null) {
            reuestQueque = Volley.newRequestQueue(getApplicationContext());
        }
        return reuestQueque;
    }

    public static synchronized MadhaparGamApp getAppInstance() {
        return appInstnace;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueueCustom(Request<T> req, String tag, HttpStack stack) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext(), stack);
        requestQueue1.add(req);
    }
}
