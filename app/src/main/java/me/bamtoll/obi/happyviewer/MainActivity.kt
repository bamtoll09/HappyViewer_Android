package me.bamtoll.obi.happyviewer

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
//import kotlinx.android.synthetic.main.layout_piece.*
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import android.util.DisplayMetrics


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var galleryAdapter: GalleryAdapter
    lateinit var hiyobi: Elements

    lateinit var pieceAdapter: PieceAdapter
    lateinit var piece: String
    val PIECE_ENTRY = "https://hiyobi.me/data/"
    val INHERENCE_CODE = "1172674"

    companion object {
        var WIDTH: Int = 0
        fun CalcHeight(width: Int, height: Int): Int {
            return (height * (WIDTH / width.toFloat())).toInt()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        WIDTH = displayMetrics.widthPixels

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        galleryRecycler.layoutManager = LinearLayoutManager(this) // it could attach recyclerview to layout (linearlayout style)

        object: AsyncTask<Void, Void, Any>() {
            override fun onPostExecute(result: Any?) {
                super.onPostExecute(result)
                var urls: Array<String> = getAttributes(getFirstChildElement(hiyobi, "img"), "src")
                var titles: Array<String> = getFirstChildElement(hiyobi, "b").eachText().toTypedArray()

                var infoElemsList: List<Elements> = getChildElements(getFirstChildElement(hiyobi, "tbody"), "tr")
                var infos: Array<GalleryItem.InfoItem> = makeInfoItems(infoElemsList)

                galleryAdapter = GalleryAdapter(makeGalleryItems(urls, titles, infos))
                galleryRecycler.adapter = galleryAdapter
            }

            override fun doInBackground(vararg params: Void?): Any {
                hiyobi = Jsoup.connect("https://hiyobi.me/list").get().getElementsByClass("gallery-content")
                return Any()
            }
        }.execute()

        /*pieceRecycler.onFlingListener = object: RecyclerView.OnFlingListener() {
            override fun onFling(p0: Int, p1: Int): Boolean {
                Log.d("SPEED", p0.toString() + "." + p1.toString())
                if (Math.abs(p1) <= 3000)
                    PieceLayoutManager.EXTRA_SPACE_RANGE = 1.0f
                else
                    PieceLayoutManager.EXTRA_SPACE_RANGE = Math.abs(p1) / 3000.0f
                return false
            }
        }
        pieceRecycler.layoutManager = PieceLayoutManager(this)

        object: AsyncTask<Void, Void, Any>() {
            override fun onPostExecute(result: Any?) {
                super.onPostExecute(result)

                var jsonArray = JSONArray(piece)
                var pieceUrls: List<String> = parsePieceNames(PIECE_ENTRY + INHERENCE_CODE + "/", jsonArray)
                pieceAdapter = PieceAdapter(pieceUrls)
                pieceRecycler.adapter = pieceAdapter
            }

            override fun doInBackground(vararg params: Void?): Any {
                piece = Jsoup.connect(PIECE_ENTRY + "json/" + INHERENCE_CODE + "_list.json").ignoreContentType(true).execute().body()
                return Any()
            }
        }.execute()*/
    }

    fun parsePieceNames(basicUrl: String, jsonArray: JSONArray): List<String> {
        var urls: ArrayList<String> = ArrayList()

        for (i: Int in IntRange(0, jsonArray.length() - 1)) {
            urls.add(basicUrl + (jsonArray[i] as JSONObject).get("name"))
        }
        return urls
    }

    fun getFirstChildElement(elements: Elements, cssQuery: String): Elements {
        var elementList: ArrayList<Element> = ArrayList()

        for (childElem: Element in elements) {
            elementList.add(childElem.select(cssQuery).first())
        }
        return Elements(elementList)
    }

    /*fun getSecondChildElement(elements: Elements, cssQuery: String): Elements {
        var elementList: ArrayList<Element> = ArrayList()

        for (childElem: Element in elements) {
            elementList.add(childElem.select(cssQuery)[1])
        }
        return Elements(elementList)
    }*/

    fun getChildElements(elements: Elements, cssQuery: String): List<Elements> {
        var elementsList: ArrayList<Elements> = ArrayList()

        for (childElem: Element in elements) {
            elementsList.add(childElem.select(cssQuery))
        }
        return elementsList
    }

    fun getAttributes(elements: Elements, attr: String): Array<String> {
        var stringList: ArrayList<String> = ArrayList()

        for (elem: Element in elements) {
            stringList.add(elem.attr(attr))
        }
        return stringList.toTypedArray()
    }

    fun makeGalleryItems(urls: Array<String>, titles: Array<String>, infos: Array<GalleryItem.InfoItem>): Array<GalleryItem> {
        var galleryItemList: ArrayList<GalleryItem> = ArrayList()

        for (i in urls.indices) {
            galleryItemList.add(GalleryItem(urls[i], titles[i], infos[i]))
        }
        return galleryItemList.toTypedArray()
    }

    fun makeInfoItems(infoElemsList: List<Elements>): Array<GalleryItem.InfoItem> {
        var infoItemList: ArrayList<GalleryItem.InfoItem> = ArrayList()

        var artist = ""
        var character = ""
        var series = ""
        var type = ""
        var tag: List<String>? = null

        for (infoElems: Elements in infoElemsList) {
            for (infoElem: Element in infoElems) {
                var infoComp: Elements = infoElem.select("td")

                when (infoComp[0].text().slice(IntRange(0, 1))) {
                    "작가" -> artist = infoComp[1].text()
                    "캐릭" -> character = infoComp[1].text()
                    "원작" -> series = infoComp[1].text()
                    "장르" -> type = infoComp[1].text()
                    "태그" -> tag = infoComp[1].select("a").eachText()
                }
            }
            infoItemList.add(GalleryItem.InfoItem(artist, character, series, type, tag))
        }

        return infoItemList.toTypedArray()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
