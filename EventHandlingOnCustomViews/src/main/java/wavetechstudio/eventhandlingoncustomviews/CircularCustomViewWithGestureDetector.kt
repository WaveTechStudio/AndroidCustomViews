package wavetechstudio.eventhandlingoncustomviews

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller

class CircularCustomViewWithGestureDetector(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val DEFAULT_FG_COLOR = -0x10000
    private val PRESSED_FG_COLOR = -0xffff01
    private val DEFAULT_BG_COLOR = -0x5f5f60
    private var backgroundPaint: Paint
    private var foregroundPaint: Paint
    private var selectedAngle: Int = 0
    private var clipPath: Path? = null
    private var pressedState: Boolean = false
    private var circleSize: Int = 0

    private val FLING_SCALE = 200
    private var gestureListener: GestureDetector
    private var angleScroller: Scroller
    private val angleAnimator: ValueAnimator? = null

    init {
        backgroundPaint = Paint()
        backgroundPaint.setColor(DEFAULT_BG_COLOR)
        backgroundPaint.setStyle(Paint.Style.FILL)
        foregroundPaint = Paint()
        foregroundPaint.setColor(DEFAULT_FG_COLOR)
        foregroundPaint.setStyle(Paint.Style.FILL)
        selectedAngle = 280
        pressedState = false

        selectedAngle = 280
        pressedState = false

        angleScroller = Scroller(context, null, true)
        angleScroller.setFinalX(selectedAngle)


//        angleAnimator = ValueAnimator.ofFloat(0, 360);
//        angleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                if(!angleScroller.isFinished()) {
//                    angleScroller.computeScrollOffset();
//                    selectedAngle = angleScroller.getCurrX();
//                    postInvalidate();
//                } else {
//                    angleAnimator.cancel();
//                    selectedAngle = angleScroller.getCurrX();
//                    postInvalidate();
//                }
//            }
//        });
        gestureListener = GestureDetector(context, object : GestureDetector.OnGestureListener {
            var processed: Boolean = false

            override fun onDown(event: MotionEvent): Boolean {
                processed = computeAndSetAngle(event.x, event.y)
                if (processed) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    changePressedState(true)
                    postInvalidate()
                }
                return processed
            }

            override fun onShowPress(e: MotionEvent) {
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                endGesture()
                return false
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                computeAndSetAngle(e2.x, e2.y)
                postInvalidate()
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                endGesture()
            }

            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                return false
            }
        })
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

    private fun endGesture() {
        parent.requestDisallowInterceptTouchEvent(false)
        changePressedState(false)
        postInvalidate()
    }

    private fun changePressedState(pressed: Boolean) {
        this.pressedState = pressed
    }

    private fun computeAndSetAngle(x1: Float, y1: Float): Boolean {
        var x = x1
        var y = y1
        x -= (width / 2).toFloat()
        y -= (height / 2).toFloat()

        val radius = Math.sqrt((x * x + y * y).toDouble())
        if (radius > circleSize / 2) return false

        var angle = (180.0 * Math.atan2(y.toDouble(), x.toDouble()) / Math.PI).toInt() + 90
        angle = if (angle > 0) angle else 360 + angle

        if (angleScroller.computeScrollOffset()) {
            angleScroller.forceFinished(true)
        }

        angleScroller.startScroll(angleScroller.getCurrX(), 0, angle - angleScroller.getCurrX(), 0)
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureListener.onTouchEvent(event)
    }
}