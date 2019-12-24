package com.example.timerlibrary;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

public class FontManager
{
    public static Typeface getRegularFont(Context context) {
        //return ResourcesCompat.getFont(context, R.font.roboto_regular);
        return ResourcesCompat.getFont(context, R.font.gotham_light);
    }

    public static Typeface getMediumFont(Context context) {
//        return ResourcesCompat.getFont(context, R.font.roboto_medium);
        return ResourcesCompat.getFont(context, R.font.gotham_medium);
    }

    public static Typeface getBoldFont(Context context) {
        return ResourcesCompat.getFont(context, R.font.roboto_bold);
    }

    public static Typeface getTimerFont(Context context) {
        return ResourcesCompat.getFont(context, R.font.bebas_neue_regular);
    }

    public static Typeface getGothamFont(Context context) {
        return ResourcesCompat.getFont(context, R.font.gotham_medium);
    }
}
