package com.ds.swipesmartrecyclerview.base

import android.content.res.Resources
import android.util.TypedValue

object DimensionHelper {


    fun dp2Px(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px).toFloat()
    }

    fun spToPx(sp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics)
    }

}