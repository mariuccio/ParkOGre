package it.green.parkogre.rest;

import android.widget.Toast;
import com.google.gson.Gson;
import it.green.parkogre.ParksListActivity;
import it.green.parkogre.rest.resource.ClientParkResponse;
import it.green.parkogre.rest.resource.OGServerResponse;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import com.loopj.android.http.*;

public class ParkAPI extends RestClient {
    private static final String GET_START_PARKS = "http://10.0.2.2:8080/park/search/gps/";
    private static final String GET_PARKS       = "http://10.0.2.2:8080/park/search/query/";
    private static final String VOTE_PARK       = "http://10.0.2.2:8080/park/feed/";
    private static final String ADD_PARK		= "http://10.0.2.2:8080/park/add";

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

    public static String addPark(String nome, double lat, double lon, final ParksListActivity parksListActivity) throws UnsupportedEncodingException {
        AsyncHttpClient client = new AsyncHttpClient();
        HttpEntity stringEntity;

        ClientParkResponse parco=new ClientParkResponse(lat, lon, nome);
        final Gson gson = new Gson();
        String toSend = gson.toJson(parco);
        stringEntity=new StringEntity(toSend);
        client.post(parksListActivity.getApplicationContext(), ADD_PARK, stringEntity, "application/textplain", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                OGServerResponse response = gson.fromJson(s,  OGServerResponse.class);
                if (response.getOk()) {
                    parksListActivity.progressDialog.dismiss();
                    Toast.makeText(parksListActivity, "lol", 10).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                parksListActivity.progressDialog.dismiss();
                Toast.makeText(parksListActivity, "asd", 10).show();
            }
        });

        return "ok";
    }
}
