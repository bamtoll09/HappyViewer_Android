package me.bamtoll.obi.happyviewer

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_piece.*
import me.bamtoll.obi.happyviewer.Piece.PreviewPagerAdapter

class PieceActivity: AppCompatActivity() {
    var thumbNail: String? = null
    var title: String? = null
    var artist: String? = null
    var character: String? = null
    var series: String? = null
    var types: String? = null
    var tags: Array<String>? = null

    companion object {
        var previewItemRect: Rect = Rect()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_piece)
//        layoutInflater.inflate(R.layout.layout_piece, findViewById(R.id.layout_main),true)

        thumbNail = intent.getStringExtra("thumbnail")
        title = intent.getStringExtra("title")
        artist = intent.getStringExtra("artist")
        character = intent.getStringExtra("character")
        series = intent.getStringExtra("series")
        types = intent.getStringExtra("types")
        tags = intent.getStringArrayExtra("tags")

        if (thumbNail == null) thumbNail = "mono7/a0.jpg"
        if (title == null) title = "평범한 8반 49화"
        if (artist == null) artist = "by. 영파카"
        if (character == null) character = "Character: 나유나"
        if (series == null) series = "Series: 평범한 8반"
        if (types == null) types = "Types: Mango"
        if (tags == null) tags = arrayOf("평범한 8반", "평", "범", "한", "8", "반")

        Picasso.get().load("file:///android_asset/" + thumbNail).into(image_piece_thumbnail)
        text_piece_title.text = title
        text_piece_artist.text = artist
        text_piece_character.text = character
        text_piece_series.text = series
        text_piece_types.text = types

        var tag = ""
        for (i in tags!!.indices) {
            tag += tags!![i]
            if (i + 1 < tags!!.size)
                tag += ", "
        }
        text_piece_tags.text = tag


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
                "file:///android_asset/" + "mono7/a1.jpg",
                "file:///android_asset/" + "mono7/a2.jpg",
                "file:///android_asset/" + "mono7/a3.jpg",
                "file:///android_asset/" + "mono7/a4.jpg",
                "file:///android_asset/" + "mono7/a5.jpg",
                "file:///android_asset/" + "mono7/a6.jpg",
                "file:///android_asset/" + "mono7/a7.jpg",
                "file:///android_asset/" + "mono7/a8.jpg",
                "file:///android_asset/" + "mono7/a9.jpg",
                "file:///android_asset/" + "mono7/a10.jpg",
                "file:///android_asset/" + "mono7/a11.jpg",
                "file:///android_asset/" + "mono7/a12.jpg",
                "file:///android_asset/" + "mono7/a13.jpg",
                "file:///android_asset/" + "mono7/a14.jpg",
                "file:///android_asset/" + "mono7/a15.jpg",
                "file:///android_asset/" + "mono7/a16.jpg",
                "file:///android_asset/" + "mono7/a17.jpg",
                "file:///android_asset/" + "mono7/a18.jpg",
                "file:///android_asset/" + "mono7/a19.jpg",
                "file:///android_asset/" + "mono7/a20.jpg",
                "file:///android_asset/" + "mono7/a21.jpg",
                "file:///android_asset/" + "mono7/a22.jpg",
                "file:///android_asset/" + "mono7/a23.jpg",
                "file:///android_asset/" + "mono7/a24.jpg",
                "file:///android_asset/" + "mono7/a25.jpg",
                "file:///android_asset/" + "mono7/a26.jpg",
                "file:///android_asset/" + "mono7/a27.jpg",
                "file:///android_asset/" + "mono7/a28.jpg",
                "file:///android_asset/" + "mono7/a29.jpg",
                "file:///android_asset/" + "mono7/a30.jpg",
                "file:///android_asset/" + "mono7/a31.jpg",
                "file:///android_asset/" + "mono7/a32.jpg",
                "file:///android_asset/" + "mono7/a33.jpg",
                "file:///android_asset/" + "mono7/a34.jpg",
                "file:///android_asset/" + "mono7/a35.jpg",
                "file:///android_asset/" + "mono7/a36.jpg",
                "file:///android_asset/" + "mono7/a37.jpg"
        )
        pager_piece_preview.adapter = PreviewPagerAdapter(smalltnUrl)
        btn_piece_read.setOnClickListener { v ->
            val intent = Intent(applicationContext, ReaderActivity::class.java)
            startActivity(intent)
        }
    }
}