package me.zzy.redot;

import android.app.Application;

import me.zzy.redot.util.SharedPreferencesUtils;

/**
 * @author ZZY
 * 12/20/20.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化本地缓存对象
        initSharedPreferences();

//        // 初始化本地数据库对象
//        AppDatabase.getInstance(this);

    }

    private void initSharedPreferences() {
        SharedPreferencesUtils.init(this);

        SharedPreferencesUtils
                .setSharedPreferences(R.string.preference_file_key)
                .put("is_scroll_forever", true)
                .put("times", Integer.MAX_VALUE)
                .put("text_size", 16f)
                .put("text_color", 0x000000)
                .put("background_color", 0xffffff)
                .put("text", "test")
                .put("speed", 10)
                .put("isHorizontal", true)
                .put("clickEnable", true)
                .finish();
//        SharedPreferencesUtils
//                .setSharedPreferences(getPackageName() + "_preferences")
//                .finish();

    }
}
