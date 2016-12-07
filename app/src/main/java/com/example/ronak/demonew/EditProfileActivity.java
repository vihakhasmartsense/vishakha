package com.example.ronak.demonew;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ronak.demonew.Util.Constants;
import com.example.ronak.demonew.Util.UtilClass;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.mpt.storage.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnFocusChange;
import cn.qqtheme.framework.picker.OptionPicker;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.example.ronak.demonew.R.id.tool_bar_profile;

public class EditProfileActivity extends FragmentActivity implements ProfileUpdateCallback, UploadInterface {
    EditText fname, lname, middleName, bloodgroup, dOB, email, fatherName, mobileNumber,location;
    ImageView ivProfilePhotoSmall, ivProfilePhoto,ivEditIcon;
    private ProfileUpdatePresenter mProfileUpdatePresenter;
    private TimePickerDialog mDialogAll;
    Toolbar toolbarProfile;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM,yyyy");
    private Calendar calendar = Calendar.getInstance();
    private int selectedPosition;
    private static final int REQUEST_CONSTANT = 200;
    private int locationId = 0;
    private int whichSelect = 0;
    private int REQUEST_CAMERA = 102;
    private int SELECT_FILE = 101;
    private int PERMISSION_REQUEST_CODE = 103;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @OnFocusChange(R.id.etProfileEditProfileFirstName)
    public void fname(){
        UtilClass.closeKeyboard(EditProfileActivity.this);
        if (!fname.hasFocus()) {
            if (!fname.getTag().toString().equalsIgnoreCase(fname.getText().toString())) {
                if (mProfileUpdatePresenter == null) {
                    mProfileUpdatePresenter = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                if (!TextUtils.isEmpty(fname.getText().toString().trim())) {
                    fname.setTag(fname.getText().toString());
                    params.put("userFirstName", fname.getText().toString());
                    mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                }
            }
        } else {
            boolean isAnyEmpty = changeFocusIfEmpty(fname);
            if (!isAnyEmpty) {
                fname.setCursorVisible(true);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        toolbarProfile = (Toolbar) findViewById(tool_bar_profile);
        toolbarProfile.setTitle("Profile");
        fname = (EditText) findViewById(R.id.etProfileEditProfileFirstName);
        lname = (EditText) findViewById(R.id.etProfileEditLastName);
        bloodgroup = (EditText) findViewById(R.id.etEditProfileBloodGroup);
        dOB = (EditText) findViewById(R.id.etEditProfileDOB);
        email = (EditText) findViewById(R.id.etEditProfileEmail);
        ivProfilePhoto = (ImageView) findViewById(R.id.ivProfilePhoto);
        ivProfilePhotoSmall = (ImageView) findViewById(R.id.ivProfilePhotoSmall);
        fatherName = (EditText) findViewById(R.id.etEditProfileMiddleName);
        location = (EditText) findViewById(R.id.etEditProfileLocation);
        mobileNumber = (EditText) findViewById(R.id.etProfileMobileNumber);
        ivEditIcon = (ImageView) findViewById(R.id.ivEditIcon);

        ivEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePhoto();
            }
        });

        String firstName = SharedPreferenceUtil.getString(Constants.UserData.UserFirstName, "");
        fname.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserFirstName, ""));
        fname.setHint(getString(R.string.firstName));
        if (firstName != null && firstName.length() > 0) {
            String first = String.valueOf(firstName.charAt(0)).toUpperCase();
            fname.setText(first + firstName.substring(1));
        }
        String lastName = SharedPreferenceUtil.getString(Constants.UserData.UserLastName, "");
        lname.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserLastName, ""));
        lname.setHint(getString(R.string.lastName));
        if (lastName != null && lastName.length() > 0) {
            String first = String.valueOf(lastName.charAt(0)).toUpperCase();
            lname.setText(first + lastName.substring(1));
        }


        bloodgroup.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserBloodGroup, ""));

        if (SharedPreferenceUtil.getString(Constants.UserData.UserBloodGroup, "").equalsIgnoreCase("") || SharedPreferenceUtil.getString(Constants.UserData.UserBloodGroup, "").equalsIgnoreCase("null")) {
            bloodgroup.setText("");
        } else {
            bloodgroup.setText(SharedPreferenceUtil.getString(Constants.UserData.UserBloodGroup, ""));
        }
        bloodgroup.setHint(getString(R.string.HintBloodGroup));


        dOB.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserDOB, ""));
        if (SharedPreferenceUtil.getString(Constants.UserData.UserDOB, "").equalsIgnoreCase("") || SharedPreferenceUtil.getString(Constants.UserData.UserDOB, "").equalsIgnoreCase("null")) {
            dOB.setText("");
            dOB.setHint(getString(R.string.HintD_O_B));
        } else {
            dOB.setText(SharedPreferenceUtil.getString(Constants.UserData.UserDOB, ""));
        }
        dOB.setTag(getString(R.string.DateOfBirth));


        location.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserLocationName, ""));
        if (SharedPreferenceUtil.getString(Constants.UserData.UserLocationName, "").equalsIgnoreCase("") || SharedPreferenceUtil.getString(Constants.UserData.UserLocationName, "").equalsIgnoreCase("null")) {
            location.setText("");
            Log.e("location","here"+location.getText().toString());
        } else {
            location.setText(SharedPreferenceUtil.getString(Constants.UserData.UserLocationName, ""));
        }
        location.setHint(getString(R.string.HintLocation));

        mobileNumber.setHint(SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, ""));
        mobileNumber.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, ""));
        email.setText(SharedPreferenceUtil.getString(Constants.UserData.UserEmail, ""));
        email.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserEmail, ""));

        fatherName.setText(SharedPreferenceUtil.getString(Constants.UserData.UserMiddleName, ""));
        fatherName.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserMiddleName, ""));
        mobileNumber.setEnabled(false);
        fname.setCursorVisible(false);
        loadProfilePic(SharedPreferenceUtil.getString(Constants.UserData.UserProfilePic, ""));


        fatherName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                UtilClass.closeKeyboard(EditProfileActivity.this);
                if (!fatherName.hasFocus()) {
                    if (!fatherName.getTag().toString().equalsIgnoreCase(fatherName.getText().toString())) {
                        if (mProfileUpdatePresenter == null) {
                            mProfileUpdatePresenter = new ProfileUpdatePresenter();
                        }
                        Map<String, String> params = new HashMap();
                        if (!TextUtils.isEmpty(fatherName.getText().toString().trim())) {
                            fatherName.setTag(fatherName.getText().toString());
                            params.put("userMiddleName", fatherName.getText().toString());
                            mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                        }
                    }
                } else {
                    boolean isAnyEmpty = changeFocusIfEmpty(fatherName);
                    if (!isAnyEmpty) {
                        fatherName.setCursorVisible(true);
                    }
                }
            }
        });
        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!location.hasFocus()) {
                    if (!location.getTag().toString().equalsIgnoreCase(location.getText().toString())) {
                        if (mProfileUpdatePresenter == null) {
                            mProfileUpdatePresenter = new ProfileUpdatePresenter();
                        }
                        Map<String, String> params = new HashMap();
                        if (!TextUtils.isEmpty(location.getText().toString().trim())) {
                            location.setTag(location.getText().toString());
                            params.put("userLocation", locationId + "");
                            mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                        }
                    }
                } else {
                    boolean isAnyEmpty = changeFocusIfEmpty(location);
                    if (!isAnyEmpty) {
                        location.setCursorVisible(false);
                        startActivityForResult(new Intent(EditProfileActivity.this, LocationActivity.class), REQUEST_CONSTANT);
                        dOB.clearFocus();
                    }
                }
            }
        });

        fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                UtilClass.closeKeyboard(EditProfileActivity.this);
                if (!fname.hasFocus()) {
                    if (!fname.getTag().toString().equalsIgnoreCase(fname.getText().toString())) {
                        if (mProfileUpdatePresenter == null) {
                            mProfileUpdatePresenter = new ProfileUpdatePresenter();
                        }
                        Map<String, String> params = new HashMap();
                        if (!TextUtils.isEmpty(fname.getText().toString().trim())) {
                            fname.setTag(fname.getText().toString());
                            params.put("userFirstName", fname.getText().toString());
                            mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                        }
                    }
                } else {
                    boolean isAnyEmpty = changeFocusIfEmpty(fname);
                    if (!isAnyEmpty) {
                        fname.setCursorVisible(true);
                    }
                }
            }
        });
        lname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                UtilClass.closeKeyboard(EditProfileActivity.this);
                if (!lname.hasFocus()) {
                    if (!lname.getTag().toString().equalsIgnoreCase(lname.getText().toString())) {
                        if (mProfileUpdatePresenter == null) {
                            mProfileUpdatePresenter = new ProfileUpdatePresenter();
                        }
                        Map<String, String> params = new HashMap();
                        if (!TextUtils.isEmpty(lname.getText().toString().trim())) {
                            params.put("userLastName", lname.getText().toString());
                            lname.setTag(lname.getText().toString());
                            mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                        }

                    }
                } else {
                    boolean isAnyEmpty = changeFocusIfEmpty(lname);
                    if (!isAnyEmpty) {
                        lname.setCursorVisible(true);
                    }

                }
            }
        });
        mobileNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                UtilClass.closeKeyboard(EditProfileActivity.this);
                if (!mobileNumber.hasFocus()) {
                    if (!mobileNumber.getTag().toString().equalsIgnoreCase(mobileNumber.getText().toString())) {
                        if (mProfileUpdatePresenter == null) {
                            mProfileUpdatePresenter = new ProfileUpdatePresenter();
                        }
                        Map<String, String> params = new HashMap();
                        if (!TextUtils.isEmpty(mobileNumber.getText().toString().trim())) {
                            mobileNumber.setTag(mobileNumber.getText().toString());
                            params.put("userMobileNo", mobileNumber.getText().toString());
                            mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                        }
                    }
                } else {
                    boolean isAnyEmpty = changeFocusIfEmpty(mobileNumber);
                    if (!isAnyEmpty) {
                        mobileNumber.setCursorVisible(true);
                    }
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                UtilClass.closeKeyboard(EditProfileActivity.this);
                if (!email.hasFocus()) {
                    if (!email.getTag().toString().equalsIgnoreCase(email.getText().toString())) {
                        if (mProfileUpdatePresenter == null) {
                            mProfileUpdatePresenter = new ProfileUpdatePresenter();
                        }
                        Map<String, String> params = new HashMap();
                        email.setTag(email.getText().toString());
                        params.put("email", email.getText().toString());
                        mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                    }
                } else {
                    boolean isAnyEmpty = changeFocusIfEmpty(email);
                    if (!isAnyEmpty) {
                        email.setCursorVisible(true);
                    }
                }
            }
        });
        bloodgroup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!bloodgroup.hasFocus()) {
                    if (!bloodgroup.getTag().toString().equalsIgnoreCase(bloodgroup.getText().toString())) {
                        if (mProfileUpdatePresenter == null) {
                            mProfileUpdatePresenter = new ProfileUpdatePresenter();
                        }
                        Map<String, String> params = new HashMap();
                        if (!TextUtils.isEmpty(bloodgroup.getText().toString().trim())) {
                            bloodgroup.setTag(bloodgroup.getText().toString());
                            params.put("userBloodGroup", bloodgroup.getText().toString());
                            mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                        }
                    }
                } else {
                    boolean isAnyEmpty = changeFocusIfEmpty(bloodgroup);
                    if (!isAnyEmpty) {
                        bloodgroup.setCursorVisible(false);
                        openBloodGroupSelector();
                    }
                }
            }
        });
        dOB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!dOB.hasFocus()) {
                    if (!dOB.getTag().toString().equalsIgnoreCase(dOB.getText().toString())) {
                        if (mProfileUpdatePresenter == null) {
                            mProfileUpdatePresenter = new ProfileUpdatePresenter();
                        }
                        Map<String, String> params = new HashMap();
                        if (!TextUtils.isEmpty(dOB.getText().toString().trim())) {
                            dOB.setTag(dOB.getText().toString());
                            params.put("userDOB", getDateOfBirthToSend(dOB.getText().toString()));
                            mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                        }
                    }
                } else {
                    boolean isAnyEmpty = changeFocusIfEmpty(dOB);
                    if (!isAnyEmpty) {
                        dOB.setCursorVisible(false);
                        openDateSelector();
                    }
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CONSTANT) {
                try {
                    location.clearFocus();
                    if (data.getBooleanExtra("isSelected", false)) {
                        JSONObject selectedLocation = new JSONObject(data.getStringExtra("selectedCity"));
                        if (selectedLocation != null) {
                            location.setText(selectedLocation.optString("locationName"));
                            this.locationId = selectedLocation.optInt("locationId");
                            Map<String,String> params=new HashMap<>();
                            mProfileUpdatePresenter=new ProfileUpdatePresenter();
                            params.put("userLocation", locationId+"");
                            mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), EditProfileActivity.this);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CAMERA) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    String imageBase64 =
                            UtilClass.getRealPathFromURI(UtilClass.getImageUri(this, bitmap), this);
                    uploadPhoto(imageBase64);
                }

            } else {
                List<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (pathList != null) {
                    uploadPhoto(pathList.get(0));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadProfilePic(final String url) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(EditProfileActivity.this).load(Constants.RequestConstants.BaseUrlForImage + url).placeholder(R.drawable.icon_placeholde).error(R.drawable.icon_placeholde).into(ivProfilePhotoSmall);
                    Picasso.with(EditProfileActivity.this).load(Constants.RequestConstants.BaseUrlForImage + url).placeholder(R.drawable.cover_placeholder).error(R.drawable.cover_placeholder).into(ivProfilePhoto);
                }
            });
    }
    private void uploadPhoto(String imageData) {
        UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
        File file = new File(imageData);
        HashMap<String, String> params = new HashMap<>();
        params.put("userProfilePicture", file.getAbsoluteFile().toString());
        WebServiceUtil webServiceUtil = new WebServiceUtil(this, UtilClass.getProfileUpdateUrl(SharedPreferenceUtil.getString(Constants.UserData.UserId, "")), params, true, params, this);
        webServiceUtil.execute();

    }
    private String getDateOfBirthToSend(String dob) {
        SimpleDateFormat dateFormate1 = new SimpleDateFormat("yyyy-MM-dd");
        String returnDate = "";
        try {
            Date date = dateFormat.parse(dob);
            returnDate = dateFormate1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    private void openDateSelector() {
        long minMaxTime = 100L * 365 * 1000 * 60 * 60 * 24L;
        long curruntMillis = System.currentTimeMillis();
        try {
            Date date = dateFormat.parse(dOB.getText().toString().trim());
            calendar.setTime(date);
            curruntMillis = calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        calendar.setTimeInMillis(millseconds);
                        Date date = calendar.getTime();
                        if (date != null) {
                            String selectedDate = dateFormat.format(date);
                            dOB.setText(selectedDate);
                            dOB.clearFocus();
                            //updateDateDateOfBirth();
                        }
                    }
                })
                .setCancelStringId("Cancel")
                .setSureStringId("Done")
                .setCurrentMillseconds(curruntMillis)
                .setTitleStringId("")
                .setYearText("")
                .setMonthText("")
                .setDayText("")
                .setCyclic(true)
                .setThemeColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark))
                .setType(Type.YEAR_MONTH_DAY)
                .setMaxMillseconds(System.currentTimeMillis() + minMaxTime)
                .setMinMillseconds(System.currentTimeMillis() - minMaxTime)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.colorTransparentGray))
                .setWheelItemTextSelectorColor(ActivityCompat.getColor(this, R.color.colorBlack))
                .setWheelItemTextSize(18)
                .build();
        mDialogAll.show(getSupportFragmentManager(), "month");
        mDialogAll.setCancelable(false);

    }


    private void openBloodGroupSelector() {
        final String[] options = getResources().getStringArray(R.array.bloodGroups);
        OptionPicker picker = new OptionPicker(this, options);
        picker.setTextSize(18);
        picker.setLineColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));
        picker.setTitleTextColor(ActivityCompat.getColor(this, R.color.colorWhite));
        picker.setTopBackgroundColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));
        picker.setTopLineColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));
        picker.setTextColor(ActivityCompat.getColor(this, R.color.colorBlack), ActivityCompat.getColor(this, R.color.colorTransparentGray));
        picker.setSelectedIndex(selectedPosition);
        picker.setSubmitText("Done");
        picker.setCancelText("Cancel");
        picker.setCancelTextColor(ActivityCompat.getColor(this, R.color.colorWhite));
        picker.setSubmitTextColor(ActivityCompat.getColor(this, R.color.colorWhite));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                int i = Arrays.asList(options).indexOf(option);
                selectedPosition = i;
                bloodgroup.setText(option);
                bloodgroup.clearFocus();
                //updateBloodGroup();
            }
        });
        picker.show();
        picker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                bloodgroup.clearFocus();
            }
        });
    }

    private boolean changeFocusIfEmpty(EditText et) {
//        if (mDialogAll != null) {
//            if (!mDialogAll.isVisible()) {
//
//                dOB.clearFocus();
//            }
//        }
        if (TextUtils.isEmpty(fname.getText().toString())) {
            fname.requestFocus();
            et.setCursorVisible(false);
            fname.setCursorVisible(true);
            UtilClass.displyMessage("First name is required", this, 0);
            return true;
        } else if (fname.getText().toString().length() > 50) {
            fname.requestFocus();
            et.setCursorVisible(false);
            UtilClass.displyMessage("Please enter valid First name (1-50 Chars)", this, 0);
            fname.setCursorVisible(true);
            return true;
        } else if (TextUtils.isEmpty(lname.getText().toString())) {
            lname.requestFocus();
            et.setCursorVisible(false);
            UtilClass.displyMessage("Last name is required", this, 0);
            lname.setCursorVisible(true);
            return true;
        } else if (lname.getText().toString().length() > 50) {
            lname.requestFocus();
            et.setCursorVisible(false);
            UtilClass.displyMessage("Plwase enter valid Last Name (1-50 chrs)", this, 0);
            lname.setCursorVisible(true);
            return true;
        }
        else if (TextUtils.isEmpty(fatherName.getText().toString())) {
            fatherName.requestFocus();
            et.setCursorVisible(false);
            UtilClass.displyMessage("Father's full name is required", this, 0);
            fatherName.setCursorVisible(true);
            return true;
        }
            else if (fatherName.getText().toString().length() > 50) {
            fatherName.requestFocus();
            et.setCursorVisible(false);
            Toast.makeText(this,"Middle name Charater allow only %0 characters",Toast.LENGTH_SHORT).show();
            //UtilClass.displyMessage(getString(R.string.middleNameCharacter50), getActivity(), 0);
            fatherName.setCursorVisible(true);
            return true;
        }
        else if (TextUtils.isEmpty(mobileNumber.getHint().toString())) {
            middleName.requestFocus();
            et.setCursorVisible(false);
            if (TextUtils.isEmpty(middleName.getHint().toString())) {
                UtilClass.displyMessage("Contact number  is required", this, 0);
            } else {
                UtilClass.displyMessage("Please enter valid contact number", this, 0);
            }
            middleName.setCursorVisible(true);
            return true;
        } else if (!TextUtils.isEmpty(email.getText().toString()) && !UtilClass.isValidEmail(email.getText().toString())) {
            email.requestFocus();
            et.setCursorVisible(false);
            UtilClass.displyMessage("Please enter valid Email ", this, 0);
            email.setCursorVisible(true);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showMessageOKCancel("You need to allow access to external storage",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        PERMISSION_REQUEST_CODE);

                            }
                        }
                    });

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private void getPhotoFromGallary() {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
              startActivityForResult(intent, SELECT_FILE);
    }

    private void updatePhoto() {

        final CharSequence[] items = {getResources().getString(R.string.takePhoto), getResources().getString(R.string.selectYourPhoto), getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.selectSources));
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals(getResources().getString(R.string.takePhoto))) {


                    whichSelect = REQUEST_CAMERA;
                    if (!checkPermission()) {
                        requestPermission();
                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }


                } else if (items[item].equals(getResources().getString(R.string.selectYourPhoto))) {
                    whichSelect = SELECT_FILE;
                    if (!checkPermission()) {
                        requestPermission();
                    } else {
                        getPhotoFromGallary();
                    }
                } else if (items[item].equals(getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onSuccessUpdateUserData(String name) {

    }

    @Override
    public void onFailUpdateUesrDate(String message) {

    }

    @Override
    public void onFailUpdateRequest() {

    }

    @Override
    public void onSuccessLocationList(JSONArray jsonArray) {

    }

    @Override
    public void onSuccessUploadImage(JSONObject response) {
        UtilClass.hideProgress();
        SharedPreferenceUtil.putValue(Constants.UserData.UserProfilePic, response.optJSONObject("response").optString("userProfilePic"));
        SharedPreferenceUtil.save();
        loadProfilePic(response.optJSONObject("response").optString("userProfilePic"));
    }

    @Override
    public void onFailUpload(String message) {
        UtilClass.hideProgress();
    }



}
