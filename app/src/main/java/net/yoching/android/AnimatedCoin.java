package net.yoching.android;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by marcrisney on 5/20/16.
 */

public class AnimatedCoin implements Runnable {

    public static final String TAG = AnimatedCoin.class.getSimpleName();
    Bitmap mHeadsImage, mTailsImage;
    private AnimatorSet animation;
    private int headsOrTails;
    private ObjectAnimator coinTossAnimator;
    private boolean mIsHeads;
    private ImageView coinImage;

    public AnimatedCoin(Context context, ImageView imageView) {

        Bitmap heads = BitmapFactory.decodeResource(context.getResources(), R.drawable.head);
        Bitmap tails = BitmapFactory.decodeResource(context.getResources(), R.drawable.tail);

        int h = 216; // height in pixels
        int w = 216; // width in pixels
        heads = Bitmap.createScaledBitmap(heads, w, h, true);
        tails = Bitmap.createScaledBitmap(tails, w, h, true);

        this.mIsHeads = (Math.random() < 0.5);
        this.mHeadsImage = Bitmap.createScaledBitmap(heads, w, h, true);
        this.mTailsImage = Bitmap.createScaledBitmap(tails, w, h, true);

        this.coinImage = imageView;

        if (mIsHeads) {
            this.coinImage.setImageBitmap(mHeadsImage);
        } else {
            this.coinImage.setImageBitmap(mTailsImage);
        }

        this.animation = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.coin_toss);
        this.animation.setTarget(coinImage);
        this.coinTossAnimator = (ObjectAnimator) animation.getChildAnimations().get(0);

        addListeners();

    }

    public void isHeads(boolean heads) {
        this.mIsHeads = heads;
    }

    public void setHeadsOrTails(int headsOrTails) {
        this.headsOrTails = headsOrTails;
    }

    @Override
    public void run() {
        this.animation.start();
    }

    final private void addListeners() {

        this.coinTossAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator vAnimation) {
                if (vAnimation.getAnimatedFraction() >= 0.25f && mIsHeads) {
                    coinImage.setImageBitmap(mTailsImage);
                    mIsHeads = false;
                }
                if (vAnimation.getAnimatedFraction() >= 0.75f && !mIsHeads) {
                    coinImage.setImageBitmap(mHeadsImage);
                    mIsHeads = true;
                }
            }
        });

        this.coinTossAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (headsOrTails > 1) {
                    coinImage.setImageBitmap(mHeadsImage);
                } else {
                    coinImage.setImageBitmap(mTailsImage);
                }
            }
        });
    }
}
