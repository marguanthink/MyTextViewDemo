package guan.com.mytextviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

/**
 * Created by 关 on 2016/5/24.
 */

public class ButtonView extends Button {
    public ButtonView(Context context) {
        super(context);
    }

    public ButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        Log.v("button","宽的模式"+widthMode);
        Log.v("button","高的模式"+heightMode);
        Log.v("button","宽的尺寸"+widthSize);
        Log.v("button","高的尺寸"+heightSize);

    }
}
