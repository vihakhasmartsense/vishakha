package com.example.ronak.demonew;

import org.json.JSONObject;

/**
 * Created by Ronak on 12/5/2016.
 */
public interface UploadInterface {
    void onSuccessUploadImage(JSONObject response);

    void onFailUpload(String message);
}
