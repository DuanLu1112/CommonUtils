/*
    ShengDao Android Client, PreferencesManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.dl.commonutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * [PreferencesManager管理类，提供get和put方法来重写SharedPreferences所提供的方法，更为实用和便捷]
 * Created by DuanLu on 2016/7/27.
 **/
@SuppressLint({ "SdCardPath", "DefaultLocale" })
public class PreferencesManager {
	private final String TAG = "PreferencesManager";

	private Context mContext;
	private SharedPreferences preferences;
	private String DATA_URL = "/data/data/";
	private String SHARED_PREFS = "/shared_prefs";
	
	private static String shareName = "SHARE_DATA";
	public static final String THEME = "Theme";
	public static final String LANG = "Lang";
	
	private static PreferencesManager instance;

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	private PreferencesManager(Context context) {
		this(context, shareName);
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param shareName
	 */
	private PreferencesManager(Context context, String shareName) {
		mContext = context;
		preferences = context.getSharedPreferences(shareName,
				Context.MODE_PRIVATE);
	}

	/**
	 * 得到单例模式的PreferencesManager对象
	 * 
	 * @param context
	 *            上下文
	 * @return
	 */
	public static PreferencesManager getInstance(Context context) {
		return getInstance(context, shareName);
	}

	/**
	 * 得到单例模式的PreferencesManager对象
	 * 
	 * @param context
	 *            上下文
	 * @param shareName
	 *            文件名称
	 * @return
	 */
	public static PreferencesManager getInstance(Context context,
			String shareName) {
		if (instance == null) {
			synchronized (PreferencesManager.class) {
				if (instance == null) {
					instance = new PreferencesManager(context, shareName);
				}
			}
		}
		return instance;
	}

	public boolean put(String key, boolean value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putBoolean(key, value);
			return edit.commit();
		}
		return false;
	}

	public boolean put(String key, String value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putString(key, value);
			return edit.commit();
		}
		return false;
	}

	public boolean put(String key, int value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putInt(key, value);
			return edit.commit();
		}
		return false;
	}

	public boolean put(String key, float value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putFloat(key, value);
			return edit.commit();
		}
		return false;
	}

	public boolean put(String key, long value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putLong(key, value);
			return edit.commit();
		}
		return false;
	}

	@SuppressLint("NewApi")
	public boolean put(String key, Set<String> value) {
		Editor edit = preferences.edit();
		if (edit != null) {
			edit.putStringSet(key, value);
			return edit.commit();
		}
		return false;
	}

	/**
	 * 直接存放对象，反射将根据对象的属性作为key，并将对应的值保存。
	 * 
	 * @param t
	 */
	@SuppressWarnings("rawtypes")
	public <T> boolean put(T t) {
		try {
			String methodName = "";
			String saveValue = "";
			String fieldName = "";
			Editor edit = preferences.edit();
			Class cls = t.getClass();

			if (edit != null) {
				
				Method[] methods = cls.getDeclaredMethods();
				Field[] fields = cls.getDeclaredFields();
				
				for (Method method : methods) {
					methodName = method.getName();
					for (Field f : fields) {
						fieldName = f.getName();
						if (methodName.toLowerCase().contains(fieldName.toLowerCase())) {

							Object value = method.invoke(t);
							if (value != null && !TextUtils.isEmpty(String.valueOf(value))) {
								saveValue = String.valueOf(value);
							}

							LogUtils.d(TAG, "key: " + fieldName + " value: " + saveValue);
							edit.putString(fieldName, String.valueOf(saveValue));
							break;
						}
					}
				}
				return edit.commit();
			}
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String get(String key) {
		return preferences.getString(key, "");
	}

	public String get(String key, String defValue) {
		return preferences.getString(key, defValue);
	}

	public boolean get(String key, boolean defValue) {
		return preferences.getBoolean(key, defValue);
	}

	public int get(String key, int defValue) {
		return preferences.getInt(key, defValue);
	}

	public float get(String key, float defValue) {
		return preferences.getFloat(key, defValue);
	}

	public long get(String key, long defValue) {
		return preferences.getLong(key, defValue);
	}

	@SuppressLint("NewApi")
	public Set<String> get(String key, Set<String> defValue) {
		return preferences.getStringSet(key, defValue);
	}

	/**
	 * 获取整个对象，跟put(T t)对应使用， 利用反射得到对象的属性，然后从preferences获取
	 * 
	 * @param cls
	 * @return
	 */
	public <T> Object get(Class<T> cls) {
		Object obj = null;
		String fieldName = "";
		try {
			obj = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields) {
				fieldName = f.getName();
				if (!"serialVersionUID".equals(fieldName)) {
					f.setAccessible(true);
					f.set(obj, get(f.getName()));
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public int getTheme(int defThemeId) {
		return instance.get(THEME, defThemeId);
	}

	public void setTheme(int themeId) {
		instance.put(THEME, themeId);
	}

	public String getLanguage(String defLang) {
		return instance.get(LANG, defLang);
	}

	public void setLang(String Language) {
		instance.put(LANG, Language);
	}

	public void clearAll() {
		try {
			String fileName = shareName+".xml";
			StringBuilder path = new StringBuilder(DATA_URL).append(mContext.getPackageName()).append(SHARED_PREFS);
			File file = new File(path.toString(), fileName);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
