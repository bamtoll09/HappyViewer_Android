package me.bamtoll.obi.happyviewer.Piece

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class PieceLayoutManager(context: Context): LinearLayoutManager(context) {

    companion object {
        var EXTRA_SPACE_RANGE = 1.0f
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return (1200 * EXTRA_SPACE_RANGE).toInt()
    }

}