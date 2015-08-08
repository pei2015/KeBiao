package com.lipei.util;

import java.util.Locale;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * 手机配置信息
 */
public class MobileConfig {

	private Context mCon;
	private DisplayMetrics mDesMetrics;

	public MobileConfig(Context mCon) {
		this.mCon = mCon.getApplicationContext();
		mDesMetrics = mCon.getApplicationContext().getResources()
				.getDisplayMetrics();
	}

	/**
	 * Holds the single instance of a ScrrenManager that is shared by the
	 * process.
	 */
	private static MobileConfig instance;

	/** Return the only one ScreenManager instance. */
	public static MobileConfig Instance(Context mCon) {
		if (instance == null) {
			instance = new MobileConfig(mCon);
		}
		return instance;
	}

	/**
	 * Get the operation system of phone.
	 */
	public String getMobileOs() {
		String mSdkVersion = Build.VERSION.RELEASE;
		// System.out.println("_____" + mSdkVersion);
		return mSdkVersion;
	};

	/**
	 * Get the model of phone.For example:C8600,W711 and so on.
	 * 
	 * @return
	 */
	public String getMobileModel() {
		String mPhoneType = Build.MODEL;
		// System.out.println("_____" + mPhoneType);
		return mPhoneType;
	}

	/**
	 * Get the number of phone,sometimes we can't get it because of sim
	 * operator's limit.
	 * 
	 * @return
	 */
	public String getMobileNum() {
		TelephonyManager tm = (TelephonyManager) mCon
				.getSystemService(Context.TELEPHONY_SERVICE);
		String tel = tm.getLine1Number();
		return tel;
	}

	/**
	 * Get the local application version.
	 * 
	 * @param context
	 * @param cls
	 * @return
	 */
	public String getAppVersion() {
		try {
			ComponentName comp = new ComponentName(mCon, mCon.getClass());
			PackageInfo pinfo = mCon.getPackageManager().getPackageInfo(
					comp.getPackageName(), 0);
			return pinfo.versionName;
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			return null;
		}
	}

	/**
	 * Get the locale setting;
	 * 
	 * @return
	 */
	public String getMobileCounry() {
		Resources res = mCon.getResources();
		Configuration conf = res.getConfiguration();
		return conf.locale.toString();
	}

	public String getMobileSettings() {
		String mTimezone = Settings.System.getString(mCon.getContentResolver(),
				Settings.System.SCREEN_OFF_TIMEOUT);
		// System.out.println("mTimezone_________" + mTimezone);
		return mTimezone;
	}

	/**
	 * Set the uses's locale in this application.You need to put
	 * android:configChanges="locale" in Application node in AndroidMenifest.xml
	 * file.
	 */
	public void setAppLocale() {
		String languageToLoad = "de";
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		mCon.getResources().updateConfiguration(config, null);
	}

	public void fetchStatus() {
		String mSdk = Build.VERSION.SDK;
		System.out.println("_____" + mSdk);
	}

	/**
	 * Get the imei by telephoney service.
	 * @return
	 */
	public String getSimImei() {
		TelephonyManager tm = (TelephonyManager) mCon
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		// System.out.println("_____" + imei);
		if (imei == null) {
			return "00000000";
		}
		return imei;
	}

	/** The height of phone in resolution. */
	public int getResolutionH() {
		return mDesMetrics.heightPixels;
	}

	/** The width of phone in resolution. */
	public int getResolutionW() {
		return mDesMetrics.widthPixels;
	}
}
