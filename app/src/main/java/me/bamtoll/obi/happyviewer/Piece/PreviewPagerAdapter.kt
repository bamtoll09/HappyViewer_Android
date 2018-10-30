package me.bamtoll.obi.happyviewer.Piece

import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.bamtoll.obi.happyviewer.R

class PreviewPagerAdapter(count: Int): PagerAdapter() {

    var mCount = count

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = LayoutInflater.from(container.context).inflate(R.layout.layout_preview, container, false)
        when (position) {
            0 -> v.setBackgroundColor(Color.GREEN)
            1 -> v.setBackgroundColor(Color.CYAN)
            2 -> v.setBackgroundColor(Color.YELLOW)
        }
        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return mCount
    }

}