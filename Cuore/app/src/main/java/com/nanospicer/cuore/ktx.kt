package com.nanospicer.cuore

import android.app.Activity
import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}


/**
 * The topmost view
 */
val Activity.rootView: View
    get() = window.decorView.rootView

fun Activity.enableInmersiveMode(drawBehindNavBar: Boolean = false) {
    val navFlag = if (drawBehindNavBar) View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION else 0
    rootView.systemUiVisibility =
        rootView.systemUiVisibility or
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                navFlag
}


fun Int.toMipmap() =
    when(this % 24) {
        0 -> R.mipmap.min1
        1 -> R.mipmap.min2
        2 -> R.mipmap.min3
        3 -> R.mipmap.min4

        4 -> R.mipmap.min5
        5 -> R.mipmap.min6
        6 -> R.mipmap.min7
        7 -> R.mipmap.min8

        8 -> R.mipmap.min9
        9 -> R.mipmap.min10
        10 -> R.mipmap.min11
        11 -> R.mipmap.min12

        12 -> R.mipmap.min13
        13 -> R.mipmap.min14
        14 -> R.mipmap.min15
        15 -> R.mipmap.min16

        16 -> R.mipmap.min17
        17 -> R.mipmap.min18
        18 -> R.mipmap.min19
        19 -> R.mipmap.min20

        20 -> R.mipmap.min21
        21 -> R.mipmap.min22
        22 -> R.mipmap.min23
        23 -> R.mipmap.min24

        else -> R.mipmap.min1
    }