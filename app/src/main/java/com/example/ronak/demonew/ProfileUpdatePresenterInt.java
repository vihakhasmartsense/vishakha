package com.example.ronak.demonew;

import android.support.v7.app.AppCompatActivity;

import java.util.Map;

/**
 * Created by Ronak on 12/5/2016.
 */
public interface ProfileUpdatePresenterInt {
    void updateUserDetail(Map<String, String> params, String userId, ProfileUpdateCallback profileUpdateCallback);

    void getLocationList(ProfileUpdateCallback mProfileUpdateCallback);

    void addNewLocationAlert(AppCompatActivity activity);

    void createNewLocation(String locationName, ProfileUpdateCallback mProfileUpdateCallback);
}
