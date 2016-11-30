package com.example.ronak.demonew;

/**
 * Created by Ronak on 11/29/2016.
 */
public interface CommentModelInt {
    interface onCommentRequestFinishListener {
        void onCommentBlankError();

        void onCommentLengthError();
    }
    public void comment(String comment, onCommentRequestFinishListener listener);
}
