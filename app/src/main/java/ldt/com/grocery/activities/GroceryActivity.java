package ldt.com.grocery.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.concurrent.ExecutorService;

import de.greenrobot.event.EventBus;
import ldt.com.grocery.R;
import ldt.com.grocery.database.User;
import ldt.com.grocery.events.GetAllStore;
import ldt.com.grocery.rest.RestClient;
import ldt.com.grocery.services.WebService;

public class GroceryActivity extends Activity {

    private MyLocationNewOverlay mLocationOverlay;
    private static ExecutorService executor;
    private static RestClient restClient;
    private final String DBG_PREFIX = "Web Service";
    private static WebService instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        EventBus.getDefault().register(this);

        MapView map = (MapView) findViewById(R.id.map);

        final Context context = getApplicationContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();

        map.setTileSource(new OnlineTileSourceBase("Google Maps", 13, 19, 256, ".png", new String[]{"http://mt3.google.com/vt/v=w2.97"}) {
            @Override
            public String getTileURLString(MapTile aTile) {
               /*
                * GOOGLE MAPS URL looks like
                * base url	const	x	y zoom
                * http://mt3.google.com/vt/v=w2.97&x=74327&y=50500&z=17
                */
                return getBaseUrl() + "&x=" + aTile.getX() + "&y=" + aTile.getY() + "&z=" + aTile.getZoomLevel();
            }
        });
        map.setUseDataConnection(true); //this actually makes the controller use only offline tiles

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(13);
        GeoPoint startPoint = new GeoPoint(16.0452691, 108.1922859);
        mapController.setCenter(startPoint);

        mLocationOverlay = new MyLocationNewOverlay(context, new GpsMyLocationProvider(context), map);
        map.getOverlays().add(mLocationOverlay);
        mLocationOverlay.enableMyLocation();

        ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(context);
        mScaleBarOverlay.setCentred(false);
        //play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(mScaleBarOverlay);

        loadData();
    }

    private void loadData() {
        WebService.getInstance(this).loadStore();
        Log.i("GROCERY", "Loading");
    }

    public void onEvent(final GetAllStore.Success event) {
        Log.i("GROCERY", "Success");
        if (event.data != null && event.data.size() > 0) { // has data
            Log.i("GROCERY", event.data.size() + "");
            for(User u: event.data){
                Log.i("GROCERY", u.getFullname());
            }
        }
    }

    public void onEvent(final GetAllStore.Failed event) {
        Log.i("GROCERY", "Fail " +  event.code + " ::: " + event.msg);
    }
}
