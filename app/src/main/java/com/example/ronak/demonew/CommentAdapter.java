package com.example.ronak.demonew;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.example.ronak.demonew.Adapter.NewsListAdapter;
import com.example.ronak.demonew.Util.Constants;
import com.example.ronak.demonew.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 11/29/2016.
 */
public class CommentAdapter extends RecyclerSwipeAdapter<CommentAdapter.MyViewHolder> implements NewsLikeCommentUpdateCallbackI, CommentListCallbackM {
    private JSONArray commentListArry;
    private Context context;
    private RequestPresenter presenter;
    protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);
    String newsId;
    String newsStatusId;
    private CommentActivity commentActivity;
    public CommentAdapter(Context context, JSONArray jsonArray, String newsId, final String newsStatusId, CommentActivity activity) {
        this.commentListArry = jsonArray;
        this.context = context;
        this.newsStatusId = newsStatusId;
        this.newsId = newsId;
        Log.i("%%%%%%%%%%%%%%%%","Adapter create");

        this.commentActivity = activity;
    }

    @Override
    public void onSuccessCommentList(JSONArray commentList) {
        UtilClass.hideProgress();
        if (commentList.length() > 0) {
            commentActivity.updateView(true);
            this.commentListArry = commentList;
            notifyDataSetChanged();
        } else {
            commentActivity.updateView(false);

        }
    }

    @Override
    public void onFailRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), context, 0);
    }

    @Override
    public void onFailResponse(String mesaage) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(mesaage, context, 0);
    }

    @Override
    public void successfulUpdateLike(JSONObject updateObj) {
        if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentAdded)) {
            UtilClass.displyMessage(context.getString(R.string.commnetAddad), context, 0);
        } else if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentEdited)) {
            UtilClass.displyMessage(context.getString(R.string.commnetUpdate), context, 0);
        } else if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentDeleted)) {
            UtilClass.displyMessage(context.getString(R.string.commnetDelete), context, 0);
        }

        if (UtilClass.isInternetAvailabel(context)) {
            if (presenter == null) {
                presenter = new RequestPresenter();
            }
            presenter.getNewsCommentList(newsId, newsStatusId, this);
        } else {
            UtilClass.hideProgress();
            UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
        }
    }

    @Override
    public void failUpdateResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, context, 0);
    }

    @Override
    public void failUpdateRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), context, 0);
    }

    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewComment = LayoutInflater.from(context).inflate(R.layout.element_comment, parent, false);
        ButterKnife.bind(this, viewComment);
        return new MyViewHolder(viewComment);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int position) {
        try {
            final JSONObject commentObj = commentListArry.getJSONObject(position);

            JSONObject userObj = commentObj.optJSONObject("user");
            Log.i("comment","view"+commentObj.optString("userLastName").toString());
            viewHolder.tvCommentPersonName.setText(userObj.optString("userFirstName") + " " + userObj.optString("userLastName"));
            viewHolder.tvComment.setText(commentObj.optString("newsComment"));
            if (!commentObj.optJSONObject("user").optString("userId").equalsIgnoreCase((SharedPreferenceUtil.getString(Constants.UserData.UserId, "")))) {
                viewHolder.swipeLayout.setRightSwipeEnabled(false);
            }
            viewHolder.tvCommentTime.setText(commentObj.optString("newsStatusDate"));
            viewHolder.tvDeleteComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UtilClass.isInternetAvailabel(context)) {
                        UtilClass.showProgress(context, context.getString(R.string.msgPleaseWait));
                        removeComment(commentObj.optString("newsStatusId"));
                        mItemManger.closeAllItems();
                    } else {
                        UtilClass.hideProgress();
                        //UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
                    }

                }

            });
            viewHolder.tvEditComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.swipeLayout.close();
                    if (context instanceof CommentActivity) {
                        commentActivity.updateComment(commentObj.optString("newsComment"), "2", commentObj.optString("newsStatusId"));
                    }
                    //   UtilClass.showProgress(context, context.getString(R.string.msgPleaseWait));

                }
            });

            mItemManger.bindView(viewHolder.itemView, position);
            String imagePath = commentObj.optString("userProfilePic");
            if (imagePath != null)
                Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + userObj.optString("userProfilePic")).error(R.drawable.ic_network_place_holder).placeholder(R.drawable.ic_network_place_holder).into(viewHolder.civUserPic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void removeComment(String newsStatusId) {
    }

    public void updateComment(JSONArray updatedCommentArray) {
        this.commentListArry = updatedCommentArray;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return commentListArry.length();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.tvCommentName)
        TextView tvCommentPersonName;
        //@BindView(R.id.tvComment)
        TextView tvComment;
        //@BindView(R.id.tvCommentTime)
        TextView tvCommentTime;
        //@BindView(R.id.civUserPic)
        ImageView civUserPic;
        //@BindView(R.id.tvDelete)
        TextView tvDeleteComment;
        //@BindView(R.id.swipe)
        SwipeLayout swipeLayout;
        //@BindView(R.id.tvEditComment)
        TextView tvEditComment;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvCommentPersonName = (TextView) itemView.findViewById(R.id.tvCommentName);
            tvComment = (TextView) itemView.findViewById(R.id.tvComment);
            tvCommentTime = (TextView) itemView.findViewById(R.id.tvCommentTime);
            tvDeleteComment = (TextView) itemView.findViewById(R.id.tvDelete);
            tvEditComment = (TextView) itemView.findViewById(R.id.tvEditComment);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            civUserPic = (ImageView) itemView.findViewById(R.id.civUserPic);
            ButterKnife.bind(this, itemView);
        }
    }
}
