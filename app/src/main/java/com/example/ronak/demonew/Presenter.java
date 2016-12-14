package com.example.ronak.demonew;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ronak.demonew.Util.UtilClass;

import org.json.JSONObject;

/**
 * Created by Ronak on 11/29/2016.
 */
public class Presenter implements PresenterInt,SignUpModelInt.OnSignUpFinishedListener, CommentModelInt.onCommentRequestFinishListener ,ChangepasswordModel.onChangePasswordRequestFinishListener{
    private CommentModel commentModel;
    private CommentViewInt commentViewInt1;
    private ChangePasswordInt changePasswordViewInt;
    private ChangepasswordModel changePasswordModel;
    private SignUpModel signUpModel;
    private SignUpInt signupView;

    public Presenter() {

    }
    @Override
    public void commentCredential(String comment, CommentViewInt commentViewInt) {
        commentViewInt1 = commentViewInt;
        commentModel = new CommentModel();
        commentModel.comment(comment, this);
    }
    public Presenter(SignUpActivity signUp) {
        this.signupView = signUp;
    }
    @Override
    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {

    }

    @Override
    public void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String middleName, AppCompatActivity activity) {
        signUpModel = new SignUpModel();
        signUpModel.signup(firstName, lastName, contactNumber, password, middleName, this, activity);
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

    @Override
    public void onSignUpSuccess() {

        signupView.signUpValidateResult(UtilClass.Success);
    }

    @Override
    public void onSignUpRequestError() {
        signupView.signUpRequestError();
    }

    @Override
    public void onSignUpFailError(String signUpError) {
        signupView.signUpResponseError(signUpError);
    }

    @Override
    public void onSignUpFirstNameError() {
        signupView.signUpValidateResult(UtilClass.FirstNameError);
    }

    @Override
    public void onSignUplastNameError() {
        signupView.signUpValidateResult(UtilClass.LastNameBlankError);
    }

    @Override
    public void onMiddleNameError() {
        signupView.signUpValidateResult(UtilClass.MiddleNameError);
    }

    @Override
    public void onSignupPasswordLengthError() {
        signupView.signUpValidateResult(UtilClass.PasswordLengthError);
    }

    @Override
    public void onsignUpPasswordError() {
        signupView.signUpValidateResult(UtilClass.PasswordError);
    }

    @Override
    public void onSignUpContactLenghtError() {
        signupView.signUpValidateResult(UtilClass.UserIdLengthError);
    }

    @Override
    public void onSignupContactNumberError() {
        signupView.signUpValidateResult(UtilClass.UserIdError);
    }

    @Override
    public void onSignUpRequiredFieldError() {
        signupView.signUpValidateResult(UtilClass.RequiredFieldError);
    }

    @Override
    public void onFirstNameLengthError() {
        signupView.signUpValidateResult(UtilClass.FirstNameLenght);
    }

    @Override
    public void onLasttNameLengthError() {
        signupView.signUpValidateResult(UtilClass.LastNameLength);
    }

    @Override
    public void onFatherstNameLengthError() {
        signupView.signUpValidateResult(UtilClass.MiddleNameLength);
    }
}
