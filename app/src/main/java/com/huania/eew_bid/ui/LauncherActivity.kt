package com.huania.eew_bid.ui

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.huania.eew_bid.R
import com.huania.eew_bid.base.BaseActivity
import com.huania.eew_bid.databinding.ActivityLuancherBinding
import com.huania.eew_bid.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_luancher.*
import org.jetbrains.anko.startActivity

class LauncherActivity : BaseActivity<ActivityLuancherBinding>() {

    override fun getLayoutId() = R.layout.activity_luancher

    override fun initActivity(savedInstanceState: Bundle?) {
        startAnim()
    }

    private fun startAnim() {
        val animation = ScaleAnimation(
            0.8f,
            2.5f,
            0.8f,
            2.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 2000
        animation.fillAfter = true
        animation.repeatCount = 0
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity<HomeActivity>()
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
        iv.startAnimation(animation)
    }
}