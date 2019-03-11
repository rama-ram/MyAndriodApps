package com.example.common;

import android.util.Log;
import android.widget.ProgressBar;

public class ProgressInfo {
    private final static String TAG = ProgressInfo.class.getSimpleName();

    private volatile Integer mProgress;
    private volatile ProgressBar mProgressBar;

    public ProgressInfo(String filename, Integer size) {
        mProgress = 0;
        mProgressBar = null;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }
    public void setProgressBar(ProgressBar progressBar) {
        Log.d(TAG, "setProgressBar  to " + progressBar);
        mProgressBar = progressBar;
    }

    public Integer getProgress() {
        return mProgress;
    }

    public void setProgress(Integer progress) {
        //calculate from db entry
        
        //this.mProgress = progress;
    }
}
