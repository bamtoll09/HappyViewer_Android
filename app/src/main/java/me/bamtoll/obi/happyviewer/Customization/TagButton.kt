package me.bamtoll.obi.happyviewer.Customization

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import me.bamtoll.obi.happyviewer.R

class TagButton(context: Context, text: String): Button(context) {

    init {
        layoutParams = LinearLayout.LayoutParams(dpToPixel(text.length * 7 + 15), dpToPixel(24))
        setPadding(0, 0, 0, 0)
        (layoutParams as LinearLayout.LayoutParams).rightMargin = 8
        this.text = text.toUpperCase()
        this.textSize = 12f
        setTextColor(Color.WHITE)
//        gravity = Gravity.CENTER
//        typeface = Typeface.SANS_SERIF
        setBackgroundResource(R.drawable.selector_tag)
    }

    fun dpToPixel(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }
}