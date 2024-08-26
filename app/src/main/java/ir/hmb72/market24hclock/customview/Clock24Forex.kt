package ir.hmb72.market24hclock.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ir.hmb72.market24hclock.R
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

class Clock24Forex @JvmOverloads constructor(
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
    private var mMinuteHandSize: Int = 0
    private var arcRadius: Float = 0f
    private var arcSpaceRatio = 0.2f
    private var arcWidth: Float = 0f
    private var arcSpacing: Float = 0f
    private var arcTextPlace: Float = 0f
    private var sizeTextNumber: Float = 0f
    private var sizeText: Float = 0f
    private var arcActiveColor = ContextCompat.getColor(context, R.color.crayola)
    private var arcColor = ContextCompat.getColor(context, R.color.philippineSilverAlpha)
    private val typeFace = ResourcesCompat.getFont(context, R.font.iransans_bold)


    private fun init() {
        if (!mIsInit) { // Check if initialization is already done
            mHeight = height
            mWidth = width

            mCentreX = mWidth / 2
            mCentreY = mHeight / 2
            mMinimum = minOf(mHeight, mWidth)

            mPadding = 100
            mRadius = (mMinimum * 0.7).toInt() - mPadding
            mAngle = Math.PI / 30 - Math.PI / 2
            mPaint = Paint()
            mPath = Path()
            arcRadius = (mRadius / 12).toFloat()
            arcWidth = (mMinimum * 0.030).toFloat()
            arcSpacing = (mMinimum * 0.010).toFloat()
            sizeTextNumber = (mRadius / 100).toFloat() * 4
            sizeText = arcRadius / 3
            mHourHandSize = mRadius / 3
            mMinuteHandSize = mRadius / 2
            mNumbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
            mIsInit = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!mIsInit) {
            init()
        }

        loadDraw(canvas)
        drawHands(canvas)
    }

    private fun loadDraw(canvas: Canvas) {
        circleBackground(arcRadius + ((arcWidth + arcSpacing) * 10.5f), canvas)

        //Draw Arcs
        drawMyArc(arcRadius, 67.5f, 127.5f, canvas, arcWidth, isActiveArc(getHourAngle(), 67.5f, 127.5f)) //London
        drawMyArc(arcRadius + arcWidth + arcSpacing, 67.5f, 127.5f, canvas, arcWidth, isActiveArc(getHourAngle(), 67.5f, 127.5f)) //Zurich
        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 2), 67.5f, 127.5f, canvas, arcWidth, isActiveArc(getHourAngle(), 67.5f, 127.5f)) //Frankfurt

        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 3), 62f, 137f, canvas, arcWidth, isActiveArc(getHourAngle(), 62f, 137f))   //Moscow
        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 3), 322.5f, 90f, canvas, arcWidth, isActiveArc(getHourAngle(), 322f, 90f)) // Tokyo

        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 4), 345f, 81.5f, canvas, arcWidth, isActiveArc(getHourAngle(), 345f, 81.5f)) // Shanghai
        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 4), 68.5f, 120f, canvas, arcWidth, isActiveArc(getHourAngle(), 68.5f, 120f)) // Johannesburg

        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 5), 345f, 97.5f, canvas, arcWidth, isActiveArc(getHourAngle(), 345f, 97.5f)) // Hong kong

        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 6), 337.5f, 120f, canvas, arcWidth, isActiveArc(getHourAngle(), 337.5f, 120f)) // Singapore
        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 6), 165f, 97.5f, canvas, arcWidth, isActiveArc(getHourAngle(), 165f, 97.5f)) // New york

        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 7), 18f, 93f, canvas, arcWidth, isActiveArc(getHourAngle(), 18f, 93f)) // MUMBAI
        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 7), 165f, 97.5f, canvas, arcWidth, isActiveArc(getHourAngle(), 165f, 97.5f)) // TORONTO

        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 8), 322.5f, 90f, canvas, arcWidth, isActiveArc(getHourAngle(), 322.5f, 90f)) // SYDNEY
        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 8), 67.5f, 80f, canvas, arcWidth, isActiveArc(getHourAngle(), 67.5f, 80f)) // RIYADH
        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 8), 165f, 97.5f, canvas, arcWidth, isActiveArc(getHourAngle(), 165f, 97.5f)) // CHICAGO

        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 9), 297.5f, 101.25f, canvas, arcWidth, isActiveArc(getHourAngle(), 297.5f, 101.25f)) // WELLINGTON
        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 9), 52.5f, 71.25f, canvas, arcWidth, isActiveArc(getHourAngle(), 52.5f, 71.25f)) // DUBAI
        drawMyArc(arcRadius + ((arcWidth + arcSpacing) * 9), 157.5f, 105f, canvas, arcWidth, isActiveArc(getHourAngle(), 157.5f, 105f)) // SAO PAULO


        // Draw text on arc
        drawTextArc(arcRadius + 5f, 20f, 127.5f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.london))
        drawTextArc(arcRadius + arcWidth + arcSpacing + 5f, 30f, 127.5f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.zurich))
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 2) + 5f, 30f, 127.5f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.frankfurt))

        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 3) + 5f, 30f, 127.5f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.moscow))
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 3) + 5f, 225f, 90f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.tokyo))

        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 4) + 5f, 250f, 90f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.shanghai))
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 4) + 5f, 20f, 120f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.johannesburg))

        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 5) + 5f, 270f, 97f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.hong_kong))

        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 6) + 5f, 280f, 97f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.singapore))
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 6) - 5f, 160f, 97f, canvas, Color.BLACK, sizeText, false, context.getString(R.string.newyork))
//
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 7) + 5f, -70f, 97f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.mumbai))
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 7) - 5f, 165f, 97f, canvas, Color.BLACK, sizeText, false, context.getString(R.string.toronto))

        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 8) - 5f, 300f, 97f, canvas, Color.BLACK, sizeText, false, context.getString(R.string.sydney))
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 8) + 5f, 330f, 80f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.riyadh))
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 8) - 5f, 165f, 97f, canvas, Color.BLACK, sizeText, false, context.getString(R.string.chicago))

        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 9) - 5f, 300f, 97f, canvas, Color.BLACK, sizeText, false, context.getString(R.string.wellington))
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 9) + 5f, 315f, 97f, canvas, Color.BLACK, sizeText, true, context.getString(R.string.dubai))
        drawTextArc(arcRadius + ((arcWidth + arcSpacing) * 9) - 5f, 165f, 97f, canvas, Color.BLACK, sizeText, false, context.getString(R.string.sao_paulo))


        circleCentral(canvas)
        outCircle(arcRadius + ((arcWidth + arcSpacing) * 10.5f), arcWidth, sizeTextNumber, canvas)

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
        mMinute = calendar.get(Calendar.MINUTE).toFloat()
        mSecond = calendar.get(Calendar.SECOND).toFloat()

        drawHourHand(canvas, mHour, mMinute)
        drawMinuteHand(canvas, mMinute)
