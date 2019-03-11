package com.example.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class ProgressListViewAdaptor extends ArrayAdapter<ProgressInfo> {
    // Simple class to make it so that we don't have to call findViewById frequently
    private static class ViewHolder {
        TextView textView;
        ProgressBar progressBar;

    }
    private static final String TAG = ProgressListViewAdaptor.class.getSimpleName();

    public ProgressListViewAdaptor(Context context, int textViewResourceId,
                                   List<ProgressInfo> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ProgressInfo info = (ProgressInfo)getItem(position);
        // We need to set the convertView's progressBar to null.

        ViewHolder holder = null;

        if(null == row) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_progressbar, parent, false);

            holder = new ViewHolder();
            holder.textView = (TextView) row.findViewById(R.id.fluidProgress);
            holder.progressBar = (ProgressBar) row.findViewById(R.id.fluidProgressaBar);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.progressBar.setProgress(info.getProgress());
        info.setProgressBar(holder.progressBar);

        //TODO: When reusing a view, invalidate the current progressBar.

        return row;
    }


}
