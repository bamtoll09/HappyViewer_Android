package me.bamtoll.obi.happyviewer.Piece

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import me.bamtoll.obi.happyviewer.PieceActivity

class PreviewImageView(context: Context, path: String): ImageView(context) {

    init {
        Picasso.get().load(path).transform(PreviewImageResizeTransform()).into(this)
        layoutParams = ViewGroup.LayoutParams(PieceActivity.previewItemRect.width(), PieceActivity.previewItemRect.height())
    }

}

class PreviewImageResizeTransform: Transformation {

    override fun key(): String {
        return "Resizing Preview Image"
    }

    override fun transform(source: Bitmap?): Bitmap {
        if (source != null) {
            var height = 1
            if (source.width > source.height) height = 0

            var scale = 0f
            if (height == 0) scale = PieceActivity.previewItemRect.width() / source.width.toFloat()
            else scale = PieceActivity.previewItemRect.height() / source.height.toFloat()

            var matrix = Matrix()
            matrix.setScale(scale, scale)
            var newBitmap = Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
            source.recycle()
            return newBitmap
        }
        return source!!
    }
}