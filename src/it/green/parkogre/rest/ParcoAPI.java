package it.green.parkogre.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import com.google.gson.Gson;

public class ParcoAPI extends RestClient {
	private static final String GET_START_PARKS = "http://lazooo.redirectme.net:8080/park/search/gps/";
	private static final String GET_PARKS = "http://lazooo.redirectme.net:8080/park/search/query/";
	
	/* API calls */
	public static String getStartParks(double d, double f) {
//		Gson jsonobject = new Gson();
//		Map map = jsonobject.fromJson(getJson(ParcoAPI.GET_START_PARKS + d + "," + f), Map.class);
//		
		InputStream is = null;
		
		try {
			is = new URL(ParcoAPI.GET_START_PARKS + d + "," + f).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			is.close();
			return jsonText;
			
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
//		String response;
//		try {
//			URL url = new URL(ParcoAPI.GET_START_PARKS + d + "," + f);
//			response = getMethod(url);
//		} catch (Exception e) {
//			response = e.getMessage();
//		}
//		return response;
	}
	
	private static String readAll(Reader rd) throws IOException {
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read())!= -1) {
				sb.append((char) cp);
			}
			return sb.toString();
	}

//	private String getJson(String string) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public String getParks(String s) {
		String response;
		try {
			URL url = new URL(ParcoAPI.GET_PARKS + URLEncoder.encode(s));
			response = getMethod(url);
		} catch (Exception e) {
			response = e.getMessage();
		}
		return response;
	}
}
