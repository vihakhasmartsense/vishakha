package com.example.ronak.demonew;

/**
 * Created by Ronak on 11/8/2016.
 */
public interface LoginInt {
    void loginValidateResult(int userIdError);

    void onFailLogin(String failMessage);

    void onRequestFail();
}
