package me.bamtoll.obi.happyviewer.Piece

import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import me.bamtoll.obi.happyviewer.R

class PreviewPagerAdapter(smalltnUrl: Array<String>): PagerAdapter() {

    var mSmalltnUrl = smalltnUrl

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = LayoutInflater.from(container.context).inflate(R.layout.layout_preview, container, false)
        val previewGrid = v.findViewById(R.id.layout_preview_grid) as GridLayout

        if ((position + 1) * 15 < mSmalltnUrl.size) {
            for (i in IntRange(position * 15, position * 15 + 14)) {
                previewGrid.addView(PreviewImageView(container.context, mSmalltnUrl[i]))
            }
        } else {
            for (i in IntRange(0, mSmalltnUrl.size % 15 - 1)) {
                previewGrid.addView(PreviewImageView(container.context, mSmalltnUrl[i + (mSmalltnUrl.size / 15 * 15)]))
            }
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
        return mSmalltnUrl.size / 15 + 1
    }

}