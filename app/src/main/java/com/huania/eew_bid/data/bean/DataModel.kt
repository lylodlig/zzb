package com.huania.eew_bid.data.bean

import androidx.lifecycle.LiveData
import com.wwy.android.view.loadpage.LoadPageStatus

data class DataModel<T>(
    val showSuccess: T? = null,
    val showLoading: Boolean = false,
    val showError: String? = null,
    val isRefresh: Boolean = false, // 刷新
    val isRefreshSuccess: Boolean = true, // 是否刷新成功
    val needLogin: Boolean? = false,
    val loadPageStatus: LiveData<LoadPageStatus>? = null
)