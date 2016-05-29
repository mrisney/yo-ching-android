package net.yoching.android;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by marcrisney on 5/14/16.
 */


public class WrexagramListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] titles;
    private final String[] subTitles;
    private final Integer[] imageIds;

    public WrexagramListAdapter(Activity context, String[] titles, String[] subTitles, Integer[] imageIds) {

        super(context, R.layout.list_single, titles);
        this.context = context;
        this.titles = titles;
        this.subTitles = subTitles;
        this.imageIds = imageIds;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);

        TextView idView = (TextView) rowView.findViewById(R.id.wrexagramId);

        int id = 1;
        try {
            id = id + position;
        } catch (Exception e) {
        }
        idView.setText(id + "");

        TextView titleView = (TextView) rowView.findViewById(R.id.title);
        titleView.setText(titles[position]);

        TextView subTitleView = (TextView) rowView.findViewById(R.id.subTitle);
        subTitleView.setText(subTitles[position]);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        imageView.setImageResource(imageIds[position]);

        return rowView;
    }
}
