package ldt.com.grocery.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import ldt.com.grocery.GroceryApplication;
import ldt.com.grocery.database.User;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class RestClient {

    private static final String DBG_PREFIX = "RestClient";
    private final RestClientApiInterface mRestClientInterface;
    private final RestClientRequestInterceptor requestInterceptor;
    private final String mEndpoint;

    public RestClient(Context context, String endpoint, Executor executor) {
        this.mEndpoint = endpoint;
        this.requestInterceptor = new RestClientRequestInterceptor(context);
        RestAdapter restAdapter = createRestAdapter(executor);
        mRestClientInterface = restAdapter.create(RestClientApiInterface.class);
    }

    public static Gson createGsonAPI(String dateFormat) {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .setDateFormat(dateFormat)
                .create();
    }

    private RestAdapter createRestAdapter(Executor executor) {
        Gson gson = createGsonAPI(GroceryApplication.getConfig("API_DATE_FORMAT"));

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .setClient(new OkClient((okHttpClient)))
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.HEADERS)
                .setExecutors(executor, executor)
                .build();
        return restAdapter;
    }

    public List<User> loadStore() {
        RestResponse.LoadStoreResponse response = mRestClientInterface.loadStore();
        return response.result;
    }

   /* public RestResponse.RegisterResponse register(String snsId, String snsType, String userFullName, String firstName, String lastName, String email, String profileImg, String gcmId, String accessToken) {
        RestRequest.RegisterRequest request = new RestRequest.RegisterRequest();
        request.platform = "android";
        request.socialToken = accessToken;
        request.pushToken = gcmId;
        request.name = userFullName;
        request.firstName = firstName;
        request.lastName = lastName;
        request.email = email;
        request.imageUrl = profileImg;
        return mRestClientInterface.register(snsId, snsType, request);
    }

    public boolean addNewRecommendation(String userId, String title, String des, String link, double[] formatedLocation) {
        RestRequest.SubmitRequest request = new RestRequest.SubmitRequest();
        request.title = title;
        request.des = des;
        request.link = link;
        request.location = formatedLocation;
        RestResponse.SubmitResponse response = mRestClientInterface.addNewRecommendation(userId, request);
        return response.success;
    }

    public String uploadProfileImage(String userId, String base64Image) {
        RestRequest.UploadRequest request = new RestRequest.UploadRequest();
        request.imageUrlBase64String = base64Image;
        RestResponse.UploadProfileImageResponse response = mRestClientInterface.uploadProfileImage(userId, request);
        return response.imageUrl;
    }

    public boolean reportRecommendation(String userId, String recId) {
        RestResponse.ReportResponse response = mRestClientInterface.reportRecommendation(userId, recId);
        return response.result;
    }

    public boolean reportUser(String userId, String reportedId) {
        RestResponse.ReportResponse response = mRestClientInterface.reportUser(userId, reportedId);
        return response.result;
    }

    public boolean deleteRecommendation(String userId, String recId) {
        RestResponse.ReportResponse response = mRestClientInterface.deleteRecommendation(userId, recId);
        return response.result;
    }

    public boolean updateRecommendation(String userId, String recId, String title, String des, String link, double[] formatedLocation) {
        RestRequest.UpdateRequest request = new RestRequest.UpdateRequest();
        request.recommendId = recId;
        request.title = title;
        request.des = des;
        request.link = link;
        request.location = formatedLocation;
        RestResponse.UpdateResponse response = mRestClientInterface.updateRecommendation(userId, request);
        return response.success;
    }*/
}
