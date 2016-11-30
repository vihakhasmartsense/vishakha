package com.example.ronak.demonew.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by smartsense on 21/09/16.
 */

public class UtilClass {
    public static final int PasswordError = 1;
    public static final int PasswordLengthError = 2;
    public static final int UserIdError = 3;
    public static final int UserIdLengthError = 4;
    public static final int Success = 5;
    public static final int SignUpRequestError = 100;
    public static final int LoginRequestError = 101;
    public static final int RequiredFieldError = 6;
    public static final int FirstNameError = 7;
    public static final int LastNameError = 8;
    public static final int FamilyMemberError = 9;
    public static final int MatchPassword = 10;
    public static final int ConfirmPassword = 11;
    public static final int FeedbackSubject = 12;
    public static final int FeedbackFieldError = 13;
    public static final int FeedbackSubjectRequiredError = 14;
    public static final int FeedbackdRequiredError = 16;
    public static final int FeedbackValidateError = 17;
    public static final int FeedbackSubjectValidateError = 15;
    public static final int FeedbackSubmitSuccess = 18;

    public static final int CommentBlankError = 19;
    public static final int CommentLenghtError = 20;


    public static final int RetryTimeOut = 20000;
    private static ProgressDialog pDialog;

    public static void displyMessage(String msg, Context context, int toastLenght) {

//        context.setTheme(R.style.NewTStyle);
//        Toast.makeText(context, msg, toastLenght).show();
//        context.setTheme(R.style.AppTheme);

    }

    public static void showProgress(Context activity, String message) {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog.cancel();
        }
        try {
//            activity.setTheme(R.style.NewTStyle);
//            pDialog = new ProgressDialog(activity);
//            pDialog.setMessage(message);
//            pDialog.setCancelable(false);
//            pDialog.show();
//            activity.setTheme(R.style.AppTheme);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {
        curruntActivity.startActivity(new Intent(curruntActivity, nextActivity));
        if (finish) {
            curruntActivity.finish();
        }
    }


    public static void hideProgress() {
        if (pDialog != null)
            try {
                pDialog.cancel();
                pDialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static String getSignupUrl() {
        Uri signupUri = Uri.parse(Constants.RequestConstants.SignupUrl).buildUpon().build();
        return signupUri.toString();
    }

    public static String getLoginUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.LoginUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getEventListUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.EventListUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getCategoryListUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.NewsCategoryListUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getLocationListUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.LocationListUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getCreateLocationUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.CreateLocationListUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getProfileUpdateUrl(String userId) {
        Uri builder = Uri.parse(Constants.RequestConstants.ProfileUpdateUrl + userId + "/").buildUpon().build();
        return builder.toString();
    }

    public static String getFeedbackUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.FeedbackUrl).buildUpon().build();
        return builder.toString();
    }


    public static String getProjectListUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.ProjectsListUrl).buildUpon().build();
        return builder.toString();
    }


    public static String getEventStatusListUrl(String eventId, String eventStatus) {
        Uri builder = Uri.parse(Constants.RequestConstants.EventStatusListUrl + eventId + "/" + eventStatus).buildUpon().build();
        return builder.toString();
    }

    public static String getEventStatusCreateUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.EventStatusCreateUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getEventStatusUpdateUrl(String eventId) {
        Uri builder = Uri.parse(Constants.RequestConstants.EventStatusUpdaetUrl + eventId + "/").buildUpon().build();
        return builder.toString();
    }

    public static String getEventDetailUrl(String eventId) {
        Uri builder = Uri.parse(Constants.RequestConstants.EventDetailUrl + eventId + "/").buildUpon().build();
        return builder.toString();
    }

    public static String getCommentUpdateUrl(String newsStatusID) {
        Uri builder = Uri.parse(Constants.RequestConstants.CommentUpdateUrl + newsStatusID + "/").buildUpon().build();
        return builder.toString();
    }

    public static String getLikeUpdateUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.LikeUpdateUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getCommentListUrk(String newsId, String newsStatusId) {
        Uri builder = Uri.parse(Constants.RequestConstants.CommentLstUrl + newsId + "/" + newsStatusId + "/").buildUpon().build();
        return builder.toString();
    }


    public static String getNewsDetailUrl(String newsId) {
        Uri uriBuilder = Uri.parse(Constants.RequestConstants.NewsDetailUrl + newsId + "/").buildUpon().build();
        return uriBuilder.toString();
    }


    public static String getNewsFeedUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.NewsFeedUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getUserVerifyUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.UserVerifyUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getUserListUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.UserListUrl).buildUpon().build();
        return builder.toString();
    }


    public static String getOtpUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.OtpUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getVerifyOtpUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.VerifyOtpUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getChangePasswordUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.ChangePasswordUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getRemoveLikeUrl(String newsStatusId) {
        Uri builder = Uri.parse(Constants.RequestConstants.RemoveLikeUrl + newsStatusId + "/").buildUpon().build();
        return builder.toString();
    }

    public static boolean isInternetAvailabel(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    static public void closeKeyboard(Activity a) {
        try {
            if (isKeyboardVisible(a)) {
                InputMethodManager imm = (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isKeyboardVisible(Activity activity) {
        Rect r = new Rect();
        View contentView = activity.findViewById(android.R.id.content);
        contentView.getWindowVisibleDisplayFrame(r);
        int screenHeight = contentView.getRootView().getHeight();

        int keypadHeight = screenHeight - r.bottom;

        return
                (keypadHeight > screenHeight * 0.15);
    }


    public static JSONArray removeJson(JSONArray source, int index) throws JSONException {
        if (index < 0 || index > source.length() - 1) {
            throw new IndexOutOfBoundsException();
        }
        final JSONArray copy = new JSONArray();
        for (int i = 0, count = source.length(); i < count; i++) {
            if (i != index) copy.put(source.get(i));
        }
        return copy;
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    static public Uri getImageUri(Context inContext, Bitmap inImage) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            // inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        } catch (Exception e) {
            e.getMessage();
            return Uri.parse("");
        }
    }

    static public String getRealPathFromURI(Uri uri, Activity a) {
        Cursor cursor = a.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public static String formatValue(double value) {
        int power;
        String suffix = " kmbt";
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int) StrictMath.log10(value);
        value = value / (Math.pow(10, (power / 3) * 3));
        formattedNumber = formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power / 3);
        return formattedNumber.length() > 4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }


}
