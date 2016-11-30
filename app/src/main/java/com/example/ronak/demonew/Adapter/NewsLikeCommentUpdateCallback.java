package com.example.ronak.demonew.Adapter;

import org.json.JSONObject;

/**
 * Created by smartsense on 08/10/16.
 */

public interface NewsLikeCommentUpdateCallback {
    void successfulUpdateLike(JSONObject updateObj);

    void failUpdateResponse(String message);

    void failUpdateRequest();
}
