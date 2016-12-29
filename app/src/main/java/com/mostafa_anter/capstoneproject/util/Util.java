package com.mostafa_anter.capstoneproject.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by mostafa_anter on 12/29/16.
 */

public class Util {

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
}
