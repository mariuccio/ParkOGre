package it.green.parkogre.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class ParkAPI extends RestClient {
    private static final String GET_START_PARKS = "http://10.0.2.2:8080/park/search/gps/";
    private static final String GET_PARKS       = "http://10.0.2.2:8080/park/search/query/";
    private static final String VOTE_PARK       = "http://10.0.2.2:8080/park/feed/";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /* API calls */
    public static String getStartParks(double d, double f) {
        InputStream is = null;

        try {
            is = new URL(ParkAPI.GET_START_PARKS + d + "," + f).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            is.close();
            return jsonText;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getParks(String s) {
        String response;
        try {
            URL url = new URL(ParkAPI.GET_PARKS + URLEncoder.encode(s));
            response = getMethod(url);
        } catch (Exception e) {
            response = e.getMessage();
        }
        return response;
    }

    public static String votePark(int id, int vote) {
        InputStream is = null;
        try {
            is = new URL(ParkAPI.VOTE_PARK + id + "/" + vote).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            is.close();
            return jsonText;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
