package me.bamtoll.obi.happyviewer.Viewer

import android.graphics.Bitmap
import com.squareup.picasso.Transformation
import android.graphics.Matrix
import me.bamtoll.obi.happyviewer.MainActivity

class ResizeTransformation: Transformation {

    companion object {
        const val IMAGE_WIDTH = 600.0f
    }

    override fun key(): String {
        return "resizing image"
    }

    override fun transform(source: Bitmap?): Bitmap {
        var bitmap:Bitmap = source!!
        var scale: Float = IMAGE_WIDTH / bitmap.width
        var matrix = Matrix()
        matrix.setScale(scale, scale)
        var newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        scale = MainActivity.WIDTH / newBitmap.width.toFloat()
        matrix.setScale(scale, scale)
        newBitmap = Bitmap.createBitmap(newBitmap, 0, 0, newBitmap.width, newBitmap.height, matrix, true)
        bitmap.recycle()
        return newBitmap
    }
}