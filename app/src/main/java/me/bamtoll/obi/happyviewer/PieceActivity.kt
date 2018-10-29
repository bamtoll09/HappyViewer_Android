package me.bamtoll.obi.happyviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_piece.*

class PieceActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_piece)
//        layoutInflater.inflate(R.layout.layout_piece, findViewById(R.id.layout_main),true)

        Picasso.get().load("file:///android_asset/" + "mono7/a1.jpeg").into(image_piece_thumbnail)
        btn_piece_download.setOnClickListener { v ->
            v.visibility = View.INVISIBLE
        }
    }
}