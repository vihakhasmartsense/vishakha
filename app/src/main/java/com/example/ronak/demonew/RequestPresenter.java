package com.example.ronak.demonew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ronak on 11/9/2016.
 */
public class RequestPresenter implements RequestPresenterInt,NewsFeedModelInt.NewsListCallback, NewsFeedModelInt.CommentListCallback, NewsFeedModelInt.NewsLikeCommentUpdate {
    HomeViewInt homeViewInt;
    private NewsFeedModel newsModel;
    NewsLikeCommentUpdateCallbackI likeUpdateCallback;
    private CommentListCallbackM commentListCallback;

    @Override
    public void onSuccessNewsList(List<NewsObject> newsList) {
        homeViewInt.onSuccessNewsList(newsList);
    }
    @Override
    public void getNewsList(HomeViewInt homeViewInt) {
        this.homeViewInt = homeViewInt;
        NewsFeedModel newsFeedModel = new NewsFeedModel();
        newsFeedModel.getNewsData(this);

    }

    @Override
    public void updateLikeComment(String newsId, String newsStatus, String newsComment, NewsLikeCommentUpdateCallbackI updateCallback) {
        this.likeUpdateCallback = updateCallback;
        newsModel = new NewsFeedModel();
        newsModel.updateNewsLikeComment(newsId, newsStatus, newsComment, this);
    }

    @Override
    public void removeLike(String newsStatusId, NewsLikeCommentUpdateCallbackI updateCallback) {
        this.likeUpdateCallback = updateCallback;
        if (newsModel == null)
            newsModel = new NewsFeedModel();
        newsModel.removeNewsLike(newsStatusId, this);
    }

    @Override
    public void getNewsCommentList(String newsId, String newsStatusId,  CommentListCallbackM commentCallback) {
        this.commentListCallback = commentCallback;
        if (newsModel == null) {
            newsModel = new NewsFeedModel();
        }
        newsModel.getCommentList(newsId, newsStatusId, this);
    }

    @Override
    public void updateComment(String newsId, String newsStatus, String newsComment, String newsStatusId, NewsLikeCommentUpdateCallbackI callback) {
        likeUpdateCallback = callback;
        if (newsModel == null) {
            newsModel = new NewsFeedModel();
        }
        newsModel.updateComment(newsId, newsStatus, newsStatusId, newsComment, this);
    }

    @Override
    public void onFailRequestNewsList() {
        homeViewInt.onFailRequest();
    }

    @Override
    public void onFailResponseNewsList(String message) {
        homeViewInt.onFailResponse(message);
    }


    @Override
    public void onSuccessLikeComment(JSONObject updateObj) {
        likeUpdateCallback.successfulUpdateLike(updateObj);
    }

    @Override
    public void onFailResponseNewsLikeComment(String message) {
        likeUpdateCallback.failUpdateResponse(message);
    }

    @Override
    public void onFailRequestNewsLikeComment() {
        likeUpdateCallback.failUpdateRequest();
    }


    @Override
    public void onSuccessCommentList(JSONArray commentList) {
        commentListCallback.onSuccessCommentList(commentList);
    }

    @Override
    public void onFailCommentListRequest() {
        commentListCallback.onFailRequest();
    }

    @Override
    public void onFailCommentResponse(String message) {
        commentListCallback.onFailResponse(message);
    }
}
