package com.example.ronak.demonew;

import org.json.JSONObject;

/**
 * Created by Ronak on 12/3/2016.
 */
public interface ChangePasswordModelInt {
    interface onChangePasswordRequestFinishListener {

        void onChangePasswordSuccess(JSONObject changeObj);

        void onFailToChangePassword(String message);

        void onNewPasswordError();

        void onConfirmPasswordError();

        void onNewPasswordLengthError();

        void onPasswordMatchError();

        void onChangePasswordRequiredFieldError();

        void onChangePasswordRequestError();
    }

    public void changePassword(String newPassword, String confirmNewPassword, String otpToken, String contactNumber, onChangePasswordRequestFinishListener listener);

}
