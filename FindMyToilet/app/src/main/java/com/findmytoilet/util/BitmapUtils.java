package com.findmytoilet.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils {

    private static final String TAG = BitmapUtils.class.getName();

    public static Bitmap toMapMarkerSize(Bitmap bitmap) {

        if (bitmap != null)
            return Bitmap.createScaledBitmap(bitmap,
                    bitmap.getWidth() / 4,
                    bitmap.getHeight() / 4,
                    false);

        return null;
    }

    public static Bitmap bitmapFromResource(Context context, Integer res) {

        if (context == null || res == null)
            return null;

        return BitmapFactory.decodeResource(context.getResources(), res);
    }
}
