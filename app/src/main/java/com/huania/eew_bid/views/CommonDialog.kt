package com.huania.eew_bid.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle

/**
 *   created by lzy
 *   on 2020/4/17
 */
class CommonDialog : Dialog {
    private var res: Int? = null

    constructor(context: Context, style: Int, res: Int) : super(context, style) {
        this.res = res
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(res!!)
    }

}