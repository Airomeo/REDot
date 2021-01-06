package me.zzy.redot.ui.design

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.*
import androidx.annotation.ColorInt
import me.zzy.redot.R
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.math.ceil

/**
 * Android auto Scroll Text,like TV News,AD devices
 *
 *
 * NEWEST LOG :
 * 1.setText() immediately take effect (v1.3.6)
 * 2.support scroll forever            (v1.3.7)
 * 3.support scroll text size
 *
 *
 * Basic knowledge：https://www.jianshu.com/p/918fec73a24d
 *
 * @author anylife.zlb@gmail.com  2013/09/02
 */
class ScrollTextView : SurfaceView, SurfaceHolder.Callback {
    private val TAG = "ScrollTextView"

    // surface Handle onto a raw buffer that is being managed by the screen compositor.
    //providing access and control over this SurfaceView's underlying surface.
//    private var surfaceHolder: SurfaceHolder? = null
    private var surfaceHolder: SurfaceHolder = this.holder        //get The surface holder

    private var paint: Paint? = null
    private var stopScroll = false // stop scroll
    private var pauseScroll = false // pause scroll

    //Default value
    private var clickEnable = false // click to stop/start
    var isHorizontal = true // horizontal｜V
    var scrollSpeed = 4 // scroll-speed
    var scrollText: String? = "" // scroll text
    private var textSize = 20f // default text size
    var mTextColor = 0xFFFFFF

    /**
     * get Background color
     *
     * @return textBackColor
     */
    var textBackgroundColor = 0x000000
    private var needScrollTimes = Int.MAX_VALUE //scroll times
    private var viewWidth = 0
    private var viewHeight = 0
    private var textWidth = 0f
    private var textX = 0f
    private var textY = 0f
    private var viewWidthPlusTextLength = 0.0f
    private lateinit var scheduledExecutorService: ScheduledExecutorService
    var isSetNewText = false
    var isScrollForever = true

    /**
     * constructs 1
     *
     * @param context you should know
     */
    constructor(context: Context?) : super(context)

    /**
     * constructs 2
     *
     * @param context CONTEXT
     * @param attrs   ATTRS
     */
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        surfaceHolder.addCallback(this)
        paint = Paint()
        val arr = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollTextView)
        clickEnable = arr.getBoolean(R.styleable.ScrollTextView_clickable, clickEnable)
        isHorizontal = arr.getBoolean(R.styleable.ScrollTextView_isHorizontal, isHorizontal)
        scrollSpeed = arr.getInteger(R.styleable.ScrollTextView_speed, scrollSpeed)
        scrollText = arr.getString(R.styleable.ScrollTextView_text)
        mTextColor = arr.getColor(R.styleable.ScrollTextView_textColor, Color.WHITE)
        textSize = arr.getDimension(R.styleable.ScrollTextView_textSize, textSize)
