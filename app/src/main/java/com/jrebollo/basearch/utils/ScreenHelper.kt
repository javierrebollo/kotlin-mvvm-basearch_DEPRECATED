package com.jrebollo.basearch.utils

import android.content.res.Resources


fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Float.toDp(): Float = this / Resources.getSystem().displayMetrics.density
fun Float.toPx(): Float = this * Resources.getSystem().displayMetrics.density
fun screenWidth(): Int = Resources.getSystem().displayMetrics.widthPixels
fun screenHeight(): Int = Resources.getSystem().displayMetrics.heightPixels

fun getPercentageOfWidthScreen(percentage: Float): Int =
    (Resources.getSystem().displayMetrics.widthPixels * percentage).toInt()



