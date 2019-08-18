package me.bamtoll.obi.happyviewer

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_piece.*
import me.bamtoll.obi.happyviewer.Customization.TagButton
import me.bamtoll.obi.happyviewer.Gallery.EXT
import me.bamtoll.obi.happyviewer.Gallery.GalleryAdapter
import me.bamtoll.obi.happyviewer.Gallery.GalleryAdapter.Companion.layoutParams
import me.bamtoll.obi.happyviewer.Gallery.GalleryAdapter.Companion.tagLayoutWidth
import me.bamtoll.obi.happyviewer.MainActivity.Companion.pf
import me.bamtoll.obi.happyviewer.Piece.PreviewPagerAdapter

class PieceActivity: AppCompatActivity() {
    var thumbNail = ""
    var inherenceCode: String? = null
    var title: String? = null
    var artist: String? = null
    var character: String? = null
    var series: String? = null
    var type: String? = null
    var tag: Array<String>? = null
    var isBMarked: Boolean = false
    var ext: String? = null
    var name: Array<String>? = null
    // var scale: FloatArray? = null

    var prevLayout: String? = null

    var tagButtons: ArrayList<Button> = ArrayList()

    lateinit var tagLayout: LinearLayout
    var firstLayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    var tagLayoutWidth = 0
    var tagLine = 0

    companion object {
        var previewItemRect: Rect = Rect()
    }

