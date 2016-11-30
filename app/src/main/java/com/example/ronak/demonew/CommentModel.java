package com.example.ronak.demonew;

import android.text.TextUtils;

/**
 * Created by Ronak on 11/29/2016.
 */
public class CommentModel implements CommentModelInt {
    @Override
    public void comment(String comment, onCommentRequestFinishListener listener) {
        if (TextUtils.isEmpty(comment)) {
            listener.onCommentBlankError();
        } else if (TextUtils.isEmpty(comment)) {
            listener.onCommentLengthError();
        }
    }
}
