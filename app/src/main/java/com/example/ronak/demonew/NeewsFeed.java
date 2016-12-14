package com.example.ronak.demonew;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ronak.demonew.Adapter.NewsListAdapter;
import com.example.ronak.demonew.Application.MadhaparGamApp;
import com.example.ronak.demonew.Util.Constants;
import com.example.ronak.demonew.Util.UtilClass;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.signin.EmailSignInConfig;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NeewsFeed extends AppCompatActivity implements HomeViewInt {
    private RequestPresenter requestPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView rvNewsList;
    @BindView(R.id.llNewsListPlaceholder)
    LinearLayout llNewsListPlaceholder;
    @BindView(R.id.srlNewsList)
    SwipeRefreshLayout srlNewsList;
    private NewsListAdapter newsDataAdapter;
    private LinearLayoutManager mLayoutManager;
    private NewsObject newsObj = new NewsObject();
    public Context mContext;
    List<NewsObject> newsList;
    int position = 0;
    private Activity activity;
    private static final int CATAGORY_REQUEST_CODE = 120;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Button btnSignOut,btnChangePass,btnUserProfile;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neews_feed);
        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        srlNewsList = (SwipeRefreshLayout) findViewById(R.id.srlNewsList);
        srlNewsList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    if (UtilClass.isInternetAvailabel(mContext)) {
                        UtilClass.showProgress(mContext, getString(R.string.msgPleaseWait));
                        if (requestPresenter == null) {
                            requestPresenter = new RequestPresenter();
                        }
                        requestPresenter.getNewsList(NeewsFeed.this);
                        srlNewsList.setRefreshing(true);
                    } else {
                        if (srlNewsList.isRefreshing()) {
                            srlNewsList.setRefreshing(false);
                        }
                        UtilClass.displyMessage(getString(R.string.msgCheckInternet), mContext, 0);
                    }

            }
        });
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            //getNewsData();
            requestPresenter = new RequestPresenter();
            requestPresenter.getNewsList(this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }
        mLayoutManager = new LinearLayoutManager(mContext);
        mContext = this;
        this.registerReceiver(pushReceiver, new IntentFilter(Constants.PushConstant.PushActionNews));
//        rvNewsList.setNestedScrollingEnabled(true);

        btnSignOut = (Button) findViewById(R.id.btnSignout);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceUtil.clear();
                SharedPreferenceUtil.save();
                UtilClass.changeActivity(NeewsFeed.this,MainActivity.class,true);
            }
        });

        btnChangePass = (Button)findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilClass.changeActivity(NeewsFeed.this,ChangePasswordActivity.class,false);
            }
        });
        btnUserProfile = (Button) findViewById(R.id.btnUserProfile);
        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilClass.changeActivity(NeewsFeed.this,EditProfileActivity.class,false);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onSuccessNewsList(List<NewsObject> newsList) {
        UtilClass.hideProgress();
        requestPresenter.getNewsDetail("1", new NewsDetailViewInt() {
            @Override
            public void onSuccessNewsDetail(NewsObject newsObject) {

            }

            @Override
            public void onFailRequest() {

            }

            @Override
            public void onFailResponse(String message) {

            }
        });
        UtilClass.hideProgress();
        if (newsList.size() > 0) {
            if (newsDataAdapter == null) {
                srlNewsList.setVisibility(View.VISIBLE);
//                llNewsListPlaceholder.setVisibility(View.GONE);
                newsDataAdapter = new NewsListAdapter(this, newsList, rvNewsList, mLayoutManager, this);
                mLayoutManager = new LinearLayoutManager(this);
                if (rvNewsList == null) {
                    rvNewsList = (RecyclerView) findViewById(R.id.recycler_view);
                }
                rvNewsList.setLayoutManager(mLayoutManager);
                rvNewsList.setAdapter(newsDataAdapter);
            } else {
                newsDataAdapter.updateAdapter(newsList);
                //applyFilter();
            }
        }
    }
    public BroadcastReceiver pushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (srlNewsList.isRefreshing()) {
                srlNewsList.setRefreshing(false);
            }
            WakeLocker.acquire(context);
            if (requestPresenter == null) {
                requestPresenter = new RequestPresenter();
            }
            requestPresenter.getNewsList(NeewsFeed.this);
            WakeLocker.release();
        }
    };
    @Override
    public void onFailRequest() {
               if (srlNewsList.isRefreshing()) {
                srlNewsList.setRefreshing(false);
            }
            UtilClass.hideProgress();
            UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), activity, 0);

    }

    @Override
    public void onFailResponse(String message) {
        if (srlNewsList.isRefreshing()) {
            srlNewsList.setRefreshing(false);
        }
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, activity, 0);
    }
    @Override
    public void onStart() {
        this.invalidateOptionsMenu();

                requestPresenter = new RequestPresenter();
                requestPresenter.getNewsList(this);

            super.onStart();
    }
    @Override
    public void onResume() {

        super.onResume();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CATAGORY_REQUEST_CODE) {
                if (newsDataAdapter != null && newsDataAdapter.getFilter() != null) {
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void updateViews(final boolean listVisible) {
        if (listVisible) {
            llNewsListPlaceholder.setVisibility(View.GONE);
            rvNewsList.setVisibility(View.VISIBLE);
        } else {
            llNewsListPlaceholder.setVisibility(View.VISIBLE);
            rvNewsList.setVisibility(View.GONE);
        }
    }

}