    init {
        layoutParams.setMargins(0, 8, 0, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_piece)
//        layoutInflater.inflate(R.layout.layout_piece, findViewById(R.id.layout_main),true)
        prevLayout = intent.getStringExtra("previous_layout")

        inherenceCode = intent.getStringExtra("inherence_code")
        title = intent.getStringExtra("title")
        artist = intent.getStringExtra("artist")
        character = intent.getStringExtra("character")
        series = intent.getStringExtra("series")
        type = intent.getStringExtra("type")
        tag = intent.getStringArrayExtra("tag")
        isBMarked = intent.getBooleanExtra("bookmark", false)
        ext = intent.getStringExtra("extension")
        name = intent.getStringArrayExtra("name")
        // scale = intent.getFloatArrayExtra("scale")

        if (inherenceCode == null) inherenceCode = "ta"
        if (title == null) title = "평범한 8반 49화"
        if (artist == null) artist = "by. 영파카"
        if (character == null) character = "Character: 나유나"
        if (series == null) series = "Series: 평범한 8반"
        if (type == null) type = "Types: Mango"
        if (tag == null) tag = arrayOf("평범한 8반", "평", "범", "한", "8", "반", "Blowjob", "Nakadashi", "Paizuri", "Ponytail", "Sole Female", "Shota", "Sole Male", "Multi-work Series")
        if (ext == null) ext = EXT.JPG
        if (name == null) name = arrayOf(
                "file:///android_asset/" + "ta/a1.jpg",
                "file:///android_asset/" + "ta/a2.jpg",
                "file:///android_asset/" + "ta/a3.jpg",
                "file:///android_asset/" + "ta/a4.jpg",
                "file:///android_asset/" + "ta/a5.jpg",
                "file:///android_asset/" + "ta/a6.jpg",
                "file:///android_asset/" + "ta/a7.jpg",
                "file:///android_asset/" + "ta/a8.jpg",
                "file:///android_asset/" + "ta/a9.jpg",
                "file:///android_asset/" + "ta/a10.jpg",
                "file:///android_asset/" + "ta/a11.jpg",
                "file:///android_asset/" + "ta/a12.jpg",
                "file:///android_asset/" + "ta/a13.jpg",
                "file:///android_asset/" + "ta/a14.jpg",
                "file:///android_asset/" + "ta/a15.jpg",
                "file:///android_asset/" + "ta/a16.jpg",
                "file:///android_asset/" + "ta/a17.jpg",
                "file:///android_asset/" + "ta/a18.jpg",
                "file:///android_asset/" + "ta/a19.jpg",
                "file:///android_asset/" + "ta/a20.jpg",
                "file:///android_asset/" + "ta/a21.jpg",
                "file:///android_asset/" + "ta/a22.jpg",
                "file:///android_asset/" + "ta/a23.jpg",
                "file:///android_asset/" + "ta/a24.jpg"
        ) else {
            for (i in name!!.indices) {
                name!![i] = "file:///android_asset/".plus(inherenceCode).plus("/").plus(name!![i])
                Log.d("NAmESNAmES", name!![i])
            }
        }
        /*if (scale == null) scale = floatArrayOf(
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f,
                1.5f
        )*/

        thumbNail = inherenceCode.plus("/a00").plus(ext) /*"/tn/".plus(inherenceCode).plus(ext)*/

        tagLayout = findViewById(R.id.layout_tags) as LinearLayout

        Picasso.get().load(GalleryAdapter.THUMBNAIL_URL.plus(thumbNail)).into(image_piece_thumbnail)
        text_piece_title.text = title
        text_piece_artist.text = "by. ".plus(artist)
        text_piece_character.text = "캐릭: ".plus(character)
        text_piece_series.text = "원작: ".plus(series)
        when (type) {
            "Artist CG" -> text_piece_type.text = "CG"
            "Doujinshi" -> text_piece_type.text = "D"
            "Manga" -> text_piece_type.text = "M"
        }

        val viewTreeObserver = tagLayout.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                tagLayoutWidth = tagLayout.width
                tagLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
        viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                tagLayoutWidth = tagLayout.width

                Log.d("width_piece", tagLayout.width.toString())
                    if (tag != null) {
                        var tagsWidth = 0

                        for (i in tag!!.indices) {
                            if (tagLine == 0) { // next layout will have 8px top margin
                                tagLayout.addView(LinearLayout(this@PieceActivity), firstLayoutParams)
                                tagLine++
                            }
                            tagsWidth += dpToPixel(tag!![i].length * 7 + 15, this@PieceActivity)
                            Log.d("tagswidth_piece", tagsWidth.toString())
                            if (tagsWidth >= tagLayoutWidth) { // add tag line
                                tagLayout.addView(LinearLayout(this@PieceActivity), layoutParams)
                                tagsWidth = dpToPixel(tag!![i].length * 7 + 15, this@PieceActivity)
                                tagLine++
                            }
                            tagButtons.add(TagButton(this@PieceActivity, tag!![i]))
                            tagButtons[i].setOnClickListener { v ->
                                Snackbar.make(v, tag!![i], Snackbar.LENGTH_SHORT).setAction("action", null).show()
                                Log.d("TATA", "Clicked")
                            }
                            (tagLayout.getChildAt(tagLine - 1) as LinearLayout).addView(tagButtons[i])
                        }
                    }
                tagLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        if (isBMarked) {
            imgbtn_piece_bookmark.setImageResource(R.drawable.ic_star_black_24dp)
        } else {
            imgbtn_piece_bookmark.setImageResource(R.drawable.ic_star_border_black_24dp)
        }

        imgbtn_piece_bookmark.setOnClickListener{ v ->
            if (isBMarked) {
                isBMarked = false
                
                pf.remove(inherenceCode!!)
                
                imgbtn_piece_bookmark.setImageResource(R.drawable.ic_star_border_black_24dp)

                val pos = MainActivity.galleryAdapter.changeBMarkState(inherenceCode!!, false)
                MainActivity.galleryAdapter.notifyItemChanged(pos)

                Toast.makeText(v.context.applicationContext, "Mark Removed", Toast.LENGTH_SHORT).show()
            }
            else {
                isBMarked = true

                MainActivity.pf.save(inherenceCode!!, inherenceCode!!)

                imgbtn_piece_bookmark.setImageResource(R.drawable.ic_star_black_24dp)

                val pos = MainActivity.galleryAdapter.changeBMarkState(inherenceCode!!, true)
                MainActivity.galleryAdapter.notifyItemChanged(pos)

                Toast.makeText(v.context.applicationContext, "Marked\n".plus(MainActivity.pf.find(inherenceCode!!)), Toast.LENGTH_SHORT).show()
            }
        }


        btn_piece_download.setOnClickListener { v ->
            v.visibility = View.INVISIBLE
        }
        seek_piece_downloading.progress = 20
        seek_piece_downloading.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        pager_piece_preview.adapter = PreviewPagerAdapter(name!!, this)
        btn_piece_read.setOnClickListener { v ->
            val intent = Intent(applicationContext, ViewerActivity::class.java)
            startActivity(intent)
            finishAndRemoveTask()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        when (prevLayout) {
            resources.getString(R.string.app_name) -> MainActivity.loadingLayout.visibility = View.GONE
        }
    }

    fun dpToPixel(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }
}