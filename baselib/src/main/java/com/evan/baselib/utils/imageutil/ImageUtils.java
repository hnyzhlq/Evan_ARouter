package com.evan.baselib.utils.imageutil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.evan.baselib.LibraryConfig;

import java.io.File;

/**
 * 图片加载工具类
 * Evan
 */
public class ImageUtils {
    /**
     * 加载网络图片
     * @param mContext
     * @param url
     * @param view
     * @param morenphoto
     */
    public static void loadImageNew(Context mContext, String url, ImageView view, int morenphoto) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(morenphoto)
                .priority(Priority.HIGH);
        Glide.with(mContext).load(url).apply(options).into(view);

    }
    /**
     * 加载网络图片
     *
     * @param url
     * @param view
     */
    public static void loadImage(String url, ImageView view) {
        loadImageDefault(url, view, 0, 0);
    }

    public static void loadImageDefault(String url, ImageView view, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url);
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载文件图片
     *
     * @param url
     * @param view
     */
    public static void loadImage(File url, ImageView view) {
        loadImageDefault(url, view, 0, 0);
    }

    public static void loadImageDefault(File url, ImageView view, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url);
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载资源文件图片
     *
     * @param url
     * @param view
     */
    public static void loadImage(int url, ImageView view) {
        loadImageDefault(url, view, 0, 0);
    }

    public static void loadImageDefault(int url, ImageView view, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url);
        loadImageFromQuest(quest, view, holder, error);

    }


    /**
     * 加载圆形图片
     *
     * @param url
     * @param view
     */
    public static void loadCircleImage(String url, ImageView view) {
        loadCircleImageDefault(url, view, 0, 0);
    }

    public static void loadCircleImageDefault(String url, ImageView view, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param view
     */
    public static void loadCircleImage(File url, ImageView view) {
        loadCircleImageDefault(url, view, 0, 0);
    }

    public static void loadCircleImageDefault(File url, ImageView view, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param view
     */
    public static void loadCircleImage(int url, ImageView view) {
        loadCircleImageDefault(url, view, 0, 0);
    }

    public static void loadCircleImageDefault(int url, ImageView view, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载带边框的圆形图片
     *
     * @param url
     * @param view
     * @param wide  边框宽度
     * @param color 边框颜色
     */
    public static void loadCircleImage(String url, ImageView view, int wide, int color) {
        loadCircleImageDefault(url, view, wide, color, 0, 0);
    }

    public static void loadCircleImageDefault(String url, ImageView view, int wide, int color, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication(), wide, color));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载带边框的圆形图片
     *
     * @param url
     * @param view
     * @param wide  边框宽度
     * @param color 边框颜色
     */
    public static void loadCircleImage(File url, ImageView view, int wide, int color) {
        loadCircleImageDefault(url, view, wide, color, 0, 0);
    }

    public static void loadCircleImageDefault(File url, ImageView view, int wide, int color, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication(), wide, color));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载带边框的圆形图片
     *
     * @param url
     * @param view
     * @param wide  边框宽度
     * @param color 边框颜色
     */
    public static void loadCircleImage(int url, ImageView view, int wide, int color) {
        loadCircleImageDefault(url, view, wide, color, 0, 0);
    }

    public static void loadCircleImageDefault(int url, ImageView view, int wide, int color, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication(), wide, color));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载固定大小圆形图片
     *
     * @param url
     * @param view
     * @param size 图片尺寸
     */
    public static void loadCircleImage(String url, ImageView view, int size) {
        loadCircleImageDefault(url, view, size, 0, 0);
    }

    public static void loadCircleImageDefault(String url, ImageView view, int size, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()))
                .override(size, size);
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载固定大小圆形图片（从文件）
     *
     * @param url
     * @param view
     * @param size 图片尺寸
     */
    public static void loadCircleImage(File url, ImageView view, int size) {
        loadCircleImageDefault(url, view, size, 0, 0);
    }

    public static void loadCircleImageDefault(File url, ImageView view, int size, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()))
                .override(size, size);
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载固定大小圆形图片（从文件）
     *
     * @param url
     * @param view
     * @param size 图片尺寸
     */
    public static void loadCircleImage(int url, ImageView view, int size) {
        loadCircleImageDefault(url, view, size, 0, 0);
    }

    public static void loadCircleImageDefault(int url, ImageView view, int size, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()))
                .override(size, size);
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载圆角图片（默认圆角4dp）
     *
     * @param url
     * @param view
     */
    public static void loadRoundImage(String url, ImageView view) {
        loadRoundImageDefault(url, view, 0, 0);
    }

    public static void loadRoundImageDefault(String url, ImageView view, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), 4));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载圆角图片（默认圆角4dp）
     *
     * @param url
     * @param view
     */
    public static void loadRoundImage(File url, ImageView view) {
        loadRoundImageDefault(url, view, 0, 0);
    }

    public static void loadRoundImageDefault(File url, ImageView view, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), 4));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载圆角图片（默认圆角4dp）
     *
     * @param url
     * @param view
     */
    public static void loadRoundImage(int url, ImageView view) {
        loadRoundImageDefault(url, view, 0, 0);
    }

    public static void loadRoundImageDefault(int url, ImageView view, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), 4));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载指定圆角的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值（dp）
     */
    public static void loadRoundImage(String url, ImageView view, int radius) {
        loadRoundImageDefault(url, view, radius, 0, 0);
    }

    public static void loadRoundImageDefault(String url, ImageView view, int radius, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载指定圆角的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值（dp）
     */
    public static void loadRoundImage(File url, ImageView view, int radius) {
        loadRoundImageDefault(url, view, radius, 0, 0);
    }

    public static void loadRoundImageDefault(File url, ImageView view, int radius, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载指定圆角的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值（dp）
     */
    public static void loadRoundImage(int url, ImageView view, int radius) {
        loadRoundImageDefault(url, view, radius, 0, 0);
    }

    public static void loadRoundImageDefault(int url, ImageView view, int radius, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius));
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载固定大小的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值(dp)
     * @param width  图片宽（px）
     * @param height 图片高（px）
     */
    public static void loadRoundImage(String url, ImageView view, int radius, int width, int height) {
        loadRoundImageDefault(url, view, radius, width, height, 0, 0);
    }

    public static void loadRoundImageDefault(String url, ImageView view, int radius, int width, int height, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius))
                .override(width, height);
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载固定大小的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值(dp)
     * @param width  图片宽（px）
     * @param height 图片高（px）
     */
    public static void loadRoundImage(File url, ImageView view, int radius, int width, int height) {
        loadRoundImageDefault(url, view, radius, width, height, 0, 0);
    }

    public static void loadRoundImageDefault(File url, ImageView view, int radius, int width, int height, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius))
                .override(width, height);
        loadImageFromQuest(quest, view, holder, error);
    }

    /**
     * 加载固定大小的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值(dp)
     * @param width  图片宽（px）
     * @param height 图片高（px）
     */
    public static void loadRoundImage(int url, ImageView view, int radius, int width, int height) {
        loadRoundImageDefault(url, view, radius, width, height, 0, 0);
    }

    public static void loadRoundImageDefault(int url, ImageView view, int radius, int width, int height, int holder, int error) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius))
                .override(width, height);
        loadImageFromQuest(quest, view, holder, error);
    }


    public static void loadImageFromQuest(GlideRequest<Drawable> quest, ImageView view, int holder, int error) {
        if (holder == 0) {
            if (LibraryConfig.getDefaultImgHolder() != -1) {
                quest = quest.placeholder(LibraryConfig.getDefaultImgHolder());
            }
        } else {
            quest = quest.placeholder(holder);
        }
        if (error == 0) {
            if (LibraryConfig.getDefaultImgError() != -1) {
                quest = quest.error(LibraryConfig.getDefaultImgError());
            }
        } else {
            quest = quest.error(error);
        }

        view.setTag(null);//需要清空tag，否则报错
        quest.into(view);
    }

    /**
     * 加载网络图片
     *
     * @param url
     * @param view
     */
    public static void loadHead(String url, ImageView view) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url);
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载文件图片
     *
     * @param url
     * @param view
     */
    public static void loadHead(File url, ImageView view) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url);
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载资源文件图片
     *
     * @param url
     * @param view
     */
    public static void loadHead(int url, ImageView view) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url);
        loadHeadFromQuest(quest, view);
    }


    /**
     * 加载圆形图片
     *
     * @param url
     * @param view
     */
    public static void loadCircleHead(String url, ImageView view) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param view
     */
    public static void loadCircleHead(File url, ImageView view) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param view
     */
    public static void loadCircleHead(int url, ImageView view) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载带边框的圆形图片
     *
     * @param url
     * @param view
     * @param wide  边框宽度
     * @param color 边框颜色
     */
    public static void loadCircleHead(String url, ImageView view, int wide, int color) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication(), wide, color));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载带边框的圆形图片
     *
     * @param url
     * @param view
     * @param wide  边框宽度
     * @param color 边框颜色
     */
    public static void loadCircleHead(File url, ImageView view, int wide, int color) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication(), wide, color));
        loadHeadFromQuest(quest, view);
    }


    /**
     * 加载带边框的圆形图片
     *
     * @param url
     * @param view
     * @param wide  边框宽度
     * @param color 边框颜色
     */
    public static void loadCircleHead(int url, ImageView view, int wide, int color) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication(), wide, color));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载固定大小圆形图片
     *
     * @param url
     * @param view
     * @param size 图片尺寸
     */
    public static void loadCircleHead(String url, ImageView view, int size) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()))
                .override(size, size);
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载固定大小圆形图片（从文件）
     *
     * @param url
     * @param view
     * @param size 图片尺寸
     */
    public static void loadCircleHead(File url, ImageView view, int size) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()))
                .override(size, size);
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载固定大小圆形图片（从文件）
     *
     * @param url
     * @param view
     * @param size 图片尺寸
     */
    public static void loadCircleHead(int url, ImageView view, int size) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(LibraryConfig.getApplication()))
                .override(size, size);
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载圆角图片（默认圆角4dp）
     *
     * @param url
     * @param view
     */
    public static void loadRoundHead(String url, ImageView view) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), 4));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载圆角图片（默认圆角4dp）
     *
     * @param url
     * @param view
     */
    public static void loadRoundHead(File url, ImageView view) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), 4));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载圆角图片（默认圆角4dp）
     *
     * @param url
     * @param view
     */
    public static void loadRoundHead(int url, ImageView view) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), 4));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载指定圆角的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值（dp）
     */
    public static void loadRoundHead(String url, ImageView view, int radius) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius));
        loadHeadFromQuest(quest, view);
    }
    /**
     * 加载指定圆角的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值（dp）
     */
    public static void loadRoundHead(File url, ImageView view, int radius) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载指定圆角的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值（dp）
     */
    public static void loadRoundHead(int url, ImageView view, int radius) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius));
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载固定大小的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值(dp)
     * @param width  图片宽（px）
     * @param height 图片高（px）
     */
    public static void loadRoundHead(String url, ImageView view, int radius, int width, int height) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius))
                .override(width, height);
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载固定大小的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值(dp)
     * @param width  图片宽（px）
     * @param height 图片高（px）
     */
    public static void loadRoundHead(File url, ImageView view, int radius, int width, int height) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius))
                .override(width, height);
        loadHeadFromQuest(quest, view);
    }

    /**
     * 加载固定大小的圆角图片
     *
     * @param url
     * @param view
     * @param radius 圆角值(dp)
     * @param width  图片宽（px）
     * @param height 图片高（px）
     */
    public static void loadRoundHead(int url, ImageView view, int radius, int width, int height) {
        GlideRequest<Drawable> quest = GlideApp.with(LibraryConfig.getApplication())
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(LibraryConfig.getApplication(), radius))
                .override(width, height);
        loadHeadFromQuest(quest, view);
    }


    public static void loadHeadFromQuest(GlideRequest<Drawable> quest, ImageView view) {
        if (LibraryConfig.getDefaultImgHolder() != -1) {
            quest = quest.placeholder(LibraryConfig.getDefaultHeadholder());
        }
        if (LibraryConfig.getDefaultImgError() != -1) {
            quest = quest.error(LibraryConfig.getDefaultHeadError());
        }

        view.setTag(null);//需要清空tag，否则报错
        quest.into(view);
    }

    /**
     * 清除图片缓存
     */
    public static void clearMemoryCache() {
        System.gc();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            GlideApp.get(LibraryConfig.getApplication()).clearMemory();
        }
    }

}
