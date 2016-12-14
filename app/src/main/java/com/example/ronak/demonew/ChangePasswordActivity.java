package com.example.ronak.demonew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ronak.demonew.Util.Constants;
import com.example.ronak.demonew.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordInt {
    //@BindView(R.id.etChangeNewPassword)
    EditText etChangeNewPassword;
    //@BindView(R.id.etChangeConfirmNewPassword)
    EditText etChangeConfirmNewPassword;
    //@BindView(R.id.btnSave)
    Button btnSave,btncCancel;
    private PresenterInt presenter;
    private String otpToken;
    private ChangePasswordInt changePasswordInt;
    private ChangePasswordInt changePasswordViewInt = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etChangeNewPassword = (EditText) findViewById(R.id.etChangeNewPassword);
        etChangeConfirmNewPassword = (EditText) findViewById(R.id.etChangeConfirmNewPassword);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilClass.closeKeyboard(ChangePasswordActivity.this);
                if (UtilClass.isInternetAvailabel(ChangePasswordActivity.this)) {
                    UtilClass.showProgress(ChangePasswordActivity.this, getString(R.string.msgPleaseWait));
                    //presenter = new Presenter();
                    presenter.changePasswordCredential(etChangeNewPassword.getText().toString().trim(), etChangeConfirmNewPassword.getText().toString().trim(), otpToken, SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, ""), changePasswordViewInt);
                } else {
                    UtilClass.displyMessage(getString(R.string.msgCheckInternet), ChangePasswordActivity.this, 0);
                }
            }
        });
        btncCancel = (Button) findViewById(R.id.btnChnagePasswordCancel);
        btncCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilClass.closeKeyboard(ChangePasswordActivity.this);
                UtilClass.hideProgress();
                finish();
            }
        });
        if (getIntent() != null) {
            otpToken = getIntent().getStringExtra("otpToken");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public void changePasswordSuccessfull(JSONObject changeObj) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(changeObj.optString("message"), this, 0);
        UtilClass.changeActivity(this, MainActivity.class, true);
    }

    @Override
    public void changePasswordFail(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    public void changePasswordValidateResult(int passwordError) {
        UtilClass.hideProgress();
        if (passwordError == UtilClass.RequiredFieldError) {
            UtilClass.displyMessage(getString(R.string.enterrequiredfiels), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        } else if (passwordError == UtilClass.PasswordError) {
            UtilClass.displyMessage(getString(R.string.enternewpassword), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        } else if (passwordError == UtilClass.PasswordLengthError) {
            UtilClass.displyMessage(getString(R.string.passwordlength), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        } else if (passwordError == UtilClass.MatchPassword) {
            UtilClass.displyMessage(getString(R.string.passwordmatch), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        } else if (passwordError == UtilClass.ConfirmPassword) {
            UtilClass.displyMessage(getString(R.string.enterConfirmPassword), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void changePasswordRequestError() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }
}
