package com.example.ronak.demonew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ronak.demonew.Application.MadhaparGamApp;
import com.example.ronak.demonew.Util.Constants;
import com.example.ronak.demonew.Util.UtilClass;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements LoginInt {

    EditText etLoginId;
    EditText etLoginPassword;
    ImageView ivClose;
    TextView tvForgetPassword;
    TextView tvUserSignUp;
    Button btnLogin;
    private LoginInt loginInt;
    Context context = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
//    String tok = SharedPreferenceUtil.getString("token","null");
    String tok = "";
    String token = SharedPreferenceUtil.getString("token","null token");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!SharedPreferenceUtil.getString("token","null token").equalsIgnoreCase("null token")) {
            UtilClass.changeActivity(MainActivity.this, NeewsFeed.class, true);
            Log.e("Login","Complete "+SharedPreferenceUtil.getString("token","null Token"));
        }
        else {
            setContentView(R.layout.activity_main);
            etLoginId = (EditText) findViewById(R.id.etLoginId);
            loginInt = this;
            etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
            etLoginId.setText("7069102725");
            etLoginPassword.setText("vishakha");

            tvForgetPassword = (TextView) findViewById(R.id.tvForgetPassword);
            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UtilClass.closeKeyboard(MainActivity.this);
                    if (UtilClass.isInternetAvailabel(MainActivity.this)) {
                        doLogin(etLoginId.getText().toString(), etLoginPassword.getText().toString());

                    } else {

                        UtilClass.displyMessage(getString(R.string.msgCheckInternet), context, 0);

                    }
                }
            });
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        }

    }


    @Override
    public void loginValidateResult(int userIdError) {
        UtilClass.hideProgress();
        if (userIdError == UtilClass.Success) {
            UtilClass.changeActivity(MainActivity.this, NeewsFeed.class, true);
        }
    }

    @Override
    public void onFailLogin(String failMessage) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(failMessage, context, Toast.LENGTH_SHORT);
    }

    @Override
    public void onRequestFail() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), context, Toast.LENGTH_SHORT);
    }

    private void doLogin(final String contactNumber, final String password) {
        String tag = "login";
        StringRequest loginRequest = new StringRequest(Request.Method.POST, UtilClass.getLoginUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("*****", "LoginResponse" + response);
                if (response != null && !response.equalsIgnoreCase("")) {
                    try {
                        JSONObject logiObject = new JSONObject(response);
                        if (logiObject != null) {
                            if (logiObject.optInt("status") == Constants.ResponseCode.LoginSuccessCode) {
                                SharedPreferenceUtil.putValue(Constants.UserData.token, logiObject.optString("token"));
                                //SharedPreferenceUtil.save();
                                Log.e("save","Complete "+SharedPreferenceUtil.getString("token","null Token"));
                                //Toast.makeText(getApplicationContext(),"NewsFeed Open",Toast.LENGTH_SHORT).show();
                                JSONObject userObject = logiObject.optJSONObject("user");
                                if (userObject != null) {
                                    try {
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserId, userObject.optString("userId"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserMobileNo, userObject.optString("userMobileNo"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserFirstName, userObject.optString("userFirstName"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserLastName, userObject.optString("userLastName"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserLocationId, userObject.optString("locationId"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserLocationName, userObject.optString("locationName"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserProfilePic, userObject.optString("userProfilePic"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserRegistrationId, userObject.optString("userRegistrationId"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.isVerified, userObject.optBoolean("isVerified"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserFBProfileName, userObject.optString("userFBProfileName"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserEmail, userObject.optString("email"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserDOB, userObject.optString("userDOB"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserMiddleName, userObject.optString("userMiddleName"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserLocationName, userObject.optString("locationName"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserBloodGroup, userObject.optString("userBloodGroup"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserRegistrationId, userObject.optString("userRegistrationId"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.isVerified, userObject.optBoolean("isVerified"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserFBProfileName, userObject.optString("userFBProfileName"));SharedPreferenceUtil.save();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    UtilClass.changeActivity(MainActivity.this, NeewsFeed.class, true);
                                }
                                SharedPreferenceUtil.save();
                            } else {
                                UtilClass.displyMessage("NewsFeed Open", context, Toast.LENGTH_SHORT);
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
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("password", password);
                params.put("userMobileNo", contactNumber);
                params.put("deviceToken", "12341234123412341234123412341234");
                return params;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
//                return params;
//            }
        };

        loginRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(loginRequest, tag);

    }
}
