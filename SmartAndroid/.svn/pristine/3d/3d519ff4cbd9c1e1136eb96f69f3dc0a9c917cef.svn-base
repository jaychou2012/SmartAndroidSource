package com.tandong.sa.appInfo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;

import com.tandong.sa.entity.MyPermission;

public class AppInfo {

	private Context c;

	public AppInfo(Context context) {

		this.c = context;
	}

	/**
	 * Get the App's PackageName
	 * 
	 * @return the App's PackageName
	 */

	public String getPackageName() {

		return c.getPackageName();
	}

	/**
	 * Get The App VersionCode
	 * 
	 * @param context
	 * @return the App VersionCode
	 */

	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {

		}
		return verCode;
	}

	/**
	 * Get The App VersionName
	 * 
	 * @param context
	 * @return the App VersionName
	 */

	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {

		}
		return verName;

	}

	/**
	 * Get The AppName
	 * 
	 * @param context
	 * @return the AppName
	 */

	public static String getAppName(Context context) {
		String verName = context
				.getResources()
				.getText(
						context.getResources().getIdentifier("app_name",
								"string", context.getPackageName())).toString();
		return verName;
	}

	/**
	 * Get The AppIcon
	 * 
	 * @param context
	 * @return the AppIcon
	 */

	public static Drawable getAppIcon(Context context) {
		Drawable icon = null;
		try {
			icon = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).applicationInfo
					.loadIcon(context.getPackageManager());
		} catch (NameNotFoundException e) {

		}
		return icon;
	}

	/**
	 * Get The App FirstInstallTime(requires API level 9)
	 * 
	 * @param context
	 * @return the App FirstInstallTime
	 */

	public static long getFirstInstallTime(Context context) {
		long fit = 0;
		try {
			fit = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).firstInstallTime;
		} catch (NameNotFoundException e) {

		}
		return fit;

	}

	/**
	 * Get The App LastUpdateTime(requires API level 9)
	 * 
	 * @param context
	 * @return the App LastUpdateTime
	 */

	public static long getLastUpdateTime(Context context) {
		long fit = 0;
		try {
			fit = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).lastUpdateTime;
		} catch (NameNotFoundException e) {

		}
		return fit;

	}

	/**
	 * Get the App's All use Permission
	 * 
	 * @param packageName
	 * @return the use Permissions by ArrayList<MyPermission> permissions
	 */

	public ArrayList<MyPermission> getUsesPermission(String packageName) {
		ArrayList<MyPermission> myPerms = new ArrayList<MyPermission>();
		try {
			PackageManager packageManager = c.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					packageName, PackageManager.GET_PERMISSIONS);
			String[] usesPermissionsArray = packageInfo.requestedPermissions;

			for (int i = 0; i < usesPermissionsArray.length; i++) {
				MyPermission permi = new MyPermission();

				// 得到每个权限的名字,如:android.permission.INTERNET
				String usesPermissionName = usesPermissionsArray[i];
				permi.setPermissionName(usesPermissionName);
				System.out.println("usesPermissionName=" + usesPermissionName);
				// 通过usesPermissionName获取该权限的详细信息
				PermissionInfo permissionInfo = packageManager
						.getPermissionInfo(usesPermissionName, 0);
				// 获得该权限属于哪个权限组,如:网络通信
				PermissionGroupInfo permissionGroupInfo = packageManager
						.getPermissionGroupInfo(permissionInfo.group, 0);
				permi.setPermissionGroup(permissionGroupInfo.loadLabel(
						packageManager).toString());
				System.out.println("permissionGroup="
						+ permissionGroupInfo.loadLabel(packageManager)
								.toString());
				// 获取该权限的标签信息,比如:完全的网络访问权限
				String permissionLabel = permissionInfo.loadLabel(
						packageManager).toString();
				permi.setPermissionLabel(permissionLabel);
				System.out.println("permissionLabel=" + permissionLabel);
				// 获取该权限的详细描述信息,比如:允许该应用创建网络套接字和使用自定义网络协议
				// 浏览器和其他某些应用提供了向互联网发送数据的途径,因此应用无需该权限即可向互联网发送数据.
				String permissionDescription = permissionInfo.loadDescription(
						packageManager).toString();
				permi.setPermissionDescription(permissionDescription);
				System.out.println("permissionDescription="
						+ permissionDescription);
				System.out
						.println("===========================================");
				myPerms.add(permi);

			}
			return myPerms;
		} catch (Exception e) {
		}
		return myPerms;
	}

	/**
	 * Get the Device's ALL Third Apps
	 * 
	 * @return the Device's ALL Third Apps
	 */

	public String getAllThirdApp() {
		String result = "";
		List<PackageInfo> packages = c.getPackageManager()
				.getInstalledPackages(0);
		for (PackageInfo i : packages) {
			if ((i.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				result += i.applicationInfo.loadLabel(c.getPackageManager())
						.toString() + ",";
			}
		}
		return result.substring(0, result.length() - 1);
	}
}
