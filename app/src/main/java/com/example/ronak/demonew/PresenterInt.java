package com.example.ronak.demonew;

import android.app.Activity;

/**
 * Created by Ronak on 11/29/2016.
 */
public interface PresenterInt {
    void commentCredential(String comment, CommentViewInt commentViewInt);

    void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish);

    void changePasswordCredential(String newPassword, String confirmNewPassword, String otpToken, String contactNumber, ChangePasswordInt changePasswordViewInt1);
}
