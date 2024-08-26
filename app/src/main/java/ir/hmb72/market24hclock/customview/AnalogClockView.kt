package ir.hmb72.market24hclock.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import java.util.Calendar

class AnalogClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private var mRadius: Int = 0
    private var mAngle: Double = 0.0
    private var mCentreX: Int = 0
    private var mCentreY: Int = 0
    private var mPadding: Int = 0
    private var mIsInit: Boolean = false
    private var mPaint = Paint()
    private var mPath = Path()
    private var mNumbers: IntArray? = null
    private var mMinimum: Int = 0
    private var mHour: Float = 0f
    private var mMinute: Float = 0f
    private var mSecond: Float = 0f
    private var mHourHandSize: Int = 0
    private var mHandSize: Int = 0

    private fun init() {
        if (!mIsInit) { // Check if initialization is already done
            mHeight = height
            mWidth = width
            mPadding = 50
            mCentreX = mWidth / 2
            mCentreY = mHeight / 2
            mMinimum = minOf(mHeight, mWidth)
            mRadius = mMinimum / 2 - mPadding
            mAngle = Math.PI / 30 - Math.PI / 2 // Can be converted to direct Kotlin calculation
            mPaint = Paint()
            mPath = Path()
            // Assuming mRect is not used, remove its initialization
            mHourHandSize = mRadius - mRadius / 2
            mHandSize = mRadius - mRadius / 4
            mNumbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
            mIsInit = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!mIsInit) {
            init()
        }

//        drawCircle(canvas)
        drawHands(canvas)
//        drawNumerals(canvas)

        // Invalidate to schedule redraw after 500 milliseconds
        postInvalidateDelayed(500)
    }


    private fun drawCircle(canvas: Canvas) {
        mPaint.reset() // Reset paint properties
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 8f) // Set attributes with float width
        canvas.drawCircle(mCentreX.toFloat(), mCentreY.toFloat(), mRadius.toFloat(), mPaint)
    }
    private fun setPaintAttributes(color: Int, stroke: Paint.Style, strokeWidth: Float) {
        mPaint.reset() // Reset paint properties
        mPaint.color = color // Set color using property setter
        mPaint.style = stroke // Set stroke style
        mPaint.strokeWidth = strokeWidth // Set stroke width with float value
        mPaint.isAntiAlias = true // Set anti-aliasing
    }
    private fun drawHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        mHour = calendar.get(Calendar.HOUR_OF_DAY).toFloat()
        // convert to 12-hour format from 24-hour format
        mHour = if (mHour > 12f) mHour - 12f else mHour
        mMinute = calendar.get(Calendar.MINUTE).toFloat()
        mSecond = calendar.get(Calendar.SECOND).toFloat()

        drawHourHand(canvas, (mHour + mMinute / 60f) * 5f)
        drawMinuteHand(canvas, mMinute)
        drawSecondsHand(canvas, mSecond)
    }
    private fun drawMinuteHand(canvas: Canvas, location: Float) {
        mPaint.reset()
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 8f)

        val angle = Math.PI * location / 30 - Math.PI / 2 // Can be converted to Kotlin math functions

        val handEndX = mCentreX + Math.cos(angle) * mHandSize.toFloat()
        val handEndY = mCentreY + Math.sin(angle) * mHandSize.toFloat()

        canvas.drawLine(mCentreX.toFloat(), mCentreY.toFloat(), handEndX.toFloat(), handEndY.toFloat(), mPaint)
    }
    private fun drawHand(canvas: Canvas, location: Float, handSize: Float, color: Int) {
        mPaint.reset()
        setPaintAttributes(color, Paint.Style.STROKE, 8f) // Consistent stroke width

        val angle = Math.PI * location / 30 - Math.PI / 2

        val handEndX = mCentreX + Math.cos(angle) * handSize.toFloat()
        val handEndY = mCentreY + Math.sin(angle) * handSize.toFloat()

        canvas.drawLine(mCentreX.toFloat(), mCentreY.toFloat(), handEndX.toFloat(), handEndY.toFloat(), mPaint)
    }
    private fun drawHourHand(canvas: Canvas, location: Float) {
        drawHand(canvas, location, mHourHandSize.toFloat(), Color.BLACK)
    }
    private fun drawSecondsHand(canvas: Canvas, location: Float) {
        drawHand(canvas, location, mHandSize.toFloat(), Color.RED)
    }
    private fun drawNumerals(canvas: Canvas) {
        mPaint.textSize = 15.toFloat() // Text size needs to be float

        for (number in mNumbers!!) { // Use safe access operator (!!) assuming mNumbers is not null
            val numString = number.toString() // Convert number to String
            val textBounds = Rect()
            mPaint.getTextBounds(numString, 0, numString.length, textBounds) // Use textBounds directly

            val angle = Math.PI / 6 * (number - 3)
            val x = (mCentreX + Math.cos(angle) * mRadius - textBounds.width() / 2).toInt()
            val y = (mCentreY + Math.sin(angle) * mRadius + textBounds.height() / 2).toInt()

            canvas.drawText(numString, x.toFloat(), y.toFloat(), mPaint)
        }
    }








}
