package me.bamtoll.obi.happyviewer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_gallery.*
import kotlinx.android.synthetic.main.layout_piece.*
import kotlinx.android.synthetic.main.layout_preview.*
import me.bamtoll.obi.happyviewer.Piece.PreviewPagerAdapter

class PieceActivity: AppCompatActivity() {
    var thumbNail: String? = null
    var title: String? = null
    var artist: String? = null
    var character: String? = null
    var series: String? = null
    var types: String? = null
    var tags: Array<String>? = null

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

        if (thumbNail == null) thumbNail = "mono7/a1.jpeg"
        if (title == null) title = "Ane Naru Mono 7"
        if (artist == null) artist = "by. Pochi"
        if (character == null) character = "Character: "
        if (series == null) series = "Series: Ane Naru Mono"
        if (types == null) types = "Types: Doujinshi"
        if (tags == null) tags = arrayOf("Blowjob", "Nakadashi", "Paizuri", "Ponytail", "Sole Female", "Shota", "Sole Male", "Multi-work Series")

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
                "file:///android_asset/" + "mono7/small/b1.jpg",
                "file:///android_asset/" + "mono7/small/b2.jpg",
                "file:///android_asset/" + "mono7/small/b3.jpg",
                "file:///android_asset/" + "mono7/small/b4.jpg",
                "file:///android_asset/" + "mono7/small/b5.jpg",
                "file:///android_asset/" + "mono7/small/b6.jpg",
                "file:///android_asset/" + "mono7/small/b7.jpg",
                "file:///android_asset/" + "mono7/small/b8.jpg",
                "file:///android_asset/" + "mono7/small/b9.jpg",
                "file:///android_asset/" + "mono7/small/b10.jpg",
                "file:///android_asset/" + "mono7/small/b11.jpg",
                "file:///android_asset/" + "mono7/small/b12.jpg",
                "file:///android_asset/" + "mono7/small/b13.jpg",
                "file:///android_asset/" + "mono7/small/b14.jpg",
                "file:///android_asset/" + "mono7/small/b15.jpg",
                "file:///android_asset/" + "mono7/small/b16.jpg",
                "file:///android_asset/" + "mono7/small/b17.jpg",
                "file:///android_asset/" + "mono7/small/b18.jpg",
                "file:///android_asset/" + "mono7/small/b19.jpg",
                "file:///android_asset/" + "mono7/small/b20.jpg",
                "file:///android_asset/" + "mono7/small/b21.jpg",
                "file:///android_asset/" + "mono7/small/b22.jpg",
                "file:///android_asset/" + "mono7/small/b23.jpg"
        )
        pager_piece_preview.adapter = PreviewPagerAdapter(smalltnUrl)
        btn_piece_read.setOnClickListener { v ->
            val intent = Intent(applicationContext, ReaderActivity::class.java)
            startActivity(intent)
        }
    }
}