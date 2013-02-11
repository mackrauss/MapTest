package ca.surveillancerights.maptest;

import org.json.JSONException;
import org.json.JSONObject;

public class Installation {
	private int id;
	private double loc_lat;
	private double loc_lng;
	private String owner_name;
	private String loc_description;
	
	
	public Installation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Installation(JSONObject instData) {
		super();
		try {
			id = instData.getInt("id");
			loc_lat = instData.getDouble("loc_lat");
			loc_lng = instData.getDouble("loc_lng");
			if (instData.has("owner_name")) {
				owner_name = instData.getString("owner_name");
			}
			if (instData.has("loc_description")) {
				loc_description = instData.getString("loc_description");
			}
		} catch (JSONException e) {
            // TODO Auto-generated catch block
			System.out.println("Error parsing installations JSON object");
            e.printStackTrace();
        }
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Installation other = (Installation) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Installation [id=" + id + ", loc_lat=" + loc_lat + ", loc_lng="
				+ loc_lng + ", owner_name=" + owner_name + ", loc_description="
				+ loc_description + "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLoc_lat() {
		return loc_lat;
	}
	public void setLoc_lat(double loc_lat) {
		this.loc_lat = loc_lat;
	}
	public double getLoc_lng() {
		return loc_lng;
	}
	public void setLoc_lng(double loc_lng) {
		this.loc_lng = loc_lng;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getLoc_description() {
		return loc_description;
	}
	public void setLoc_description(String loc_description) {
		this.loc_description = loc_description;
	}
	
}
