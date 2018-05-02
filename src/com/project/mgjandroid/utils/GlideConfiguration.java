package com.project.mgjandroid.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.project.mgjandroid.base.FileCache;

/**
 * Created by yuandi on 2016/11/28.
 */

public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //自定义缓存目录，磁盘缓存给150M
        builder.setDiskCache(new DiskLruCacheFactory(FileCache.getAppCacheDirectory(), "image_cache", 100 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}