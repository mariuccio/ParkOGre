package it.green.parkogre.rest.resource;

import org.json.JSONException;
import org.json.JSONObject;

//Methods for JSONObject interpretation
public class Park implements Resource {
	private JSONObject jsonObject;
	
	public Park(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

    public int getId() {
        int result=0;
        try{
            result = jsonObject.getInt("id");
        } catch (JSONException e) {
        }
        return result;
    }

	public int getVoteNum() {
		int result = 0;
		try {
			result = jsonObject.getInt("numVoti");
		} catch (JSONException e) {
		}
		return result;
	}
	
	public double getCurrentVote() {
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
		String present = this.getParkName() + ", " + this.getCity();
		return present;
	}
	
	public String getParkName() {
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
