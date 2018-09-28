package me.bamtoll.obi.happyviewer

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatButton
import android.util.TypedValue
import android.widget.GridLayout

class TagButton(context: Context, text: String): AppCompatButton(context) {

    init {
        layoutParams = GridLayout.LayoutParams()
        layoutParams.width = dpToPixel(0)
        layoutParams.height = dpToPixel(24)
        setPadding(0, 0, 0, 0)
        (layoutParams as GridLayout.LayoutParams).columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        this.text = text
        setTextColor(Color.WHITE)

        setBackgroundResource(R.drawable.selector_tag)
    }

    fun dpToPixel(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }
}