package me.zzy.redot.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes

/**
 * @author ZZY
 * 12/20/20.
 * Singleton
 * reference https://github.com/open-android/SharedPreferencesUtils
 */
class SharedPreferencesUtils private constructor() {
    fun put(@StringRes key: Int, value: Any) {
        put(mContext!!.getString(key), value)
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    fun put(key: String?, value: Any): SharedPreferencesUtils {
        if (value is String) {
            editor!!.putString(key, value)
        } else if (value is Int) {
            editor!!.putInt(key, value)
        } else if (value is Boolean) {
            editor!!.putBoolean(key, value)
        } else if (value is Float) {
            editor!!.putFloat(key, value)
        } else if (value is Long) {
            editor!!.putLong(key, value)
        } else {
            editor!!.putString(key, value.toString())
        }
        return this
    }

    operator fun get(@StringRes key: Int, defValue: Any): Any? {
        return get(mContext!!.getString(key), defValue)
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
    operator fun get(key: String?, defValue: Any): Any? {
        if (defValue is String) {
            return sharedPreferences.getString(key, defValue)
        } else if (defValue is Int) {
            return sharedPreferences.getInt(key, defValue)
        } else if (defValue is Boolean) {
            return sharedPreferences.getBoolean(key, defValue)
        } else if (defValue is Float) {
            return sharedPreferences.getFloat(key, defValue)
        } else if (defValue is Long) {
            return sharedPreferences.getLong(key, defValue)
        }
        return null
    }

    fun putStringSet(@StringRes key: Int, value: Set<String?>): SharedPreferencesUtils {
        return putStringSet(mContext!!.getString(key), value)
    }

    fun putStringSet(key: String?, value: Set<String?>): SharedPreferencesUtils {
        editor!!.putStringSet(key, value)
        return this
    }

    fun getStringSet(key: String?, defValue: Set<String?>): Set<String>? {
        return sharedPreferences.getStringSet(key, defValue)
    }

    fun getStringSet(@StringRes key: Int, defValue: Set<String?>): Set<String>? {
        return getStringSet(mContext!!.getString(key), defValue)
    }

    fun apply() {
        editor!!.apply()
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     * otherwise false.
     */
    operator fun contains(key: String?): Boolean {
        return sharedPreferences.contains(key)
    }

    operator fun contains(@StringRes key: Int): Boolean {
        return contains(mContext!!.getString(key))
    }

    /**
     * 返回所有的键值对
     *
     * @return Returns a map containing a list of pairs key/value representing
     * the preferences.
     */
    val all: Map<String, *>
        get() = sharedPreferences.all

    fun remove(@StringRes key: Int) {
        remove(mContext!!.getString(key))
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key The name of the preference to remove.
     */
    fun remove(key: String?) {
        editor!!.remove(key)
        editor!!.apply()
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        editor!!.clear()
        editor!!.apply()
    }

    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        private var editor: SharedPreferences.Editor? = null
        private var mContext: Context? = null
        val instance = SharedPreferencesUtils()
        fun init(context: Context?): SharedPreferencesUtils {
            mContext = context
            return instance
        }

        /**
         * 承担了getInstance()的任务
         */
        fun setSharedPreferences(@StringRes key: Int): SharedPreferencesUtils {
            return setSharedPreferences(mContext!!.getString(key))
        }

        @SuppressLint("CommitPrefEdits")
        fun setSharedPreferences(name: String?): SharedPreferencesUtils {
            sharedPreferences = mContext!!.getSharedPreferences(name, Context.MODE_PRIVATE)
            editor = sharedPreferences.edit()
            return instance
        }
    }
}