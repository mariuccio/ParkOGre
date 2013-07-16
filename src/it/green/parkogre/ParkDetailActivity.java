package it.green.parkogre;


import java.io.IOException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

public class ParkDetailActivity extends Activity 
{
	private GPS		  gps		  	  = null;
	private TextView  textName    	  = null;
	private TextView  textCity    	  = null;
	private TextView  textAddress     = null;
	private TextView  textCoordinates = null;
	private TextView  textVoteNum     = null;
	private ImageView photo       	  = null;
	private ImageView vote1 	 	  = null;
	private ImageView vote2 	 	  = null;
	private ImageView vote3 	      = null;
	private Button    toVote	      = null;
	private Button    indications     = null;
	private double	  votoAttuale           ;
	private int       numvoti               ;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		/**********Standard Activity Start*************/
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_park_detail);
		
		/**********Taking Layouts from XML Files*******/
		textName 	    = (TextView) 	findViewById(R.id.textName           );
		textCity 	    = (TextView) 	findViewById(R.id.textCity           );
		textAddress     = (TextView) 	findViewById(R.id.textAddress        );
		textCoordinates = (TextView) 	findViewById(R.id.textCoordinates    );
		textVoteNum     = (TextView)    findViewById(R.id.textVoteNum        );
		photo 		    = (ImageView) 	findViewById(R.id.Photo              );
		vote1 		    = (ImageView) 	findViewById(R.id.Vote1              );
		vote2 		    = (ImageView) 	findViewById(R.id.Vote2              );
		vote3 		    = (ImageView) 	findViewById(R.id.Vote3              );
		toVote 		    = (Button) 		findViewById(R.id.Vote               );
		indications     = (Button) 		findViewById(R.id.Indicazions        );
		
		/**********Taking values from previous activity variables*********/
		votoAttuale     =                        getIntent().getDoubleExtra("votoattuale", 0);
		numvoti         =                        getIntent().getIntExtra("numvoti", 0);
		textName.       setText("Nome: "       + getIntent().getStringExtra("nomeparco"      ));
		textCity.       setText("Città: " + getIntent().getStringExtra("city"));
		textAddress.    setText("Indirizzo: " + getIntent().getStringExtra("indirizzoparco"));
		textCoordinates. setText("Coordinate: " + getIntent().getStringExtra("coordinate"));
		
		/****It writes number of votes using numvoti int variable****/
		textVoteNum.setText("Numero Voti: " + Integer.toString(numvoti));
				
		/**********Set button listeners*********/
		addListenerOnButtons(this);
		
		/**********Set park's image from url*********/		
		try {
			addPhoto(getIntent().getStringExtra("imageurl"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**********Set users' vote*********/
		showVote(votoAttuale);
		
		
	    
	}
	
	
	/*****Function that sets up png visibility depending on users' vote*****/
	public void showVote(Double x)
	{
		if (x<0.25)
		{
			vote1.setImageResource(R.drawable.pescio4su4);
			vote2.setImageResource(R.drawable.pescio4su4);
			vote3.setImageResource(R.drawable.pescio4su4);
		}
		if (x<0.5 && x>=0.25)
		{
			vote1.setImageResource(R.drawable.pescio4su4);
			vote2.setImageResource(R.drawable.pescio4su4);
			vote3.setImageResource(R.drawable.pescio3su4);
		}
		if (x<0.75 && x>=0.5)
		{
			vote1.setImageResource(R.drawable.pescio4su4);
			vote2.setImageResource(R.drawable.pescio4su4);
			vote3.setImageResource(R.drawable.pescio2su4);
		}
		if (x<1 && x>=0.75)
		{
			vote1.setImageResource(R.drawable.pescio4su4);
			vote2.setImageResource(R.drawable.pescio4su4);
			vote3.setImageResource(R.drawable.pescio1su4);
		}
		if (x<1.25 && x>=1)
		{
			vote1.setImageResource(R.drawable.pescio4su4);
			vote2.setImageResource(R.drawable.pescio4su4);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<1.5 && x>=1.25)
		{
			vote1.setImageResource(R.drawable.pescio4su4);
			vote2.setImageResource(R.drawable.pescio3su4);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<1.75 && x>=1.5)
		{
			vote1.setImageResource(R.drawable.pescio4su4);
			vote2.setImageResource(R.drawable.pescio2su4);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<2 && x>=1.75)
		{
			vote1.setImageResource(R.drawable.pescio4su4);
			vote2.setImageResource(R.drawable.pescio1su4);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<2.25 && x>=2)
		{
			vote1.setImageResource(R.drawable.pescio4su4);
			vote2.setVisibility(View.INVISIBLE);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<2.5 && x>=2.25)
		{
			vote1.setImageResource(R.drawable.pescio3su4);
			vote2.setVisibility(View.INVISIBLE);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<2.75 && x>=2.5)
		{
			vote1.setImageResource(R.drawable.pescio2su4);
			vote2.setVisibility(View.INVISIBLE);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<3 && x>=2.75)
		{
			vote1.setImageResource(R.drawable.pescio1su4);
			vote2.setVisibility(View.INVISIBLE);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<3.25 && x>=3)
		{
			vote1.setImageResource(R.drawable.marghe1su4);
			vote2.setVisibility(View.INVISIBLE);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<3.5 && x>=3.25)
		{
			vote1.setImageResource(R.drawable.marghe2su4);
			vote2.setVisibility(View.INVISIBLE);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<3.75 && x>=3.5)
		{
			vote1.setImageResource(R.drawable.marghe3su4);
			vote2.setVisibility(View.INVISIBLE);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<4 && x>=3.75)
		{
			vote1.setImageResource(R.drawable.marghe4su4);
			vote2.setVisibility(View.INVISIBLE);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<4.25 && x>=4)
		{
			vote1.setImageResource(R.drawable.marghe4su4);
			vote2.setImageResource(R.drawable.marghe1su4);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<4.5 && x>=4.25)
		{
			vote1.setImageResource(R.drawable.marghe4su4);
			vote2.setImageResource(R.drawable.marghe2su4);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<4.75 && x>=4.5)
		{
			vote1.setImageResource(R.drawable.marghe4su4);
			vote2.setImageResource(R.drawable.marghe3su4);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<5 && x>=4.75)
		{
			vote1.setImageResource(R.drawable.marghe4su4);
			vote2.setImageResource(R.drawable.marghe4su4);
			vote3.setVisibility(View.INVISIBLE);
		}
		if (x<5.25 && x>=5)
		{
			vote1.setImageResource(R.drawable.marghe4su4);
			vote2.setImageResource(R.drawable.marghe4su4);
			vote1.setImageResource(R.drawable.marghe1su4);
		}
		if (x<5.5 && x>5.25)
		{
			vote1.setImageResource(R.drawable.marghe4su4);
			vote2.setImageResource(R.drawable.marghe4su4);
			vote1.setImageResource(R.drawable.marghe2su4);
		}
		if (x<5.75 && x>=5.5)
		{
			vote1.setImageResource(R.drawable.marghe4su4);
			vote2.setImageResource(R.drawable.marghe4su4);
			vote1.setImageResource(R.drawable.marghe3su4);
		}
		if (x<=6 && x>=5.75)
		{
			vote1.setImageResource(R.drawable.marghe4su4);
			vote2.setImageResource(R.drawable.marghe4su4);
			vote1.setImageResource(R.drawable.marghe4su4);
		}
	}
	
	public void addListenerOnButtons(final Context context)
	{
		/***On click it shows a menu where the user can choose a vote from 0 to 6***/
		toVote.setOnClickListener(new OnClickListener() 
		{ 
			public void onClick(View arg0) 
			{				 
				   Toast.makeText(ParkDetailActivity.this,
					"Work In Progress!", Toast.LENGTH_SHORT).show();
				   //login (secondo me è meglio farlo all'inizio)
				   //menu da 0 a 6?	 
				   //spedisco valore al server
				   AlertDialog.Builder adb=new AlertDialog.Builder(context);
				   adb.setTitle("Vote this Park");
				   adb.setMessage("Choose from 0 to 6");
				   adb.setNeutralButton("I don't want to vote", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        }
				     });
				   adb.show();
			} 
		});
		/***On click it shows an activity where the user can see google maps indications from his location to the park***/
		indications.setOnClickListener(new OnClickListener() 
		{ 
			public void onClick(View arg0) 
			{				 
				   Toast.makeText(ParkDetailActivity.this,
					"Work In Progress!", Toast.LENGTH_SHORT).show();
//				   Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?+" +
//					          "saddr="+gps.getLatitude()+","+gps.getLongitude()+"&daddr="+ getIntent().getStringExtra("latitude" )+","+ getIntent().getStringExtra("longitude" )));
//					         startActivity(navigation);
				   //intento a google maps	 
			} 
		});
	}
	/***Function that takes photo from the url and puts it in the imageview***/
	public void addPhoto(String url) throws IOException
	{
		URL newurl = new URL(url); 
		Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream()); 
		photo.setImageBitmap(mIcon_val);
	}	
}
