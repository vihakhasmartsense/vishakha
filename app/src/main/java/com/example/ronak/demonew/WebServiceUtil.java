package com.example.ronak.demonew;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Ronak on 12/5/2016.
 */
public class WebServiceUtil {

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

    public void execute() {
    }
}
