/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.yoching.android;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private final static String WREXAGRAM_ID = "wrexagram";
    private AnimatedCoin[] animatedCoins;
    private Deque<ImageView> imageViewStack = new ArrayDeque<ImageView>();
    private Bitmap splitLine, strongLine;
    private StringBuffer outcomeBuffer;
    private Button throwButton;
    private int deviceHeight = 0;
    private Handler handler;

    //private static Aroma aroma;
    private final static String APP_TOKEN = "3e7ee9ec-9e9e-479e-a44a-24c7376d2786";


    private final String[] wrexagramTitles = new String[64];
    private final String[] wrexagramSubTitles = new String[64];
    private final String[] wrexagramText = new String[64];
    private final Integer[] wrexagramImageIds = new Integer[64];

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        throwButton = (Button) findViewById(R.id.throw_button);
        throwButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Exo-ExtraBold.otf"));
        initCoinToss();
        addDrawerItems();

        WrexagramListAdapter adapter = new  WrexagramListAdapter(MainActivity.this, wrexagramTitles, wrexagramSubTitles, wrexagramImageIds);

        mDrawerList = (ListView)findViewById(R.id.navList);

        mDrawerList.setAdapter(adapter);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();


       setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        int width = getResources().getDisplayMetrics().widthPixels;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
        params.width = width;
        mDrawerList.setLayoutParams(params);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, position+" - clicked", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
                Intent intent= new Intent(MainActivity.this,ViewWrexagramActivity.class);
               //intent.putExtra("string",Yourlist.get(pos).sms);
                intent.putExtra("wrexagram",position+1+"");

                PendingIntent pendingIntent =
                        TaskStackBuilder.create(MainActivity.this)
                                // add all of DetailsActivity's parents to the stack,
                                // followed by DetailsActivity itself
                                .addNextIntentWithParentStack(intent)
                                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                builder.setContentIntent(pendingIntent);


                startActivity(intent);
                finish();
            }
        });
    }

    private void addDrawerItems() {

        String jsonString = WrexagramUtils.getResourceText(this, R.raw.wrexagrams);
        Gson gson = new GsonBuilder().create();

        Type collectionType = new TypeToken<List<Wrexagram>>(){}.getType();
        List<Wrexagram> wrexagramList = gson.fromJson(jsonString, collectionType);

        // 1. add wrexagram titles and subtitles
        for (int i = 0; i < 64; i++) {
            wrexagramTitles[i] = wrexagramList.get(i).getTitle() +"\n";
            wrexagramSubTitles[i] = wrexagramList.get(i).getSubtitle();
        }

        //  2. add wrexagram icons
        for (int i = 0; i < 64; i++) {
            wrexagramImageIds[i] = this.getResources().getIdentifier("wrexagram"+String.valueOf(i+1), "drawable", this.getPackageName());
        }


        //mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subTitles);
        // /mDrawerList.setAdapter(mAdapter);



    }


    protected void initCoinToss(){
        handler = new Handler();
        deviceHeight = getResources().getDisplayMetrics().heightPixels;

        animatedCoins = new AnimatedCoin[3];
        animatedCoins[0] = new AnimatedCoin(this, (ImageView) findViewById(R.id.coin_one));
        animatedCoins[1] = new AnimatedCoin(this, (ImageView) findViewById(R.id.coin_two));
        animatedCoins[2] = new AnimatedCoin(this, (ImageView) findViewById(R.id.coin_three));

        int h = 30; // height in pixels
        int w = 360; // width in pixels

        splitLine = BitmapFactory.decodeResource(this.getResources(), R.drawable.wrexagram_splitline);
        strongLine = BitmapFactory.decodeResource(this.getResources(), R.drawable.wrexagram_strongline);
        splitLine = Bitmap.createScaledBitmap(splitLine, w, h, true);
        strongLine = Bitmap.createScaledBitmap(strongLine, w, h, true);

        imageViewStack.add((ImageView) findViewById(R.id.wrex_line_one));
        imageViewStack.add((ImageView) findViewById(R.id.wrex_line_two));
        imageViewStack.add((ImageView) findViewById(R.id.wrex_line_three));
        imageViewStack.add((ImageView) findViewById(R.id.wrex_line_four));
        imageViewStack.add((ImageView) findViewById(R.id.wrex_line_five));
        imageViewStack.add((ImageView) findViewById(R.id.wrex_line_six));

        outcomeBuffer = new StringBuffer();

    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                SpannableString s = new SpannableString("64 WREXAGRAMS");
                s.setSpan(new TypefaceSpan("Exo-Bold.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                getSupportActionBar().setTitle(s);

               // invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
             //   invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    public void flipCoins(View view) {

        final List<Integer> outcomes = new ArrayList<Integer>(3);
        for (int i = 0; i < 3; i++) {
            int j = Math.random() > .5 ? 1 : 2;
            outcomes.add(new Integer(j));
            animatedCoins[i].setHeadsOrTails(j);
        }

        int heads = Collections.frequency(outcomes, new Integer(1));

        Bitmap lineRender = null;
        if (heads >= 2) {
            lineRender = strongLine;
            outcomeBuffer.append("1");
        } else {
            lineRender = splitLine;
            outcomeBuffer.append("2");
        }
        Log.d(TAG, "wrexagram outcome buffer  : " + outcomeBuffer.toString());

        if (!imageViewStack.isEmpty()) {

            List<Long> list = new ArrayList<Long>();
            for(int i=2;i<6;i++){
                list.add(new Long(i*100));  // list contains: [2,3,4,5]
            }
            Collections.shuffle(list);


            handler.postDelayed(animatedCoins[0], list.get(0));
            handler.postDelayed(animatedCoins[1], list.get(1));
            handler.postDelayed(animatedCoins[2], list.get(2));


            final ImageView wrexaLine = imageViewStack.pop();
            wrexaLine.setImageBitmap(lineRender);
            wrexaLine.setVisibility(View.INVISIBLE);
            wrexaLine.postDelayed(new Runnable() {
                @Override
                public void run() {
                    wrexaLine.setVisibility(View.VISIBLE);
                }
            }, 2300);


        }
        Intent intent = new Intent(MainActivity.this, ViewWrexagramActivity.class);
        TossListener tossListener = new TossListener(intent, outcomeBuffer);
        handler.postDelayed(tossListener, 2000);
    }
    private class TossListener implements Runnable {
        private StringBuffer sb;
        private Intent intent;

        public TossListener(Intent intent, StringBuffer sb) {
            this.sb = sb;
            this.intent = intent;
        }

        @Override
        public void run() {
            try {
                if (sb.length() >= 6) {
                    // get the first 6 characters
                    String wrexnum = sb.toString().substring(0, 6);
                    Log.d(TAG, "wrexnum stringbuffer generated : " +wrexnum);
                    int id = WrexagramUtils.getOutcome(Integer.parseInt(wrexnum));
                    Log.d(TAG, "wrexnum generated : " +id);

                    intent.putExtra(WREXAGRAM_ID, Integer.toString(id));
                    startActivity(intent);
                }
            } catch (Exception e) {
                Log.d(TAG, "error with getting wrexagram outcome : " + e.toString());
            }
        }
    }
}
