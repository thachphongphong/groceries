package ldt.com.grocery;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dtlinh on 11/19/2015.
 */
public class GroceryApplication extends Application {
    private static Context context;
    private static Properties properties;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        loadConfig();
    }

    private void loadConfig() {
        try {
            Resources resources = this.context.getResources();
            AssetManager assetManager = resources.getAssets();
            Properties properties = new Properties();

            InputStream inputStream = assetManager.open("app.properties");
            properties.load(inputStream);
            this.properties = properties;
            Log.e("GroceryApplication", properties.getProperty("END_POINT"));
        } catch (IOException e) {
            Log.e("GroceryApplication", "Failed to open app property file");
            Log.e("GroceryApplication", e.getMessage());
        }
    }

    public static String getConfig(String property) {
        return properties.getProperty(property);
    }
}
