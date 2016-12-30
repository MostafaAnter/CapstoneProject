package com.mostafa_anter.capstoneproject.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mostafa_anter on 9/26/16.
 */

public class MArticlesPrefStore {
    private static final String SOURCE_KEY = "articles_source";
    private static final String DEFAULT_SOURCE_VALUE = "bbc-news";
    private static final String DEFAULT_SOURCE_NAME = "BBC NEWS";

    private static final String PREFKEY = "MArticlesPreferencesStore";
    private SharedPreferences mArticlesPreferences;

    public MArticlesPrefStore(Context context){
        mArticlesPreferences = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public void clearPreference(){
        SharedPreferences.Editor editor = mArticlesPreferences.edit();
        editor.clear().apply();
    }

    public void addSourceValue(String value){
        SharedPreferences.Editor editor = mArticlesPreferences.edit();
        editor.putString(SOURCE_KEY, value);
        editor.apply();
    }

    public void addSourceName(String value, String sourceName){
        SharedPreferences.Editor editor = mArticlesPreferences.edit();
        editor.putString(value, sourceName);
        editor.apply();
    }

    public String getSourceValue(){
        return mArticlesPreferences.getString(SOURCE_KEY, DEFAULT_SOURCE_VALUE);
    }

    public String getSourceName(String sourceValue){
        return mArticlesPreferences.getString(sourceValue, DEFAULT_SOURCE_NAME);
    }
}
