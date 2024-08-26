package ir.hmb72.market24hclock.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import ir.hmb72.market24hclock.R


class BackgroundClock @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : View(context, attrs, defStyleAttr) {

        private val paint = Paint()
        private val wideSizeArc = 10f
        private val sizeChange = 40
        private val typeFace = ResourcesCompat.getFont(context, R.font.iransans_bold)


        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)


            // رسم قوس ها
            drawMyArc(sizeChange + 20f, 67.5f, 127.5f, canvas, Color.LTGRAY, wideSizeArc) //London
            drawMyArc(sizeChange + 35f, 67.5f, 127.5f, canvas, Color.LTGRAY, wideSizeArc) //Zurich
            drawMyArc(sizeChange + 50f, 67.5f, 127.5f, canvas, Color.LTGRAY, wideSizeArc) //Frankfurt

            drawMyArc(sizeChange + 65f, 62f, 137f, canvas, Color.LTGRAY, wideSizeArc)   //Moscow
            drawMyArc(sizeChange + 65f, 322.5f, 90f, canvas, Color.LTGRAY, wideSizeArc) // Tokyo

            drawMyArc(sizeChange + 80f, 345f, 81.5f, canvas, Color.LTGRAY, wideSizeArc) // Shanghai
            drawMyArc(sizeChange + 80f, 68.5f, 120f, canvas, Color.LTGRAY, wideSizeArc) // Johannesburg

            drawMyArc(sizeChange + 95f, 345f, 97.5f, canvas, Color.LTGRAY, wideSizeArc) // Hong kong

            drawMyArc(sizeChange + 110f, 337.5f, 120f, canvas, Color.LTGRAY, wideSizeArc) // Singapore
            drawMyArc(sizeChange + 110f, 165f, 97.5f, canvas, Color.LTGRAY, wideSizeArc) // New york

            drawMyArc(sizeChange + 125f, 18f, 93f, canvas, Color.LTGRAY, wideSizeArc) // MUMBAI
            drawMyArc(sizeChange + 125f, 165f, 97.5f, canvas, Color.LTGRAY, wideSizeArc) // TORONTO

            drawMyArc(sizeChange + 140f, 322.5f, 90f, canvas, Color.LTGRAY, wideSizeArc) // SYDNEY
            drawMyArc(sizeChange + 140f, 67.5f, 80f, canvas, Color.LTGRAY, wideSizeArc) // RIYADH
            drawMyArc(sizeChange + 140f, 165f, 97.5f, canvas, Color.LTGRAY, wideSizeArc) // CHICAGO

            drawMyArc(sizeChange + 155f, 297.5f, 101.25f, canvas, Color.LTGRAY, wideSizeArc) // WELLINGTON
            drawMyArc(sizeChange + 155f, 52.5f, 71.25f, canvas, Color.LTGRAY, wideSizeArc) // DUBAI
            drawMyArc(sizeChange + 155f, 157.5f, 105f, canvas, Color.LTGRAY, wideSizeArc) // SAO PAULO

            drawCircleClock(canvas)


            // رسم متن روی قوس
            drawTextArc(sizeChange.toFloat() + 2.5f + 20f, 20f, 127.5f, canvas, Color.BLACK, 8f, true, "L S E - L O N D O N")
            drawTextArc(sizeChange.toFloat() + 2.5f + 35f, 30f, 127.5f, canvas, Color.BLACK, 8f, true, "S I X - Z U R I C H ")
            drawTextArc(sizeChange.toFloat() + 2.5f + 50f, 30f, 127.5f, canvas, Color.BLACK, 8f, true, "F W B - F R A N K F U R T ")
            drawTextArc(sizeChange.toFloat() + 2.5f + 65f, 30f, 127.5f, canvas, Color.BLACK, 8f, true, "M O E X - M O S C O W ")
            drawTextArc(sizeChange.toFloat() + 2.5f + 65f, 225f, 90f, canvas, Color.BLACK, 8f, true, "J P X - T O K Y O ")
            drawTextArc(sizeChange.toFloat() + 2.5f + 80f, 250f, 90f, canvas, Color.BLACK, 8f, true, "S S E - S H A N G H A I ")
            drawTextArc(sizeChange.toFloat() + 2.5f + 80f, 20f, 120f, canvas, Color.BLACK, 8f, true, "J S E - J O H A N N E S B U R G ")
            drawTextArc(sizeChange.toFloat() + 2.5f + 95f, 270f, 97f, canvas, Color.BLACK, 8f, true, "H K E X - H O N G  K O N G ")
            drawTextArc(sizeChange.toFloat() + 2.5f + 110f, 280f, 97f, canvas, Color.BLACK, 8f, true, "S G X - S I N G A P O R E")
            drawTextArc(sizeChange.toFloat() + (-3f) + 110f, 160f, 97f, canvas, Color.BLACK, 8f, false, "N Y S E - N A S D A Q - N E W  Y O R K")
            drawTextArc(sizeChange.toFloat() + (2.5f) + 125f, -70f, 97f, canvas, Color.BLACK, 8f, true, "S S E - M U M B A I")
            drawTextArc(sizeChange.toFloat() + (-3f) + 125f, 165f, 97f, canvas, Color.BLACK, 8f, false, "T S E - T O R O N T O")
            drawTextArc(sizeChange.toFloat() + (-3f) + 140f, 300f, 97f, canvas, Color.BLACK, 8f, false, "A S X - S Y D N E Y")
            drawTextArc(sizeChange.toFloat() + (2.5f) + 140f, 330f, 80f, canvas, Color.BLACK, 8f, true, "T A D A W U L - R I Y A D H")
            drawTextArc(sizeChange.toFloat() + (-3f) + 140f, 165f, 97f, canvas, Color.BLACK, 8f, false, "N Y S E - C H I C A G O")
            drawTextArc(sizeChange.toFloat() + (-3f) + 155f, 300f, 97f, canvas, Color.BLACK, 8f, false, "N Z X - W E L L I N G T O N")
            drawTextArc(sizeChange.toFloat() + (2.5f) + 155f, 315f, 97f, canvas, Color.BLACK, 8f, true, "D F M - D U B A I")
            drawTextArc(sizeChange.toFloat() + (-3f) + 155f, 165f, 97f, canvas, Color.BLACK, 8f, false, "B 3 - B O V E S P A - S A O  P A U L O")

            //Draw circle
            drawCircle(canvas)


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
            arcColor: Int,
            strokeWide: Float,
        ) {
            val centerX = width / 2f
            val centerY = height / 2f
            //Draw Arc :
            val oval = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWide
            paint.color = arcColor
            paint.typeface = typeFace
            canvas.drawArc(oval, startAngle, sweepAngle, false, paint)


        }

        private fun drawCircleClock(canvas: Canvas) {

            //Draw arc
            drawMyArc(220f, 0f, 360f, canvas, Color.BLACK, 20f)

            //draw text numbers
            val radius = 215f
            // هر عدد 15 درجه جابجا می‌شود (360 درجه / 24 عدد = 15 درجه)
            for (i in 1..24) {
                val angle = (i * 15f) + 270f
                drawTextArc(radius, angle, 2f, canvas, Color.WHITE, 12f, false, i.toString())
                //کشیدن خط برای هر عدد
                /*     val endX = centerX + radius * cos(angle.toDouble()) // ??
                       val endY = centerY + radius * sin(angle.toDouble()) // ??

                       paint.strokeWidth = 2f
                       canvas.drawLine(centerX, centerY, endX.toFloat(), endY.toFloat() , paint)
                */
            }


        }

        private fun drawCircle(canvas: Canvas){
            val centerX = width / 2f
            val centerY = height / 2f
            val radius = 20
            //Draw Arc :
            val oval = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
            paint.style = Paint.Style.FILL
    //        paint.strokeWidth = strokeWide
            paint.color = Color.LTGRAY

            canvas.drawArc(oval, 0f, 360f, false, paint)
        }


    }
