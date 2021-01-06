package me.zzy.redot

import android.app.Application
import me.zzy.redot.util.SharedPreferencesUtils

/**
 * @author ZZY
 * 12/20/20.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化本地缓存对象
        initSharedPreferences()

        // 初始化本地数据库对象
        AppDatabase.getDatabase(this)
    }

    private fun initSharedPreferences() {
        SharedPreferencesUtils.Companion.init(this)
        SharedPreferencesUtils.Companion.setSharedPreferences(R.string.preference_file_key)
                .put("is_scroll_forever", true)
                .put("times", Int.MAX_VALUE)
                .put("text_size", 16f)
                .put("text_color", 0xffffff)
                .put("background_color", 0x000000)
                .put("text", "test")
                .put("speed", 10)
                .put("isHorizontal", true)
                .put("clickEnable", true)
                .apply()
        //        SharedPreferencesUtils
//                .setSharedPreferences(getPackageName() + "_preferences")
//                .finish();
    }
}