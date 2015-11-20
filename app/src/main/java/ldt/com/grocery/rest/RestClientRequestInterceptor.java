package ldt.com.grocery.rest;

import android.content.Context;

import ldt.com.grocery.GroceryApplication;
import retrofit.RequestInterceptor;

public class RestClientRequestInterceptor implements RequestInterceptor {
    private final Context context;

    public RestClientRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public synchronized void intercept(RequestFacade request) {
        request.addHeader("X-Grocery-Token", GroceryApplication.getConfig("END_POINT_ACCESS_TOKEN"));
        request.addHeader("Content-Type", "application/json");
    }
}
