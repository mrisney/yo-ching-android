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

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                SpannableString s = new SpannableString("64 WREXAGRAMS");
                s.setSpan(new TypefaceSpan("Exo-Bold.otf"), 0, s.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                getSupportActionBar().setTitle(s);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

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
}
