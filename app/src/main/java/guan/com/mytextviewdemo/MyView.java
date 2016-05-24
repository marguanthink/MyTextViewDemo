package guan.com.mytextviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by 关 on 2016/5/24.
 */

public class MyView extends View {
    /**
     * 需要绘制的文字
     */
    private String mText;
    private ArrayList<String> mTextList;
    /**
     * 文本的颜色
     */
    private int mTextColor;
    /**
     * 文本的大小
     */
    private float mTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextList = new ArrayList<String>();
        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyView, defStyleAttr, 0);
        mText = a.getString(R.styleable.MyView_mText);
        mTextColor = a.getColor(R.styleable.MyView_mTextColor, Color.BLACK);
        mTextSize = a.getDimension(R.styleable.MyView_mTextSize, 100);
        Log.v("MyView", "文本总长度:" + mText);
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //获得绘制文本的宽和高
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        //绘制文字
        for (int i = 0; i < mTextList.size(); i++) {
            mPaint.getTextBounds(mTextList.get(i), 0, mTextList.get(i).length(), mBound);
            Log.v("MyView", "mBound.h:" + mBound.height());
            Log.v("MyView", "在X:" + (getWidth() / 2 - mBound.width() / 2) + "  Y:" + (getPaddingTop() + (mBound.height() * i)) + "  绘制：" + mTextList.get(i));
            canvas.drawText(mTextList.get(i), (getWidth() / 2 - mBound.width() / 2), (getPaddingTop() + (mBound.height() * i)), mPaint);
        }
    }

    boolean isOneLines = true;
    float lineNum;
    float spLineNum;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        Log.v("MyView", "宽的模式:" + widthMode);
        Log.v("MyView", "高的模式:" + heightMode);
        Log.v("MyView", "宽的尺寸:" + widthSize);
        Log.v("MyView", "高的尺寸:" + heightSize);

        float textWidth = mBound.width();   //文本的宽度
        if (mTextList.size() == 0) {
            //将文本分段
            int padding = getPaddingLeft() + getPaddingRight();
            int specWidth = widthSize - padding; //能够显示文本的最大宽度
            if (textWidth < specWidth) {
                //说明一行足矣显示
                lineNum = 1;
                mTextList.add(mText);
            } else {
                //超过一行
                isOneLines = false;
                spLineNum = textWidth / specWidth;
                if ((spLineNum + "").contains(".")) {
                    lineNum = Integer.parseInt((spLineNum + "").substring(0, (spLineNum + "").indexOf("."))) + 1;
                } else {
                    lineNum = spLineNum;
                }
                int lineLength = (int) (mText.length() / spLineNum);
                Log.v("MyView", "文本总长度:" + mText);
                Log.v("MyView", "文本总长度:" + mText.length());
                Log.v("MyView", "能绘制文本的宽度:" + lineLength);
                Log.v("MyView", "需要绘制:" + lineNum + "行");
                Log.v("MyView", "lineLength:" + lineLength);
                for (int i = 0; i < lineNum; i++) {
                    String lineStr;
                    if (mText.length() < lineLength) {
                        lineStr = mText.substring(0, mText.length());
                    } else {
                        lineStr = mText.substring(0, lineLength);
                    }
                    Log.v("MyView", "lineStr:" + lineStr);
                    mTextList.add(lineStr);
                    if (!TextUtils.isEmpty(mText)) {
                        if (mText.length() < lineLength) {
                            mText = mText.substring(0, mText.length());
                        } else {
                            mText = mText.substring(lineLength, mText.length());
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            width = widthSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸
            if (isOneLines) {
                //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding值，在构造方法执行完就被赋值
                width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            } else {
                //如果是多行，说明控件宽度应该填充父窗体
                width = widthSize;
            }
            Log.v("MyView", "文本的宽度:" + textWidth + "控件的宽度：" + width);
        }
        //高度跟宽度处理方式一样
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            float textHeight = mBound.height();
            if (isOneLines) {
                height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            } else {
                //如果是多行
                height = (int) (getPaddingTop() + textHeight * lineNum + getPaddingBottom());
                ;
            }

            Log.v("MyView", "文本的高度:" + textHeight + "控件的高度：" + height);
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);
    }
}
