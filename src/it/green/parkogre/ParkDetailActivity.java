package it.green.parkogre;

import java.io.IOException;
import java.net.URL;
import android.app.Dialog;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.*;
import it.green.parkogre.rest.ParkAPI;
import org.json.JSONObject;

public class ParkDetailActivity extends Activity {
    private GPS             gps             = null;
    private TextView        textName        = null;
    private TextView        textCity        = null;
    private TextView        textAddress     = null;
    private TextView        textCoordinates = null;
    private TextView        textVoteNum     = null;
    private ImageView       photo           = null;
    private ImageView       vote1           = null;
    private ImageView       vote2           = null;
    private ImageView       vote3           = null;
    private Button          toVote          = null;
    private Button          indications     = null;
    private double          currentVote     = 0   ;
    private int             voteNum         = 0   ;
    private int             id              = 0   ;
    private ProgressDialog  progressdialog  = null;
    /*Vote popup*/
    private Dialog voteDialog = null;
    private Button          vote0Button     = null;
    private Button          vote1Button     = null;
    private Button          vote2Button     = null;
    private Button          vote3Button     = null;
    private Button          vote4Button     = null;
    private Button          vote5Button     = null;
    private Button          vote6Button     = null;
    private Button          noVoteButton    = null;
    boolean                 connected             ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**********Standard Activity Start*************/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);

        /**********Taking Layouts from XML Files*******/
        textName        =   (TextView)  findViewById(R.id.textName          );
        textCity        =   (TextView)  findViewById(R.id.textCity          );
        textAddress     =   (TextView)  findViewById(R.id.textAddress       );
        textCoordinates =   (TextView)  findViewById(R.id.textCoordinates   );
        textVoteNum     =   (TextView)  findViewById(R.id.textVoteNum       );
        photo           =   (ImageView) findViewById(R.id.Photo             );
        vote1           =   (ImageView) findViewById(R.id.Vote1             );
        vote2           =   (ImageView) findViewById(R.id.Vote2             );
        vote3           =   (ImageView) findViewById(R.id.Vote3             );
        toVote          =   (Button)    findViewById(R.id.Vote              );
        indications     =   (Button)    findViewById(R.id.Indications       );

        /**********Taking values from previous activity variables*********/
        id              = getIntent().getIntExtra   ("id"           , 0);
        currentVote     = getIntent().getDoubleExtra("currentvote"  , 0);
        voteNum         = getIntent().getIntExtra   ("votenum"      , 0);
        textName        .setText("Name: "        + getIntent().getStringExtra("parkname"   ));
        textCity        .setText("City: "        + getIntent().getStringExtra("city"       ));
        textAddress     .setText("Address: "     + getIntent().getStringExtra("parkaddress"));
        textCoordinates .setText("Coordinates: " + getIntent().getStringExtra("coordinates"));
        connected       = getIntent().getBooleanExtra("connected", false);

        /****It writes number of votes using voteNum int variable****/
        textVoteNum.setText("Votes' Number: " + Integer.toString(voteNum));

        /**********Set buttons' listeners*********/
        addListenerOnButtons(this);

        /**********Set park's image from url*********/
        try {
            addPhoto(getIntent().getStringExtra("imageurl"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**********Set users' graphical vote*********/
        showVote(currentVote);
    }


    /**
     * Function that sets up png visibility depending on users' vote
     */
    public void showVote(Double x) {
        if (x < 0.25) {
            vote1.setImageResource(R.drawable.pescio4su4);
            vote2.setImageResource(R.drawable.pescio4su4);
            vote3.setImageResource(R.drawable.pescio4su4);
        }
        if (x < 0.5 && x >= 0.25) {
            vote1.setImageResource(R.drawable.pescio4su4);
            vote2.setImageResource(R.drawable.pescio4su4);
            vote3.setImageResource(R.drawable.pescio3su4);
        }
        if (x < 0.75 && x >= 0.5) {
            vote1.setImageResource(R.drawable.pescio4su4);
            vote2.setImageResource(R.drawable.pescio4su4);
            vote3.setImageResource(R.drawable.pescio2su4);
        }
        if (x < 1 && x >= 0.75) {
            vote1.setImageResource(R.drawable.pescio4su4);
            vote2.setImageResource(R.drawable.pescio4su4);
            vote3.setImageResource(R.drawable.pescio1su4);
        }
        if (x < 1.25 && x >= 1) {
            vote1.setImageResource(R.drawable.pescio4su4);
            vote2.setImageResource(R.drawable.pescio4su4);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 1.5 && x >= 1.25) {
            vote1.setImageResource(R.drawable.pescio4su4);
            vote2.setImageResource(R.drawable.pescio3su4);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 1.75 && x >= 1.5) {
            vote1.setImageResource(R.drawable.pescio4su4);
            vote2.setImageResource(R.drawable.pescio2su4);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 2 && x >= 1.75) {
            vote1.setImageResource(R.drawable.pescio4su4);
            vote2.setImageResource(R.drawable.pescio1su4);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 2.25 && x >= 2) {
            vote1.setImageResource(R.drawable.pescio4su4);
            vote2.setVisibility(View.INVISIBLE);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 2.5 && x >= 2.25) {
            vote1.setImageResource(R.drawable.pescio3su4);
            vote2.setVisibility(View.INVISIBLE);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 2.75 && x >= 2.5) {
            vote1.setImageResource(R.drawable.pescio2su4);
            vote2.setVisibility(View.INVISIBLE);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 3 && x >= 2.75) {
            vote1.setImageResource(R.drawable.pescio1su4);
            vote2.setVisibility(View.INVISIBLE);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 3.25 && x >= 3) {
            vote1.setImageResource(R.drawable.marghe1su4);
            vote2.setVisibility(View.INVISIBLE);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 3.5 && x >= 3.25) {
            vote1.setImageResource(R.drawable.marghe2su4);
            vote2.setVisibility(View.INVISIBLE);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 3.75 && x >= 3.5) {
            vote1.setImageResource(R.drawable.marghe3su4);
            vote2.setVisibility(View.INVISIBLE);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 4 && x >= 3.75) {
            vote1.setImageResource(R.drawable.marghe4su4);
            vote2.setVisibility(View.INVISIBLE);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 4.25 && x >= 4) {
            vote1.setImageResource(R.drawable.marghe4su4);
            vote2.setImageResource(R.drawable.marghe1su4);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 4.5 && x >= 4.25) {
            vote1.setImageResource(R.drawable.marghe4su4);
            vote2.setImageResource(R.drawable.marghe2su4);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 4.75 && x >= 4.5) {
            vote1.setImageResource(R.drawable.marghe4su4);
            vote2.setImageResource(R.drawable.marghe3su4);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 5 && x >= 4.75) {
            vote1.setImageResource(R.drawable.marghe4su4);
            vote2.setImageResource(R.drawable.marghe4su4);
            vote3.setVisibility(View.INVISIBLE);
        }
        if (x < 5.25 && x >= 5) {
            vote1.setImageResource(R.drawable.marghe4su4);
            vote2.setImageResource(R.drawable.marghe4su4);
            vote3.setImageResource(R.drawable.marghe1su4);
        }
        if (x < 5.5 && x > 5.25) {
            vote1.setImageResource(R.drawable.marghe4su4);
            vote2.setImageResource(R.drawable.marghe4su4);
            vote3.setImageResource(R.drawable.marghe2su4);
        }
        if (x < 5.75 && x >= 5.5) {
            vote1.setImageResource(R.drawable.marghe4su4);
            vote2.setImageResource(R.drawable.marghe4su4);
            vote3.setImageResource(R.drawable.marghe3su4);
        }
        if (x <= 6 && x >= 5.75) {
            vote1.setImageResource(R.drawable.marghe4su4);
            vote2.setImageResource(R.drawable.marghe4su4);
            vote3.setImageResource(R.drawable.marghe4su4);
        }
    }

    public void addListenerOnButtons(final Context context) {
        /***On click it shows a menu where the user can choose a vote from 0 to 6***/
        toVote.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                /*vote dialog's initialization*/
                voteDialog = new Dialog(ParkDetailActivity.this);
                voteDialog.setContentView(R.layout.vote_dialog);
                voteDialog.setTitle("Vote this Park, from 0 to 6");
                final Dialog loginDialog = new Dialog(context);
                loginDialog.setContentView(R.layout.login_dialog);
                loginDialog.setTitle("Login");

                final EditText userText = (EditText) loginDialog.findViewById(R.id.UserText);
                final EditText passwordText = (EditText) loginDialog.findViewById(R.id.PasswordText);

                /*vote dialog's buttons' initialization*/
                vote0Button     = (Button) voteDialog.findViewById(R.id.Vote0Button );
                vote1Button     = (Button) voteDialog.findViewById(R.id.Vote1Button );
                vote2Button     = (Button) voteDialog.findViewById(R.id.Vote2Button );
                vote3Button     = (Button) voteDialog.findViewById(R.id.Vote3Button );
                vote4Button     = (Button) voteDialog.findViewById(R.id.Vote4Button );
                vote5Button     = (Button) voteDialog.findViewById(R.id.Vote5Button );
                vote6Button     = (Button) voteDialog.findViewById(R.id.Vote6Button );
                noVoteButton    = (Button) voteDialog.findViewById(R.id.NoVoteButton);

                vote0Button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        votePark(id, 0);
                        voteDialog.dismiss();
                    }
                });

                vote1Button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        votePark(id, 1);
                        voteDialog.dismiss();
                    }
                });

                vote2Button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        votePark(id, 2);
                        voteDialog.dismiss();
                    }
                });

                vote3Button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        votePark(id, 3);
                        voteDialog.dismiss();
                    }
                });

                vote4Button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        votePark(id, 4);
                        voteDialog.dismiss();
                    }
                });

                vote5Button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        votePark(id, 5);
                        voteDialog.dismiss();
                    }
                });

                vote6Button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        votePark(id, 6);
                        voteDialog.dismiss();
                    }
                });

                noVoteButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        voteDialog.dismiss();
                    }
                });

                userText.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (userText.getText().toString().equals("User"))
                            userText.setText("");
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return false;
                    }
                });

                passwordText.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (passwordText.getText().toString().equals("Password"))
                            passwordText.setText("");
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return false;
                    }
                });

                ImageButton loginGoogleImageButton = (ImageButton) loginDialog.findViewById(R.id.loginGoogleImageButton);
                loginGoogleImageButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connected = true;
                        loginDialog.dismiss();
                        voteDialog.show();
                    }
                });

                ImageButton loginFacebookImageButton = (ImageButton) loginDialog.findViewById(R.id.loginFacebookImageButton);
                loginFacebookImageButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connected = true;
                        loginDialog.dismiss();
                        voteDialog.show();
                    }
                });

                if(connected)
                    voteDialog.show();
                else
                    loginDialog.show();
            }
        });

        /***On click it shows an activity where the user can see google maps indications from his location to the park***/
        indications.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                /**********GPS Initialization*******/
                gps = new GPS(ParkDetailActivity.this);
                if (!gps.canGetLocation())
                    gps.showSettingsAlert();

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+gps.getLatitude()+","+gps.getLongitude()+"&daddr="+getIntent().getStringExtra("coordinates")));
                startActivity(intent);
            }
        });
    }

    /*Function that takes photo from the url and puts it in the imageview*/
    public void addPhoto(String url) throws IOException {
        URL newurl = new URL(url);
        Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        photo.setImageBitmap(mIcon_val);
    }

    /*AsyncTask for vote's server-client connection*/
    public void votePark(int id, int vote) {
        new VoteParkTask().execute(id, vote);
    }

    private class VoteParkTask extends AsyncTask<Integer, Void, String> {
        protected void onPreExecute() {
            progressdialog = new ProgressDialog(ParkDetailActivity.this);
            progressdialog.setIndeterminate(true);
            progressdialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.anim_progress_bar));
            progressdialog.setMessage("Caricamento...");
            progressdialog.show();
            vote0Button   .setEnabled(false);
            vote1Button   .setEnabled(false);
            vote2Button   .setEnabled(false);
            vote3Button   .setEnabled(false);
            vote4Button   .setEnabled(false);
            vote5Button   .setEnabled(false);
            vote6Button   .setEnabled(false);
            noVoteButton  .setEnabled(false);
        }

        protected String doInBackground(Integer... i) {
            String result;
            JSONObject jsonResult;
            try {
                jsonResult = new JSONObject(new ParkAPI().votePark(i[0], i[1]));
                if(jsonResult.getBoolean("ok"))
                    result = "Good vote";
                else
                    result = "Something went wrong";
            } catch (Exception e) {
                result = "Something went wrong with server connection";
            }
            return result;
        }


        protected void onPostExecute(String result) {
            Toast.makeText(ParkDetailActivity.this, result, Toast.LENGTH_SHORT).show();
            vote0Button   .setEnabled(true);
            vote1Button   .setEnabled(true);
            vote2Button   .setEnabled(true);
            vote3Button   .setEnabled(true);
            vote4Button   .setEnabled(true);
            vote5Button   .setEnabled(true);
            vote6Button   .setEnabled(true);
            noVoteButton  .setEnabled(true);
            progressdialog.cancel();
        }
    }
}