//        needScrollTimes = arr.getInteger(R.styleable.ScrollTextView_times, Int.MAX_VALUE)
        isScrollForever = arr.getBoolean(R.styleable.ScrollTextView_isScroll, true)
        textBackgroundColor = arr.getInt(R.styleable.ScrollTextView_textBackgroundColor, Color.BLACK)
        paint!!.color = mTextColor
        paint!!.textSize = textSize
        setZOrderOnTop(true) //Control whether the surface view's surface is placed on top of its window.
        holder.setFormat(PixelFormat.TRANSLUCENT)
        isFocusable = true
        arr.recycle()
    }

    /**
     * measure text height width
     *
     * @param widthMeasureSpec  widthMeasureSpec
     * @param heightMeasureSpec heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mHeight = getFontHeight(textSize) //实际的视图高
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        // when layout width or height is wrap_content ,should init ScrollTextView Width/Height
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT && layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(viewWidth, mHeight)
            viewHeight = mHeight
        } else if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(viewWidth, viewHeight)
        } else if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(viewWidth, mHeight)
            viewHeight = mHeight
        }
    }

    /**
     * surfaceChanged
     *
     * @param arg0 arg0
     * @param arg1 arg1
     * @param arg2 arg1
     * @param arg3 arg1
     */
    override fun surfaceChanged(arg0: SurfaceHolder, arg1: Int, arg2: Int, arg3: Int) {
        Log.d(TAG, "arg0:$arg0  arg1:$arg1  arg2:$arg2  arg3:$arg3")
    }

    /**
     * surfaceCreated,init a new scroll thread.
     * lockCanvas
     * Draw something
     * unlockCanvasAndPost
     *
     * @param holder holder
     */
    override fun surfaceCreated(holder: SurfaceHolder) {
        stopScroll = false
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        scheduledExecutorService.scheduleAtFixedRate(ScrollTextThread(), 100, 100, TimeUnit.MILLISECONDS)
        Log.d(TAG, "ScrollTextTextView is created")
    }

    /**
     * surfaceDestroyed
     *
     * @param arg0 SurfaceHolder
     */
    override fun surfaceDestroyed(arg0: SurfaceHolder) {
        stopScroll = true
        scheduledExecutorService.shutdownNow()
        Log.d(TAG, "ScrollTextTextView is destroyed")
    }

    /**
     * text height
     *
     * @param fontSize fontSize
     * @return fontSize`s height
     */
    private fun getFontHeight(fontSize: Float): Int {
        val paint = Paint()
        paint.textSize = fontSize
        val fm = paint.fontMetrics
        return ceil((fm.descent - fm.ascent).toDouble()).toInt()
    }

    /**
     * set background color
     *
     * @param color textBackColor
     */
    fun setScrollTextBackgroundColor(color: Int) {
        setBackgroundColor(color)
        textBackgroundColor = color
    }

    /**
     * get text size
     *
     * @return  px
     */
    fun getTextSize(): Float {
        return px2sp(this.context, textSize).toFloat()
    }

    /**
     * set scroll text size SP
     *
     * @param textSizeTem scroll times
     */
    fun setTextSize(textSizeTem: Float) {
        if (textSize < 20) {
            throw IllegalArgumentException("textSize must  > 20")
        } else //重新设置Size
        //试图区域也要改变

        //实际的视图高,thanks to WG

//            require(textSize <= 900) { "textSize must  < 900" }
        textSize = sp2px(context, textSizeTem).toFloat()
        //重新设置Size
        paint!!.textSize = textSize
        //试图区域也要改变
        measureVarious()

        //实际的视图高,thanks to WG
        val mHeight = getFontHeight(textSizeTem)
        val lp = this.layoutParams
        lp.width = viewWidth
        lp.height = dip2px(this.context, mHeight.toFloat())
//        this.layoutParams = lp
        isSetNewText = true
    }

    /**
     * dp to px
     *
     * @param context c
     * @param dpValue dp
     * @return
     */
    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * sp to px
     *
     * @param context c
     * @param spValue sp
     * @return
     */
    private fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    private fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * set scroll text
     *
     * @param newText scroll text
     */
    fun setText(newText: String?) {
        isSetNewText = true
        stopScroll = false
        scrollText = newText
        measureVarious()
    }

    /**
     * Set the text color
     *
     * @param color A color value in the form 0xAARRGGBB.
     */
    fun setTextColor(@ColorInt color: Int) {
        mTextColor = color
        paint!!.color = mTextColor
    }

    /**
     * set scroll speed
     *
     * @param speed SCROLL SPEED [4,14] ///// 0?
     */
    fun setSpeed(speed: Int) {
        if (speed > 14 || speed < 4) {
            throw IllegalArgumentException("Speed was invalid integer, it must between 4 and 14")
        } else {
            this.scrollSpeed = speed
        }
    }

    /**
     * touch to stop / start
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!clickEnable) {
            return true
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> pauseScroll = !pauseScroll
        }
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return if (clickEnable) {
            super.dispatchTouchEvent(event)
        } else {
            false
        }
    }

    /**
     * scroll text vertical
     */
    private fun drawVerticalScroll() {
        val strings: MutableList<String> = ArrayList()
        var start = 0
        var end = 0
        while (end < scrollText!!.length) {
            while (paint!!.measureText(scrollText!!.substring(start, end)) < viewWidth && end < scrollText!!.length) {
                end++
            }
            start = if (end == scrollText!!.length) {
                strings.add(scrollText!!.substring(start, end))
                break
            } else {
                end--
                strings.add(scrollText!!.substring(start, end))
                end
            }
        }
        val fontHeight = paint!!.fontMetrics.bottom - paint!!.fontMetrics.top
        val fontMetrics = paint!!.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseLine = viewHeight / 2 + distance
        for (n in strings.indices) {
            var i = viewHeight + fontHeight
            while (i > -fontHeight) {
                if (stopScroll || isSetNewText) {
                    return
                }
                if (pauseScroll) {
                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        Log.e(TAG, e.toString())
                    }
                    i -= 3
                    continue
                }
                val canvas = surfaceHolder.lockCanvas()
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                canvas.drawText(strings[n], 0f, i, paint!!)
                surfaceHolder.unlockCanvasAndPost(canvas)
                if (i - baseLine < 4 && i - baseLine > 0) {
                    if (stopScroll) {
                        return
                    }
                    try {
                        Thread.sleep((scrollSpeed * 1000).toLong())
                    } catch (e: InterruptedException) {
                        Log.e(TAG, e.toString())
                    }
                }
                i -= 3
            }
        }
    }

    /**
     * Draw text
     *
     * @param X X
     * @param Y Y
     */
    @Synchronized
    private fun draw(X: Float, Y: Float) {
        val canvas = surfaceHolder.lockCanvas()
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        canvas.drawText(scrollText!!, X, Y, paint!!)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        this.visibility = visibility
    }

    /**
     * measure text
     */
    private fun measureVarious() {
        textWidth = paint!!.measureText(scrollText)
        viewWidthPlusTextLength = viewWidth + textWidth
        textX = (viewWidth - viewWidth / 5).toFloat()

        //baseline measure !
        val fontMetrics = paint!!.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        textY = viewHeight / 2 + distance
    }

    /**
     * Scroll thread
     *
     */
    internal inner class ScrollTextThread : Runnable {
        override fun run() {
            measureVarious()
            while (!stopScroll) {

                // NoNeed Scroll，短文不滚动
//                if (textWidth < getWidth()) {
//                    draw(1, textY);
//                    stopScroll = true;
//                    break;
//                }
                if (isHorizontal) {
                    if (pauseScroll) {
                        try {
                            Thread.sleep(500)
                        } catch (e: InterruptedException) {
                            Log.e(TAG, e.toString())
                        }
                        continue
                    }
                    draw(viewWidth - textX, textY)
                    textX += scrollSpeed.toFloat()
                    if (textX > viewWidthPlusTextLength) {
                        textX = 0f
                        --needScrollTimes
                    }
                } else {
                    drawVerticalScroll()
                    isSetNewText = false
                    --needScrollTimes
                }
                if (needScrollTimes <= 0 && isScrollForever) {
                    stopScroll = true
                }
            }
        }
    }
}