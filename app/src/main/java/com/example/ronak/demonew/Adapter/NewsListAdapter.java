package com.example.ronak.demonew.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronak.demonew.CommentActivity;
import com.example.ronak.demonew.HomeViewInt;
import com.example.ronak.demonew.MainActivity;
import com.example.ronak.demonew.NeewsFeed;
import com.example.ronak.demonew.NewsObject;
import com.example.ronak.demonew.R;
import com.example.ronak.demonew.RequestPresenter;
import com.example.ronak.demonew.Util.Constants;
import com.example.ronak.demonew.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/7/2016.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> implements NewsLikeCommentUpdateCallback, HomeViewInt, Filterable, com.example.ronak.demonew.NewsLikeCommentUpdateCallbackI {
    List<NewsObject> newsList;
    String newsId1;
    private RecyclerView rvNewsList;
    private LinearLayoutManager rvManager;
    private NewsListAdapter newsDataAdapter;
    private RequestPresenter requestPresenter;
    private List<NewsObject> tempList;
    private NewsFilter newsFilter;
    private Activity activity;
    private LinearLayoutManager mLayoutManager;
    public Context mContext;
    private NeewsFeed hf;
    private LinearLayout placeHolder;

//    public NewsListAdapter(Context context, List<NewsObject> newsList, RecyclerView rvNewsList, LinearLayoutManager rvManager, NewsListAdapter hdf, NeewsFeed hf) {
//        this.rvNewsList = rvNewsList;
//        this.newsList = newsList;
//        this.context = context;
//        this.rvManager = rvManager;
//        this.tempList = newsList;
//        this.hf = hf;
//        this.placeHolder = placeHolder;
//    }

    public NewsListAdapter(Context context, List<NewsObject> newsList, RecyclerView rvNewsList, LinearLayoutManager mLayoutManager, NeewsFeed hf) {
        this.rvNewsList = rvNewsList;
        this.newsList = newsList;
        this.mContext = context;
        this.hf = hf;
        this.tempList = newsList;

        this.rvManager = mLayoutManager;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View viewNewsData = LayoutInflater.from(mContext).inflate(R.layout.news_list, parent, false);

        ButterKnife.bind(mContext, viewNewsData);
        return new MyViewHolder(viewNewsData);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewsObject newsObj = newsList.get(position);
        Log.i("title", "NewsFeedResponse " + newsObj.getNewsTitle().toString());
        Log.i("city", "NewsFeedResponse " + newsObj.getNewsCity().toString());
        Log.i("description", "NewsFeedResponse " + newsObj.getNewsDescription().toString());
        Log.i("datetime", "NewsFeedResponse " + newsObj.getNewsDataAndTime().toString());
        holder.tvNewsDescription.setText(newsObj.getNewsDescription().toString());
        holder.tvNewsCity.setText("\u2022 " + newsObj.getNewsCity());
        holder.tvNewsTitle.setText(newsObj.getNewsTitle());
        holder.tvNewsLikeCount.setText(newsObj.getNewsLikeCount());
        holder.tvNewsCommentCount.setText(newsObj.getNewsCommentCount());
        holder.tvNewsDateTime.setText(newsObj.getNewsDataAndTime());
        if(newsObj.isCommented()){
            holder.ivNewsComment.setImageResource(R.mipmap.ic_news_comment_filled);
        }else {
            holder.ivNewsComment.setImageResource(R.mipmap.ic_news_comment);
        }
        if(newsObj.getNewsStatusId().equalsIgnoreCase("")){
            holder.ivNewsLike.setImageResource(R.mipmap.ic_news_like);
        }else {
            holder.ivNewsLike.setImageResource(R.mipmap.ic_news_like_filled);
        }
        holder.ivNewsLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsId1 = newsObj.getNewsId();
                if(requestPresenter == null){
                    requestPresenter = new RequestPresenter();
                }
                if(newsObj.getNewsStatusId().equalsIgnoreCase("")){
                    requestPresenter.updateLikeComment(newsObj.getNewsId(),"2","",NewsListAdapter.this);
                }else{
                    requestPresenter.removeLike(newsObj.getNewsStatusId(),NewsListAdapter.this);
                }
            }
        });
        holder.ivNewsComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("newsId",newsObj.getNewsId());
                intent.putExtra("newsStatusId","1");
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void successfulUpdateLike(JSONObject updateObj) {
        if (UtilClass.isInternetAvailabel(mContext)) {
            UtilClass.showProgress(mContext, mContext.getString(R.string.msgPleaseWait));
            if (requestPresenter == null) {
                requestPresenter = new RequestPresenter();
            }
            requestPresenter.getNewsList((HomeViewInt) this);
        } else {
            //UtilClass.displyMessage(context.getString(R.string.msgPleaseWait), (View.OnClickListener) context, 0);
        }

    }

    @Override
    public void failUpdateResponse(String message) {
        UtilClass.hideProgress();
       // UtilClass.displyMessage(message, (View.OnClickListener) context, 0);
    }

    @Override
    public void failUpdateRequest() {
        UtilClass.hideProgress();
        //UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), (View.OnClickListener) context, 0);
    }

    @Override
    public void onSuccessNewsList(List<NewsObject> newsList) {
        UtilClass.hideProgress();
        updateAdapter(newsList);
    }


    @Override
    public void onFailRequest() {
        UtilClass.hideProgress();
      //  UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), (View.OnClickListener) context, 0);
    }

    @Override
    public void onFailResponse(String message) {
        UtilClass.hideProgress();
       // UtilClass.displyMessage(message, (View.OnClickListener) context, 0);
    }

    @Override
    public Filter getFilter() {
        if (newsFilter == null) {
            newsFilter = new NewsFilter(this);
        }
        return newsFilter;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.newsTitle)
        TextView tvNewsTitle;
