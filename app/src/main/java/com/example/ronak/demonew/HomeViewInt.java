package com.example.ronak.demonew;

import java.util.List;

/**
 * Created by Ronak on 11/9/2016.
 */
public interface HomeViewInt {
    void onSuccessNewsList(List<NewsObject> newsList);

    void onFailRequest();

    void onFailResponse(String message);

}
