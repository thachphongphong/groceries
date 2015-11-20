package ldt.com.grocery.rest;

/**
 * Created by hlang on 6/9/15.
 */
public class RestRequest {

    public static class RegisterRequest {
        public String platform; // platform: android or ios
        public String socialToken; // FB or TW access token
        public String pushToken; // gcm registration id
        public String name; // full name
        public String firstName; // first name
        public String lastName; // last name
        public String email; // email
        public String imageUrl; // imageUrl
    }

    public static class SubmitRequest {
        public String title;
        public String des;
        public String link;
        public double[] location;
    }

    public static class UpdateRequest {
        public String recommendId;
        public String title;
        public String des;
        public String link;
        public double[] location;
    }

    public static class UploadRequest {
        public String imageUrlBase64String;
    }

}
