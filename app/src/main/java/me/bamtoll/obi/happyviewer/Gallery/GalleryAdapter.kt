package me.bamtoll.obi.happyviewer.Gallery

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import com.squareup.picasso.Picasso
import me.bamtoll.obi.happyviewer.*

class GalleryAdapter(data: List<GalleryItem>, activity: MainActivity): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var mData: ArrayList<GalleryItem> = arrayListOf(
            GalleryItem("/assets/mono7/", "Ane Naru Mono 7",
                    GalleryItem.InfoItem("Pochi.", "", "Ane Naru Mono", "Doujinshi", listOf("Blowjob", "Nakadashi", "Paizuri", "Ponytail", "Sole Female", "Shota", "Sole Male", "Multi-work Series"
                    ))
            )
    )
    /*data as ArrayList<GalleryItem>*/
    private val mainActivity = activity
    var firstLayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    var viewHolderInit = 0

    companion object {
        private const val THUMBNAIL_URL = "file:///android_asset/"
        private const val IMAGE_FORMT = ".jpg"
        private const val VIEW_CONTENT = 0
        private const val VIEW_FOOTER = 1
    }

    init {
        layoutParams.setMargins(0, 8, 0, 0)
    }

    enum class MEASURED {
        X, Y, WIDTH, HEIGHT
    }

    fun addData(elements: List<GalleryItem>) {
        mData.addAll(elements)
        notifyDataSetChanged()
    }

    fun removeAll() {
        mData.removeAll(mData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        var layout = 0
        when (p1) {
            VIEW_CONTENT -> layout = R.layout.item_gallery
            VIEW_FOOTER -> layout = R.layout.item_progress
        }
        return ViewHolder(LayoutInflater.from(p0.context).inflate(layout, p0, false), p1)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        when (p0.viewType) {
            VIEW_CONTENT -> {
                if (viewHolderInit == 0) {
                    val viewTreeObserver = p0.tagLayout.viewTreeObserver
                    viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
                        override fun onGlobalLayout() {
                            p0.tagLayoutWidth = p0.tagLayout.width
                            p0.tagLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    })
                    viewHolderInit = 1
                }
//                Picasso.get().load(THUMBNAIL_URL + mData[p1].inherenceCode + IMAGE_FORMT).fit().placeholder(R.mipmap.ic_launcher).into(p0.thumbnailImage)
                Picasso.get().load(THUMBNAIL_URL + "mono7/a1.jpeg").transform(ThumbNailTransformation(p0.context)).placeholder(R.mipmap.ic_launcher).into(p0.thumbnailImage)
                p0.titleText.text = mData[p1].title
                p0.artistText.text = "by. ".plus(mData[p1].infoItem.artist)
                //p0.characterText.text = "캐릭: ".plus(mData[p1].infoItem.character)
                p0.seriesText.text = "원작: ".plus(mData[p1].infoItem.series)
                // p0.typeText.text = "종류: ".plus(mData[p1].infoItem.type)

                p0.itemView.setOnClickListener { v ->
                    Log.d("AsDf", mData[p1].inherenceCode + " " + p1)
                    val intent = Intent(v.context.applicationContext, PieceActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    // intent.putExtra("inherence_code", mData[p1].inherenceCode)
                    v.context.applicationContext.startActivity(intent)
                }
            }
            /*VIEW_FOOTER -> {
                p0.progressBar.visibility = View.VISIBLE
                mainActivity.loadPage(false)
            }*/
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

        val viewTreeObserver = holder.tagLayout.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                Log.d("width", holder.tagLayout.width.toString())
                if (mData[holder.adapterPosition].infoItem.tag != null) {
                    var tags: List<String> = mData[holder.adapterPosition].infoItem.tag!!
                    var tagsWidth = 0

                    for (i in tags.indices) {
                        if (holder.tagLine == 0) { // next layout will have 8px top margin
                            holder.tagLayout.addView(LinearLayout(holder.context), firstLayoutParams)
                            holder.tagLine++
                        }
                        tagsWidth += dpToPixel(tags[i].length * 7 + 15, holder.context)
                        Log.d("tagswidth", tagsWidth.toString())
                        if (tagsWidth >= holder.tagLayoutWidth) { // add tag line
                            holder.tagLayout.addView(LinearLayout(holder.context), layoutParams)
                            tagsWidth = dpToPixel(tags[i].length * 7 + 15, holder.context)
                            holder.tagLine++
                        }
                        (holder.tagLayout.getChildAt(holder.tagLine - 1) as LinearLayout).addView(TagButton(holder.context, tags[i]))
                    }
                }
                holder.tagLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }



    fun dpToPixel(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }

    override fun getItemViewType(position: Int): Int {
        /*if (position == mData.size - mData.size % 15)
            return VIEW_FOOTER*/

        return VIEW_CONTENT
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

        holder.tagLayoutWidth = 0
        holder.tagLine = 0
        /*when (holder.viewType) {
            VIEW_CONTENT -> {
                holder.tagLayout.removeAllViews()
            }
            VIEW_FOOTER -> {
                holder.progressBar.visibility = View.GONE
            }
        }*/
    }

    override fun getItemCount(): Int {
        /*val revision = mData.size % 15

        return (mData.size - revision + VIEW_FOOTER)*/
        return 1
    }

    class ViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder(view) {
        val viewType = viewType
        val context: Context = view.context
        var tagLayoutWidth = 0
        var tagLine = 0

        lateinit var thumbnailImage: ImageView
        lateinit var titleText: TextView
        lateinit var artistText: TextView
        // lateinit var characterText: TextView
        lateinit var seriesText: TextView
        // lateinit var typeText: TextView
        lateinit var tagLayout: LinearLayout

        lateinit var progressBar: ProgressBar

        init {
            when (viewType) {
                VIEW_CONTENT -> {
                    thumbnailImage = itemView.findViewById(R.id.image_gallery_thumbnail) as ImageView
                    titleText = itemView.findViewById(R.id.text_gallery_title) as TextView
                    artistText = itemView.findViewById(R.id.text_gallery_artist) as TextView
                    // characterText = itemView.findViewById(R.id.text_gallery_character) as TextView
                    seriesText = itemView.findViewById(R.id.text_gallery_series) as TextView
                    // typeText = itemView.findViewById(R.id.text_gallery_type) as TextView
                    tagLayout = itemView.findViewById(R.id.layout_tags) as LinearLayout
                }
                VIEW_FOOTER -> {
                    progressBar = itemView.findViewById(R.id.progress) as ProgressBar
                }
            }
        }

        fun addTagLayoutChildren(tag: List<String>?, holder: ViewHolder, firstLayoutParams: LinearLayout.LayoutParams, layoutParams: LinearLayout.LayoutParams) {
            if (tag != null) {
                var tags: List<String> = tag!!
                var tagsWidth = 0

                for (i in tags.indices) {
                    if (holder.tagLine == 0) { // next layout will have 8px top margin
                        holder.tagLayout.addView(LinearLayout(holder.context), firstLayoutParams)
                        holder.tagLine++
                    }
                    tagsWidth.plus(tags[i].length * 7 + 15)
                    if (tagsWidth >= holder.tagLayout.width) { // add tag line
                        holder.tagLayout.addView(LinearLayout(holder.context), layoutParams)
                        holder.tagLine++
                    }
                    (holder.tagLayout.getChildAt(holder.tagLine - 1) as LinearLayout).addView(TagButton(holder.context, tags[i]))
                }
            }
        }
    }
}