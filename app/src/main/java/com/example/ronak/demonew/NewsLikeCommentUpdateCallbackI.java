package com.example.ronak.demonew;

import org.json.JSONObject;

/**
 * Created by Ronak on 11/29/2016.
 */
public interface NewsLikeCommentUpdateCallbackI {
    void successfulUpdateLike(JSONObject updateObj);

    void failUpdateResponse(String message);

    void failUpdateRequest();
}