//      @BindView(R.id.newsDateTime)
        TextView tvNewsDateTime;
//        @BindView(R.id.newsCity)
        TextView tvNewsCity;
//        @BindView(R.id.tvNewsDescription)
        TextView tvNewsDescription;
//        @BindView(R.id.tvNewsLikeCount)
        TextView tvNewsLikeCount;
//        @BindView(R.id.tvNewsCommentCount)
        TextView tvNewsCommentCount;
        ImageView ivNewsComment;
        ImageView ivNewsLike;
        LinearLayout linearLayout;
//        @BindView(R.id.ivNewsLike)
//        ImageView ivNewsLike;
//        @BindView(R.id.ivNewsComment)
//        ImageView ivNewsComment;
//        @BindView(R.id.llNewsUpdate)
//        LinearLayout llNewsUpdate;
//        @BindView(R.id.llNewsDetail)
//        LinearLayout llNewsDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNewsDescription= (TextView) itemView.findViewById(R.id.tvNewsDescription);
            tvNewsTitle = (TextView)itemView.findViewById(R.id.tvNewsTitle);
            tvNewsCity = (TextView)itemView.findViewById(R.id.tvNewsCity);
            tvNewsDateTime = (TextView)itemView.findViewById(R.id.tvNewsDateTime);
            tvNewsCommentCount = (TextView) itemView.findViewById(R.id.tvNewsCommentCount);
            tvNewsLikeCount = (TextView) itemView.findViewById(R.id.tvNewsLikeCount);
            ivNewsComment = (ImageView) itemView.findViewById(R.id.ivNewsComment);
            ivNewsLike = (ImageView)itemView.findViewById(R.id.ivNewsLike);
            ButterKnife.bind(mContext, itemView);
        }
    }

    public void updateAdapter(List<NewsObject> newsList) {
        UtilClass.hideProgress();
        this.newsList = newsList;
        this.tempList = newsList;
        notifyDataSetChanged();

    }


    private class NewsFilter extends Filter {
        private NewsListAdapter newsListAdapter;

        public NewsFilter(NewsListAdapter newsListAdapter) {
            this.newsListAdapter = newsListAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<NewsObject> filteredList = new ArrayList<>();
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).getNewsCatagory().equals(charSequence)) {
                    filteredList.add(tempList.get(i));
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            newsList = (List<NewsObject>) filterResults.values;
            if (newsList.size() > 0) {
                hf.updateViews(true);

            } else {
                hf.updateViews(false);
                Log.e("Data","No Data Found");
                Toast.makeText(mContext,"No data Found",Toast.LENGTH_SHORT).show();
            }
            notifyDataSetChanged();
        }
    }

    private void updateViews(boolean b) {
//        if (listVisible) {
//            llNewsListPlaceholder.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        } else {
//            llNewsListPlaceholder.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }
    }

//    private void applyFilter() {
//        String selectedValue = SharedPreferenceUtil.getString(Constants.DifferentData.SelectedCatagory, "clear");
//        if (selectedValue.equalsIgnoreCase("clear")) {
//            updateAdapter();
//        } else {
//            getFilter().filter(selectedValue);
//        }
//    }


}
