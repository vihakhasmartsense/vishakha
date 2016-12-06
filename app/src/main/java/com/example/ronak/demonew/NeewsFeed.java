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
        toolbar.setTitle("Newsfeed");
        //toolbar.setTitleTextColor();
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            getNewsData();
            requestPresenter = new RequestPresenter();
            requestPresenter.getNewsList(this);
        } else {
            //UtilClass.displyMessage(getString(R.string.msgCheckInternet), (View.OnClickListener) this, 0);
        }
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

    public void getNewsData() {
        String tag = "newsFeed";
        StringRequest newsRequest = new StringRequest(Request.Method.GET, UtilClass.getNewsFeedUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("*****", "NewsFeedResponse" + response);

                try {
                    final NewsObject newsObj = new NewsObject();
                    if (newsObj != null) {
                        Log.e("status", newsObj.toString());
                    } else {
                        Log.e("newsObj", "Null");
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplication(),"NewObj Null",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //newsListCallback.onFailRequestNewsList();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        newsRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MadhaparGamApp.getAppInstance().addToRequestQueue(newsRequest, tag);
    }

    private void sendNewsList(final JSONArray newsArray) {
        List<NewsObject> newsList = new ArrayList<>();
        if (newsArray != null) {
            for (int i = 0; i < newsArray.length(); i++) {
                NewsObject newsObject = new NewsObject();
                newsObject.setNewsTitle(newsArray.optJSONObject(i).optString("newsTitle"));
                JSONObject location = newsArray.optJSONObject(i).optJSONObject("newsLocation");
                if (location != null)
                    newsObject.setNewsCity(location.optString("locationName"));
                newsObject.setNewsDescription(newsArray.optJSONObject(i).optString("newsDescription"));
                newsObject.setNewsCommentCount(newsArray.optJSONObject(i).optString("newsComments"));
                newsObject.setNewsImageArray(newsArray.optJSONObject(i).optJSONArray("newsImages").toString());
                newsObject.setNewsLikeCount(newsArray.optJSONObject(i).optString("newsLikes"));
                newsObject.setNewsId(newsArray.optJSONObject(i).optString("newsId"));
                newsObject.setNewsDataAndTime(newsArray.optJSONObject(i).optString("newsCreatedDate"));
                newsObject.setCommented(newsArray.optJSONObject(i).optBoolean("isCommented"));
                JSONObject newsCatagoryObj = newsArray.optJSONObject(i).optJSONObject("newsCategory");
                if (newsCatagoryObj != null) {
                    newsObject.setNewsCatagory(newsCatagoryObj.optString("categoryName"));
                }
                String newsStatusId = newsArray.optJSONObject(i).optString("newsStatusId");
                newsObject.setNewsStatusId(newsStatusId);
                newsList.add(newsObject);
            }
        }
        //newsListCallback.onSuccessNewsList(newsList);
    }


    @Override
    public void onSuccessNewsList(List<NewsObject> newsList) {
        UtilClass.hideProgress();
        if (newsList.size() > 0) {
            if (newsDataAdapter == null) {
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

    @Override
    public void onFailRequest() {

    }

    @Override
    public void onFailResponse(String message) {

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
