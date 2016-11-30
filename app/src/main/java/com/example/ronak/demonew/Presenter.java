package com.example.ronak.demonew;

import com.example.ronak.demonew.Util.UtilClass;

/**
 * Created by Ronak on 11/29/2016.
 */
public class Presenter implements PresenterInt, CommentModelInt.onCommentRequestFinishListener {
    private CommentModel commentModel;
    private CommentViewInt commentViewInt1;
    @Override
    public void commentCredential(String comment, CommentViewInt commentViewInt) {
        commentViewInt1 = commentViewInt;
        commentModel = new CommentModel();
        commentModel.comment(comment, this);
    }

    @Override
    public void onCommentBlankError() {
        commentViewInt1.onCommentresult(UtilClass.CommentBlankError);
    }

    @Override
    public void onCommentLengthError() {
        commentViewInt1.onCommentresult(UtilClass.CommentLenghtError);
    }
}
