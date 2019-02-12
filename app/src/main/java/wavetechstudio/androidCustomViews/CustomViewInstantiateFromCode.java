package wavetechstudio.androidCustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewInstantiateFromCode extends View {

    private Paint paint;
    private static final int DEFAULT_SIZE = 1000;
    private static final int DEFAULT_FILL_COLOR = 0xffff0000;


    public CustomViewInstantiateFromCode(Context context) {
        super(context);
        init(DEFAULT_FILL_COLOR);
    }

    public CustomViewInstantiateFromCode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        int fillColor;

        TypedArray ta = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomViewMeasurement, 0, 0);
        try {
            fillColor = ta.getColor(R.styleable.CustomViewMeasurement_fillcolor, DEFAULT_FILL_COLOR);
        } finally {
            ta.recycle();
        }

        init(fillColor);
    }

    private void init(int fillColor) {
        paint = new Paint();
        paint.setColor(fillColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrikeThruText(true);
    }

    public void setFillColor(int fillColor) {
        paint.setColor(fillColor);
    }

    /**
     * @param measureSpec width / height measure specs
     * @return returning appropriate size
     */
    private static int getMeasurementSize(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return size;

            case MeasureSpec.AT_MOST:
                return Math.min(DEFAULT_SIZE, size);

            case MeasureSpec.UNSPECIFIED:
            default:
                return DEFAULT_SIZE;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getMeasurementSize(widthMeasureSpec);
        int height = getMeasurementSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        super.onDraw(canvas);
    }
}
