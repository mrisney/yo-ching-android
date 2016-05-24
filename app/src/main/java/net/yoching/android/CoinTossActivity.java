package net.yoching.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

//import tech.aroma.client.Aroma;


public class CoinTossActivity extends AppCompatActivity {

    public static final String TAG = CoinTossActivity.class.getSimpleName();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);
        throwButton = (Button) findViewById(R.id.throw_button);
        throwButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Exo-ExtraBold.otf"));
        initCoinToss();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "leaving coin toss activity");
        handler.removeCallbacksAndMessages(TossListener.class);
        super.onPause();
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
    public void flipCoins(View view) {

        final List<Integer> outcomes = new ArrayList<Integer>(3);
        for (int i = 0; i < 3; i++) {
            int j = Math.random() > .5 ? 1 : 2;
            outcomes.add(new Integer(j));
            animatedCoins[i].setHeadsOrTails(j);
        }

        int heads = Collections.frequency(outcomes, new Integer(2));

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
            handler.postDelayed(animatedCoins[0], 400);
            handler.postDelayed(animatedCoins[1], 200);
            handler.postDelayed(animatedCoins[2], 100);

            Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
            ImageView wrexaLine = imageViewStack.pop();
            wrexaLine.setImageBitmap(lineRender);
            wrexaLine.setAnimation(fadeIn);
        }
        Intent intent = new Intent(CoinTossActivity.this, ViewWrexagramActivity.class);
        TossListener tossListener = new TossListener(intent, outcomeBuffer);
        handler.postDelayed(tossListener, 2000);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
        return super.onTouchEvent(event);
    }
/*
    public static E random(Set<E> set) {
        int index = random.nextInt(set.size();
        if (set instanceof ImmutableSet) {
            // ImmutableSet.asList() is O(1), as is .get() on the returned list
            return set.asList().get(index);
        }
        return Iterables.get(set, index);
    }
    */

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
                    int id = WrexagramUtils.getOutcome(Integer.parseInt(sb.toString().substring(0, 6)));
                    intent.putExtra(WREXAGRAM_ID, Integer.toString(id));
                    startActivity(intent);
                }
            } catch (Exception e) {
                Log.d(TAG, "error with getting wrexagram outcome : " + e.toString());
            }
        }
    }
}
