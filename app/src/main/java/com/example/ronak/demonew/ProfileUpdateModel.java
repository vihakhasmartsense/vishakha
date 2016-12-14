package com.example.ronak.demonew;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ronak.demonew.Application.MadhaparGamApp;
import com.example.ronak.demonew.Util.Constants;
import com.example.ronak.demonew.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ronak on 12/5/2016.
 */
public class ProfileUpdateModel implements ProfileUpdateModelInt {
    @Override
    public void updateProfileUserName(final Map<String, String> params, String userId, final ProfileUpdateListener mProfileUpdateListener) {
        String tag = "updateProfile";
        StringRequest profileRequest = new StringRequest(Request.Method.PUT, UtilClass.getProfileUpdateUrl(userId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("$$$$$$$$$$$$$$","resp"+response);
                    JSONObject infoObj = new JSONObject(response);
                    if (infoObj.optInt("status") == Constants.ResponseCode.SuccessCode) {

                        mProfileUpdateListener.onSuccessUpdateProfile(infoObj.optString("message"));
                        updateInformation(infoObj.optJSONObject("response"));
                    } else {
                        mProfileUpdateListener.onFailProfileUpdateRequest(infoObj.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProfileUpdateListener.onSuccessUpdateProfile("update");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        profileRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().
                addToRequestQueue(profileRequest, tag);
    }

    @Override
    public void getLocationList(final ProfileUpdateListener mProfileUpdateListener) {
        String tag = "getLocationList";
        StringRequest locationRequest = new StringRequest(Request.Method.GET, UtilClass.getLocationListUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("****", "LocationList" + response);
                if (response != null) {
                    try {
                        JSONObject locationObj = new JSONObject(response);
                        if (locationObj != null) {
                            if (locationObj.optInt("status") == Constants.ResponseCode.SuccessCode) {

                                mProfileUpdateListener.onSuccessLocationList(locationObj.optJSONArray("response"));
                            } else {
                                mProfileUpdateListener.onFailProfileUpdateRequest(locationObj.optString("message"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProfileUpdateListener.onFailProfileUpdateRequest();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }

        };
        locationRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().
                addToRequestQueue(locationRequest, tag);
    }

    @Override
    public void createNewLocation(String locationName, ProfileUpdateListener mProfileUpdateListener) {

    }
    private void updateInformation(JSONObject userData) {
        SharedPreferenceUtil.putValue(Constants.UserData.UserId, userData.optString("userId"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserMobileNo, userData.optString("userMobileNo"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserFirstName, userData.optString("userFirstName"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserMiddleName, userData.optString("userMiddleName"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserLastName, userData.optString("userLastName"));
        JSONObject locationObj = userData.optJSONObject("userLocation");
        if (locationObj != null) {
            SharedPreferenceUtil.putValue(Constants.UserData.UserLocationId, locationObj.optString("locationId"));
            SharedPreferenceUtil.putValue(Constants.UserData.UserLocationName, locationObj.optString("locationName"));
        }
        SharedPreferenceUtil.putValue(Constants.UserData.UserProfilePic, userData.optString("userProfilePic"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserProfession, userData.optString("userProfession"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserEmail, userData.optString("email"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserDOB, userData.optString("userDOB"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserBloodGroup, userData.optString("userBloodGroup"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserRegistrationId, userData.optString("userRegistrationId"));
        SharedPreferenceUtil.putValue(Constants.UserData.isVerified, userData.optBoolean("isVerified"));
        SharedPreferenceUtil.putValue(Constants.UserData.UserFBProfileName, userData.optString("userFBProfileName"));
        Log.e("",""+userData);
        SharedPreferenceUtil.save();
    }
}
