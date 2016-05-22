package net.yoching.android.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by marcrisney on 5/22/16.
 */
public class SignikaFontTextView extends TextView {
    public SignikaFontTextView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/SignikaRegular.ttf"));

    }
}
