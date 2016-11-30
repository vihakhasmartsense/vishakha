package com.example.ronak.demonew;

import com.example.ronak.demonew.Adapter.NewsListAdapter;

/**
 * Created by Ronak on 11/9/2016.
 */
public interface RequestPresenterInt {
    void getNewsList(HomeViewInt homeViewInt);
    void updateLikeComment(String newsId, String newsStatus, String newsComment, NewsLikeCommentUpdateCallbackI updateCallback);

    void removeLike(String newsStatusId, NewsLikeCommentUpdateCallbackI updateCallback);

    //void getNewsDetail(String newsId, NewsDetailViewInt newsDetailCallback);

    void getNewsCommentList(String newsId, String newsStatusId,  CommentListCallbackM commentCallback);

    void updateComment(String newsId, String newsStatus, String newsComment, String newsStatusId, NewsLikeCommentUpdateCallbackI callback);

}
