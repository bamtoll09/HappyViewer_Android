package me.bamtoll.obi.happyviewer

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
        pager_piece_preview.adapter = PreviewPagerAdapter(3)
    }
}