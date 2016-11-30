package com.example.ronak.demonew;

import java.io.Serializable;

/**
 * Created by smartsense on 08/10/16.
 */

public class NewsObject implements Serializable {
    public String getNewsStatusId() {
        return newsStatusId;
    }

    public String getNewsCatagory() {
        return newsCatagory;
    }

    public void setNewsCatagory(String newsCatagory) {

        this.newsCatagory = newsCatagory;
    }

    public String newsCatagory;

    public void setNewsStatusId(String newsStatusId) {
        this.newsStatusId = newsStatusId;
    }

    public String newsStatusId;

    public boolean isCommented() {
        return isCommented;
    }

    public void setCommented(boolean commented) {
        isCommented = commented;
    }

    public boolean isCommented;

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsCity() {
        return newsCity;
    }

    public void setNewsCity(String newsCity) {
        this.newsCity = newsCity;
    }

    public String getNewsLikeCount() {
        return newsLikeCount;
    }

    public void setNewsLikeCount(String newsLikeCount) {
        this.newsLikeCount = newsLikeCount;
    }

    public String getNewsCommentCount() {
        return newsCommentCount;
    }

    public void setNewsCommentCount(String newsCommentCount) {
        this.newsCommentCount = newsCommentCount;
    }

    public String newsTitle;
    public String newsDescription;
    public String newsCity;
    public String newsLikeCount;
    public String newsCommentCount;


    public String newsDataAndTime;

    public String getNewsImageArray() {
        return newsImageArray;
    }

    public void setNewsImageArray(String newsImageArray) {
        this.newsImageArray = newsImageArray;
    }

    public String newsImageArray;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsDataAndTime() {
        return newsDataAndTime;
    }

    public void setNewsDataAndTime(String newsDataAndTime) {
        this.newsDataAndTime = newsDataAndTime;
    }

    public String newsId;



}
