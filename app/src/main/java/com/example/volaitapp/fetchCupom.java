package com.example.volaitapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class fetchCupom extends AsyncTaskLoader<String> {
    String mType;
    String mQuery;
    fetchCupom(Context context, String type, @Nullable String complement){
        super(context);
        mType = type;
        mQuery = complement;
    }


    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {
            return NetworkUtils.searchElementsWeb(mQuery);
    }
}
