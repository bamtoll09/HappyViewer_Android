package me.bamtoll.obi.happyviewer.Gallery

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.bamtoll.obi.happyviewer.MainActivity
import me.bamtoll.obi.happyviewer.PieceActivity
import me.bamtoll.obi.happyviewer.R
import me.bamtoll.obi.happyviewer.TagButton

class GalleryAdapter(data: List<GalleryItem>, activity: MainActivity): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var mData: ArrayList<GalleryItem> = data as ArrayList<GalleryItem>
    private val mainActivity = activity

    companion object {
        private const val THUMBNAIL_URL = "https://aa.hiyobi.me/tn/"
        private const val IMAGE_FORMT = ".jpg"
        private const val VIEW_CONTENT = 0
        private const val VIEW_FOOTER = 1
    }

    fun addData(elements: List<GalleryItem>) {
        mData.addAll(elements)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        var layout: Int = 0
        when (p1) {
            VIEW_CONTENT -> layout = R.layout.item_gallery
            VIEW_FOOTER -> layout = R.layout.item_progress
        }
        return ViewHolder(LayoutInflater.from(p0.context).inflate(layout, p0, false), p1)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        when (p0.viewType) {
            VIEW_CONTENT -> {
                Picasso.get().load(THUMBNAIL_URL + mData[p1].inherenceCode + IMAGE_FORMT).fit().placeholder(R.mipmap.ic_launcher).into(p0.thumbnailImage)
                p0.titleText.text = mData[p1].title

                if (mData[p1].infoItem.tag != null) {
                    var tags: List<String> = mData[p1].infoItem.tag!!

                    for (tag: String in tags) {
                        p0.tagLayout.addView(TagButton(p0.context, tag))
                    }
                }

                p0.itemView.setOnClickListener { v ->
                    Log.d("AsDf", mData[p1].inherenceCode + " " + p1)
                    val intent = Intent(v.context.applicationContext, PieceActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("inherence_code", mData[p1].inherenceCode)
                    v.context.applicationContext.startActivity(intent)
                }
            }
            VIEW_FOOTER -> {
                p0.progressBar.visibility = View.VISIBLE
                mainActivity.loadPage()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == mData.size - mData.size % 15)
            return VIEW_FOOTER

        return VIEW_CONTENT
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

        when (holder.viewType) {
            VIEW_CONTENT -> {
                holder.tagLayout.removeAllViews()
            }
            VIEW_FOOTER -> {
                holder.progressBar.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        val revision = mData.size % 15

        return (mData.size - revision + VIEW_FOOTER)
    }

    class ViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder(view) {
        val viewType = viewType
        val context: Context = view.context

        lateinit var thumbnailImage: ImageView
        lateinit var titleText: TextView
        lateinit var tagLayout: GridLayout

        lateinit var progressBar: ProgressBar

        init {
            when (viewType) {
                VIEW_CONTENT -> {
                    thumbnailImage = itemView.findViewById(R.id.image_gallery_thumbnail) as ImageView
                    titleText = itemView.findViewById(R.id.text_gallery_title) as TextView
                    tagLayout = itemView.findViewById(R.id.layout_tags) as GridLayout
                }
                VIEW_FOOTER -> {
                    progressBar = itemView.findViewById(R.id.progress) as ProgressBar
                }
            }
        }

    }
}