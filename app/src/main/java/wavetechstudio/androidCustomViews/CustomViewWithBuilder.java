package wavetechstudio.androidCustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewWithBuilder extends View {

    private static final int DEFAULT_SIZE = 1000;
    private static final int DEFAULT_FILL_COLOR = 0xffff0000;
    private static final int PREVIEW_RED = 0xffff0000;
    private static final int PREVIEW_BLUE = 0xff0000ff;
    private static final int PREVIEW_GREEN = 0xff00ff00;
    private static final int PREVIEW_BLACK = 0xff000000;
    private Paint paint;
    private boolean firstDraw;
    private int[] colorArray;


    private CustomViewWithBuilder(Builder builder) {
        super(builder.context);

        init(builder.topLeftColor,
                builder.topRightColor,
                builder.bottomLeftColor,
                builder.bottomRightColor);
    }


    public CustomViewWithBuilder(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            init(PREVIEW_RED,
                    PREVIEW_BLUE,
                    PREVIEW_GREEN,
                    PREVIEW_BLACK);
        } else {
            throw new IllegalStateException("Not supposed to be parametrized from XML in this example");
        }
    }


    private void init(int topLeftColor, int topRightColor, int bottomLeftColor, int bottomRightColor) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        colorArray = new int[]{
                topLeftColor,
                topRightColor,
                bottomRightColor,
                bottomLeftColor
        };

        firstDraw = true;
    }


    public static class Builder {
        private Context context;
        private int topLeftColor = DEFAULT_FILL_COLOR;
        private int topRightColor = DEFAULT_FILL_COLOR;
        private int bottomLeftColor = DEFAULT_FILL_COLOR;
        private int bottomRightColor = DEFAULT_FILL_COLOR;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder topLeftColor(int topLeftColor) {
            this.topLeftColor = topLeftColor;
            return this;
        }

        public Builder topRightColor(int topRightColor) {
            this.topRightColor = topRightColor;
            return this;
        }

        public Builder bottomLeftColor(int bottomLeftColor) {
            this.bottomLeftColor = bottomLeftColor;
            return this;
        }

        public Builder bottomRightColor(int bottomRightColor) {
            this.bottomRightColor = bottomRightColor;
            return this;
        }

        public CustomViewWithBuilder build() {
            return new CustomViewWithBuilder(this);
        }
    }


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
        if (firstDraw) {
            LinearGradient lg = new LinearGradient(0, 0, getWidth(), getHeight(),
                    colorArray, null, Shader.TileMode.CLAMP);

            paint.setShader(lg);
            firstDraw = false;
        }

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        super.onDraw(canvas);
    }
}