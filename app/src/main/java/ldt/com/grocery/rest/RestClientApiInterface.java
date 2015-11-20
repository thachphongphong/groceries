package ldt.com.grocery.rest;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

public interface RestClientApiInterface {

    @POST("/location/")
    Response locations(
            @Header("X-Kudos-socialId") String snsId,
            @Header("X-Kudos-socialType") String snsType,
            @Body TypedInput body);

    @GET("/users/userlist")
    RestResponse.LoadStoreResponse loadStore();

    /*@POST("/user/signup/")
    RestResponse.RegisterResponse register(
            @Header("X-Kudos-socialId") String snsId,
            @Header("X-Kudos-socialType") String snsType,
            @Header("X-Kudos-timezone") String timeZone,
            @Body RestRequest.RegisterRequest request);

    @POST("/recommend/add")
    RestResponse.SubmitResponse addNewRecommendation(
            @Header("X-Kudos-userId") String userId,
            @Body RestRequest.SubmitRequest request);

    @GET("/recommend")
    RestResponse.GetLocalRecommendationResponse getLocalRecommendations(
            @Header("X-Kudos-userId") String userId,
            @Query("lat") String lat,
            @Query("lon") String lon);

    @GET("/recommend/global")
    RestResponse.GetLocalRecommendationResponse getGlobalRecommendations(
            @Header("X-Kudos-userId") String userId);

    @GET("/kudos/add/{recommendId}")
    RestResponse.GiveKudosResponse giveKudos(
            @Header("X-Kudos-userId") String userId,
            @Path("recommendId") String recId);

    @GET("/user/get/{userId}")
    RestResponse.GetProfileResponse getUserProfile(
            @Header("X-Kudos-userId") String userId,
            @Path("userId") String userIdToGetProfile,
            @Query("lat") String lat,
            @Query("lon") String lon);

    @GET("/recommend/getSubmissions/{userId}")
    RestResponse.GetLocalRecommendationResponse getSubmissionsList(
            @Header("X-Kudos-userId") String userId,
            @Path("userId") String userIdToGet);

    @GET("/recommend/gaveKudos")
    RestResponse.GetLocalRecommendationResponse getGivenKudosList(
            @Header("X-Kudos-userId") String userId);

    @GET("/user/signout")
    RestResponse.LogoutResponse logout(
            @Header("X-Kudos-userId") String userId);

    @GET("/user/globalTop")
    RestResponse.GetLeadingUsers getGlobalLeadingUsers(
            @Header("X-Kudos-userId") String userId);

    @GET("/user/localTop")
    RestResponse.GetLeadingUsers getLocalLeadingUsers(
            @Header("X-Kudos-userId") String userId,
            @Query("lat") String lat,
            @Query("lon") String lon);

    @POST("/image/upload")
    RestResponse.UploadProfileImageResponse uploadProfileImage(
            @Header("X-Kudos-userId") String userId,
            @Body RestRequest.UploadRequest base64Image);

    @GET("/recommend/detail/{recommendId}")
    RestResponse.GetRecommendationDetailsResponse getRecommendationDetails(
            @Header("X-Kudos-userId") String userId,
            @Path("recommendId") String recommendId);

    @POST("/recommend/report/{recommendId}")
    RestResponse.ReportResponse reportRecommendation(
            @Header("X-Kudos-userId") String userId,
            @Path("recommendId") String recId);

    @POST("/user/report/{userId}")
    RestResponse.ReportResponse reportUser(
            @Header("X-Kudos-userId") String userId,
            @Path("userId") String reportedId);

    @POST("/recommend/delete/{recommendId}")
    RestResponse.ReportResponse deleteRecommendation(
            @Header("X-Kudos-userId") String userId,
            @Path("recommendId") String recId);

    @POST("/recommend/update")
    RestResponse.UpdateResponse updateRecommendation(
            @Header("X-Kudos-userId") String userId,
            @Body RestRequest.UpdateRequest request);*/
}
