package me.bamtoll.obi.happyviewer

import android.content.Context
import android.graphics.PointF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log

class PieceLayoutManager(context: Context): LinearLayoutManager(context) {

    companion object {
        var EXTRA_SPACE_RANGE = 1.0f
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return (1200 * EXTRA_SPACE_RANGE).toInt()
    }

}