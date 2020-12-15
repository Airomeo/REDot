package me.zzy.redot.ui.design;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import anylife.scrolltextview.ScrollTextView;
import me.zzy.redot.R;

public class TextSettingActivity extends AppCompatActivity {
    public static final String TEXT_INPUT_KEY = "textInput";
    public static final String SCROLL_SIZE_KEY = "scrollSize";
    public static final String SCROLL_SPEED_KEY = "scrollSpeed";
    public static final String TEXT_COLOR_KEY = "textColor";
    public static final String TEXT_BG_COLOR_KEY = "textBgColor";

    public static final int REQUEST_SETTING_CODE = 0001;
    private ImageButton checkBtn;
    private ScrollTextView scrollTextView;

    private int textColor, textBgColor;

    private int scrollSpeed = 5;
    private float scrollSize = 20f;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        scrollTextView = findViewById(R.id.scrollText);
//        hideBottomUIMenu();
        scrollTextView.setZOrderOnTop(false);
        checkBtn = findViewById(R.id.check);
        editText = findViewById(R.id.text_input);

//        scrollTextView.setTextSize(getIntent().getFloatExtra(SCROLL_SIZE_KEY, scrollSize));
//        scrollTextView.setSpeed(getIntent().getIntExtra(SCROLL_SPEED_KEY, scrollSpeed));
//        scrollTextView.setTextColor(getIntent().getIntExtra(TEXT_COLOR_KEY, 0));
//        scrollTextView.setScrollTextBackgroundColor(getIntent().getIntExtra(TEXT_BG_COLOR_KEY, 0));
//
//        if (!TextUtils.isEmpty(getIntent().getStringExtra(TEXT_INPUT_KEY))) {
//            scrollTextView.setText(getIntent().getStringExtra(TEXT_INPUT_KEY));
//            editText.setText(getIntent().getStringExtra(TEXT_INPUT_KEY));
//        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                scrollTextView.setText(s.toString());
                if (s.toString().equals("")) {
                    scrollTextView.setText(getString(R.string.input_text_hint));
                }
            }
        });

        checkBtn.setOnClickListener(v -> {
//            Intent intent = new Intent();
//            intent.putExtra(TEXT_INPUT_KEY, editText.getText().toString());
//            intent.putExtra(SCROLL_SIZE_KEY, scrollTextView.getTextSize());
//            intent.putExtra(SCROLL_SPEED_KEY, scrollSpeed);
//            intent.putExtra(TEXT_COLOR_KEY, textColor);
//            intent.putExtra(TEXT_BG_COLOR_KEY, textBgColor);
//            setResult(RESULT_OK, intent);

            Intent intent = new Intent(this, FullscreenActivity.class);
            startActivity(intent);


//            finish();
        });

        TextView textColorView = findViewById(R.id.text_color);
        textColorView.setOnClickListener(v -> {
//                selectColor(true);
        });

        TextView textBgView = findViewById(R.id.bg_color);
        textBgView.setOnClickListener(v -> {
//                selectColor(false);
        });

        SeekBar textSizeSeekBar = findViewById(R.id.text_size_seek_bar);
        textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scrollTextView.setTextSize(progress);
                scrollSize = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar textSpeedSeekBar = findViewById(R.id.text_speed_seek_bar);
        textSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scrollTextView.setSpeed(progress);
                scrollSpeed = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    PopupWindow mPopWindow;

//    private void selectColor(final boolean isSetTextColor) {
//        View contentView = LayoutInflater.from(TextActivity.this).inflate(R.layout.color_pop_win, null);
//        mPopWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//
//        //显示PopupWindow
//        View rootView = LayoutInflater.from(TextActivity.this).inflate(R.layout.activity_setting, null);
//        mPopWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
//
//        final ColorPicker picker = contentView.findViewById(R.id.picker);
//        Button confirm = contentView.findViewById(R.id.confirm);
//
//        //To get the color
//        picker.getColor();
//        picker.setOldCenterColor(picker.getColor());
//
//        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
//            @Override
//            public void onColorChanged(int color) {
//                if (isSetTextColor) {
//                    scrollTextView.setTextColor(picker.getColor());
//                    textColor = picker.getColor();
//                } else {
//                    textBgColor = picker.getColor();
//                    scrollTextView.setScrollTextBackgroundColor(textBgColor);
//                }
//            }
//        });
//        picker.setShowOldCenterColor(false);
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPopWindow.dismiss();
//            }
//        });
//    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}