package it.green.parkogre.rest.resource;

import org.json.JSONException;
import org.json.JSONObject;

public class Parco implements Resource {
	private static String numVoti;
	private static String votoAttuale;
	private static String city;
	private static String nomeParco;
	private static String indirizzoParco;
	private static String latitude;
	private static String longitude;
	private static String coordinate;
	private static String imageURL;
	
	private JSONObject jsonObject;
	
	public Parco(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public int getNumVoti() {
		int result = 0;
		try {
			result = jsonObject.getInt("numVoti");
		} catch (JSONException e) {
		}
		return result;
	}
	
	public double getvotoAttuale() {
		double result = 0;
		try {
			result = jsonObject.getDouble("votoAttuale");
		} catch (JSONException e) {
		}
		return result;
	}
	
	public String getCity() {
		String result = null;
		try {
			result = jsonObject.getString("city");
		} catch (JSONException e) {
		}
		return result;
	}
	@Override
	public String toString() {
		String present = this.getNomeParco() + ", " + this.getCity();
		return present;
	}
	
	public String getNomeParco() {
		String result = null;
		try {
			result = jsonObject.getString("nomeParco");
		} catch (JSONException e) {
		}
		return result;
	}
	
	public String getIndirizzoParco() {
		String result = null;
		try {
			result = jsonObject.getString("indirizzoParco");
		} catch (JSONException e) {
		}
		return result;
	}
	
	public Double getLatitude() {
		Double result = null;
		try {
			result = jsonObject.getDouble("latitude");
		} catch (JSONException e) {
		}
		return result;
	}
	
	public Double getLongitude() {
		Double result = null;
		try {
			result = jsonObject.getDouble("longitude");
		} catch (JSONException e) {
		}
		return result;
	}
	
	public String getCoordinate() {
		String result = null;
		try {
			result = jsonObject.getString("coordinate");
		} catch (JSONException e) {
		}
		return result;
	}
	
	public String getImageURL() {
		String result = null;
		try {
			result = jsonObject.getString("imageURL");
		} catch (JSONException e) {
		}
		return result;
	}
} 
