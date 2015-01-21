package com.smartandroid.sa.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.StatFs;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Android相关辅助方法和快捷方法
 * 
 * @author Administrator
 * 
 */
public class AssistTool {
	private Context c;

	public AssistTool(Context context) {

		this.c = context;
	}

	/**
	 * 将JAVA里获取到的13位毫秒数转换为PHP使用的10位的时间戳毫秒数
	 * 
	 * @param javaTime
	 *            JAVA里获取的13位时间戳毫秒数
	 * @return PHP里使用的10位时间戳毫秒数
	 */
	public String toPHPTime(String javaTime) {
		String phpTime = javaTime.substring(0, 10);
		return phpTime;
	}

	public void CountJump(long mills, final Class<? extends Activity> clazz,
			final boolean finish) {
		Handler handler_jump = new Handler();
		handler_jump.postDelayed(new Runnable() {

			@Override
			public void run() {
				gotoActivity(clazz, finish);

			}
		}, mills);
	}

	/**
	 * 将时间戳毫秒数转换为标准的时间格式：yyyy-MM-dd_HH:mm:ss
	 * 
	 * @param millisecond
	 *            时间毫秒数
	 * @return 标准时间格式
	 */
	public String toNormalTime(long millisecond) {
		return DateFormat.format("yyyy-MM-dd HH:mm:ss", millisecond).toString();
	}

	public String toNormalTime(long millisecond, String model) {
		return DateFormat.format(model, millisecond).toString();
	}

	/**
	 * 采用InputStream、OutputStream、File、FileOutputStream操作：复制assets目录下的文件到手机里（
	 * 位置可以自己指定）
	 * 
	 * @author Administrator
	 * 
	 */
	public void copyAssets(String assetsUri, String savaPath) {

		AssetManager assets = c.getAssets();
		InputStream stream = null;// 输入流，读取数据
		OutputStream output = null;
		try {
			stream = assets.open(assetsUri);// 这个已经直接转换为InputStream
			if (stream == null) {
				Toast.makeText(c, "没有文件可以复制", Toast.LENGTH_SHORT).show();
			}
			String folder = "/mnt/sdcard/Android/data";// 目标文件夹
			File f = new File(folder);
			if (!f.exists()) {
				f.mkdir();
			}
			// String filePath = "/mnt/sdcard/Android/data/copyTest.apk";
			File file = new File(savaPath);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int read;
			while ((read = stream.read(buffer)) != -1) {
				output.write(buffer, 0, read);
			}
			output.flush();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				output.close();
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 从资源文件中（asset）读取文本文档(如果出现乱码，可以调换输入的格式参数charsetName)
	 * 
	 * @param assetsUri
	 *            文本文档的路径
	 * @param charsetName
	 *            读取显示的文本格式(UTF-8或者GBK)
	 * @return 读取后的文本信息
	 */
	public String readTxtFromAssets(String assetsUri, String charsetName) {
		String text = null;
		// 获得输入流
		try {
			InputStream in = c.getAssets().open(assetsUri);
			int size = in.available();
			// 将输入流读到字节数组中（内存）
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			text = new String(buffer, charsetName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

	public InputStream getISAssets(String name) {
		AssetManager am = c.getAssets();
		InputStream is = null;
		try {
			is = am.open(name);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}

	public String getStringFromAssets(String fileName) {
		String Result = "";
		try {
			InputStreamReader inputReader = new InputStreamReader(c
					.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";

			while ((line = bufReader.readLine()) != null)
				Result += line;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result;
	}

	public void AutoMakeDir(String url) {
		File f = new File(url);
		if (!f.exists()) {
			f.mkdir();
		}
	}

	public boolean FileExists(String url) {
		File f = new File(url);
		if (!f.exists()) {
			return false;
		}
		return true;
	}

	public void copy(String ASSETS_NAME, String savePath, String saveName) {
		String filename = savePath + "/" + saveName;

		File dir = new File(savePath);
		// 如果目录不中存在，创建这个目录
		if (!dir.exists())
			dir.mkdir();
		try {
			if (!(new File(filename)).exists()) {
				InputStream is = c.getResources().getAssets().open(ASSETS_NAME);
				FileOutputStream fos = new FileOutputStream(filename);
				byte[] buffer = new byte[7168];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
		Intent intent = new Intent(c, clazz);
		c.startActivity(intent);
		if (finish) {
			((Activity) c).finish();
		}
	}

	/**
	 * 使用该方法打印输出调试信息
	 * 
	 * @param log
	 */
	public static void print(String log) {

		System.out.println(log);

	}

	public void gotoActivity(Class<? extends Activity> clazz, boolean finish,
			Bundle pBundle) {
		Intent intent = new Intent(c, clazz);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		c.startActivity(intent);
		if (finish) {
			((Activity) c).finish();
		}
	}

	private WakeLock wakeLock;

	public void acquireWakeLock() {
		if (wakeLock != null) {
			PowerManager pm = (PowerManager) c
					.getSystemService(c.POWER_SERVICE);
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
	public String getWifiIp() {
		WifiManager wifiManager = (WifiManager) c
				.getSystemService(Context.WIFI_SERVICE);
		int ip = wifiManager.getConnectionInfo().getIpAddress();
		return intToIp(ip);
	}

	private static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + ((i >> 24) & 0xFF);
	}

	public void hideSoftKeyboard(View view) {
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) c
					.getSystemService(c.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public void showToast(int stringRes, int duration) {
		Toast.makeText(c, stringRes, duration).show();
	}

	public void showToast(String stringRes, int duration) {
		Toast.makeText(c, stringRes, duration).show();
	}

	public void showToast(int stringRes) {
		Toast.makeText(c, stringRes, Toast.LENGTH_SHORT).show();
	}

	public void showToast(String stringRes) {
		Toast.makeText(c, stringRes, Toast.LENGTH_SHORT).show();
	}

	public static String userinfo = "userinfo_pref";

	public void savePreferenceBoolean(String key, boolean value) {
		SharedPreferences preferences = c.getSharedPreferences(userinfo,
				c.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getPreferenceBoolean(String key) {
		SharedPreferences preferences = c.getSharedPreferences(userinfo,
				c.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	public void savePreferenceString(String key, String value) {
		SharedPreferences preferences = c.getSharedPreferences(userinfo,
				c.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void deleteKey(String key) {
		SharedPreferences preferences = c.getSharedPreferences(userinfo,
				c.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public void savePreferenceLong(String key, long value) {
		SharedPreferences preferences = c.getSharedPreferences(userinfo,
				c.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public String getPreferenceString(String key) {
		SharedPreferences preferences = c.getSharedPreferences(userinfo,
				c.MODE_PRIVATE);
		return preferences.getString(key, "");
	}

	public long getPreferenceLong(String key) {
		SharedPreferences preferences = c.getSharedPreferences(userinfo,
				c.MODE_PRIVATE);
		return preferences.getLong(key, 0);
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
			PackageManager manager = c.getPackageManager();
			PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "1.0";
		}
	}

	public int getVersionCode() {
		try {
			PackageManager manager = c.getPackageManager();
			PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
			int versionCode = info.versionCode;
			return versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	public static void setNoTitle(Context ct) {
		((Activity) ct).getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	}

	public static void setFullScreen(Context ct) {
		setNoTitle(ct);
		((Activity) ct).getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
