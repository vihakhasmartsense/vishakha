package com.example.ronak.demonew.Util;

/**
 * Created by smartsense on 05/10/16.
 */

public class Constants {
    public static class DifferentData {
        public static final String GoingStatus = "1";
        public static final String InterestedStatus = "2";
        public static final String NotGoingStatus = "3";
        public static String Check = "check";
        public static final int ViewPagerInterval = 2000;
        public static final String SelectedCatagory = "selectedCatagory";
        public static final String CommentAdded = "Done";
        public static final String CommentEdited = "Updated";
        public static final String CommentDeleted = "Removed";
    }

    public static class RequestConstants {
        public static String BaseUrl = "http://139.59.29.185:90/api/";
        public static String BaseUrlForImage = "http://139.59.29.185:90";

        public static String LoginUrl = BaseUrl + "login/";
        public static String EventListUrl = BaseUrl + "events/";
        public static String NewsCategoryListUrl = BaseUrl + "categories/";
        public static String LocationListUrl = BaseUrl + "location/";
        public static String CreateLocationListUrl = BaseUrl + "createLocation/";
        public static String EventStatusListUrl = BaseUrl + "eventStatuses/";
        public static String EventStatusCreateUrl = BaseUrl + "eventStatusCreate/";
        public static String ProfileUpdateUrl = BaseUrl + "user/";
        public static String EventStatusUpdaetUrl = BaseUrl + "eventStatusDetail/";
        public static String EventDetailUrl = BaseUrl + "eventsDetail/";
        public static String FeedbackUrl = BaseUrl + "feedback/";
        public static String ProjectsListUrl = BaseUrl + "projects/";
        public static String AboutUsUrl = BaseUrl + "aboutus.html";
        public static String IntroductionUrl = BaseUrl + "introduction.html";
        public static String LikeUpdateUrl = BaseUrl + "newsStatus/";
        public static String CommentLstUrl = BaseUrl + "newsStatusList/";
        public static String CommentUpdateUrl = BaseUrl + "newsStatusDetail/";
        public static String NewsFeedUrl = BaseUrl + "newsfeed/";
        public static String UserVerifyUrl = BaseUrl + "verifyUserVerificationOTP/";
        public static String UserListUrl = BaseUrl + "user/";
        public static String OtpUrl = BaseUrl + "sendOTP/";
        public static String NewsStatusUrl = BaseUrl + "newsStatus/";
        public static String VerifyOtpUrl = BaseUrl + "verifyOTP/";
        public static String ChangePasswordUrl = BaseUrl + "resetPassword/";
        public static String RemoveLikeUrl = BaseUrl + "newsStatusDetail/";
        public static String SignupUrl = BaseUrl + "register/";
        public static String NewsDetailUrl = BaseUrl + "newsDetail/";
        public static String HeaderPostfix = "JWT ";
        public static String PhotoEvent = "http://139.59.29.185";
        public static String DefaultToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IjkxOTQwODU4MDgwMSIsInVzZXJNb2JpbGVObyI6IjkxOTQwODU4MDgwMSIsInVzZXJfaWQiOjEsImVtYWlsIjoia2FyYW5AZ21haWwuY29tIiwiZXhwIjoxNDg1NjUzMTIxfQ.nPGJW1rtAl0r9wE31mjjraFj8QHkCZiralX-1jaQs2g";
    }

    public static class UserData {
        public static String UserId = "userId";
        public static String UserMobileNo = "userMobileNo";
        public static String UserMiddleName = "userMiddleName";
        public static String UserFirstName = "userFirstName";
        public static String UserLastName = "userLastName";
        public static String UserLocationId = "locationId";
        public static String UserLocationName = "locationName";
        public static String UserProfilePic = "userProfilePic";
        public static String UserProfession = "userProfession";
        public static String UserEmail = "email";
        public static String UserDOB = "userDOB";
        public static String UserBloodGroup = "userBloodGroup";
        public static String UserFamilyMemberCount = "userFamilyMemberCount";
        public static String UserRegistrationId = "userRegistrationId";
        public static String isVerified = "isVerified";
        public static String UserFBProfileName = "userFBProfileName";
        public static String token = "token";
    }

    public static class ResponseCode {
        public static int LoginSuccessCode = 200;
        public static int SignUpSuccessCode = 201;
        public static int SuccessCode = 200;
        public static int OtpVerificationSuccess = 204;
        public static int RemoveLikeSuccess = 204;
        //public static int SignUpSuccessCode1 = 406;
    }

    public static class PushConstant {
        public static final String PushActionNews = "PUSH_TYPE_NEWS";
        public static final String PushActionEvent = "PUSH_TYPE_EVENT";
        public static final int NewsFeedPush = 2;
        public static final int EventPush = 1;


    }


}
