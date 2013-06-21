package it.green.parkogre;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ParkDetailActivity extends Activity 
{
	private GPS		  gps		  = null;
	private TextView  textName    = null;
	private TextView  textCity    = null;
	private TextView  textIndirizzo    = null;
	private TextView  textCoordinate    = null;
	private ImageView photo       = null;
	private ImageView vote1 	  = null;
	private ImageView vote2 	  = null;
	private ImageView vote3 	  = null;
	private Button    toVote	  = null;
	private Button    indications = null;
	private double	  votoAttuale = 0;
	private int       numvoti     = 0;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_park_detail);
		
		textName 	= (TextView) 	findViewById(R.id.textName);
		textCity 	= (TextView) 	findViewById(R.id.textCity);
		textIndirizzo 	= (TextView) 	findViewById(R.id.textIndirizzo);
		textCoordinate 	= (TextView) 	findViewById(R.id.textCoordinate);
		photo 		= (ImageView) 	findViewById(R.id.Foto);
		vote1 		= (ImageView) 	findViewById(R.id.Voto1);
		vote2 		= (ImageView) 	findViewById(R.id.Voto2);
		vote3 		= (ImageView) 	findViewById(R.id.Voto3);
		toVote 		= (Button) 		findViewById(R.id.Vota);
		indications = (Button) 		findViewById(R.id.Indicazioni);
		votoAttuale = getIntent().getDoubleExtra("votoattuale", 0);
		numvoti     = getIntent().getIntExtra("numvoti", 0);
				
		textName. setText(getIntent().getStringExtra("nomeparco" ));
		textCity. setText(getIntent().getStringExtra("city" ));
		textIndirizzo. setText(getIntent().getStringExtra("indirizzoparco" ));
		textCoordinate. setText(getIntent().getStringExtra("coordinate" ));
		
//		showVote(Float.parseFloat("vote"));
		addListenerOnButtons();
		try {
			addPhoto(getIntent().getStringExtra("imageurl"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showVote(votoAttuale);
	}
	
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
	
	public void addListenerOnButtons()
	{
		toVote.setOnClickListener(new OnClickListener() 
		{ 
			public void onClick(View arg0) 
			{				 
				   Toast.makeText(ParkDetailActivity.this,
					"Work In Progress!", Toast.LENGTH_SHORT).show();
				   //login (secondo me è meglio farlo all'inizio)
				   //menu da 0 a 6?	 
				   //spedisco valore al server
			} 
		});
		
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
	
	public void addPhoto(String url) throws IOException
	{
		URL newurl = new URL(url); 
		Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream()); 
		photo.setImageBitmap(mIcon_val);
	}	
}
