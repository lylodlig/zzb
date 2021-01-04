package com.huania.eew_bid.base

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner

interface BindingFactory {
    fun inflate(parent: ViewGroup, lifecycleOwner: LifecycleOwner?)
}