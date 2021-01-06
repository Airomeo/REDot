package me.zzy.redot.ui.design

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.android.synthetic.main.activity_fullscreen.*
import me.zzy.redot.R

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        val intent = intent
        if (intent != null) {
            fullscreen_content.setText(intent.getStringExtra(TextSettingActivity.TEXT_INPUT_KEY))
//                        fullscreen_content.setTextSize(intent.getFloatExtra(SCROLL_SIZE_KEY, 20f));
            fullscreen_content.setSpeed(intent.getIntExtra(TextSettingActivity.SCROLL_SPEED_KEY, 10))
            fullscreen_content.setTextColor(intent.getIntExtra(TextSettingActivity.TEXT_COLOR_KEY, 0))
            fullscreen_content.setScrollTextBackgroundColor(intent.getIntExtra(TextSettingActivity.TEXT_BG_COLOR_KEY, 0))
        }
    }

    // 第一次按下返回键的事件
    private var firstPressedTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - firstPressedTime < 2000) {
            super.onBackPressed()
        } else {
            Toast.makeText(this@FullscreenActivity, "再按一次退出", Toast.LENGTH_SHORT).show()
            firstPressedTime = System.currentTimeMillis()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {

        WindowCompat.setDecorFitsSystemWindows(window, false)
//         there may be an issue with setting the systemBarsBehavior on some devices using this alpha library.
//         see: https://issuetracker.google.com/issues/173203649#comment2
//         Use this instead until the bug is fixed
//        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
//            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
             // Enables regular immersive mode.
             // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
             // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}