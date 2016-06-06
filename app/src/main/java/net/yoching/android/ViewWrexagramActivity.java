package net.yoching.android;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ViewWrexagramActivity extends AppCompatActivity {

    public static final String TAG = ViewWrexagramActivity.class.getSimpleName();
    private TextView toolbarTitle, titleTextView, wrexagramTextView, whatsUpTextView, whatsUp;
    private ImageView wrexagramImageView;
    private List<Wrexagram> wrexagramList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        Button sharePictureButton = (Button)findViewById(R.id.main_share_picture_button);
        sharePictureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SharePictureActivity.class);
                startActivity(intent);
            }
        });

*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wrexagram);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Exo-ExtraBold.otf"));

        String jsonString = WrexagramUtils.getResourceText(this, R.raw.wrexagrams);
        Gson gson = new GsonBuilder().create();

        Type collectionType = new TypeToken<List<Wrexagram>>() {}.getType();
        wrexagramList = gson.fromJson(jsonString, collectionType);

        wrexagramImageView = (ImageView) findViewById(R.id.wrexagram_image_view);

        titleTextView = (TextView) findViewById(R.id.wrexagram_title);
        titleTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Exo-ExtraBold.otf"));
        wrexagramTextView = (TextView) findViewById(R.id.wrexagram_text_view);

        whatsUp = (TextView) findViewById(R.id.whats_up);
        whatsUp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Exo-ExtraBold.otf"));
        whatsUpTextView = (TextView) findViewById(R.id.wrexagram_whats_up);

        Bundle b = getIntent().getExtras();

        if (b != null) {

            try {
                String wrexagramId = (String) b.get("wrexagram");
                int id = Integer.parseInt(wrexagramId);
                Log.d(TAG, "wrexagram  id : " + id);
                toolbarTitle.setText("WREXAGRAM "+id);
                setImage(id);
                setWrexagramTitle(id);
                setText(id);
                setWhatsUp(id);

            } catch (Exception e) {
                Log.d(TAG, "wrexagram  error : " + e.toString());
            }
        }
    }


    public void applyActionBar(android.support.v7.app.ActionBar ab, ActionBarActivity mContext, View custom) {
        ab = mContext.getSupportActionBar();
        ab.setCustomView(custom);
       // ab.setTitle(R.string.st_action_title_main);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        //ab.setLogo(R.drawable.conect_icon);
        ab.setDisplayUseLogoEnabled(true); }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                startActivity(upIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setImage(int id) {
        String imageWrexId = "wrexagram" + id;
        Log.d(TAG, "getting wrexagram image : " + imageWrexId);
        try {
            int imageId = R.drawable.class.getField(imageWrexId).getInt(null);
            wrexagramImageView.setImageResource(imageId);
        } catch (Exception e) {
            Log.d(TAG, "problem getting wrexagram image, error : " + e.toString());
        }
    }

    protected void setWrexagramTitle(int id) {
        id = id - 1;
        String title = wrexagramList.get(id).getTitle();
        titleTextView.setText(title);
    }

    protected void setText(int id) {

        String wrexgramTextId = "wrexagram" + id;
        Log.d(TAG, "getting wrexgram text : " + wrexgramTextId);
        try {
            int textId = R.raw.class.getField(wrexgramTextId).getInt(null);
            String wrexagramText = WrexagramUtils.getResourceText(this, textId);
            wrexagramTextView.setText(wrexagramText);
        } catch (Exception e) {
            Log.d(TAG, "problem getting wrexagram text, error : " + e.toString());
        }

    }

    protected void setWhatsUp(int id) {
        id = id - 1;

        String whatsUp = wrexagramList.get(id).getWhatsUp();
        Log.d(TAG, "getting whatsup text : " + whatsUp);
        whatsUpTextView.setText(whatsUp);
    }
}
