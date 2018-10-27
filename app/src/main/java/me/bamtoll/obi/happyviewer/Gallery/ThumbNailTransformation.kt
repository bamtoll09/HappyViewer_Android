package me.bamtoll.obi.happyviewer.Gallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.TypedValue
import com.squareup.picasso.Transformation
import me.bamtoll.obi.happyviewer.MainActivity

class ThumbNailTransformation(context: Context): Transformation {

    val IMAGE_WIDTH = dpToPixel(120, context).toFloat()

    override fun key(): String {
        return "resizing image"
    }

    override fun transform(source: Bitmap?): Bitmap {
        var bitmap: Bitmap = source!!
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

    fun dpToPixel(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }
}