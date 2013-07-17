package it.green.parkogre;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import it.green.parkogre.rest.ParcoAPI;
import it.green.parkogre.rest.resource.Parco;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ParksListActivity extends Activity 
{
	private EditText			searchText	= null;
	private ImageButton 		addPlace	= null;
	private ImageButton 		searchPlace = null;
	private ImageButton 		nearSort 	= null;
	private ImageButton 		voteSort 	= null;
	private ListView 			resultList 	= null;
	private ArrayList<Parco> 	parks       = null;
	private ArrayAdapter<Parco> adapter 	= null;
	private GPS  				gps  		= null;
	private ProgressDialog 		dialog 		= null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parks_list);
		
		gps = new GPS(ParksListActivity.this);
        if(!gps.canGetLocation())
           	gps.showSettingsAlert();  
		
        searchText	= (EditText)	findViewById(R.id.SearchText);
		addPlace 	= (ImageButton) findViewById(R.id.AddPlace);
		searchPlace = (ImageButton) findViewById(R.id.SearchPlace);
		nearSort 	= (ImageButton) findViewById(R.id.NearSort);
		voteSort 	= (ImageButton) findViewById(R.id.VoteSort);
		resultList 	= (ListView) 	findViewById(R.id.parksListView);
		//superBar 	= (ImageView) 	findViewById(R.id.SuperBar);
		
		adapter = new ArrayAdapter<Parco>(getApplicationContext(), R.layout.listview_layout, new ArrayList<Parco>());
		resultList.setAdapter(adapter);
		resultList.setCacheColorHint(Color.TRANSPARENT);
		resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
			{
				Parco park = (Parco) adapter.getItemAtPosition(position);
				Intent intent = new Intent(ParksListActivity.this, ParkDetailActivity.class);
				//variables for opening activity
                intent.putExtra("id",             park.getId());
				intent.putExtra("numvoti",        park.getNumVoti());
				intent.putExtra("votoattuale",    park.getvotoAttuale());
				intent.putExtra("city",           park.getCity());
				intent.putExtra("nomeparco",      park.getNomeParco());
				intent.putExtra("indirizzoparco", park.getIndirizzoParco());
				intent.putExtra("coordinate",     park.getCoordinate());
				intent.putExtra("imageurl",       park.getImageURL());
				intent.putExtra("latitudine",     park.getLatitude());
				intent.putExtra("longitudine",    park.getLongitude());
				startActivity(intent);
			}
		});
		fetchParks(resultList);
		addListenerOnButtons(this);
	}
	
	 public static double distFrom(double lat1, double lng1, double lat2, double lng2) 
	 {
	     double earthRadius = 3958.75;
	     double dLat = Math.toRadians(lat2-lat1);
	     double dLng = Math.toRadians(lng2-lng1);
	     double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	                Math.sin(dLng/2) * Math.sin(dLng/2);
	     double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	     double dist = earthRadius * c;

	     int meterConversion = 1609;

	     return dist * meterConversion;
	 }
	
	public void addListenerOnButtons(final Context context)
	{
		addPlace.setOnClickListener(new OnClickListener() 
		{ 
			public void onClick(View arg0) 
			{			
				gps.getLatitude();
				gps.getLongitude();
//				Toast.makeText(ParksListActivity.this,
//				"addPlaceButton is clicked!"+"lat: "+gps.getLatitude()+"long: "+gps.getLongitude(), Toast.LENGTH_SHORT).show();
				//google account
				//comunica latitudine e longitudine al server
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_place_dialog);
                dialog.setTitle("Add the Park where you are");

                EditText parkNameText = (EditText) dialog.findViewById(R.id.ParkNameText);

                Button addParkButton = (Button) dialog.findViewById(R.id.AddParkButton);
                addParkButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //aggiungo il parco vedendo il nome in parkNameText e passo al server lat long e nome
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
		
		searchPlace.setOnClickListener(new OnClickListener() 
		{ 
			public void onClick(View arg0) 
			{
                Toast.makeText(ParksListActivity.this,
					"latitude: "+gps.getLatitude()+" longitude: "+gps.getLongitude(), Toast.LENGTH_SHORT).show();
				fetchParksSearch(resultList);
			} 
		});
		
		nearSort.setOnClickListener(new OnClickListener() 
		{ 
			public void onClick(View arg0) 
			{
//                  Toast.makeText(ParksListActivity.this,
//					"nearSortButton is clicked!", Toast.LENGTH_SHORT).show();
					//ordina per vicinanza
				
					Collections.sort(parks, new Comparator<Parco>() {
				      public int compare(Parco a, Parco b) {
				          double dist1 = distFrom(a.getLatitude(), 
				                  a.getLongitude(),
				                  gps.getLatitude(), 
				                  gps.getLongitude());
				          double dist2 = distFrom(b.getLatitude(), 
				                  b.getLongitude(), 
				                  gps.getLatitude(), 
				                  gps.getLongitude());
				          if(dist1 == dist2)
				              return 0;
				          return dist1 < dist2 ? -1 : 1;             
				         }  
				     }); 
//				       adapter.clear();
//				     for (Parco park : parks) 
//				     {
//				      adapter.add(park);
//				     }
//				     resultList.setAdapter(adapter);
					fetchParksOrder(resultList);
			} 
		});
		
		voteSort.setOnClickListener(new OnClickListener() 
		{ 
			public void onClick(View arg0) 
			{				 
//				    Toast.makeText(ParksListActivity.this,
//					"voteSortButton is clicked!", Toast.LENGTH_SHORT).show();
					//ordina per voto
				   	Collections.sort(parks, new Comparator<Parco>() {
			         public int compare(Parco a, Parco b) {
			             double voto1 = a.getvotoAttuale();
			             double voto2 = b.getvotoAttuale();
			             if(voto1 == voto2)
			                     return 0;
			                return voto1 > voto2 ? -1 : 1;
			            }
			        }); 
//				       adapter.clear();
//				     for (Parco park : parks) 
//				     {
//				      adapter.add(park);
//				     }
//				     resultList.setAdapter(adapter);
					fetchParksOrder(resultList);
			} 
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parks_list, menu);
		return true;
	}

	public void fetchParks(View v) 
	{
		new FetchParksTask().execute();
	}
	public void fetchParksSearch(View v)
	{
		new FetchParksSearchTask().execute();
	}
	
	private class FetchParksTask extends AsyncTask<Void, Void, ArrayList<Parco>>
	{
		@Override
		protected void onPreExecute() 
		{
			dialog = new ProgressDialog(ParksListActivity.this);
			dialog.setIndeterminate(true);
			dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.anim_progress_bar));
			dialog.setMessage("Caricamento...");
			dialog.show();
			searchPlace.setEnabled(false);
			addPlace.	setEnabled(false);
			nearSort.	setEnabled(false);
			voteSort.	setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected ArrayList<Parco> doInBackground(Void... params)
		{
			parks = new ArrayList<Parco>();
			
			try 
			{
				// Parse JSONObject as simple text and put values inside adapter
//				System.out.println("CIAO"+ParcoAPI.getStartParks(gps.getLatitude(), gps.getLongitude()));
				JSONArray jsonResult = new JSONArray(ParcoAPI.getStartParks(gps.getLatitude(), gps.getLongitude()));
//				System.out.println("OOOOOH"+jsonResult);
//				if(jsonResult==null)
//				{
//					parks.add(new Parco(null));
//					return parks;
//				}
				for (int i = 0; i < jsonResult.length(); i++) 
				{
//					System.out.println(jsonResult.getJSONObject(i));
					parks.add(new Parco(jsonResult.getJSONObject(i)));
				}
			} 
			catch (Exception e) 
			{
//				Log.e("ogre", e.getMessage());
				parks = null;
			}
			return parks;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Parco> parks)
		{
			searchPlace.setEnabled(true);
			addPlace.	setEnabled(true);
			nearSort.	setEnabled(true);
			voteSort.	setEnabled(true);
			dialog.cancel();			
			adapter.clear();
			if(parks!=null) 
			{
				for (Parco park : parks)
				{
					adapter.add(park);
				}
				
			}
			
			super.onPostExecute(parks);
		}
	}
	private class FetchParksSearchTask extends AsyncTask<Void, Void, ArrayList<Parco>>
	{
		@Override
		protected void onPreExecute() 
		{
			dialog = new ProgressDialog(ParksListActivity.this);
			dialog.setIndeterminate(true);
			dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.anim_progress_bar));
			dialog.setMessage("Caricamento...");
			dialog.show();
			searchPlace.setEnabled(false);
			addPlace.	setEnabled(false);
			nearSort.	setEnabled(false);
			voteSort.	setEnabled(false);
			super.onPreExecute();
		}

		@Override
		protected ArrayList<Parco> doInBackground(Void... params)
		{
			//ArrayList<Parco> parks = new ArrayList<Parco>();
			//comunica il valore al server e attende il risultato
			
			try 
			{
                parks.clear();
				// Parse JSONObject as simple text and put values inside adapter
				JSONArray jsonResult = new JSONArray(new ParcoAPI().getParks(searchText.getText().toString()));
//				if(jsonResult==null)
//				{
//					parks.add(new Parco(null));
//				}
				for (int i = 0; i < jsonResult.length(); i++) 
				{
					parks.add(new Parco(jsonResult.getJSONObject(i)));
				}
			} 
			catch (Exception e) 
			{
				//Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			return parks;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Parco> parks)
		{
			searchPlace.setEnabled(true);
			addPlace.	setEnabled(true);
			nearSort.	setEnabled(true);
			voteSort.	setEnabled(true);
			dialog.     cancel();
			adapter.    clear();
			if (parks!=null)
			{
				for (Parco park : parks)
				{
					adapter.add(park);
				}	
			}
			super.onPostExecute(parks);
		}
	}




	public void fetchParksOrder(View v)
	{
		new FetchParksOrderTask().execute();
	}
	
	
	private class FetchParksOrderTask extends AsyncTask<Void, Void, ArrayList<Parco>>
	{
		@Override
		protected void onPreExecute() 
		{
			dialog = new ProgressDialog(ParksListActivity.this);
			dialog.setIndeterminate(true);
			dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.anim_progress_bar));
			dialog.setMessage("Caricamento...");
			dialog.show();
			searchPlace.setEnabled(false);
			addPlace.	setEnabled(false);
			nearSort.	setEnabled(false);
			voteSort.	setEnabled(false);
			super.onPreExecute();
		}
	
		@Override
		protected ArrayList<Parco> doInBackground(Void... params)
		{
			/*parks = new ArrayList<Parco>();
			
			try 
			{
				// Parse JSONObject as simple text and put values inside adapter
	//			System.out.println("CIAO"+ParcoAPI.getStartParks(gps.getLatitude(), gps.getLongitude()));
				JSONArray jsonResult = new JSONArray(ParcoAPI.getStartParks(gps.getLatitude(), gps.getLongitude()));
	//			System.out.println("OOOOOH"+jsonResult);
	//			if(jsonResult==null)
	//			{
	//				parks.add(new Parco(null));
	//				return parks;
	//			}
				for (int i = 0; i < jsonResult.length(); i++) 
				{
	//				System.out.println(jsonResult.getJSONObject(i));
					parks.add(new Parco(jsonResult.getJSONObject(i)));
				}
			} 
			catch (Exception e) 
			{
	//			Log.e("ogre", e.getMessage());
				parks = null;
			}
			*/
			return parks;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Parco> parks)
		{
			searchPlace.setEnabled(true);
			addPlace.	setEnabled(true);
			nearSort.	setEnabled(true);
			voteSort.	setEnabled(true);
			dialog.cancel();			
			adapter.clear();
			
			if(parks!=null) 
			{
				for (Parco park : parks)
				{
					adapter.add(park);
				}
				
			}
			
			super.onPostExecute(parks);
		}
	}

}