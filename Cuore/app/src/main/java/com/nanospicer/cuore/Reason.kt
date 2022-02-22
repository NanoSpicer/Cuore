package com.nanospicer.cuore

fun reason(dsl: ReasonDSL.() -> Unit): Reason = ReasonDSL().apply(dsl).let {
    Reason(it.title, it.description, it.mipmapId)
}




data class Reason(
    val title: String,
    val description: String,
    val mipmapId: Int,
    var isCollapsed: Boolean = true
)

class ReasonDSL(
    var title: String = "",
    var description: String = "",
    var mipmapId: Int = R.mipmap.min1
)

fun String.nullIfEmpty() = if(this.isEmpty()) null else this