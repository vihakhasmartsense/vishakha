package com.example.ronak.demonew;

/**
 * Created by Ronak on 12/14/2016.
 */
public interface NewsDetailViewInt {
    void onSuccessNewsDetail(NewsObject newsObject);

    void onFailRequest();

    void onFailResponse(String message);
}
