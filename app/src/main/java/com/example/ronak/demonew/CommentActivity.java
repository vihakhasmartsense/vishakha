package com.example.ronak.demonew;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.example.ronak.demonew.Util.Constants;
import com.example.ronak.demonew.Util.UtilClass;
import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class CommentActivity extends AppCompatActivity implements CommentViewInt,CommentListCallbackM,NewsLikeCommentUpdateCallbackI{

    RecyclerView rvCommentList;
    @BindView(R.id.etCommentText)
    EditText etCommentText;

    Presenter presenter;
    //@BindView(R.id.ivNoCommentsPlaceholder)
    ImageView ivNoCommentsPlaceholder;

    @OnFocusChange(R.id.etCommentText)
    void commentText() {
        etCommentText.setSelection(etCommentText.getText().length());
    }
    TextView tvSendComment;
    @OnClick(R.id.tvSendComment)
    public void sendCommentText(){
        Log.e("button","click1");
        if (!etCommentText.getText().toString().equalsIgnoreCase("") && etCommentText.getText().toString().length() < 200) {
            if (presenterClass == null)
                presenterClass = new RequestPresenter();
        } else {
            if (etCommentText.getText().toString().length() > 200) {
                UtilClass.displyMessage(getString(R.string.commentValidationlessthan200), this, 0);
            }
        }
        if (((String) tvSendComment.getTag()).equalsIgnoreCase("add")) {
            presenterClass.updateLikeComment(getIntent().getStringExtra("newsId"), "1", etCommentText.getText().toString(), this);
            presenter = new Presenter();
            presenter.commentCredential(etCommentText.getText().toString(), this);
        } else {
            presenterClass.updateComment(getIntent().getStringExtra("newsId"), (String) etCommentText.getTag(), etCommentText.getText().toString(), "1", this);
        }
    }
    @OnClick(R.id.tvSendComment)
    public void sendComment() {
        Log.e("button","click1");
        if (!etCommentText.getText().toString().equalsIgnoreCase("") && etCommentText.getText().toString().length() < 200) {
            if (presenterClass == null)
                presenterClass = new RequestPresenter();
        } else {
            if (etCommentText.getText().toString().length() > 200) {
                UtilClass.displyMessage(getString(R.string.commentValidationlessthan200), this, 0);
            }
        }
        if (((String) tvSendComment.getTag()).equalsIgnoreCase("add")) {
            presenterClass.updateLikeComment(getIntent().getStringExtra("newsId"), "1", etCommentText.getText().toString(), this);
            presenter = new Presenter();
            presenter.commentCredential(etCommentText.getText().toString(), this);
        } else {
            presenterClass.updateComment(getIntent().getStringExtra("newsId"), (String) etCommentText.getTag(), etCommentText.getText().toString(), "1", this);
        }
    }
    CommentAdapter commentAdapter;
    private LinearLayoutManager mLayoutManager;
    RequestPresenter presenterClass;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        rvCommentList = (RecyclerView)findViewById(R.id.rvCommentList);
        ivNoCommentsPlaceholder = (ImageView) findViewById(R.id.ivNoCommentsPlaceholder);
        tvSendComment = (TextView) findViewById(R.id.tvSendComment);
        etCommentText = (EditText) findViewById(R.id.etCommentText);
        tvSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Button","Click1");
                if (!etCommentText.getText().toString().equalsIgnoreCase("") && etCommentText.getText().toString().length() < 200) {
                    if (presenterClass == null)
                        presenterClass = new RequestPresenter();
                } else {
                    if (etCommentText.getText().toString().length() > 200) {
                        Log.e("Comment error","Comment must < than 200 character"+etCommentText.toString());
                        //UtilClass.displyMessage(getString(R.string.commentValidationlessthan200), this, 0);
                    }
                }
                if (((String) tvSendComment.getTag()).equalsIgnoreCase("add")) {
                    presenterClass.updateLikeComment(getIntent().getStringExtra("newsId"), "1", etCommentText.getText().toString(), CommentActivity.this);
                    presenter = new Presenter();
                    presenter.commentCredential(etCommentText.getText().toString(),CommentActivity.this );
                } else {
                    presenterClass.updateComment(getIntent().getStringExtra("newsId"), (String) etCommentText.getTag(), etCommentText.getText().toString(), "1", CommentActivity.this);
                }
            }
        });
        tvSendComment.setTag("add");
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenterClass = new RequestPresenter();
        if (getIntent() != null) {
            presenterClass.getNewsCommentList(getIntent().getStringExtra("newsId"), getIntent().getStringExtra("newsStatusId"), this);
        }
        mLayoutManager = new LinearLayoutManager(this);
    }

    public void updateComment(String newsComment, String newsStatus, String newsStatusId) {
        etCommentText.setTag(newsStatusId);
        tvSendComment.setTag("update");
        etCommentText.setText(newsComment);
    }

    public void updateView(boolean listToDisplay) {
        if (listToDisplay) {
            ivNoCommentsPlaceholder.setVisibility(View.GONE);
            rvCommentList.setVisibility(View.VISIBLE);

        } else {
            ivNoCommentsPlaceholder.setVisibility(View.VISIBLE);
            rvCommentList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCommentresult(int commentBlankError) {
        if (commentBlankError == UtilClass.CommentBlankError) {
            UtilClass.displyMessage(getString(R.string.commentEmpty), CommentActivity.this, Toast.LENGTH_SHORT);
        } else if (commentBlankError == UtilClass.CommentLenghtError) {
            UtilClass.displyMessage(getString(R.string.commentLength), CommentActivity.this, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessCommentList(JSONArray commentList) {
        if (commentList.length() > 0) {
            updateView(true);
            if (commentAdapter == null) {
                commentAdapter = new CommentAdapter(this, commentList, getIntent().getStringExtra("newsId"), getIntent().getStringExtra("newsStatusId"), this);
                rvCommentList.setLayoutManager(mLayoutManager);
                commentAdapter.setMode(Attributes.Mode.Single);
                (commentAdapter).setMode(Attributes.Mode.Single);
                rvCommentList.setAdapter(commentAdapter);
            } else {
                commentAdapter.updateComment(commentList);
                if (commentList.length() > 0)
                    rvCommentList.smoothScrollToPosition(commentList.length() - 1);
            }
        } else {
            updateView(false);
        }
    }

    @Override
    public void onFailRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }

    @Override
    public void onFailResponse(String mesaage) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(mesaage, this, 0);
    }

    @Override
    public void successfulUpdateLike(JSONObject updateObj) {
        if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentAdded)) {
            UtilClass.displyMessage(getString(R.string.commnetAddad), this, 0);
        } else if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentEdited)) {
            UtilClass.displyMessage(getString(R.string.commnetUpdate), this, 0);
        } else if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentDeleted)) {
            UtilClass.displyMessage(getString(R.string.commnetDelete), this, 0);
        }
        etCommentText.setText("");
        tvSendComment.setTag("add");
        if (presenterClass == null) {
            presenterClass = new RequestPresenter();
        }
        presenterClass.getNewsCommentList(getIntent().getStringExtra("newsId"), getIntent().getStringExtra("newsStatusId"), this);
    }

    @Override
    public void failUpdateResponse(String message) {
        tvSendComment.setTag("add");
        etCommentText.setText("");
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    public void failUpdateRequest() {
        tvSendComment.setTag("add");
        etCommentText.setText("");
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }
}
