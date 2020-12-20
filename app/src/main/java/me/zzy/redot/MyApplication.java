package me.zzy.redot;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import me.zzy.redot.util.SharedPreferencesUtils;

/**
 * @author ZZY
 * 12/20/20.
 */
public class MyApplication extends Application {
    private static SharedPreferences defaultSharedPreferences;
    private static SharedPreferences historyPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化本地缓存对象
        initSharedPreferences();

        // 初始化本地数据库对象
//        AppDatabase.getInstance(this);

    }

    private void initSharedPreferences() {
        SharedPreferencesUtils.getInstance();
        //默认的SharedPreferences
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //自定义的SharedPreferences
        historyPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        SharedPreferencesUtils.getInstance
        //初始化数据
//        SharedPreferencesUtils.getInstance().put(defaultSharedPreferences, "asd", "sd");
//        SharedPreferencesUtils.getInstance().put(historyPreferences, "asd", "sd");
    }
}
