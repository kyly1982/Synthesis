package com.tars.synthesis;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

/**
 * Created by kyly on 2015/11/16.
 */
public class Synthesis extends Application {

    private final int IMAGE_POOL_SIZE = 3;// 线程池数量
    private final int CONNECT_TIME = 15 * 1000;// 连接时间
    private final int TIME_OUT = 30 * 1000;// 超时时间

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化各种组件
     */
    private void init(){
        ImageLoader.getInstance().init(getImageLoaderConfiguration());
    }

    private ImageLoaderConfiguration getImageLoaderConfiguration(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        return configuration;
    }

    private void initImageLoader() {
        ImageLoader loader = ImageLoader.getInstance();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(750, 480)
                .threadPoolSize(IMAGE_POOL_SIZE)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                        // 对于同一url只缓存一个图
                        //.memoryCache(new UsingFreqLimitedMemoryCache(MEM_CACHE_SIZE)).memoryCacheSize(MEM_CACHE_SIZE)
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.FIFO)
                .discCache(new UnlimitedDiskCache(new File(getImageCacheDir())))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), CONNECT_TIME, TIME_OUT))
                .writeDebugLogs().build();
        loader.init(configuration);
    }

    public String getImageCacheDir() {
        return getCacheRoot() + File.separator + getString(R.string.imagecache_dir) + File.separator;
    }


    private String getCacheRoot() {
        String mCacheDir = null;
        if (null == mCacheDir) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                if (null != getExternalCacheDir()) {
                    mCacheDir = getExternalCacheDir().getPath();
                } else {
                    mCacheDir = getCacheDir().getPath();
                }
            } else {
                mCacheDir = getCacheDir().getPath();
            }
        }
        return mCacheDir;
    }
}
