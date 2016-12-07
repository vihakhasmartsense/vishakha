package com.example.ronak.demonew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ronak.demonew.Adapter.LocationAdapter;
import com.example.ronak.demonew.Util.UtilClass;

import org.json.JSONArray;

public class LocationActivity extends AppCompatActivity implements ProfileUpdateCallback{

    RecyclerView rvLocationList;
    EditText etLocation;
    private ProfileUpdatePresenter mProfilePresenter;
    private LocationAdapter mLocationAdapter;
    ImageView ivSearchHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        rvLocationList = (RecyclerView) findViewById(R.id.rvLocationList);
        etLocation = (EditText) findViewById(R.id.etLocationSearch);
        mProfilePresenter = new ProfileUpdatePresenter();
        ivSearchHint = (ImageView) findViewById(R.id.ivSearchHint);
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            mProfilePresenter.getLocationList(this);
        } else {
            UtilClass.hideProgress();
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }
        etLocation.addTextChangedListener(etLocationSearch);
    }
    private final TextWatcher etLocationSearch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (etLocation.getText().toString().length() > 0) {
                ivSearchHint.setVisibility(View.GONE);
            } else {
                ivSearchHint.setVisibility(View.VISIBLE);
            }
            if (mLocationAdapter != null) {
                mLocationAdapter.getFilter().filter(etLocation.getText().toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

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
        UtilClass.hideProgress();
        UtilClass.closeKeyboard(this);
        if (jsonArray != null) {
            if (mLocationAdapter == null) {
                mLocationAdapter = new LocationAdapter(this, jsonArray);
                rvLocationList.setAdapter(mLocationAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                rvLocationList.setLayoutManager(layoutManager);
            } else {
                mLocationAdapter.updateLocationList(jsonArray);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
//        if (item.getItemId() == R.id.action_add_location) {
//            if (mProfilePresenter == null) {
//                mProfilePresenter = new ProfileUpdatePresenter();
//            }
//            mProfilePresenter.addNewLocationAlert(this);
//        }
        return super.onOptionsItemSelected(item);
    }
}
