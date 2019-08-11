package me.bamtoll.obi.happyviewer.Gallery

class GalleryItem(var inherenceCode: String, var title: String, var infoItem: InfoItem) {

    class InfoItem(var artist: String = "",
                   var character: String = "",
                   var series: String = "",
                   var type: String = "",
                   var tag: Array<String>? = null
    )
}