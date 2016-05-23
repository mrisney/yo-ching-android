package net.yoching.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;


//import tech.aroma.client.Aroma;


public class CoinTossActivity extends AppCompatActivity {

    public static final String TAG = CoinTossActivity.class.getSimpleName();

    private AnimatedCoin[] animatedCoins;
    private ImageView wrexLineOne, wrexLineTwo, wrexLineThree, wrexLineFour, wrexLineFive, wrexLineSix;
    private Deque<ImageView> imageViewStack = new ArrayDeque<ImageView>();
    private Bitmap splitLine, strongLine;
    private StringBuffer outcomeBuffer;
    //private static Aroma aroma;
    private final static String APP_TOKEN = "3e7ee9ec-9e9e-479e-a44a-24c7376d2786";

    private Button throwButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coin_toss);




        throwButton = (Button)findViewById(R.id.throw_button);
        throwButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Exo-ExtraBold.otf"));


        animatedCoins = new AnimatedCoin[3];

        animatedCoins[0] = new AnimatedCoin(this, (ImageView) findViewById(R.id.coin_one));
        animatedCoins[1] = new AnimatedCoin(this, (ImageView) findViewById(R.id.coin_two));
        animatedCoins[2] = new AnimatedCoin(this, (ImageView) findViewById(R.id.coin_three));

        int h = 30; // height in pixels
        int w = 270; // width in pixels

        wrexLineOne = (ImageView) findViewById(R.id.wrex_line_one);
        wrexLineTwo = (ImageView) findViewById(R.id.wrex_line_two);
        wrexLineThree = (ImageView) findViewById(R.id.wrex_line_three);
        wrexLineFour = (ImageView) findViewById(R.id.wrex_line_four);
        wrexLineFive = (ImageView) findViewById(R.id.wrex_line_five);
        wrexLineSix = (ImageView) findViewById(R.id.wrex_line_six);

        splitLine = BitmapFactory.decodeResource(this.getResources(), R.drawable.wrexagram_splitline);
        strongLine = BitmapFactory.decodeResource(this.getResources(), R.drawable.wrexagram_strongline);
        splitLine = Bitmap.createScaledBitmap(splitLine, w, h, true);
        strongLine = Bitmap.createScaledBitmap(strongLine, w, h, true);

        imageViewStack.add(wrexLineOne);
        imageViewStack.add(wrexLineTwo);
        imageViewStack.add(wrexLineThree);
        imageViewStack.add(wrexLineFour);
        imageViewStack.add(wrexLineFive);
        imageViewStack.add(wrexLineSix);

        outcomeBuffer = new StringBuffer();
    }


    public void flipCoins(View view) {
/*
        if (outcomeBuffer.size = 6){

        }
  */

        Log.d(TAG, "wrexagram outcome buffer  : " + outcomeBuffer.toString());

        int coinOneOutcome = Math.random() > .5 ? 1 : 2;
        int coinTwoOutcome = Math.random() > .5 ? 1 : 2;
        int coinThreeOutcome = Math.random() > .5 ? 1 : 2;


        List<Integer> outcome = new ArrayList<Integer>(3);

        outcome.add(Integer.valueOf(coinOneOutcome));
        outcome.add(Integer.valueOf(coinTwoOutcome));
        outcome.add(Integer.valueOf(coinThreeOutcome));

        int heads = Collections.frequency(outcome, new Integer(1));
        Bitmap lineRender = splitLine;

        if (heads >= 2) {
            lineRender = strongLine;
            outcomeBuffer.append("1");
        } else{
            lineRender = splitLine;
            outcomeBuffer.append("2");
        }


        animatedCoins[0].setHeadsOrTails(coinOneOutcome);
        animatedCoins[1].setHeadsOrTails(coinTwoOutcome);
        animatedCoins[2].setHeadsOrTails(coinThreeOutcome);

        Handler handler = new Handler();
        handler.postDelayed(animatedCoins[0], 400);
        handler.postDelayed(animatedCoins[1], 200);
        handler.postDelayed(animatedCoins[2], 100);

        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);

        if (imageViewStack.isEmpty()){

            int wrexnum = Integer.parseInt(outcomeBuffer.toString().substring(0,6));
            Log.d(TAG, "wrexnum  : " + wrexnum);

            int wrexagram = WrexagramUtils.getOutcome(wrexnum);
            Log.d(TAG, "wrexagram   : " + wrexagram);
            //Toast.makeText(CoinTossActivity.this, wrexagram, Toast.LENGTH_SHORT).show();

            Intent intent= new Intent(CoinTossActivity.this,ViewWrexagramActivity.class);
            intent.putExtra("wrexagram",wrexagram+"");

            startActivity(intent);


        } else{

            ImageView wrexaLine = imageViewStack.pop();
            wrexaLine.setImageBitmap(lineRender);
            wrexaLine.startAnimation(fadeIn);
         }
        }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int headsOrTails = Math.random() > .5 ? 1 : 2;

            Log.d(TAG, "heads or tails : " + headsOrTails);


            //theBoolean ^= true;


            animatedCoins[0].isHeads(false);
            animatedCoins[1].isHeads(true);
            animatedCoins[2].isHeads(false);


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
}
