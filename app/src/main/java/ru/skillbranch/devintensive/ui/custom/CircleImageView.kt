package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import ru.skillbranch.devintensive.R
import kotlin.math.roundToInt


class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ImageView(context, attrs, defStyleAttr) {

    private var borderColor = Color.WHITE
    private var borderWidth = 2
    private var mInitialized = true

    fun getBorderWidth() = borderWidth

    fun setBorderWidth(@Dimension dp:Int) {
        borderWidth = dp
    }

    fun getBorderColor() = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = colorId
    }

    init {

        if (attrs != null) {
            val a = context.obtainStyledAttributes (attrs, R.styleable.CircleImageView, 0, 0);

            borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, Color.WHITE)
            borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, 2)

            a.recycle()
        }
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

//    private fun setupBitmap() {
//        if (!mInitialized) {
//            return
//        }
//        mBitmap = getBitmapFromDrawable(drawable)
//        if (mBitmap == null) {
//            return
//        }
//        mBitmapShader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
//        mBitmapPaint.shader = mBitmapShader
//    }

    override fun onDraw(canvas: Canvas) {
        drawRoundImage(canvas)
        drawStroke(canvas)
    }

    private fun drawStroke(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val radius = width / 2f

        /* Border paint */
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth.toFloat()
        canvas.drawCircle(width / 2f, width / 2f, radius - borderWidth / 2f, paint)
    }

    private fun drawRoundImage(canvas: Canvas) {
        var b: Bitmap? = getBitmapFromDrawable(drawable)
        val bitmap = b?.copy(Bitmap.Config.ARGB_8888, true)

        /* Scale the bitmap */
        val scaledBitmap: Bitmap
        val ratio: Float = bitmap!!.width.toFloat() / bitmap.height.toFloat()
        val height: Int = (width / ratio).roundToInt()
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)

        /* Cutting the outer of the circle */
        val shader: Shader
        shader = BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val rect = RectF()
        rect.set(0f, 0f, width.toFloat(), height.toFloat())

        val imagePaint = Paint()
        imagePaint.isAntiAlias = true
        imagePaint.shader = shader
        canvas.drawRoundRect(rect, width.toFloat(), height.toFloat(), imagePaint)
    }
}