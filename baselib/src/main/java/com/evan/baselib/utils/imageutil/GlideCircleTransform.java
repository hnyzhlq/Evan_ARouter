package com.evan.baselib.utils.imageutil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 圆形图片Transform
 * Created by Evan on 2018/3/27.
 */

public class GlideCircleTransform extends BitmapTransformation {
    private Paint mBorderPaint;
    private float mBorderWidth;
    public GlideCircleTransform(Context context) {
        super(context);
    }
    public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
        super(context);
        mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;

        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }


    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = source.getWidth();
        int x = 0;
        int y = 0;
        Bitmap squared = source;

        if (Math.abs(source.getWidth() - source.getHeight()) > 10) {
            size = Math.min(source.getWidth(), source.getHeight());
            x = (source.getWidth() - size) / 2;
            y = (source.getHeight() - size) / 2;
            squared = Bitmap.createBitmap(source, x, y, size, size);
        }

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        if (mBorderPaint != null) {
            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return result;
    }

    public String getId() {
        return getClass().getName();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}