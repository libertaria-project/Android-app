package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.stocksexchange.android.ui.utils.extensions.spToPx

/**
 * A wrapper around [ImageView] with marking functionality.
 */
class MarkableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {


    companion object {

        private const val DEFAULT_TEXT_SIZE = 18f
        private const val DEFAULT_TEXT_COLOR = Color.WHITE

    }


    private var mOuterBackgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mInnerBackgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mTextPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mOuterBackgroundBounds: RectF = RectF()

    private var mInnerBackgroundBounds: RectF = RectF()

    private var mTextBounds: Rect = Rect()

    private var mViewSize: IntArray = IntArray(2)

    /**
     * A boolean flag indicating whether to draw backgrounds on this
     * image view or not.
     */
    var shouldDrawBackground: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    /**
     * A type of shape to draw on this image view.
     */
    var backgroundShape: Shape = Shape.RECTANGULARISH
        set(value) {
            field = value
            invalidate()
        }

    /**
     * A mark (letter) to draw on this image view.
     */
    var mark: String = ""
        set(value) {
            field = value
            invalidate()
        }




    init {
        mTextPaint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        mTextPaint.textSize = spToPx(DEFAULT_TEXT_SIZE)
        mTextPaint.color = DEFAULT_TEXT_COLOR
        mTextPaint.textAlign = Paint.Align.CENTER
    }


    /**
     * Sets a color of the outer background of this image view.
     *
     * @param color The color to set
     */
    fun setOuterBackgroundColor(@ColorInt color: Int) {
        mOuterBackgroundPaint.color = color
        invalidate()
    }


    /**
     * Sets a color of the inner background of this image view.
     */
    fun setInnerBackgroundColor(@ColorInt color: Int) {
        mInnerBackgroundPaint.color = color
        invalidate()
    }


    /**
     * Sets a text size of the text paint.
     *
     * @param textSize The text size to set (in pixels).
     */
    fun setTextSize(textSize: Int) {
        mTextPaint.textSize = textSize.toFloat()
        invalidate()
    }


    /**
     * Gets the text size of the text paint.
     *
     * @return The text size (in pixels)
     */
    fun getTextSize(): Int {
        return mTextPaint.textSize.toInt()
    }


    /**
     * Sets a text color of the text paint.
     *
     * @param textColor The text color to set
     */
    fun setTextColor(@ColorInt textColor: Int) {
        mTextPaint.color = textColor
        invalidate()
    }


    /**
     * Gets the text color of the text paint.
     *
     * @return The text color
     */
    fun getTextColor(): Int {
        return mTextPaint.color
    }


    /**
     * Checks whether this image is marked.
     *
     * @return true if marked; false otherwise
     */
    fun isMarked(): Boolean {
        return mark.isNotBlank()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mViewSize[0] = measuredWidth
        mViewSize[1] = measuredHeight

        mOuterBackgroundBounds.set(
            0f,
            0f,
            mViewSize[0].toFloat(),
            mViewSize[1].toFloat()
        )

        mInnerBackgroundBounds.set(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            (mViewSize[0] - paddingRight).toFloat(),
            (mViewSize[1] - paddingBottom).toFloat()
        )
    }


    override fun onDraw(canvas: Canvas) {
        if(shouldDrawBackground) {
            if(backgroundShape == Shape.RECTANGULARISH) {
                canvas.drawRect(mOuterBackgroundBounds, mOuterBackgroundPaint)
                canvas.drawRect(mInnerBackgroundBounds, mInnerBackgroundPaint)
            } else if(backgroundShape == Shape.OVALISH) {
                canvas.drawOval(mOuterBackgroundBounds, mOuterBackgroundPaint)
                canvas.drawOval(mInnerBackgroundBounds, mInnerBackgroundPaint)
            }
        }

        super.onDraw(canvas)

        if(isMarked()) {
            mTextPaint.getTextBounds(mark, 0, mark.length, mTextBounds)

            canvas.drawText(
                mark,
                (mViewSize[0] / 2).toFloat(),
                ((mViewSize[1] + mTextBounds.height()) / 2).toFloat(),
                mTextPaint
            )
        }
    }


    enum class Shape {

        RECTANGULARISH,
        OVALISH

    }


}