package me.bamtoll.obi.happyviewer.Viewer

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

class ViewerLayoutManager(context: Context): LinearLayoutManager(context) {

    companion object {
        var EXTRA_SPACE_RANGE = 1.0f
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        Log.d("SPEEEEEEED", (2000 * EXTRA_SPACE_RANGE).toString())
        return (2000 * EXTRA_SPACE_RANGE).toInt()
    }

}