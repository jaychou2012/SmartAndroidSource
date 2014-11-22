package com.tandong.sa.activity;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.StatFs;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class SmartFragmentActivity extends FragmentActivity {

	public static boolean alwaysShowPrint = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);

	}

	public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		if (finish) {
			finish();
		}
	}

	private Handler handler_jump;

	public void CountJump(long mills, final Class<? extends Activity> clazz,
			final boolean finish) {
		handler_jump = new Handler();
		handler_jump.postDelayed(new Runnable() {

			@Override
			public void run() {
				gotoActivity(clazz, finish);

			}
		}, mills);
	}

	/**
	 * 使用该方法打印输出调试信息
	 * 
	 * @param log
	 */
	public static void print(String log) {
		if (alwaysShowPrint) {
			System.out.println(log);
		}
	}

	public void gotoActivity(Class<? extends Activity> clazz, boolean finish,
			Bundle pBundle) {
		Intent intent = new Intent(this, clazz);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
		if (finish) {
			finish();
		}
	}

	public void finish(int animIn, int animOut) {
		super.finish();
		overridePendingTransition(animIn, animOut);
	}

	/**
	 * 设置禁止弹出软键盘操作
	 */
	public void disableSoftkeyBoardAutoShow() {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	/**
	 * 保持界面显示
	 */
	public void keepScreenOn() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private WakeLock wakeLock;

	public void acquireWakeLock() {
		if (wakeLock != null) {
			PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP, this
					.getClass().getCanonicalName());
			wakeLock.acquire();
		}
	}

	public void releaseWakeLock() {
		if (wakeLock != null && wakeLock.isHeld()) {
			wakeLock.release();
			wakeLock = null;
		}

	}

	/**
	 * 获取本机wifi网络下的ip地址
	 * 
	 * @return
	 */
	// public String getWifiIp() {
	// WifiManager wifiManager = (WifiManager)
	// getSystemService(Context.WIFI_SERVICE);
	// int ip = wifiManager.getConnectionInfo().getIpAddress();
	// return intToIp(ip);
	// }

	private static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + ((i >> 24) & 0xFF);
	}

	public void hideSoftKeyboard(View view) {
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public void showToast(int stringRes, int duration) {
		Toast.makeText(this, stringRes, duration).show();
	}

	public void showToast(String stringRes, int duration) {
		Toast.makeText(this, stringRes, duration).show();
	}

	public void showToast(int stringRes) {
		Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
	}

	public void showToast(String stringRes) {
		Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
	}

	public static String userinfo = "userinfo_pref";

	public void savePreferenceBoolean(String key, boolean value) {
		SharedPreferences preferences = getSharedPreferences(userinfo,
				MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getPreferenceBoolean(String key) {
		SharedPreferences preferences = getSharedPreferences(userinfo,
				MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	public void savePreferenceString(String key, String value) {
		SharedPreferences preferences = getSharedPreferences(userinfo,
				MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void deleteKey(String key) {
		SharedPreferences preferences = getSharedPreferences(userinfo,
				MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public void savePreferenceLong(String key, long value) {
		SharedPreferences preferences = getSharedPreferences(userinfo,
				MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public String getPreferenceString(String key) {
		SharedPreferences preferences = getSharedPreferences(userinfo,
				MODE_PRIVATE);
		return preferences.getString(key, "");
	}

	public long getPreferenceLong(String key) {
		SharedPreferences preferences = getSharedPreferences(userinfo,
				MODE_PRIVATE);
		return preferences.getLong(key, 0);
	}

	// public void setNoTitle() {
	// getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	// }
	//
	// public void setFullScreen() {
	// setNoTitle();
	// getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	// }

	public void screenPortraitDir() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public void screenLandscapeDir() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * 检测sdcard是否可用
	 * 
	 * @return true为可用，否则为不可用
	 */
	public static boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}

	/**
	 * Checks if there is enough Space on SDCard
	 * 
	 * @param updateSize
	 *            Size to Check
	 * @return True if the Update will fit on SDCard, false if not enough space
	 *         on SDCard Will also return false, if the SDCard is not mounted as
	 *         read/write
	 */
	public static boolean enoughSpaceOnSdCard(long updateSize) {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return (updateSize < getRealSizeOnSdcard());
	}

	/**
	 * get the space is left over on sdcard
	 */
	public static long getRealSizeOnSdcard() {
		File path = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * Checks if there is enough Space on phone self
	 * 
	 */
	public static boolean enoughSpaceOnPhone(long updateSize) {
		return getRealSizeOnPhone() > updateSize;
	}

	/**
	 * get the space is left over on phone self
	 */
	public static long getRealSizeOnPhone() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long realSize = blockSize * availableBlocks;
		return realSize;
	}

	/**
	 * 根据手机分辨率从dp转成px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "1.0";
		}
	}

	public int getVersionCode() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			int versionCode = info.versionCode;
			return versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
}