//        drawSecondsHand(canvas, mSecond)
    }

    private fun drawHand(canvas: Canvas, angle: Double, handSize: Float, color: Int) {
        mPaint.reset()
        setPaintAttributes(color, Paint.Style.FILL, 8f)


        // محاسبه مختصات انتهای عقربه
        val handEndX = mCentreX + cos(angle) * handSize
        val handEndY = mCentreY + sin(angle) * handSize

        // مختصات قاعده مثلث
        val baseLeftX = mCentreX + cos(angle + Math.PI / 24) * 50
        val baseLeftY = mCentreY + sin(angle + Math.PI / 24) * 50
        val baseRightX = mCentreX + cos(angle - Math.PI / 24) * 50
        val baseRightY = mCentreY + sin(angle - Math.PI / 24) * 50


        mPath.reset()
        mPath.moveTo(baseLeftX.toFloat(), baseLeftY.toFloat())
        mPath.lineTo(handEndX.toFloat(), handEndY.toFloat())
        mPath.lineTo(baseRightX.toFloat(), baseRightY.toFloat())
        mPath.close()

        canvas.drawPath(mPath, mPaint)
    }

    private fun drawMinuteHand(canvas: Canvas, minutes: Float) {
        val angle = Math.PI * minutes / 30 - Math.PI / 2
        drawHand(canvas, angle, mMinuteHandSize.toFloat(), Color.BLACK)
    }

    private fun drawHourHand(canvas: Canvas, hour: Float, minutes: Float) {
        val angle = Math.PI * (hour + minutes / 60) / 12 - Math.PI / 2
        drawHand(canvas, angle, mHourHandSize.toFloat(), Color.BLACK)
    }

    private fun drawSecondsHand(canvas: Canvas, location: Float) {
//        drawHand(canvas, location, mHandSize.toFloat(), Color.RED)
    }



    private fun drawTextArc(
        radius: Float,
        startAngle: Float,
        sweepAngle: Float,
        canvas: Canvas,
        textColor: Int,
        sizeText: Float,
        revers: Boolean,
        text: String,
    ) {
        val centerX = width / 2
        val centerY = height / 2

        //Inner oval
        val oval = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        val path = if (!revers) {
            Path().apply { addArc(oval, startAngle, sweepAngle) }
        } else {
            Path().apply { addArc(oval, startAngle + 180f, -sweepAngle) }
        }

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = textColor
            textSize = sizeText

        }
        //محاسبه موقعیت متن
        val pathMeasure = PathMeasure(path, false)
        val textWidth = textPaint.measureText(text)
        val hOffset = (pathMeasure.length - textWidth) / 2
        //Draw Text
        canvas.drawTextOnPath(text, path, hOffset, 0f, textPaint)


    }

    private fun drawMyArc(
        radius: Float,
        startAngle: Float,
        sweepAngle: Float,
        canvas: Canvas,
        strokeWide: Float,
        isActive: Boolean,
    ) {
        val centerX = width / 2f
        val centerY = height / 2f
        //Draw Arc :
        val oval = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = strokeWide
        mPaint.color = if (isActive) arcActiveColor else arcColor
        mPaint.typeface = typeFace
        canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint)


    }

    private fun outCircle(radius: Float, arcWidth: Float, sizeText: Float, canvas: Canvas) {

        //Draw arc
        val centerX = width / 2f
        val centerY = height / 2f

        //Draw Arc :
        val oval = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = arcWidth * 1.5f
        mPaint.color = Color.BLACK
        mPaint.typeface = typeFace
        canvas.drawArc(oval, 0f, 360f, false, mPaint)

        //draw text numbers

        // هر عدد 15 درجه جابجا می‌شود (360 درجه / 24 عدد = 15 درجه)
        for (i in 1..24) {
            val angle = (i * 15f) + 270f
            drawTextArc(radius - 10f, angle, 2f, canvas, Color.WHITE, sizeText, false, i.toString())
            //کشیدن خط برای هر عدد
            /*     val endX = centerX + radius * cos(angle.toDouble()) // ??
                   val endY = centerY + radius * sin(angle.toDouble()) // ??

                   paint.strokeWidth = 2f
                   canvas.drawLine(centerX, centerY, endX.toFloat(), endY.toFloat() , paint)
            */
        }


    }

    private fun circleCentral(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = 15
        //Draw Arc :
        val oval = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        mPaint.style = Paint.Style.FILL
//        paint.strokeWidth = strokeWide
        mPaint.color = Color.LTGRAY

        canvas.drawArc(oval, 0f, 360f, false, mPaint)
    }

    private fun circleBackground(radius: Float, canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f

        //Draw Arc :
        val oval = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.WHITE

        canvas.drawArc(oval, 0f, 360f, false, mPaint)
    }


    private fun isActiveArc(hourAngle: Float, startAngle: Float, sweepAngle: Float): Boolean {
        val endAngle = startAngle + sweepAngle


        return (hourAngle in startAngle..endAngle) || (hourAngle + 360f in startAngle..endAngle)

    }


    private fun getHourAngle(): Float {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)



        val hourAngle = ((hour * 15)).toFloat()
        val minuteAngle = if (minute <= 56) (minute * 0.25).toFloat() else 0f

        val totalAngle = ((hourAngle + minuteAngle) - 90f)

        return totalAngle


    }


}