package com.iranplanner.tourism.iranplanner.photoViewer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.iranplanner.tourism.iranplanner.R;
import java.util.List;
import tools.Util;

/**
 * Created by h.vahidimehr on 25/02/2017.
 */

public class ImageGridAdapter extends ArrayAdapter {
    private Context mContext;
    private List<String> resultImageLists;

    public ImageGridAdapter(Context context, int layoutResourceId, List<String> resultImageLists) {
        super(context, layoutResourceId, resultImageLists);
        this.mContext = context;
        this.resultImageLists = resultImageLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageGridAdapter.ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_items, parent, false);
            holder = new ImageGridAdapter.ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ImageGridAdapter.ViewHolder) row.getTag();
        }
       Util.setImageView(resultImageLists.get(position), mContext, holder.image, null);
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
