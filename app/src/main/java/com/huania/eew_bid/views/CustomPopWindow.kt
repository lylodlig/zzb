package com.huania.eew_bid.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.RequiresApi


class CustomPopWindow private constructor(private val mContext: Context) {
    var width: Int = 0
        private set
    var height: Int = 0
        private set
    private var mIsFocusable = true
    private var mIsOutside = true
    private var mResLayoutId = -1
    private var mContentView: View? = null
    private var mPopupWindow: PopupWindow? = null
    private var mAnimationStyle = -1

    private var mClippEnable = true//default is true
    private var mIgnoreCheekPress = false
    private var mInputMode = -1
    private var mOnDismissListener: PopupWindow.OnDismissListener? = null
    private var mSoftInputMode = -1
    private var mTouchable = true//default is ture
    private var mOnTouchListener: View.OnTouchListener? = null

    /**
     *
     * @param anchor
     * @param xOff
     * @param yOff
     * @return
     */
    fun showAsDropDown(anchor: View, xOff: Int, yOff: Int): CustomPopWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAsDropDown(anchor, xOff, yOff)
        }
        return this
    }

    fun showAsDropDown(anchor: View): CustomPopWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAsDropDown(anchor)
        }
        return this
    }

    fun getView() = mContentView

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun showAsDropDown(anchor: View, xOff: Int, yOff: Int, gravity: Int): CustomPopWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAsDropDown(anchor, xOff, yOff, gravity)
        }
        return this
    }


    /**
     * 相对于父控件的位置（通过设置Gravity.CENTER，下方Gravity.BOTTOM等 ），可以设置具体位置坐标
     * @param parent
     * @param gravity
     * @param x the popup's x location offset
     * @param y the popup's y location offset
     * @return
     */
    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int): CustomPopWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAtLocation(parent, gravity, x, y)
        }
        return this
    }

    /**
     * 添加一些属性设置
     * @param popupWindow
     */
    private fun apply(popupWindow: PopupWindow) {
        popupWindow.isClippingEnabled = mClippEnable
        if (mIgnoreCheekPress) {
            popupWindow.setIgnoreCheekPress()
        }
        if (mInputMode != -1) {
            popupWindow.inputMethodMode = mInputMode
        }
        if (mSoftInputMode != -1) {
            popupWindow.softInputMode = mSoftInputMode
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener)
        }
        if (mOnTouchListener != null) {
            popupWindow.setTouchInterceptor(mOnTouchListener)
        }
        popupWindow.isTouchable = mTouchable


    }

    private fun build(): PopupWindow {

        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId, null)
        }

        if (width != 0 && height != 0) {
            mPopupWindow = PopupWindow(mContentView, width, height)
        } else {
            mPopupWindow = PopupWindow(
                mContentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        if (mAnimationStyle != -1) {
            mPopupWindow!!.animationStyle = mAnimationStyle
        }

        apply(mPopupWindow!!)//设置一些属性

        mPopupWindow!!.isFocusable = mIsFocusable
        mPopupWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mPopupWindow!!.isOutsideTouchable = mIsOutside

        if (width == 0 || height == 0) {
            mPopupWindow!!.contentView
                .measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            width = mPopupWindow!!.contentView.measuredWidth
            height = mPopupWindow!!.contentView.measuredHeight
        }

        mPopupWindow!!.update()

        return mPopupWindow as PopupWindow
    }

    /**
     * 关闭popWindow
     */
    fun dissmiss() {
        if (mPopupWindow != null) {
            mPopupWindow!!.dismiss()
        }
    }


    class PopupWindowBuilder(context: Context) {
        private val mCustomPopWindow: CustomPopWindow

        init {
            mCustomPopWindow = CustomPopWindow(context)
        }

        fun size(width: Int, height: Int): PopupWindowBuilder {
            mCustomPopWindow.width = width
            mCustomPopWindow.height = height
            return this
        }


        fun setFocusable(focusable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsFocusable = focusable
            return this
        }


        fun setView(resLayoutId: Int): PopupWindowBuilder {
            mCustomPopWindow.mResLayoutId = resLayoutId
            mCustomPopWindow.mContentView = null
            return this
        }

        fun setView(view: View): PopupWindowBuilder {
            mCustomPopWindow.mContentView = view
            mCustomPopWindow.mResLayoutId = -1
            return this
        }

        fun setOutsideTouchable(outsideTouchable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsOutside = outsideTouchable
            return this
        }

        /**
         * 设置弹窗动画
         * @param animationStyle
         * @return
         */
        fun setAnimationStyle(animationStyle: Int): PopupWindowBuilder {
            mCustomPopWindow.mAnimationStyle = animationStyle
            return this
        }


        fun setClippingEnable(enable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mClippEnable = enable
            return this
        }


        fun setIgnoreCheekPress(ignoreCheekPress: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIgnoreCheekPress = ignoreCheekPress
            return this
        }

        fun setInputMethodMode(mode: Int): PopupWindowBuilder {
            mCustomPopWindow.mInputMode = mode
            return this
        }

        fun setOnDissmissListener(onDissmissListener: PopupWindow.OnDismissListener): PopupWindowBuilder {
            mCustomPopWindow.mOnDismissListener = onDissmissListener
            return this
        }


        fun setSoftInputMode(softInputMode: Int): PopupWindowBuilder {
            mCustomPopWindow.mSoftInputMode = softInputMode
            return this
        }


        fun setTouchable(touchable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mTouchable = touchable
            return this
        }

        fun setTouchIntercepter(touchIntercepter: View.OnTouchListener): PopupWindowBuilder {
            mCustomPopWindow.mOnTouchListener = touchIntercepter
            return this
        }


        fun create(): CustomPopWindow {
            //构建PopWindow
            mCustomPopWindow.build()
            return mCustomPopWindow
        }

    }

}