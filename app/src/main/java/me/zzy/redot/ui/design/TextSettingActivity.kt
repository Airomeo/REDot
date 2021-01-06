package me.zzy.redot.ui.design

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting_text.*
import me.zzy.redot.AppDatabase
import me.zzy.redot.R
import me.zzy.redot.room.entity.Board

class TextSettingActivity : AppCompatActivity() {

    //    private var textColor: Int = 0
    private var scrollSpeed: Int = 5
    private var scrollSize: Float = 20f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_text)

        scrollText.setSpeed(scrollText.scrollSpeed)
        scrollText.setTextSize(scrollText.getTextSize());
        scrollText.setTextColor(scrollText.mTextColor);
        scrollText.setScrollTextBackgroundColor(scrollText.textBackgroundColor);
        scrollText.setText(scrollText.scrollText)


        text_size_seek_bar.progress = scrollSize.toInt()
        text_speed_seek_bar.progress = scrollText.scrollSpeed
        text_color.setCurrentColor(scrollText.mTextColor)
        bg_color.setCurrentColor(scrollText.textBackgroundColor)

        text_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                scrollText.setText(s.toString())
                if (s.toString() == "") {
                    scrollText.setText(getString(R.string.input_text_hint))
                }
            }
        })

        check.setOnClickListener {
//            TODO: 存配置信息到数据库，传数据到下一个activity
            //TODO: 重写scrollText
            val board = Board()
            board.text = text_input.text.toString()
            board.backgroundColor = scrollText.textBackgroundColor
            board.textColor = scrollText.mTextColor
            board.textSize = scrollText.getTextSize()
            board.clickable = scrollText.isClickable
            board.speed = scrollSpeed
            //            board.is_scroll_forever = scrollText.isScrollForever;
            board.isHorizontal = scrollText.isHorizontal
            AppDatabase.getDatabase(this).boardDao().insertAll(board)

            val intent = Intent(this, FullscreenActivity::class.java)
            intent.putExtra(TEXT_INPUT_KEY, if (text_input.text.toString() == "") getString(R.string.input_text_hint) else text_input.text.toString())
            intent.putExtra(SCROLL_SIZE_KEY, scrollText.getTextSize())
            intent.putExtra(SCROLL_SPEED_KEY, scrollSpeed)
            intent.putExtra(TEXT_COLOR_KEY, scrollText.mTextColor)
            intent.putExtra(TEXT_BG_COLOR_KEY, scrollText.textBackgroundColor)
            startActivity(intent)
        }

        text_size_seek_bar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                scrollText.setTextSize(progress.toFloat())
                scrollSize = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        text_speed_seek_bar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                scrollText.setSpeed(progress)
                Log.d("TAG", "onProgressChanged: "+progress)
                scrollSpeed = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        text_color.setOnColorChangerListener(object : ColorBarView.OnColorChangeListener {
            override fun onColorChange(color: Int) {
                Log.i("COLOR_INT", "颜色发生变化$color")
                scrollText.setTextColor(color)
            }
        })

        bg_color.setOnColorChangerListener(object : ColorBarView.OnColorChangeListener {
            override fun onColorChange(color: Int) {
                Log.i("COLOR_INT", "颜色发生变化$color")
                scrollText.setScrollTextBackgroundColor(color)
            }

        })
    }

    companion object {
        const val TEXT_INPUT_KEY = "textInput"
        const val SCROLL_SIZE_KEY = "scrollSize"
        const val SCROLL_SPEED_KEY = "scrollSpeed"
        const val TEXT_COLOR_KEY = "textColor"
        const val TEXT_BG_COLOR_KEY = "textBgColor"
        const val REQUEST_SETTING_CODE = 1
    }
}