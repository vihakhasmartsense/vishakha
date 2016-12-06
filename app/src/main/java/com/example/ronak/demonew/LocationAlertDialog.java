package com.example.ronak.demonew;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;

/**
 * Created by Ronak on 12/5/2016.
 */
public class LocationAlertDialog extends AlertDialog.Builder implements ProfileUpdateCallback {

    public LocationAlertDialog(Context context) {
        super(context);
    }

    @Override
    public void onSuccessUpdateUserData(String name) {

    }

    @Override
    public void onFailUpdateUesrDate(String message) {

    }

    @Override
    public void onFailUpdateRequest() {

    }

    @Override
    public void onSuccessLocationList(JSONArray jsonArray) {

    }

    public void openLocationDialog(AppCompatActivity activity) {
    }
}
