package ca.surveillancerights.maptest;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.OverlayItem;

/**
 * This is from https://developers.google.com/maps/documentation/android/hello-mapview
 */
public class InstallationsOverlay extends com.google.android.maps.ItemizedOverlay {
	// ArrayList to store all overlay items
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	// constructor
	public InstallationsOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		// set context (needed to enable touch events on markers)
		mContext = context;
	}
	
	// Function that allows adding of new overlay items
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    // handles drawing of each item?
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		// return the overlay item from the array indicated by the integer handed in
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		// return the correct size of the array holding the overlay items
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);	
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  
	  dialog.setIcon(R.drawable.androidmarker);
	  
	  dialog.show();
	  return true;
	}

}
