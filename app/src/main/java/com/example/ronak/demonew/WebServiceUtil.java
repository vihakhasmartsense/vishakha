package com.example.ronak.demonew;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ronak.demonew.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ronak on 12/5/2016.
 */
public class WebServiceUtil extends AsyncTask<String, Integer, String> {

    HashMap<String, String> formValues;
    HashMap<String, String> fileValues;
    private UploadInterface uploadInterface;

    Boolean uploadFile;
    String reqUrl;
    String result;
    Context context;
    public WebServiceUtil(EditProfileActivity context, String profileUpdateUrl, HashMap<String, String> params, boolean b, HashMap<String, String> params1, EditProfileActivity editProfileActivity1) {
        this.context = context;
        this.reqUrl = profileUpdateUrl;
        this.formValues = params;
        this.uploadInterface = editProfileActivity1;

        this.uploadFile = b;
        if (uploadFile) {

            this.fileValues = params1;
        }
    }


    @Override
    protected void onPreExecute() {

        super.onPreExecute();

    }
    @Override
    protected String doInBackground(String... strings) {
        List<String> response = null;
        Log.e("doInBack","starts");
        try {
            MultipartUtility mu = new MultipartUtility(context, reqUrl, "UTF-8");

            for (Map.Entry<String, String> entry : formValues.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                mu.addFormField(key, value);
            }
            if (uploadFile) {
                for (Map.Entry<String, String> entry : fileValues.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    File file = new File(value);
                    Log.e("Path","aaaaaaaaaa"+file.getAbsolutePath());
                    mu.addFilePart(key, file);
                }
            }
            response = mu.finish();


        } catch (IOException e) {

            e.printStackTrace();
        }
        if (response != null) {
            return response.toString();
        } else {
            return null;
        }
    }
    protected void onPostExecute(String result) {

        if (result != null) {
            try {
                JSONArray picArray = new JSONArray(result);
                JSONObject picObj = picArray.optJSONObject(0);
                if (picObj.optInt("status") == Constants.ResponseCode.SuccessCode) {
                    uploadInterface.onSuccessUploadImage(picObj);
                } else {
                    uploadInterface.onFailUpload(picObj.optString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                uploadInterface.onFailUpload("");
            }
        }
        uploadInterface.onFailUpload("");
    }
}
