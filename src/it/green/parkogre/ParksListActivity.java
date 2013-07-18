package it.green.parkogre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.*;
import org.json.JSONArray;
import it.green.parkogre.rest.ParkAPI;
import it.green.parkogre.rest.resource.Park;

public class ParksListActivity extends Activity {
    private EditText            searchText  = null;
    private ImageButton         addPlace    = null;
    private ImageButton         searchPlace = null;
    private ImageButton         nearSort    = null;
    private ImageButton         voteSort    = null;
    private ListView            resultList  = null;
    private ArrayList<Park>     parks       = null;
    private ArrayAdapter<Park>  adapter     = null;
    private GPS                 gps         = null;
    private ProgressDialog      dialog      = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**********Standard Activity Start*************/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parks_list);

        /**********GPS Initialization*******/
        gps = new GPS(ParksListActivity.this);
        if (!gps.canGetLocation())
            gps.showSettingsAlert();

        /**********Taking Layouts from XML Files*******/
        searchText  = (EditText)    findViewById(R.id.SearchText    );
        addPlace    = (ImageButton) findViewById(R.id.AddPlace      );
        searchPlace = (ImageButton) findViewById(R.id.SearchPlace   );
        nearSort    = (ImageButton) findViewById(R.id.NearSort      );
        voteSort    = (ImageButton) findViewById(R.id.VoteSort      );
        resultList  = (ListView)    findViewById(R.id.parksListView );

        /**********Adapter for the listview and ParkDetailActivity intent*******/
        adapter = new ArrayAdapter<Park>(getApplicationContext(), R.layout.listview_layout, new ArrayList<Park>());
        resultList.setAdapter(adapter);
        resultList.setCacheColorHint(Color.TRANSPARENT);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Park park = (Park) adapter.getItemAtPosition(position);
                Intent intent = new Intent(ParksListActivity.this, ParkDetailActivity.class);
                //variables for opening activity
                intent.putExtra("id",           park.getId());
                intent.putExtra("votenum",      park.getVoteNum());
                intent.putExtra("currentvote",  park.getCurrentVote());
                intent.putExtra("city",         park.getCity());
                intent.putExtra("parkname",     park.getNomeParco());
                intent.putExtra("parkaddress",  park.getIndirizzoParco());
                intent.putExtra("coordinates",  park.getCoordinate());
                intent.putExtra("imageurl",     park.getImageURL());
                intent.putExtra("latitude",     park.getLatitude());
                intent.putExtra("longitude",    park.getLongitude());
                startActivity(intent);
            }
        });
        fetchParks(resultList);
        addListenerOnButtons(this);
    }

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return dist * meterConversion;
    }

    public void addListenerOnButtons(final Context context) {
        /*Add park where you are in the server's database*/
        addPlace.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                gps.getLatitude();
                gps.getLongitude();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_place_dialog);
                dialog.setTitle("Add the Park where you are");

                final EditText parkNameText = (EditText) dialog.findViewById(R.id.ParkNameText);

                //Code for empty the editText at touch
                parkNameText.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (parkNameText.getText().toString().equals("Add Park's Name Here"))
                            parkNameText.setText("");
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return false;
                    }
                });

                Button addParkButton = (Button) dialog.findViewById(R.id.AddParkButton);
                addParkButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO aggiungo il parco vedendo il nome in parkNameText e passo al server lat long e nome
                    }
                });

                Button noAddParkButton = (Button) dialog.findViewById(R.id.NoAddParkButton);
                noAddParkButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });

        /*Search parks according to what you edit*/
        searchPlace.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                fetchParksSearch(resultList);
            }
        });

        /*Sort parks according to distances from you*/
        nearSort.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Collections.sort(parks, new Comparator<Park>() {
                    public int compare(Park a, Park b) {
                        double dist1 = distFrom(a.getLatitude(),
                                a.getLongitude(),
                                gps.getLatitude(),
                                gps.getLongitude());
                        double dist2 = distFrom(b.getLatitude(),
                                b.getLongitude(),
                                gps.getLatitude(),
                                gps.getLongitude());
                        if (dist1 == dist2)
                            return 0;
                        return dist1 < dist2 ? -1 : 1;
                    }
                });
                fetchParksOrder(resultList);
            }
        });

        /*Sort parks according to votes*/
        voteSort.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Collections.sort(parks, new Comparator<Park>() {
                    public int compare(Park a, Park b) {
                        double v1 = a.getCurrentVote();
                        double v2 = b.getCurrentVote();
                        if (v1 == v2)
                            return 0;
                        return v1 > v2 ? -1 : 1;
                    }
                });
                fetchParksOrder(resultList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parks_list, menu);
        return true;
    }

    public void fetchParks(View v) {
        new FetchParksTask().execute();
    }

    public void fetchParksSearch(View v) {
        new FetchParksSearchTask().execute();
    }

    private class FetchParksTask extends AsyncTask<Void, Void, ArrayList<Park>> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ParksListActivity.this);
            dialog.setIndeterminate(true);
            dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.anim_progress_bar));
            dialog.setMessage("Caricamento...");
            dialog.show();
            searchPlace.setEnabled(false);
            addPlace.setEnabled(false);
            nearSort.setEnabled(false);
            voteSort.setEnabled(false);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Park> doInBackground(Void... params) {
            parks = new ArrayList<Park>();

            try {
                JSONArray jsonResult = new JSONArray(ParkAPI.getStartParks(gps.getLatitude(), gps.getLongitude()));
                for (int i = 0; i < jsonResult.length(); i++) {
                    parks.add(new Park(jsonResult.getJSONObject(i)));
                }
            } catch (Exception e) {
                parks = null;
            }

            return parks;
        }

        @Override
        protected void onPostExecute(ArrayList<Park> parks) {
            searchPlace.setEnabled(true);
            addPlace.setEnabled(true);
            nearSort.setEnabled(true);
            voteSort.setEnabled(true);
            dialog.cancel();
            adapter.clear();
            if (parks != null) {
                for (Park park : parks) {
                    adapter.add(park);
                }

            }

            super.onPostExecute(parks);
        }
    }

    private class FetchParksSearchTask extends AsyncTask<Void, Void, ArrayList<Park>> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ParksListActivity.this);
            dialog.setIndeterminate(true);
            dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.anim_progress_bar));
            dialog.setMessage("Caricamento...");
            dialog.show();
            searchPlace.setEnabled(false);
            addPlace.setEnabled(false);
            nearSort.setEnabled(false);
            voteSort.setEnabled(false);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Park> doInBackground(Void... params) {
            try {
                parks.clear();
                JSONArray jsonResult = new JSONArray(new ParkAPI().getParks(searchText.getText().toString()));
                for (int i = 0; i < jsonResult.length(); i++) {
                    parks.add(new Park(jsonResult.getJSONObject(i)));
                }
            } catch (Exception e) {
            }
            return parks;
        }

        @Override
        protected void onPostExecute(ArrayList<Park> parks) {
            searchPlace.setEnabled(true);
            addPlace.setEnabled(true);
            nearSort.setEnabled(true);
            voteSort.setEnabled(true);
            dialog.cancel();
            adapter.clear();

            if (parks != null) {
                for (Park park : parks) {
                    adapter.add(park);
                }
            }

            super.onPostExecute(parks);
        }
    }


    public void fetchParksOrder(View v) {
        new FetchParksOrderTask().execute();
    }


    private class FetchParksOrderTask extends AsyncTask<Void, Void, ArrayList<Park>> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ParksListActivity.this);
            dialog.setIndeterminate(true);
            dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.anim_progress_bar));
            dialog.setMessage("Caricamento...");
            dialog.show();
            searchPlace.setEnabled(false);
            addPlace.setEnabled(false);
            nearSort.setEnabled(false);
            voteSort.setEnabled(false);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Park> doInBackground(Void... params) {
            return parks;
        }

        @Override
        protected void onPostExecute(ArrayList<Park> parks) {
            searchPlace.setEnabled(true);
            addPlace.setEnabled(true);
            nearSort.setEnabled(true);
            voteSort.setEnabled(true);
            dialog.cancel();
            adapter.clear();

            if (parks != null) {
                for (Park park : parks) {
                    adapter.add(park);
                }
            }

            super.onPostExecute(parks);
        }
    }

}