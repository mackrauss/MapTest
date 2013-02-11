package ca.surveillancerights.maptest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MapTestActivity extends MapActivity {
	private MyLocationOverlay myLocationOverlay;
	private GeoPoint geoPoint;
	MapView mapView;
	public ArrayList<Installation> installations = new ArrayList<Installation>();
	public InstallationsOverlay installationMarkers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        
        if (networkInfo != null && networkInfo.isConnected()) {
        
	        // do a GET call to retrieve installations.json
	        // this is done in an AsyncTask
	        getInstallations();
	        
	        // Stuff to add one point and zoom to it
	//        geoPoint = new GeoPoint( (int) (52.334822 * 1E6), (int) (4.668907 * 1E6));
	        
	        // this moves the map view to the geoPoint and changes the Zoom level
	//        MapController mc = mapview.getController();
	//        mc.setZoom(9);
	//        mc.animateTo(geoPoint);
	        
	        // This adds a marker for the geoPoint
	        List<Overlay> mapOverlays = mapView.getOverlays();
	        mapOverlays.clear();
	//        mapOverlays.add(new MyOverlay());
	//        mapview.invalidate();
	
	        // Add installation markers
	        //List<Overlay> mapOverlays = mapview.getOverlays();
	        Drawable drawable = this.getResources().getDrawable(R.drawable.pin_red_on);
	        installationMarkers = new InstallationsOverlay(drawable, this);
	        
	        // create geo point
	        GeoPoint point = new GeoPoint(19240000,-99120000);
	        // create overlay item with geopoint and additional information
	        OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
	        // add overlay item to array within installtionsOverlay instance
	        installationMarkers.addOverlay(overlayitem);
	        
	//        GeoPoint point2 = new GeoPoint(35410000, 139460000);
	//        OverlayItem overlayitem2 = new OverlayItem(point2, "Sekai, konichiwa!", "I'm in Japan!");
	//        installationMarkers.addOverlay(overlayitem2);
	
	        mapOverlays.add(installationMarkers);	        
        } else {
	    	showToast("No network connection active");
	    }
        
        // Adds current location marker
        myLocationOverlay = new MyLocationOverlay(this, mapView); 
        mapView.getOverlays().add(myLocationOverlay); 
        mapView.invalidate();
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
    	return OptionsMenu.create(this, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	return OptionsMenu.selectItem(this, item);
        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.new_game:
//                newGame();
//                return true;
//            case R.id.help:
//                showHelp();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        } 
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/** Uses a AsyncTask to do a GET request retrieving the installations.json information */
    public void getInstallations() {
//    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//    	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//    	    
//	    if (networkInfo != null && networkInfo.isConnected()) {
    	    // fetch data
	    	new RestTask().execute("http://backend.dev.surveillancerights.ca/installations.json");
//	    } else {
//	        // display error
//	    	// TODO: Use a toast
//	    	showToast("No network connection active");
//	    } 
    }
    
    public void showToast (String toastText) {
    	Context context = getApplicationContext();
    	int duration = Toast.LENGTH_LONG;

    	Toast toast = Toast.makeText(context, toastText, duration);
    	toast.show();
    }
	
//	private class MyOverlay extends com.google.android.maps.Overlay {
//		@Override
//		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
//			super.draw(canvas, mapView, shadow);
//			if (!shadow) {
//				Point point = new Point();
//				mapView.getProjection().toPixels(geoPoint, point);
//				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.marker_default);
//				int x = point.x - bmp.getWidth() / 2;
//				int y = point.y - bmp.getHeight();
//				canvas.drawBitmap(bmp, x, y, null);
//			}
//		}
//	}
	
	private class RestTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            
         // TODO parse JSON into some object representation and then add
            // map markers filled with data from these representational objects
            try {
	            JSONArray JSONinstallations = new JSONArray(responseString);
	
	            for (int i = 0; i < JSONinstallations.length(); i++) {
	                JSONObject JSONinstallation = (JSONObject) JSONinstallations.get(i);
	                Installation installation = new Installation(JSONinstallation);
	                System.out.println(installation.toString());
	                installations.add(installation);
	            }
	            System.out.println("Installations: " +JSONinstallations.length());
//	        } catch (MalformedURLException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
	        } catch (JSONException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
            
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Installation inst;
        	int latE6;
        	int lngE6;
        	GeoPoint point;
            // create overlay item with geopoint and additional information
            OverlayItem overlayitem;
            //Do anything with response..
            //textView1.setText(result);
            
            for (int i = 0; i < installations.size(); i++) {
            	inst = installations.get(i);
            	latE6 = (int)(inst.getLoc_lat() * 1E6);
            	lngE6 = (int)(inst.getLoc_lng() * 1E6);
            	point = new GeoPoint(latE6,lngE6);
                // create overlay item with geopoint and additional information
                overlayitem = new OverlayItem(point, inst.getOwner_name(), inst.getLoc_description());
                // add overlay item to array within installtionsOverlay instance
                installationMarkers.addOverlay(overlayitem);
            }
        }
    }
}
