package ldt.com.grocery.rest;

import java.util.List;

import ldt.com.grocery.database.User;

/**
 * Created by hlang on 7/17/15.
 */
public class RestResponse {

    public static class RegisterResponse {
        public String userId;
        public String fullName;
        public String imageUrl;
        public boolean isFirst;
        public boolean isActive;
    }

    public static class SubmitResponse {
        public boolean success;
    }

    public static class UpdateResponse {
        public boolean success;
    }

    public class UploadProfileImageResponse {
        public String imageUrl;
        public boolean success;
    }

    public static class LoadStoreResponse {
        public List<User> result;
    }

}
