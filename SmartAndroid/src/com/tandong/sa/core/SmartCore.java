package com.tandong.sa.core;

import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

public class SmartCore {
	private Context c;

	public SmartCore(Context context) {
		this.c = context;
	}

	public int smartResource(String idName, String idType) {

		return c.getResources().getIdentifier(idName, idType,
				c.getPackageName());
	}

	public static int smartResourceId(Context context, String idName,
			String idType) {

		return context.getResources().getIdentifier(idName, idType,
				context.getPackageName());
	}

	public static void getMotionEvent(View v, int action, float x, float y) {
		// ±»¶¯´¥Ãþ£º29.490723£¬17.842651
		MotionEvent event = MotionEvent.obtain(SystemClock.uptimeMillis(),
				SystemClock.uptimeMillis() + 5, action, x, y, 0);
		v.dispatchTouchEvent(event);
	}
}
