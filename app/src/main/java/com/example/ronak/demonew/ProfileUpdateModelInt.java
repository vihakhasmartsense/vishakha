package com.example.ronak.demonew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Ronak on 12/5/2016.
 */
public interface ProfileUpdateModelInt {
    void updateProfileUserName(Map<String, String> params, String userId, ProfileUpdateListener mProfileUpdateListener);

    void getLocationList(ProfileUpdateListener mProfileUpdateListener);

    void createNewLocation(String locationName, ProfileUpdateListener mProfileUpdateListener);

    interface ProfileUpdateListener {
        void onSuccessUpdateProfile(String message);

        void onFailProfileUpdateRequest();

        void onFailProfileUpdateRequest(String message);

        void onSuccessLocationList(JSONArray locatioList);
    }
}
