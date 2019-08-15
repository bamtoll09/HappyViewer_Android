package me.bamtoll.obi.happyviewer.Gallery

class EXT {
    companion object {
        val JPG = ".jpg"
        val PNG = ".png"
    }
}

class GalleryItem(var inherenceCode: String, var title: String, var infoItem: InfoItem, var isBMarked: Boolean = false, var ext: String = EXT.JPG) {

    class InfoItem(var artist: String = "",
                   var character: String = "",
                   var series: String = "",
                   var type: String = "",
                   var tag: Array<String>? = null
    )
}