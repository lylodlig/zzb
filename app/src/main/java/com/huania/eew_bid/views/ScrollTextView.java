package com.huania.eew_bid.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

//Created by Jim on 2018/4/13.自动循环竖直滚动播放文字。
public class ScrollTextView extends ScrollView {
    public String text;//TextView文字输入
    private int num;//初始值
    private TextView mTextView;
    private int scrollDelay = 100;//定时频率
    private int scrollDistance = 1;//滚动距离
    private boolean mClick = true;//是否可以点击 true|false 不能点击|可以点击
    private int mTextSize = 20;//默认字体大小
    private int color = Color.RED;//设置文字的颜色

    public void setColor(int color) {
        this.color = color;
    }

    private int getColor() {
        return color;
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setClick(boolean click) {
        mClick = !click;
    }

    public boolean getClick() {
        return mClick;
    }

    public int getScrollDelay() {
        return scrollDelay;
    }

    public void setScrollDelay(int scrollDelay) {
        this.scrollDelay = scrollDelay;
    }

    public int getScrollDistance() {
        return scrollDistance;
    }

    public void setScrollDistance(int scrollDistance) {
        this.scrollDistance = scrollDistance;
    }

    Timer timer ;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        initView();
    }

    public ScrollTextView(Context context) {
        this(context, null);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private void initView() {
        if (mTextView == null) {
            mTextView = new TextView(getContext());
            ListView.LayoutParams layoutParams = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT);
            mTextView.setLayoutParams(layoutParams);
            if (timer != null) {
                timer.cancel();
            }
            timer=new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    start();
                }
            }, 0, scrollDelay);
        }
        removeAllViews();
        this.addView(mTextView);
        /*View inflate = LayoutInflater.from(getContext()).inflate(R.layout.activity_textview, this);
        mTextView = inflate.findViewById(R.id.tv_textview);*/
        mTextView.setTextColor(color);
//        mTextView.setGravity(Gravity.CENTER);//设置字体横向居中
//        mTextView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelOffset(getTextSize()));//设置文字大小
        mTextView.setText(getText());
        this.setVerticalScrollBarEnabled(false);//隐藏滚动条
        this.setOnTouchListener((v, event) -> {
            //点击事件的拦截
            return getClick();
        });
        mTextView.setOnTouchListener((v, event) -> {
            //点击事件的拦截
            return getClick();
        });

    }


    /**
     * 开始滚动
     *
     * @param
     */
    public void start() {
        num += scrollDistance;
        int off = mTextView.getMeasuredHeight() - this.getHeight();
        if (num >= off) {
            num = 0;
            mTextView.scrollTo(0, 0);
        }
        if (off > 0) {
            mTextView.scrollTo(0, num);
        }
    }


    /*
       取消定时器
        */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // AppUtils.e("jim", "断开链接");
        mTextView = null;
        timer.cancel();
    }


}