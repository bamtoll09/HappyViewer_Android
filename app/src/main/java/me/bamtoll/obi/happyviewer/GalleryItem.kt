package me.bamtoll.obi.happyviewer

class GalleryItem(var thumbnailUrl: String, var title: String) {
    class InfoItem(var artist: String = "", var character: String = "", var series: String = "", var type: String = "", var tag: String = "")
}