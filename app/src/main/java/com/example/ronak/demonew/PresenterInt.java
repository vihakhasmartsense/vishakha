package com.example.ronak.demonew;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ronak on 11/29/2016.
 */
public interface PresenterInt {
    void commentCredential(String comment, CommentViewInt commentViewInt);

    void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish);

    void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String middleName, AppCompatActivity activity);


    void changePasswordCredential(String newPassword, String confirmNewPassword, String otpToken, String contactNumber, ChangePasswordInt changePasswordViewInt1);
}
