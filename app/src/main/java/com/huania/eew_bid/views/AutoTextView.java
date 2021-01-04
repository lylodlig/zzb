package com.huania.eew_bid.views;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.w3c.dom.Text;

/**
 * created by lzy
 * on 2020/3/10
 */
public class AutoTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private final float mHeight;
    private final Context mContext;
    //mInUp,mOutUp分别构成向上翻页的进出动画
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;

    //mInDown,mOutDown分别构成向下翻页的进出动画
    private Rotate3dAnimation mInDown;
    private Rotate3dAnimation mOutDown;

    public AutoTextView(Context context) {
        this(context, null);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.auto3d);
        mHeight = 36f;
//        a.recycle();
        mContext = context;
        init();
    }

    private void init() {
        setFactory(this);
        //向上翻页的进出动画
        mInUp = createAnim(-90, 0, true, true);//进动画
        mOutUp = createAnim(0, 90, false, true);//出动画
        //向下翻页的进出动画
        mInDown = createAnim(90, 0, true, false);
        mOutDown = createAnim(0, -90, false, false);
        //继承TextSwitcher主要用于文件切换，比如 从文字A 切换到 文字 B，
        //setInAnimation()后，A将执行inAnimation，
        //setOutAnimation()后，B将执行OutAnimation
        setInAnimation(mInUp);
        setOutAnimation(mOutUp);
    }

    //动画
    private Rotate3dAnimation createAnim(float start, float end, boolean turnIn, boolean turnUp) {
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, turnIn, turnUp);
        //设置动画持续时间
        rotation.setDuration(300);
        //fillAfter设为true，则动画执行后，控件将停留在动画结束的状态,false 则不停留
        rotation.setFillAfter(false);
        //设置动画的变化速度  加速
        rotation.setInterpolator(new AccelerateInterpolator());
        return rotation;
    }

    //这里返回的TextView，就是我们看到的View
    @Override
    public View makeView() {
        MarqueeTextView t = new MarqueeTextView(mContext);
        t.setGravity(Gravity.CENTER);
        t.setTextColor(Color.parseColor("#0551A5"));
        t.setTextSize(16);
        t.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        t.setMarqueeRepeatLimit(-1);
        t.setMaxLines(3);
        t.setFocusable(true);
        t.setFocusableInTouchMode(true);
        t.setSingleLine(true);
        return t;
    }

    //定义动作，向下滚动翻页
    public void previous() {
        if (getInAnimation() != mInDown) {
            setInAnimation(mInDown);
        }
        if (getOutAnimation() != mOutDown) {
            setOutAnimation(mOutDown);
        }
    }

    //定义动作，向上滚动翻页
    public void next() {
        if (getInAnimation() != mInUp) {
            setInAnimation(mInUp);
        }
        if (getOutAnimation() != mOutUp) {
            setOutAnimation(mOutUp);
        }
    }

    /**
     * 3d动画
     * 重新定义了一个Animation，覆写了initialize和applyTransformation方法
     */

    class Rotate3dAnimation extends Animation {
        private final float mFromDegrees; //初始值
        private final float mToDegrees; //最终值
        private final boolean mTurnIn; //进
        private final boolean mTurnUp; //出
        private float mCenterX;
        private float mCenterY;
        private Camera mCamera;

        public Rotate3dAnimation(float fromDegrees, float toDegrees, boolean turnIn, boolean turnUp) {

            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        //初始化动作
        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight() / 2;
            mCenterX = getWidth() / 2;
        }

        //定义动画效果
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            final float fromDegrees = mFromDegrees;
            //当前值 = 初始值 + （最终值 - 初始值） * interpolatedTime;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;

            //通过camera进行一些矩阵操作，最后对matrix进行变化
            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime), 0.0f);
            }
            camera.rotateX(degrees);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}