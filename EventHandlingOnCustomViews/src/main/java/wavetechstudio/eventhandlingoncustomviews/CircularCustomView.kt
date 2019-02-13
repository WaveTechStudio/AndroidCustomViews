package wavetechstudio.eventhandlingoncustomviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class CircularCustomView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val DEFAULT_FG_COLOR = -0x10000
    private val PRESSED_FG_COLOR = -0xffff01
    private val DEFAULT_BG_COLOR = -0x5f5f60
    private var backgroundPaint: Paint
    private var foregroundPaint: Paint
    private var selectedAngle: Int = 0
    private var clipPath: Path? = null
    private var pressedState: Boolean = false
    private var circleSize: Int = 0

    init {
        backgroundPaint = Paint()
        backgroundPaint.setColor(DEFAULT_BG_COLOR)
        backgroundPaint.setStyle(Paint.Style.FILL)
        foregroundPaint = Paint()
        foregroundPaint.setColor(DEFAULT_FG_COLOR)
        foregroundPaint.setStyle(Paint.Style.FILL)
        selectedAngle = 280
        pressedState = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (pressedState) {
            foregroundPaint.setColor(PRESSED_FG_COLOR)
        } else {
            foregroundPaint.setColor(DEFAULT_FG_COLOR)
        }

        circleSize = width
        if (height < circleSize) circleSize = height

        val horMargin = (width - circleSize) / 2.toFloat()
        val verMargin = (height - circleSize) / 2.toFloat()

        // create a clipPath the first time
        if (clipPath == null) {
            val clipWidth: Float = (circleSize * 0.75).toFloat()

            val clipX = (width - clipWidth) / 2
            val clipY = (height - clipWidth) / 2
            clipPath = Path()
            clipPath!!.addArc(
                    clipX,
                    clipY,
                    clipX + clipWidth,
                    clipY + clipWidth,
                    0.toFloat(), (360).toFloat())
        }

        canvas.clipRect(0, 0, width, height)
        canvas.clipPath(clipPath, Region.Op.DIFFERENCE)

        canvas.save()
        canvas.rotate((-90).toFloat(), (width / 2).toFloat(), (height / 2).toFloat())

        canvas.drawArc(horMargin, verMargin,
                horMargin + circleSize, verMargin + circleSize, 0.toFloat(), 360.toFloat(), true, backgroundPaint)

        canvas.drawArc(
                horMargin,
                verMargin,
                horMargin + circleSize,
                verMargin + circleSize,
                0.toFloat(), selectedAngle.toFloat(), true, foregroundPaint)

        canvas.restore()
    }

    private fun changePressedState(pressed: Boolean) {
        this.pressedState = pressed
        invalidate()
        postInvalidate()
    }

    private fun computeAndSetAngle(x1: Float, y1: Float): Boolean {
        var x = x1
        var y = y1
        x -= (width / 2).toFloat()
        y -= (height / 2).toFloat()

        val radius = Math.sqrt((x * x + y * y).toDouble())
        if (radius > circleSize / 2) return false

        val angle = (180.0 * Math.atan2(y.toDouble(), x.toDouble()) / Math.PI).toInt() + 90
        selectedAngle = if (angle > 0) angle else 360 + angle
        return true
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val processed: Boolean
        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                processed = computeAndSetAngle(event.x, event.y)
                if (processed) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    changePressedState(true)
                }
                return processed
            }

            MotionEvent.ACTION_UP -> {
                parent.requestDisallowInterceptTouchEvent(false)
                changePressedState(false)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                processed = computeAndSetAngle(event.x, event.y)
                invalidate()
                return processed
            }
            else -> return false
        }
    }

}