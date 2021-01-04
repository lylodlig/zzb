package com.huania.eew_bid.views.loadpage

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

class LoadPageViewForStatus @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var failText: TextView
    private lateinit var noNetText: TextView
    private lateinit var emptyText: TextView
    private lateinit var progressBar: ProgressBar

    init {
    }

    fun failTextView(): TextView {
        return failText
    }

    fun noNetTextView(): TextView {
        return noNetText
    }

    fun emptyTextView(): TextView {
        return emptyText
    }

    fun progressBarView(): ProgressBar {
        return progressBar
    }

}
