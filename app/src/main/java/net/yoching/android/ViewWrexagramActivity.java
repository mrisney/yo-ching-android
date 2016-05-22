package net.yoching.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewWrexagramActivity extends AppCompatActivity {

    public static final String TAG = ViewWrexagramActivity.class.getSimpleName();
    private TextView wrexagramTextView;
    private ImageView wrexagramImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wrexagram);
        wrexagramTextView = (TextView) findViewById(R.id.wrexagram_text_view);
        wrexagramImageView = (ImageView) findViewById(R.id.wrexagram_image_view);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            String wrexagram = "wrexagram" + (String) b.get("wrexagram");

            Log.d(TAG, "wrexagram  : " + wrexagram);
            try {
                int textId = R.raw.class.getField(wrexagram).getInt(null);
                String wrexagramText = WrexagramUtils.getResourceText(this, textId);
                wrexagramTextView.setText(wrexagramText);

                int imageId = R.drawable.class.getField(wrexagram).getInt(null);
                wrexagramImageView.setImageResource(imageId);

            } catch (Exception e) {
                Log.d(TAG, "wrexagram  error : " + e.toString());
            }
        }
    }
}
