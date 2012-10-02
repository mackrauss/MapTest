package ca.surveillancerights.maptest;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;


public class MapTestActivity extends MapActivity {
	private MyLocationOverlay myLocationOverlay;
	private GeoPoint geoPoint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MapView mapview = (MapView) findViewById(R.id.mapview);
        mapview.setBuiltInZoomControls(true);
        
        // Stuff to add one point and zoom to it
        geoPoint = new GeoPoint( (int) (52.334822 * 1E6), (int) (4.668907 * 1E6));
        
        // this moves the map view to the geoPoint and changes the Zoom level
//        MapController mc = mapview.getController();
//        mc.setZoom(9);
//        mc.animateTo(geoPoint);
        
        // This adds a marker for the geoPoint
        List<Overlay> mapOverlays = mapview.getOverlays();
        mapOverlays.clear();
        mapOverlays.add(new MyOverlay());
//        mapview.invalidate();

        // Add installation markers
        //List<Overlay> mapOverlays = mapview.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.pin_red_on);
        InstallationsOverlay installationMarkers = new InstallationsOverlay(drawable, this);
        
        // create geo point
        GeoPoint point = new GeoPoint(19240000,-99120000);
        // create overlay item with geopoint and additional information
        OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
        // add overlay item to array within installtionsOverlay instance
        installationMarkers.addOverlay(overlayitem);
        
        GeoPoint point2 = new GeoPoint(35410000, 139460000);
        OverlayItem overlayitem2 = new OverlayItem(point2, "Sekai, konichiwa!", "I'm in Japan!");
        installationMarkers.addOverlay(overlayitem2);

        mapOverlays.add(installationMarkers);
        
        // Adds current location marker
        myLocationOverlay = new MyLocationOverlay(this, mapview); 
        mapview.getOverlays().add(myLocationOverlay); 
        mapview.invalidate();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	myLocationOverlay.disableMyLocation();
    	//myLocationOverlay.disableCompass();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	myLocationOverlay.enableMyLocation();
    	//myLocationOverlay.enableCompass();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class MyOverlay extends com.google.android.maps.Overlay {
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			if (!shadow) {
				Point point = new Point();
				mapView.getProjection().toPixels(geoPoint, point);
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.marker_default);
				int x = point.x - bmp.getWidth() / 2;
				int y = point.y - bmp.getHeight();
				canvas.drawBitmap(bmp, x, y, null);
			}
		}
	}
}
