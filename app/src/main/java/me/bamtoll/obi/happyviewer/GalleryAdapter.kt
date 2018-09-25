package me.bamtoll.obi.happyviewer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class GalleryAdapter(data: Array<GalleryItem>): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var mData: Array<GalleryItem> = data

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        var v: View = LayoutInflater.from(p0.context).inflate(R.layout.item_gallery, p0, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Picasso.get().load(mData[p1].thumbnailUrl).fit().placeholder(R.mipmap.ic_launcher).into(p0.thumbnailImage)
        p0.titleText.text = mData[p1].title
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var thumbnailImage: ImageView = itemView.findViewById(R.id.image_gallery_thumbnail) as ImageView
        var titleText: TextView = itemView.findViewById(R.id.text_gallery_title) as TextView
    }

    class GalleryItem(var thumbnailUrl: String, var title: String)
}