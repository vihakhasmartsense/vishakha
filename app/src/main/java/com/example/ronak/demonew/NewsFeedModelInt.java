package com.example.ronak.demonew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ronak on 10/7/2016.
 */
public interface NewsFeedModelInt {
    interface NewsListCallback {
        void onSuccessNewsList(List<NewsObject> newsArray);

        void onFailRequestNewsList();

        void onFailResponseNewsList(String message);
    }

    interface NewsLikeCommentUpdate {
        void onSuccessLikeComment(JSONObject updateObject);

        void onFailResponseNewsLikeComment(String message);

        void onFailRequestNewsLikeComment();
    }

    interface NewsDetailCallback {
        void onSuccessNewsDetail(NewsObject newsObject);

        void onFailNewsDetailRequest();

        void onFailNewsDetailResponse(String message);
    }

    interface CommentListCallback {
        void onSuccessCommentList(JSONArray commentList);

        void onFailCommentListRequest();

        void onFailCommentResponse(String message);
    }

    interface CatagoryListListener {
        void onSuccessCatagoryList(JSONArray catagoryList);

        void onFailCatagoryListRequest();

        void onFailCatagoryListResponse(String message);
    }

    void getCatagories(CatagoryListListener listener);

    void getNewsData(NewsListCallback newsListCallback);

    void updateNewsLikeComment(String newsId, String newsStatus, String newsComment, NewsLikeCommentUpdate updateCallback);

    void removeNewsLike(String newsStatusId, NewsLikeCommentUpdate updateCallback);

    void getNewsDetail(String newsId, NewsDetailCallback newsDetailCallback);

    void getCommentList(String newsId, String newsStausId, CommentListCallback commentListCallback);

    void updateComment(String newsId, String newsStatus, String newsStatusId, String newsComment, NewsLikeCommentUpdate updateCallback);

}
