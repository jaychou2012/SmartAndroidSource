package com.smartandroid.sa.netWork;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Some Method about Android NetWork
 * 
 * @author tandong
 * 
 */

public class NetWork {
	private Context c;
	public static String json_result = null;

	public NetWork(Context context) {

		this.c = context;
	}

	/**
	 * User access to network status
	 * 
	 * @return Phone network or WIFI connectivity, return boolean type
	 */
	public boolean checkNetInfo() {
		ConnectivityManager con = (ConnectivityManager) c.getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();// wifi״̬
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting(); // Network״̬
		if (wifi || internet) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Get The Phone's Net ConnectType(wifi or 3G)
	 * 
	 * Need Permission��android.permission.ACCESS_NETWORK_STATE
	 * 
	 * @param context
	 * @return 0 means 3G state,1 means wifi state
	 */

	public int getConnectedType() {
		if (c != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) c
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	/**
	 * Get Phone IMEI Number
	 * 
	 * @return imei
	 */

	public String getImei() {
		TelephonyManager telephonyManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		return imei;
	}

}
