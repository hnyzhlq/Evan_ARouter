package com.evan.baselib.manager;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


import com.evan.baselib.LibraryConfig;
import com.evan.baselib.utils.UIUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Evan
 * @time 2017/12/30  11:54
 * @desc 线程池管理类，常规线程调用用此类进行
 * 包括静态方法，在主线程执行task，在主线程弹出toast
 */


public class ThreadPoolManager {

    private ThreadPoolExecutor mExecutor;            // 常规线程，只需要一个
    private ThreadPoolExecutor mDownExecutor;            // 下载线程，只需要一个

    /*--------------- 以下参数直接决定了一个线程池,所以交给外部创建的时候定义 ---------------*/
    private int mCorePoolSize;
    private int mMaximumPoolSize;
    private long mKeepAliveTime;

    private int mDownCorePoolSize;
    private int mDownMaximumPoolSize;
    private long mDownKeepAliveTime;

    /**
     * 普通线程池代理(网络请求)
     */
    private static ThreadPoolManager mNormalThreadPoolProxy;    // 单例

    /**
     * 返回普通线程池代理的实例
     *
     * @return
     */
    public static ThreadPoolManager getInstance() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolManager.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolManager(5, 5, 3000,3,3,3000);
                }

            }
        }
        return mNormalThreadPoolProxy;
    }

    private ThreadPoolManager(int corePoolSize, int maximumPoolSize, long keepAliveTime,int downCorePoolSize, int downMaximumPoolSize, long downKeepAliveTime) {
        super();
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
        mKeepAliveTime = keepAliveTime;
        mDownCorePoolSize = downCorePoolSize;
        mDownMaximumPoolSize = downMaximumPoolSize;
        mDownKeepAliveTime = downKeepAliveTime;
    }

    /**
     * 初始化常规线程池
     */
    private synchronized void initThreadPoolExecutor() {// 双重检查加锁,只有在第一次实例化的时候才会启用同步机制
        TimeUnit unit = TimeUnit.MILLISECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            synchronized (ThreadPoolManager.class) {
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    mExecutor = new ThreadPoolExecutor(
                            mCorePoolSize, // 核心线程数
                            mMaximumPoolSize, // 最大线程数
                            mKeepAliveTime, // 保持时间
                            unit, // 时间单位
                            workQueue,// 任务队列
                            threadFactory, // 线程工厂
                            handler// 异常部活期
                    );
                }
            }
        }
    }
    /**
     * 初始化下载线程池
     */
    private synchronized void initDownThreadPoolExecutor() {// 双重检查加锁,只有在第一次实例化的时候才会启用同步机制
        TimeUnit unit = TimeUnit.MILLISECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        if (mDownExecutor == null || mDownExecutor.isShutdown() || mDownExecutor.isTerminated()) {
            synchronized (ThreadPoolManager.class) {
                if (mDownExecutor == null || mDownExecutor.isShutdown() || mDownExecutor.isTerminated()) {
                    mDownExecutor = new ThreadPoolExecutor(
                            mDownCorePoolSize, // 核心线程数
                            mDownMaximumPoolSize, // 最大线程数
                            mDownKeepAliveTime, // 保持时间
                            unit, // 时间单位
                            workQueue,// 任务队列
                            threadFactory, // 线程工厂
                            handler// 异常部活期
                    );
                }
            }
        }
    }

    /**
     Future:
     1. 判断任务是否执行完成
     2. 可以拿到任务执行完成之后的结果
     3. 可以捕获任务执行过程中抛出的异常
     */
    /**
     * 常规线程提交任务
     */
    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }

    /**
     * 常规线程执行任务（常规使用）
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    /**
     * 常规线程移除任务
     */
    public void remove(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
    /**
     * 下载线程提交任务
     */
    public Future<?> downSubmit(Runnable task) {
        initDownThreadPoolExecutor();
        return mDownExecutor.submit(task);
    }

    /**
     * 下载线程执行任务（常规使用）
     */
    public void downExecute(Runnable task) {
        initDownThreadPoolExecutor();
        mDownExecutor.execute(task);
    }

    /**
     * 下载线程移除任务
     */
    public void downRemove(Runnable task) {
        initDownThreadPoolExecutor();
        mDownExecutor.remove(task);
    }
    /**
     * 主线程操作
     */
    /**
     * 得到主线中中的一个handler
     */
    public static Handler getMainThreadHandler() {
        return LibraryConfig.getMainHandle();
    }

    /**
     * 安全的执行一个task
     */
    public static void postTaskSafely(Runnable task) {
        // 得到当前线程的线程id
        long curThreadId = android.os.Process.myTid();
        // 如果当前线程的线程id==主线程线程id
        if (curThreadId == Looper.getMainLooper().getThread().getId()) {
            task.run();
        } else { // 如果当前线程的线程id!=主线程线程id
            getMainThreadHandler().post(task);
        }
    }

    /**
     * 延迟执行一个任务
     */
    public static void postTaskSafelyDelay(Runnable task, long delayMillis) {
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    /**
     * 移除任务
     */
    public static void removeTaskSafely(Runnable task) {
        getMainThreadHandler().removeCallbacks(task);
    }

    /**
     * 主线程弹出Toast
     */
    public static void safeToast(int res) {
        safeToast(UIUtils.getString(res));
    }

    public static void safeToast(final String string) {
        safeToast(string, Toast.LENGTH_SHORT);
    }

    public static void safeToast(final String string, final int mode) {
        ThreadPoolManager.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LibraryConfig.getApplication(),
                        string, mode).show();
            }
        });
    }
}
