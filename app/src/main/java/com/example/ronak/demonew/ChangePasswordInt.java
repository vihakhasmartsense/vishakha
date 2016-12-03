package com.example.ronak.demonew;

import org.json.JSONObject;

/**
 * Created by Ronak on 12/3/2016.
 */
public interface ChangePasswordInt {
    void changePasswordSuccessfull(JSONObject changeObj);

    void changePasswordFail(String message);

    void changePasswordValidateResult(int passwordError);

    void changePasswordRequestError();
}
