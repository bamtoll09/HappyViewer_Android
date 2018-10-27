package me.bamtoll.obi.happyviewer

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatButton
import android.util.TypedValue
import android.widget.LinearLayout

class TagButton(context: Context, text: String): AppCompatButton(context) {

    init {
        layoutParams = LinearLayout.LayoutParams(dpToPixel(text.length * 7 + 15), dpToPixel(24))
        setPadding(0, 0, 0, 0)
        (layoutParams as LinearLayout.LayoutParams).rightMargin = 8
        this.text = text
        this.textSize = 12f
        setTextColor(Color.WHITE)

        setBackgroundResource(R.drawable.selector_tag)
    }

    fun dpToPixel(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }
}