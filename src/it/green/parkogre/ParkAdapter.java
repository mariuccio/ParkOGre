package it.green.parkogre;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import it.green.parkogre.rest.resource.Park;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: mario
 * Date: 7/22/13
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParkAdapter extends ArrayAdapter<Park> {
    Context context;
    int layoutResourceId;
    ArrayList<Park> data = null;

    public ParkAdapter(Context context, int layoutResourceId, ArrayList<Park> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ParkHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ParkHolder();

            holder.imageVote1 = (ImageView)row.findViewById(R.id.imageVote1);
            holder.imageVote2 = (ImageView)row.findViewById(R.id.imageVote2);
            holder.imageVote3 = (ImageView)row.findViewById(R.id.imageVote3);
            holder.imagePhoto = (ImageView)row.findViewById(R.id.photoImageView);
            holder.txtCity    = (TextView) row.findViewById(R.id.txtCity);
            holder.txtName    = (TextView) row.findViewById(R.id.txtName);
            row.setTag(holder);
        }
        else
        {
            holder = (ParkHolder)row.getTag();
        }

        Park park = data.get(position);
        try{
            holder.imagePhoto.setImageBitmap(BitmapFactory.decodeStream((new URL(park.getImageURL()).openConnection().getInputStream())));
        } catch (IOException e){}
        double x = park.getCurrentVote();
        holder.txtName.setText(park.getParkName());
        holder.txtCity.setText(park.getCity());
        if (x < 0.25) {
            holder.imageVote1.setImageResource(R.drawable.pescio4su4);
            holder.imageVote2.setImageResource(R.drawable.pescio4su4);
            holder.imageVote3.setImageResource(R.drawable.pescio4su4);
        }
        if (x < 0.5 && x >= 0.25) {
            holder.imageVote1.setImageResource(R.drawable.pescio4su4);
            holder.imageVote2.setImageResource(R.drawable.pescio4su4);
            holder.imageVote3.setImageResource(R.drawable.pescio3su4);
        }
        if (x < 0.75 && x >= 0.5) {
            holder.imageVote1.setImageResource(R.drawable.pescio4su4);
            holder.imageVote2.setImageResource(R.drawable.pescio4su4);
            holder.imageVote3.setImageResource(R.drawable.pescio2su4);
        }
        if (x < 1 && x >= 0.75) {
            holder.imageVote1.setImageResource(R.drawable.pescio4su4);
            holder.imageVote2.setImageResource(R.drawable.pescio4su4);
            holder.imageVote3.setImageResource(R.drawable.pescio1su4);
        }
        if (x < 1.25 && x >= 1) {
            holder.imageVote1.setImageResource(R.drawable.pescio4su4);
            holder.imageVote2.setImageResource(R.drawable.pescio4su4);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 1.5 && x >= 1.25) {
            holder.imageVote1.setImageResource(R.drawable.pescio4su4);
            holder.imageVote2.setImageResource(R.drawable.pescio3su4);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 1.75 && x >= 1.5) {
            holder.imageVote1.setImageResource(R.drawable.pescio4su4);
            holder.imageVote2.setImageResource(R.drawable.pescio2su4);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 2 && x >= 1.75) {
            holder.imageVote1.setImageResource(R.drawable.pescio4su4);
            holder.imageVote2.setImageResource(R.drawable.pescio1su4);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 2.25 && x >= 2) {
            holder.imageVote1.setImageResource(R.drawable.pescio4su4);
            holder.imageVote2.setVisibility(View.INVISIBLE);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 2.5 && x >= 2.25) {
            holder.imageVote1.setImageResource(R.drawable.pescio3su4);
            holder.imageVote2.setVisibility(View.INVISIBLE);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 2.75 && x >= 2.5) {
            holder.imageVote1.setImageResource(R.drawable.pescio2su4);
            holder.imageVote2.setVisibility(View.INVISIBLE);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 3 && x >= 2.75) {
            holder.imageVote1.setImageResource(R.drawable.pescio1su4);
            holder.imageVote2.setVisibility(View.INVISIBLE);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 3.25 && x >= 3) {
            holder.imageVote1.setImageResource(R.drawable.marghe1su4);
            holder.imageVote2.setVisibility(View.INVISIBLE);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 3.5 && x >= 3.25) {
            holder.imageVote1.setImageResource(R.drawable.marghe2su4);
            holder.imageVote2.setVisibility(View.INVISIBLE);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 3.75 && x >= 3.5) {
            holder.imageVote1.setImageResource(R.drawable.marghe3su4);
            holder.imageVote2.setVisibility(View.INVISIBLE);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 4 && x >= 3.75) {
            holder.imageVote1.setImageResource(R.drawable.marghe4su4);
            holder.imageVote2.setVisibility(View.INVISIBLE);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 4.25 && x >= 4) {
            holder.imageVote1.setImageResource(R.drawable.marghe4su4);
            holder.imageVote2.setImageResource(R.drawable.marghe1su4);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 4.5 && x >= 4.25) {
            holder.imageVote1.setImageResource(R.drawable.marghe4su4);
            holder.imageVote2.setImageResource(R.drawable.marghe2su4);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 4.75 && x >= 4.5) {
            holder.imageVote1.setImageResource(R.drawable.marghe4su4);
            holder.imageVote2.setImageResource(R.drawable.marghe3su4);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 5 && x >= 4.75) {
            holder.imageVote1.setImageResource(R.drawable.marghe4su4);
            holder.imageVote2.setImageResource(R.drawable.marghe4su4);
            holder.imageVote3.setVisibility(View.INVISIBLE);
        }
        if (x < 5.25 && x >= 5) {
            holder.imageVote1.setImageResource(R.drawable.marghe4su4);
            holder.imageVote2.setImageResource(R.drawable.marghe4su4);
            holder.imageVote3.setImageResource(R.drawable.marghe1su4);
        }
        if (x < 5.5 && x > 5.25) {
            holder.imageVote1.setImageResource(R.drawable.marghe4su4);
            holder.imageVote2.setImageResource(R.drawable.marghe4su4);
            holder.imageVote3.setImageResource(R.drawable.marghe2su4);
        }
        if (x < 5.75 && x >= 5.5) {
            holder.imageVote1.setImageResource(R.drawable.marghe4su4);
            holder.imageVote2.setImageResource(R.drawable.marghe4su4);
            holder.imageVote3.setImageResource(R.drawable.marghe3su4);
        }
        if (x <= 6 && x >= 5.75) {
            holder.imageVote1.setImageResource(R.drawable.marghe4su4);
            holder.imageVote2.setImageResource(R.drawable.marghe4su4);
            holder.imageVote3.setImageResource(R.drawable.marghe4su4);
        }
        return row;
    }

    static class ParkHolder
    {
        ImageView imagePhoto;
        ImageView imageVote1;
        ImageView imageVote2;
        ImageView imageVote3;
        TextView  txtCity;
        TextView  txtName;
    }
}
