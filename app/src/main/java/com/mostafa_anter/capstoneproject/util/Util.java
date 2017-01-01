package com.mostafa_anter.capstoneproject.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mostafa_anter.capstoneproject.data.ArticlesContract;
import com.mostafa_anter.capstoneproject.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mostafa_anter on 12/29/16.
 */

public class Util {

    // -------------------- start of splash utils----------------------
    // when use animate view function you must implement this interface
    public interface AnimateView {
        public void onAnimationEnd();
    }

    /**
     *
     * @param mContext current context
     * @param animResource anim resource file name
     * @param view view that you want to anim
     * @param instance instance from AnimateView interface to handle on animation end
     */
    public static void animateView(Context mContext, int animResource,View view, final AnimateView instance) {
        Animation fade0 = AnimationUtils.loadAnimation(mContext, animResource);
        view.startAnimation(fade0);
        fade0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                instance.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    // -------------------- end of splash utils----------------------

    // -------------------- start of toolbar utils----------------------
    // when use animate view function you must implement this interface
    public interface NavigationOnClickListener {
        public void setNavigationOnClickListener();
    }

    /**
     * set navigation icon, hide title and subtitle
     * @param mContext
     * @param toolbar
     * @param navigationIcon equal 0 if not want to add navigation icon although set navigationOnClick null
     * @param navigationOnClickListener must implement this when use this function
     */
    public static void manipulateToolbar(@NonNull AppCompatActivity mContext,
                                   @NonNull Toolbar toolbar,
                                   int navigationIcon,
                                   @Nullable final NavigationOnClickListener navigationOnClickListener,
                                   boolean isCustoomTitle){
        mContext.setSupportActionBar(toolbar);

        if (navigationIcon != 0 && navigationOnClickListener != null) {
            toolbar.setNavigationIcon(navigationIcon);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                navigationOnClickListener.setNavigationOnClickListener();
                }
            });
        }

        if (isCustoomTitle) {
        /*
        * hide title
        * */
            mContext.getSupportActionBar().setDisplayShowTitleEnabled(false);
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
            toolbar.setTitle("");
            toolbar.setSubtitle("");
        }
    }

    /**
     *
     * @param mContext current context
     * @param fontPath path to font.ttf ex. "fonts/normal.ttf" if there fonts directory under assets
     * @param view that view you want to change it type face should extend text view
     */
    public static void changeViewTypeFace(Context mContext, String fontPath, TextView view){
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), fontPath);
        view.setTypeface(font);
    }

    public static List<Article> returnListFromCursor(Cursor cursor){
        List<Article> rowItemList = new ArrayList<>();
        if (cursor.getCount() != 0 && cursor.moveToFirst()){
            do{
                String author = cursor.getString(cursor.getColumnIndex(ArticlesContract.ArticleEntry.COLUMN_AUTHOR));
                String title = cursor.getString(cursor.getColumnIndex(ArticlesContract.ArticleEntry.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(ArticlesContract.ArticleEntry.COLUMN_DESCRIPTION));
                String url = cursor.getString(cursor.getColumnIndex(ArticlesContract.ArticleEntry.COLUMN_URL));
                String urlToImage = cursor.getString(cursor.getColumnIndex(ArticlesContract.ArticleEntry.COLUMN_URLTOIMAGE));
                String publishedAt = cursor.getString(cursor.getColumnIndex(ArticlesContract.ArticleEntry.COLUMN_PUBLISHEDAT));
                String source = cursor.getString(cursor.getColumnIndex(ArticlesContract.ArticleEntry.COLUMN_SOURCE));
                rowItemList.add(new Article(author, title, description, url, urlToImage, publishedAt, source));
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();

        return rowItemList;
    }
}
