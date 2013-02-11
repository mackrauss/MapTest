package ca.surveillancerights.maptest;

import java.util.List;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OptionsMenu {
	public static boolean create(MapTestActivity act, Menu menu) {
		MenuInflater inflater = act.getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	public static boolean selectItem(MapTestActivity act, MenuItem item) {
		Log.v(act.getClass().getName(),
				"Menu item selected: " + item.toString() + " ("
						+ item.getItemId() + ")");
//		if (item.getItemId() == R.id.settings) {
//			Intent prefsActivity = new Intent(getBaseContext(),
//					SurveillanceWatchSettings.class);
//			startActivityForResult(prefsActivity,
//					SurveillanceWatchShell.SET_PREFERENCES);
//			return true;
//		} else
		
		switch (item.getItemId()) {
			case R.id.menu_settings:
				return true;
			case R.id.menu_reload:
//				reload(act);
				return true;
			default:
				return false;
		}
	}
	
	public static void reload(MapTestActivity act) {
//		Log.d("PhoneGapShell", "Deleting cache...");
//		MapView mapView = (MapView) act.findViewById(R.id.mapview);
//		List<Overlay> mapOverlays = mapView.getOverlays();
//        mapOverlays.clear();
        act.getInstallations();
	}
}
