package com.example.ronak.demonew;

import org.json.JSONArray;

/**
 * Created by Ronak on 12/5/2016.
 */
public interface ProfileUpdateCallback {
    void onSuccessUpdateUserData(String name);

    void onFailUpdateUesrDate(String message);

    void onFailUpdateRequest();

    void onSuccessLocationList(JSONArray jsonArray);
}
