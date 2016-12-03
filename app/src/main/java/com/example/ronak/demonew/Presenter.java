package com.example.ronak.demonew;

import android.app.Activity;

import com.example.ronak.demonew.Util.UtilClass;

import org.json.JSONObject;

/**
 * Created by Ronak on 11/29/2016.
 */
public class Presenter implements PresenterInt, CommentModelInt.onCommentRequestFinishListener ,ChangepasswordModel.onChangePasswordRequestFinishListener{
    private CommentModel commentModel;
    private CommentViewInt commentViewInt1;
    private ChangePasswordInt changePasswordViewInt;
    private ChangepasswordModel changePasswordModel;
    @Override
    public void commentCredential(String comment, CommentViewInt commentViewInt) {
        commentViewInt1 = commentViewInt;
        commentModel = new CommentModel();
        commentModel.comment(comment, this);
    }

    @Override
    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {

    }

    @Override
    public void changePasswordCredential(String newPassword, String confirmNewPassword, String otpToken, String contactNumber, ChangePasswordInt changePasswordViewInt1) {
        changePasswordViewInt = changePasswordViewInt1;
        changePasswordModel = new ChangepasswordModel();
        changePasswordModel.changePassword(newPassword, confirmNewPassword, otpToken, contactNumber, this);
    }

    @Override
    public void onCommentBlankError() {
        commentViewInt1.onCommentresult(UtilClass.CommentBlankError);
    }

    @Override
    public void onCommentLengthError() {
        commentViewInt1.onCommentresult(UtilClass.CommentLenghtError);
    }

    @Override
    public void onChangePasswordSuccess(JSONObject changeObj) {
        changePasswordViewInt.changePasswordSuccessfull(changeObj);
    }

    @Override
    public void onFailToChangePassword(String message) {
        changePasswordViewInt.changePasswordFail(message);
    }

    @Override
    public void onNewPasswordError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.PasswordError);
    }

    @Override
    public void onConfirmPasswordError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.ConfirmPassword);
    }

    @Override
    public void onNewPasswordLengthError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.PasswordLengthError);
    }

    @Override
    public void onPasswordMatchError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.MatchPassword);
    }

    @Override
    public void onChangePasswordRequiredFieldError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.RequiredFieldError);
    }

    @Override
    public void onChangePasswordRequestError() {
        changePasswordViewInt.changePasswordRequestError();
    }
}
