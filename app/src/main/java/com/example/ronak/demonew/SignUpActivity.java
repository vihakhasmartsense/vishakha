package com.example.ronak.demonew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ronak.demonew.Util.UtilClass;

public class SignUpActivity extends AppCompatActivity implements SignUpInt {
    EditText etFname,etLname,etMiddleName,etSignPassword,etMobileNumber;
    Button btnSignUp;
    PresenterInt presenter;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etFname = (EditText) findViewById(R.id.etFirstName);
        etLname = (EditText) findViewById(R.id.etLasttName);
        etMiddleName = (EditText) findViewById(R.id.etMiddleName);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
        etSignPassword = (EditText) findViewById(R.id.etSignUpPassord);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilClass.closeKeyboard(SignUpActivity.this);
                if (UtilClass.isInternetAvailabel(SignUpActivity.this)) {
                    UtilClass.showProgress(SignUpActivity.this, getString(R.string.msgPleaseWait));
                    Log.e("btn","click");
                    presenter = new Presenter(SignUpActivity.this);
                    presenter.signUpValidationCredentials(etFname.getText().toString(), etLname.getText().toString(), etMobileNumber.getText().toString(), etSignPassword.getText().toString(), etMiddleName.getText().toString(), SignUpActivity.this);
                } else {
                    UtilClass.displyMessage(getString(R.string.msgCheckInternet), SignUpActivity.this, 0);
                }
            }
        });
    }
    @Override
    public void signUpValidateResult(int success) {
        UtilClass.hideProgress();
        switch (success){
            case UtilClass.RequiredFieldError: {
                Toast.makeText(this,"Enter Required Field",Toast.LENGTH_SHORT).show();
                break;
            }
            case UtilClass.MiddleNameError:{
                Toast.makeText(this,"Middle Name is Required",Toast.LENGTH_SHORT).show();
                break;
            }
            case UtilClass.FirstNameError:{
                Toast.makeText(this,"First name is Required",Toast.LENGTH_SHORT).show();
                break;
            }
            case UtilClass.FirstNameLenght:{
                Toast.makeText(this,"Enter valid First Name (1-50 Chars)",Toast.LENGTH_SHORT).show();
                break;
            }
            case UtilClass.LastNameBlankError:{
                Toast.makeText(this,"Last Name is Required",Toast.LENGTH_SHORT).show();
                Log.e("Last Name","Error");
                break;
            }
            case UtilClass.LastNameLength:{
                Toast.makeText(this,"Please Enter Valid Last Name (1-50 chars)",Toast.LENGTH_SHORT).show();

                break;
            }
            case UtilClass.MiddleNameLength:{
                Toast.makeText(this,"Enter valid Father's Name(1-50 chars)",Toast.LENGTH_SHORT).show();
                break;
            }
            case UtilClass.PasswordError:{
                Toast.makeText(this,"Enter valid Password",Toast.LENGTH_SHORT).show();
                break;
            }
            case UtilClass.PasswordLengthError:{
                Toast.makeText(this,"Enter Password Greater than 6 Chars",Toast.LENGTH_SHORT).show();
                break;
            }
            case UtilClass.UserIdError:{
                Toast.makeText(this,"Mobile number is Required",Toast.LENGTH_SHORT).show();
                break;
            }
            case UtilClass.UserIdLengthError:{
                Toast.makeText(this,"Contact No length is required 7 to 10 digit",Toast.LENGTH_SHORT).show();
                break;
            }
            case UtilClass.Success:{
                UtilClass.changeActivity(SignUpActivity.this,MainActivity.class,false);
                break;
            }
            default:{
                setContentView(R.layout.activity_sign_up);
                break;
            }
        }
    }

    @Override
    public void signUpRequestError() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), SignUpActivity.this, 0);
    }
    @Override
    public void signUpResponseError(String signUpError) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(signUpError, SignUpActivity.this, 0);
    }
}
