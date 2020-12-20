package me.zzy.redot.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.util.Map;
import java.util.Set;

/**
 * @author ZZY
 * 12/20/20.
 * Singleton
 * reference https://github.com/open-android/SharedPreferencesUtils
 */
public class SharedPreferencesUtils {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Context mContext;

    private static final SharedPreferencesUtils UTILS = new SharedPreferencesUtils();

    public static SharedPreferencesUtils getInstance() {
        return UTILS;
    }

    private SharedPreferencesUtils() {
    }

    public static SharedPreferencesUtils init(Context context) {
        mContext = context;
        return UTILS;
    }

    /**
     * 承担了getInstance()的任务
     */
    public static SharedPreferencesUtils setSharedPreferences(@StringRes int key) {
        return setSharedPreferences(mContext.getString(key));
    }

    @SuppressLint("CommitPrefEdits")
    public static SharedPreferencesUtils setSharedPreferences(String name) {
        sharedPreferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return UTILS;
    }

    public void put(@StringRes int key, @NonNull Object value) {
        put(mContext.getString(key), value);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public SharedPreferencesUtils put(String key, @NonNull Object value) {

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        return this;
    }

    public Object get(@StringRes int key, @NonNull Object defValue) {
        return get(mContext.getString(key), defValue);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     */
    public Object get(String key, @NonNull Object defValue) {
        if (defValue instanceof String) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defValue);
        }
        return null;
    }

    public SharedPreferencesUtils putStringSet(@StringRes int key, @NonNull Set<String> value) {
        return putStringSet(mContext.getString(key), value);
    }

    public SharedPreferencesUtils putStringSet(String key, @NonNull Set<String> value) {
        editor.putStringSet(key, value);
        return this;
    }

    public Set<String> getStringSet(String key, @NonNull Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    public Set<String> getStringSet(@StringRes int key, @NonNull Set<String> defValue) {
        return getStringSet(mContext.getString(key), defValue);
    }

    public void finish() {
        editor.apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     * otherwise false.
     */
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public boolean contains(@StringRes int key) {
        return contains(mContext.getString(key));
    }

    /**
     * 返回所有的键值对
     *
     * @return Returns a map containing a list of pairs key/value representing
     * the preferences.
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public void remove(@StringRes int key) {
        remove(mContext.getString(key));
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key The name of the preference to remove.
     */
    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        editor.clear();
        editor.apply();
    }
}


