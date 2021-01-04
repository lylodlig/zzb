package com.huania.eew_bid.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleItemAdapter(@LayoutRes private val itemLayoutRes: Int) :
    RecyclerView.Adapter<SimpleItemAdapter.ViewHolder>() {

    var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(itemLayoutRes, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

}