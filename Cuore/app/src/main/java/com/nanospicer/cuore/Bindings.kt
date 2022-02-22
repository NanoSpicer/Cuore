package com.nanospicer.cuore

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("reasons")
fun mapReasons(rv: RecyclerView, reasons: List<Reason>?) {
    rv.apply {
        layoutManager = LinearLayoutManager(rv.context)
        val items = reasons.orEmpty().mapIndexed { index, reason ->
            ReasonUI(reason) {
                reason.isCollapsed = !reason.isCollapsed
                adapter?.notifyItemChanged(index)
            }
        }
        adapter = NanoAdapter(items)
        scheduleLayoutAnimation()
    }
}