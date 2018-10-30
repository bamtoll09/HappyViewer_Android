package me.bamtoll.obi.happyviewer.Piece

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

class PreviewImageView(context: Context, path: String): ImageView(context) {

    init {
        Picasso.get().load(path).into(this)
    }

}