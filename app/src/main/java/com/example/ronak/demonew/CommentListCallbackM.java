package com.example.ronak.demonew;

import org.json.JSONArray;

/**
 * Created by Ronak on 11/29/2016.
 */
public interface CommentListCallbackM {
    void onSuccessCommentList(JSONArray commentList);

    void onFailRequest();

    void onFailResponse(String mesaage);
}
