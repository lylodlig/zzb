package com.huania.eew_bid.ext

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.huania.eew_bid.views.SimpleItemAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator


/**
 *   created by lzy
 *   on 2020/3/3
 */
@BindingAdapter("adapter", requireAll = false)
fun banner(
    view: Banner<*, BannerAdapter<*, *>>,
    adapter: BannerAdapter<*, *>?
) {
    adapter ?: return
    view.apply {
        setAdapter(adapter)
//        indicator = CircleIndicator(view.context)
        start()
    }
}

@BindingAdapter("txt", "template", requireAll = false)
fun textView(
    view: TextView,
    txt: Any?,
    template: String?
) {
    try {
        view.apply {
            text = if (template.isNullOrBlank()) txt.toString() else {
                var templateNew = template
                txt.toString().contains("&&").yes {
                    txt.toString().split("&&").forEachIndexed { index, s ->
                        templateNew = templateNew!!.replace("%${index + 1}", s)
                    }
                }.otherwise {
                    templateNew = templateNew!!.replace("%", txt.toString())
                }
                templateNew
            }
        }
    } catch (e: Exception) {
        throw Exception("BindingAdapter参数使用错误")
    }
}

@BindingAdapter("click", requireAll = false)
fun bindClicks(
    view: View,
    click: ((View) -> Unit)?
) {
    view.setOnClickListener {
        if (click != null) {
            click(it)
        }
    }
}

@BindingAdapter("adapter", requireAll = false)
fun bindSimpleAdapter(
    recyclerView: RecyclerView,
    adapter: SimpleItemAdapter
) {
    recyclerView.adapter = adapter
}

@BindingAdapter("onLoadMore", "onRefresh", requireAll = false)
fun bindSmartRefreshLayout(
    refreshLayout: SmartRefreshLayout,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit
) {
    refreshLayout.setOnLoadMoreListener { onLoadMore() }
    refreshLayout.setOnRefreshListener { onRefresh() }
}


@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter(
    "drawableWidth",
    "drawableHeight",
    "drawableLeft",
    "drawableRight",
    "drawableTop",
    "drawableBottom",
    requireAll = false
)
fun bindTextView(
    textView: TextView,
    drawableWidth: Int,
    drawableHeight: Int,
    @DrawableRes drawableLeft: Int,
    @DrawableRes drawableRight: Int,
    @DrawableRes drawableTop: Int,
    @DrawableRes drawableBottom: Int
) {
    val context = textView.context
    context.resources.run {
        val left = if (drawableLeft == 0) null else
            ContextCompat.getDrawable(context, drawableLeft)?.apply {
                setBounds(
                    0,
                    0,
                    drawableWidth.dp(),
                    drawableHeight.dp()
                )
            }
        val right = if (drawableRight == 0) null else
            ContextCompat.getDrawable(context, drawableRight)?.apply {
                setBounds(
                    0,
                    0,
                    drawableWidth.dp(),
                    drawableHeight.dp()
                )
            }
        val top = if (drawableTop == 0) null else
            ContextCompat.getDrawable(context, drawableTop)?.apply {
                setBounds(
                    0,
                    0,
                    drawableWidth.dp(),
                    drawableHeight.dp()
                )
            }
        val bottom = if (drawableBottom == 0) null else
            ContextCompat.getDrawable(context, drawableBottom)?.apply {
                setBounds(
                    0,
                    0,
                    drawableWidth.dp(),
                    drawableHeight.dp()
                )
            }
        textView.setCompoundDrawables(left, top, right, bottom)
    }
}

@BindingAdapter("visible")
fun visible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("image", "backImage", requireAll = false)
fun image(view: ImageView, @DrawableRes image: Int, @DrawableRes backImage: Int) {
    if (image != 0) {
        view.setImageResource(image)
    }
    if (backImage != 0) {
        view.setBackgroundResource(backImage)
    }
}
