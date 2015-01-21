package com.smartandroid.sa.aysnc;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import android.content.Context;

/**
 * 简单的异步任务，仅仅指定返回结果的类型，不可输入参数
 * 
 * @author MaTianyu 2014-2-23下午8:57:55
 */
public abstract class SCachedTask<T extends Serializable> extends
		CTask<Object, Object, T> {
	public SCachedTask(Context context, String key, long cacheTime,
			TimeUnit unit) {
		super(context, key, cacheTime, unit);
	}

	@Override
	protected T doConnectNetwork(Object... params) throws Exception {
		return doConnectNetwork();
	}

	protected abstract T doConnectNetwork() throws Exception;
}
