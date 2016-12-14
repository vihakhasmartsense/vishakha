package com.example.ronak.demonew;

/**
 * Created by Ronak on 12/8/2016.
 */
public interface SignUpInt {
    public void signUpValidateResult(int success);

    public void signUpRequestError();

    public void signUpResponseError(String signUpError);
}
