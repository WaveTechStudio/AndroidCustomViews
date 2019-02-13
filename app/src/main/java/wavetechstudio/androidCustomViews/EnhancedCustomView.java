package wavetechstudio.androidCustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class EnhancedCustomView extends View {
    private static final int DEFAULT_SIZE = 1000;
    private static final int DEFAULT_FILL_COLOR = 0xffff0000;
    private Paint backgroundPaint;

    public EnhancedCustomView(Context context) {
        this(context, null);
    }

    public EnhancedCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(DEFAULT_FILL_COLOR);
    }

    private static int getMeasurementSize(int measureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return size;

            case MeasureSpec.AT_MOST:
                return Math.min(defaultSize, size);

            case MeasureSpec.UNSPECIFIED:
            default:
                return defaultSize;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getMeasurementSize(widthMeasureSpec, DEFAULT_SIZE);
        int height = getMeasurementSize(heightMeasureSpec, DEFAULT_SIZE);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int leftX = getPaddingLeft();
        int rightX = getWidth() - getPaddingLeft() - getPaddingRight();

        int topY = getPaddingTop();
        int bottomY = getHeight() - getPaddingTop() - getPaddingBottom();

        canvas.drawRect(leftX, topY, rightX, bottomY, backgroundPaint);
        super.onDraw(canvas);
    }
}
