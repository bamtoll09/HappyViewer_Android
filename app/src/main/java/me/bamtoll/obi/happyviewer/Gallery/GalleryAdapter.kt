package me.bamtoll.obi.happyviewer.Gallery

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import com.beust.klaxon.Klaxon
import com.squareup.picasso.Picasso
import me.bamtoll.obi.happyviewer.*
import me.bamtoll.obi.happyviewer.Customization.TagButton
import me.bamtoll.obi.happyviewer.MainActivity.Companion.pf
import java.io.*
import java.lang.Exception

class GalleryAdapter(data: List<GalleryItem>, activity: AppCompatActivity): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var mData: ArrayList<GalleryItem> = ArrayList(data)

    /*data as ArrayList<GalleryItem>*/
    private val activity = activity
    var firstLayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    var viewHolderInit = 0

    companion object {
        public const val THUMBNAIL_URL = "file:///android_asset/"

        private const val VIEW_CONTENT = 0
        private const val VIEW_FOOTER = 1

        lateinit var layoutParams: LinearLayout.LayoutParams

        var tagLayoutWidth = 0
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
                            tagLayoutWidth = p0.tagLayout.width
                            p0.tagLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    })
                    viewHolderInit = 1
                }
//                Picasso.get().load(THUMBNAIL_URL + mData[p1].inherenceCode + IMAGE_FORMT).fit().placeholder(R.mipmap.ic_launcher).into(p0.thumbnailImage)
                Picasso.get().load(THUMBNAIL_URL.plus(mData[p1].inherenceCode).plus("/a00").plus(mData[p1].ext)).transform(ThumbNailTransformation(p0.context)).placeholder(R.mipmap.ic_launcher).into(p0.thumbnailImage)
                p0.titleText.text = mData[p1].title
                p0.artistText.text = "by. ".plus(mData[p1].infoItem.artist)
                p0.characterText.text = "캐릭: ".plus(mData[p1].infoItem.character)
                p0.seriesText.text = "원작: ".plus(mData[p1].infoItem.series)
                // p0.typeText.text = "종류: ".plus(mData[p1].infoItem.type)
                when (mData[p1].infoItem.type) {
                    "Artist CG" -> p0.typeText.text = "CG"
                    "Doujinshi" -> p0.typeText.text = "D"
                    "Manga" -> p0.typeText.text = "M"
                }

                if (mData[p1].isBMarked) {
                    p0.bmarkButton.setImageResource(R.drawable.ic_star_black_24dp)
                } else {
                    p0.bmarkButton.setImageResource(R.drawable.ic_star_border_black_24dp)
                }

                p0.artistText.setOnClickListener { v ->
                    Toast.makeText(v.context.applicationContext, mData[p1].infoItem.artist, Toast.LENGTH_SHORT).show()
                }
                p0.characterText.setOnClickListener { v ->
                    Toast.makeText(v.context.applicationContext, mData[p1].infoItem.character, Toast.LENGTH_SHORT).show()
                }
                p0.seriesText.setOnClickListener { v ->
                    Toast.makeText(v.context.applicationContext, mData[p1].infoItem.series, Toast.LENGTH_SHORT).show()
                }

                p0.bmarkButton.setOnClickListener { v ->
                    if (mData[p1].isBMarked) {
                        mData[p1].isBMarked = false

                        pf.remove(mData[p1].inherenceCode)

                        p0.bmarkButton.setImageResource(R.drawable.ic_star_border_black_24dp)
                        Toast.makeText(v.context.applicationContext, "Mark Removed", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        mData[p1].isBMarked = true

                        pf.save(mData[p1].inherenceCode, mData[p1].inherenceCode)

                        p0.bmarkButton.setImageResource(R.drawable.ic_star_black_24dp)
                        Toast.makeText(v.context.applicationContext, "Marked\n".plus(pf.find(mData[p1].inherenceCode)), Toast.LENGTH_LONG).show()
                    }

                    if (activity.title == activity.resources.getString(R.string.activity_bookmarks)) {
                        val pos = MainActivity.galleryAdapter.changeBMarkState(mData[p1].inherenceCode, false)
                        if (pos > -1) {
                            mData.removeAt(p1)
                            notifyDataSetChanged()

                            MainActivity.galleryAdapter.notifyItemChanged(pos)
                        }
                    }
                }

                p0.itemView.setOnClickListener { v ->
                    Log.d("AsDf", mData[p1].inherenceCode + " " + p1)

                    when (activity.title) {
                        activity.resources.getString(R.string.app_name) -> MainActivity.loadingLayout.visibility = View.VISIBLE
                        else -> Log.d("ttitle", "BBip")
                    }

                    Thread(Runnable {
                        run {
                            var json: String = ""
                            try {
                                val  inputStream:InputStream = activity.assets.open("ta/ta_list.json")
                                json = inputStream.bufferedReader().use{it.readText()}
                                val result = decodeData(json)
                                val names = Array<String>(result.size, init = {
                                    result.keys.elementAt(it)
                                })
                                val scales = FloatArray(result.size, init = {
                                    result.values.elementAt(it)
                                })

                                Log.d("NAmENAmE", names.joinToString())

                                val intent = Intent(v.context.applicationContext, PieceActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("inherence_code", mData[p1].inherenceCode)
                                intent.putExtra("title", mData[p1].title)
                                intent.putExtra("artist", mData[p1].infoItem.artist)
                                intent.putExtra("character", mData[p1].infoItem.character)
                                intent.putExtra("series", mData[p1].infoItem.series)
                                intent.putExtra("type", mData[p1].infoItem.type)
                                intent.putExtra("tag", mData[p1].infoItem.tag)
                                intent.putExtra("bookmark", mData[p1].isBMarked)
                                intent.putExtra("extension", mData[p1].ext)
                                intent.putExtra("name", names)
                                Storage.scales = scales

                                intent.putExtra("previous_layout", activity.title)

                                v.context.applicationContext.startActivity(intent)
                            } catch (ex: Exception) {
                                ex.printStackTrace()

                                Toast.makeText(activity.applicationContext, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }).start()
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

        if (holder.tagLayout.childCount != 0) {
            holder.tagLayout.removeAllViews()
            holder.tagLine = 0
        }
        val viewTreeObserver = holder.tagLayout.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                Log.d("width", holder.tagLayout.width.toString())

                if (mData[holder.adapterPosition].infoItem.tag != null) {
                    var tags: Array<String> = mData[holder.adapterPosition].infoItem.tag!!
                    var tagButtons: ArrayList<Button> = ArrayList()
                    var tagsWidth = 0

                    for (i in tags.indices) {
                        if (holder.tagLine == 0) { // next layout will have 8px top margin
                            holder.tagLayout.addView(LinearLayout(holder.context), firstLayoutParams)
                            holder.tagLine++
                        }
                        tagsWidth += dpToPixel(tags[i].length * 7 + 15, holder.context)
                        Log.d("tagswidth", tagsWidth.toString())
                        if (tagsWidth >= tagLayoutWidth) { // add tag line
                            holder.tagLayout.addView(LinearLayout(holder.context), layoutParams)
                            tagsWidth = dpToPixel(tags[i].length * 7 + 15, holder.context)
                            holder.tagLine++
                        }
                        tagButtons.add(TagButton(holder.context, tags!![i]))
                        tagButtons[i].setOnClickListener { v ->
                            Snackbar.make(v, tags!![i], Snackbar.LENGTH_SHORT).setAction("action", null).show()
                            Log.d("TATA", "Clicked")
                        }
                        (holder.tagLayout.getChildAt(holder.tagLine - 1) as LinearLayout).addView(tagButtons[i])
                    }
                }
                holder.tagLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    fun dpToPixel(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }

    fun changeBMarkState(code: String, state: Boolean): Int {
        for (i in mData.indices) {
            if (mData[i].inherenceCode == code) {
                mData[i].isBMarked = state
                return i
            }
        }
        return -1
    }

    fun decodeData(json: String): MutableMap<String, Float> {
        Log.d("jsonjsonjsonsjs", json)

        val jsonResult = Klaxon()
                .parseArray<Data>(json)

//        val klaxon = Klaxon()
//        val result = arrayListOf<Data>()
//        JsonReader(StringReader(json)).use { reader ->
//            reader.beginArray()
//                while (reader.hasNext()) {
//                    val data = klaxon.parse<Data>(reader)
//                    result.add(data)
//                }
//            }

        Log.d("jsonjsonjsonsjs", jsonResult!![0].name)

        var result: MutableMap<String, Float> = mutableMapOf()
        for (i in jsonResult!!.indices) {
            result.put(jsonResult!![i].name, jsonResult!![i].height / jsonResult!![i].width.toFloat())
        }

        return result
    }

    override fun getItemViewType(position: Int): Int {
        /*if (position == mData.size - mData.size % 15)
            return VIEW_FOOTER*/

        return VIEW_CONTENT
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

//        holder.tagLayoutWidth = 0
//        holder.tagLine = 0
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
        return mData.size
    }

    class ViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder(view) {
        val viewType = viewType
        val context: Context = view.context
        var tagLine = 0

        lateinit var thumbnailImage: ImageView
        lateinit var titleText: TextView
        lateinit var artistText: TextView
        lateinit var characterText: TextView
        lateinit var seriesText: TextView
        lateinit var typeText: TextView
        lateinit var tagLayout: LinearLayout

        lateinit var bmarkButton: ImageButton

        lateinit var progressBar: ProgressBar

        init {
            when (viewType) {
                VIEW_CONTENT -> {
                    thumbnailImage = itemView.findViewById(R.id.image_gallery_thumbnail) as ImageView
                    titleText = itemView.findViewById(R.id.text_gallery_title) as TextView
                    artistText = itemView.findViewById(R.id.text_gallery_artist) as TextView
                    characterText = itemView.findViewById(R.id.text_gallery_character) as TextView
                    seriesText = itemView.findViewById(R.id.text_gallery_series) as TextView
                    typeText = itemView.findViewById(R.id.text_gallery_type) as TextView
                    tagLayout = itemView.findViewById(R.id.layout_tags) as LinearLayout
                    bmarkButton = itemView.findViewById(R.id.imgbtn_gallery_bookmark) as ImageButton
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