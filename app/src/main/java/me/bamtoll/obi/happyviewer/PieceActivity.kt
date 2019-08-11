package me.bamtoll.obi.happyviewer

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_piece.*
import me.bamtoll.obi.happyviewer.Piece.PreviewPagerAdapter

class PieceActivity: AppCompatActivity() {
    var thumbNail: String? = null
    var title: String? = null
    var artist: String? = null
    var character: String? = null
    var series: String? = null
    var type: String? = null
    var tag: Array<String>? = null
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

        title = intent.getStringExtra("title")
        artist = intent.getStringExtra("artist")
        character = intent.getStringExtra("character")
        series = intent.getStringExtra("series")
        type = intent.getStringExtra("type")
        tag = intent.getStringArrayExtra("tag")

        thumbNail = "ta/a0.jpg"
        if (title == null) title = "평범한 8반 49화"
        if (artist == null) artist = "by. 영파카"
        if (character == null) character = "Character: 나유나"
        if (series == null) series = "Series: 평범한 8반"
        if (type == null) type = "Types: Mango"
        if (tag == null) tag = arrayOf("평범한 8반", "평", "범", "한", "8", "반", "Blowjob", "Nakadashi", "Paizuri", "Ponytail", "Sole Female", "Shota", "Sole Male", "Multi-work Series")

        tagLayout = findViewById(R.id.layout_tags) as LinearLayout

        Picasso.get().load("file:///android_asset/" + thumbNail).into(image_piece_thumbnail)
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

        btn_piece_download.setOnClickListener { v ->
            v.visibility = View.INVISIBLE
        }
        seek_piece_downloading.progress = 20
        seek_piece_downloading.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })
        var smalltnUrl = arrayOf(
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
                "file:///android_asset/" + "ta/a24.jpg"/*,
                "file:///android_asset/" + "ta/a25.jpg",
                "file:///android_asset/" + "ta/a26.jpg",
                "file:///android_asset/" + "ta/a27.jpg",
                "file:///android_asset/" + "ta/a28.jpg",
                "file:///android_asset/" + "ta/a29.jpg",
                "file:///android_asset/" + "ta/a30.jpg",
                "file:///android_asset/" + "ta/a31.jpg",
                "file:///android_asset/" + "ta/a32.jpg",
                "file:///android_asset/" + "ta/a33.jpg",
                "file:///android_asset/" + "ta/a34.jpg",
                "file:///android_asset/" + "ta/a35.jpg",
                "file:///android_asset/" + "ta/a36.jpg",
                "file:///android_asset/" + "ta/a37.jpg"*/
        )
        pager_piece_preview.adapter = PreviewPagerAdapter(smalltnUrl)
        btn_piece_read.setOnClickListener { v ->
            val intent = Intent(applicationContext, ReaderActivity::class.java)
            startActivity(intent)
        }
    }

    fun dpToPixel(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}