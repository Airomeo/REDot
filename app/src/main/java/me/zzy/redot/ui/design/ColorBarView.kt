package me.zzy.redot.ui.design

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.zzy.redot.R

/**
 * Author: Aeolou
 * Date:2020/3/14 0014
 * Email:tg0804013x@gmail.com
 */
class ColorBarView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {
    private var onColorChangeListener: OnColorChangeListener? = null
    private var width = 0f
    private var height = 0f

    //长条宽高
    private var barWidth = 0f
    private var barHeight = 0f

    //滑块
    private var thumbDrawable = 0
    private var thumbBitmap: Bitmap? = null

    //滑块宽高
    private var thumbWidth = 0f
    private var thumbHeight = 0f

    //滑块当前的位置
    private var currentThumbOffset = 0f

    //彩色长条开始位置
    private var barStartX = 0f
    private var barStartY = 0f

    //长条画笔
    private var barPaint: Paint? = null

    //滑块画笔
    private var thumbPaint: Paint? = null
    private var currentColor = 0

    interface OnColorChangeListener {
        fun onColorChange(color: Int)
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0) {
    }

    private fun initView() {
        thumbBitmap = BitmapFactory.decodeResource(resources, thumbDrawable)
    }

    private fun init(context: Context) {
        //初始化渐变色
        initColors()
        STATUS = STATUS_INIT
        barPaint = Paint()
        barPaint!!.isAntiAlias = true
        barPaint!!.strokeCap = Paint.Cap.ROUND
        thumbPaint = Paint()
        thumbPaint!!.isAntiAlias = true
        thumbPaint!!.strokeCap = Paint.Cap.ROUND
    }

    private fun initColors() {
        val colorCount = 12
        val colorAngleStep = 360 / colorCount
        colors = IntArray(colorCount + 1)
        val hsv = floatArrayOf(0f, 1f, 1f)
        for (i in colors.indices) {
            hsv[0] = (360 - i * colorAngleStep % 360).toFloat()
            if (hsv[0].equals(360)) {
                hsv[0] = 359F
            }
            colors[i] = Color.HSVToColor(hsv)
        }
    }

    private fun initCustomAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorBarView)
        if (typedArray != null) {
            thumbDrawable = typedArray.getResourceId(R.styleable.ColorBarView_thumbDrawable, R.mipmap.color_icon_button)
            barHeight = typedArray.getDimension(R.styleable.ColorBarView_barHeight, 30f)
            thumbHeight = typedArray.getDimension(R.styleable.ColorBarView_thumbHeight, 80f)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    /**
     * 测量高
     *
     * @param heightMeasureSpec
     * @return
     */
    private fun measureHeight(heightMeasureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(heightMeasureSpec)
        val specSize = MeasureSpec.getSize(heightMeasureSpec)
        result = if (specMode == MeasureSpec.EXACTLY) {
            //精确值模式
            Math.max(Math.max(thumbHeight.toInt(), barHeight.toInt()), specSize)
        } else if (specMode == MeasureSpec.AT_MOST) {
            //最大值模式
            Math.max(thumbHeight.toInt(), barHeight.toInt() + paddingTop + paddingBottom)
        } else {
            specSize
        }
        return result
    }

    /**
     * 测量高
     *
     * @param widthMeasureSpec
     * @return
     */
    private fun measureWidth(widthMeasureSpec: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(widthMeasureSpec)
        val specSize = MeasureSpec.getSize(widthMeasureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            //精确值模式
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            //最大值模式
            result = 200
        }
        return result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        width = w.toFloat()
        height = h.toFloat()
        thumbWidth = thumbHeight * (thumbBitmap!!.width.toFloat() / thumbBitmap!!.height.toFloat())
        barWidth = width - thumbWidth
        barStartX = thumbWidth / 2 //不从0开始，左右边缘用于显示滑块
        barStartY = height / 2 - barHeight / 2
        super.onSizeChanged(w, h, oldw, oldh)
    }

    /**
     * 处理点击和滑动事件
     *
     * @param event
     * @return
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentThumbOffset = event.x
                if (currentThumbOffset <= thumbWidth / 2) {
                    currentThumbOffset = thumbWidth / 2 + 1
                }
                if (currentThumbOffset >= barWidth + thumbWidth / 2) {
                    currentThumbOffset = barWidth + thumbWidth / 2
                }
                STATUS = STATUS_SEEK
            }
            MotionEvent.ACTION_MOVE -> {
                currentThumbOffset = event.x
                if (currentThumbOffset <= thumbWidth / 2) {
                    currentThumbOffset = thumbWidth / 2 + 1
                }
                if (currentThumbOffset >= barWidth + thumbWidth / 2) {
                    currentThumbOffset = barWidth + thumbWidth / 2
                }
            }
        }
        changColor()
        if (onColorChangeListener != null) {
            onColorChangeListener!!.onColorChange(currentColor)
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        drawBar(canvas)
        drawThumb(canvas)
        super.onDraw(canvas)
    }

    /**
     * 滑动滑块使颜色发生变化
     */
    private fun changColor() {
        val position = currentThumbOffset - thumbWidth / 2.0f //当前滑块中心的长度
        val colorH = 360 - position / barWidth * 360
        currentColor = Color.HSVToColor(floatArrayOf(colorH, 1.0f, 1.0f))
    }

    /**
     * 获取当前颜色
     *
     * @return
     */
    private fun getCurrentColor(): Int {
        return currentColor
    }

    /**
     * 设置当前颜色
     *
     * @param currentColor
     */
    fun setCurrentColor(currentColor: Int) {
        this.currentColor = currentColor
        if (onColorChangeListener != null) {
            onColorChangeListener!!.onColorChange(currentColor)
        }
        invalidate()
    }

    /**
     * 绘制底部颜色条
     *
     * @param canvas
     */
    private fun drawBar(canvas: Canvas) {
        barPaint!!.shader = LinearGradient(barStartX, barStartY + barHeight / 2,
                barStartX + barWidth, barStartY + barHeight / 2,
                colors, null, Shader.TileMode.CLAMP)
        canvas.drawRoundRect(RectF(barStartX, barStartY, barStartX + barWidth,
                barStartY + barHeight), barHeight / 2, barHeight / 2, barPaint!!)
    }

    /**
     * 绘制滑块
     * @param canvas
     */
    private fun drawThumb(canvas: Canvas) {
        val currentColorHSV = FloatArray(3)
        Color.RGBToHSV(Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor), currentColorHSV)
        //根据HSV计算颜色所在位置
        val position = barWidth * currentColorHSV[0] / 360.0f
        currentThumbOffset = barWidth - position + thumbWidth / 2
        canvas.drawBitmap(thumbBitmap!!, null, thumbRect, thumbPaint)
    }

    /**
     * 获取滑块所在的矩形区域
     */
    private val thumbRect: RectF
        private get() = RectF(currentThumbOffset - thumbWidth / 2, height / 2 - thumbHeight / 2,
                currentThumbOffset + thumbWidth / 2, height / 2 + thumbHeight / 2)

    fun     setOnColorChangerListener(onColorChangerListener: OnColorChangeListener?) {
        onColorChangeListener = onColorChangerListener
    }

    companion object {
        private lateinit var colors: IntArray
        private var STATUS = 0
        private const val STATUS_INIT = 0

        //移动了action bar
        private const val STATUS_SEEK = 1
    }

    init {
        init(context)
        initCustomAttrs(context, attrs)
        initView()
    }
}