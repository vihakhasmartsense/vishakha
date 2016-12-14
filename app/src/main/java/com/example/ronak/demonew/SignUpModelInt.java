package com.example.ronak.demonew;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ronak on 12/8/2016.
 */
public interface SignUpModelInt {
    void signup(String firstName, String lastName, String contactNumber, String password, String middleName, SignUpModelInt.OnSignUpFinishedListener listener, AppCompatActivity activity);

    interface OnSignUpFinishedListener {

        void onSignUpSuccess();

        void onSignUpRequestError();

        void onSignUpFailError(String signUpError);

        void onSignUpFirstNameError();

        void onSignUplastNameError();

        void onMiddleNameError();

        void onSignupPasswordLengthError();

        void onsignUpPasswordError();

        void onSignUpContactLenghtError();

        void onSignupContactNumberError();

        void onSignUpRequiredFieldError();

        void onFirstNameLengthError();

        void onLasttNameLengthError();

        void onFatherstNameLengthError();
    }
}
