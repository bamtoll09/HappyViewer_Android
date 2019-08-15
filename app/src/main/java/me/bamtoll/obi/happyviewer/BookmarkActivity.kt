package me.bamtoll.obi.happyviewer

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_gallery.*
import me.bamtoll.obi.happyviewer.Gallery.GalleryAdapter
import me.bamtoll.obi.happyviewer.Gallery.GalleryItem
import me.bamtoll.obi.happyviewer.Preference.Preference
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class BookmarkActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    lateinit var galleryAdapter: GalleryAdapter
    lateinit var hiyobi: Elements

    lateinit var pf: Preference

    companion object {
        var WIDTH: Int = 0
        var HEIGHT: Int = 0
        var PAGE = 0
        var bool = false
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutInflater.inflate(R.layout.layout_gallery, findViewById(R.id.layout_main),true)
        setSupportActionBar(toolbar)

        fab.visibility = View.GONE

        pf = Preference("bookmarks", applicationContext)

        /*val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)*/

        galleryRecycler.layoutManager = LinearLayoutManager(this) // it could attach recyclerview to layout (linearlayout style)
        galleryRecycler.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val inherenceCodes = pf.findAll()
        var items = Array(inherenceCodes.size, init = {
            GalleryItem(inherenceCodes[it], "Ane Naru Mono 7",
                    GalleryItem.InfoItem("Pochi.", "WolfGirl", "Ane Naru Mono", "Manga",
                            arrayOf("Blowjob", "Nakadashi", "Paizuri", "Ponytail", "Sole Female", "Shota", "Sole Male", "Multi-work Series")
                    ),
                    true
            )
        })
        galleryAdapter = GalleryAdapter(items.toList(),this@BookmarkActivity)
        galleryRecycler.adapter = galleryAdapter
        galleryAdapter.notifyDataSetChanged()

        // loadPage(true)
        // https://ltn.hitomi.la/galleries/1320979.js
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

    fun getGalleryCode(elements: Elements, attr: String): Array<String> {
        var stringList: ArrayList<String> = ArrayList()

        for (elem: Element in elements) {
            stringList.add(elem.attr(attr).slice(IntRange(24, 30)))
        }
        return stringList.toTypedArray()
    }

    fun makeGalleryItems(urls: Array<String>, titles: Array<String>, infos: Array<GalleryItem.InfoItem>): List<GalleryItem> {
        var galleryItemList: ArrayList<GalleryItem> = ArrayList()

        for (i in urls.indices) {
            galleryItemList.add(GalleryItem(urls[i], titles[i], infos[i]))
        }
        return galleryItemList
    }

    fun makeInfoItems(infoElemsList: List<Elements>): Array<GalleryItem.InfoItem> {
        var infoItemList: ArrayList<GalleryItem.InfoItem> = ArrayList()

        var artist = ""
        var character = ""
        var series = ""
        var type = ""
        var tag: Array<String>? = null

        for (infoElems: Elements in infoElemsList) {
            for (infoElem: Element in infoElems) {
                var infoComp: Elements = infoElem.select("td")

                when (infoComp[0].text().slice(IntRange(0, 1))) {
                    "작가" -> artist = infoComp[1].text()
                    "캐릭" -> character = infoComp[1].text()
                    "원작" -> series = infoComp[1].text()
                    "장르" -> type = infoComp[1].text()
                    "태그" -> tag = infoComp[1].select("a").eachText().toTypedArray()
                }
            }
            infoItemList.add(GalleryItem.InfoItem(artist, character, series, type, tag))
        }

        return infoItemList.toTypedArray()
    }

    fun loadPage(init: Boolean) {
        if (init)
            PAGE = 1
        else
            PAGE++

        object: AsyncTask<Void, Void, Any>() {
            override fun onPostExecute(result: Any?) {
                super.onPostExecute(result)
                var codes: Array<String> = getGalleryCode(getFirstChildElement(hiyobi, "img"), "src")
                var titles: Array<String> = getFirstChildElement(hiyobi, "b").eachText().toTypedArray()
                // size: 15

                var infoElemsList: List<Elements> = getChildElements(getFirstChildElement(hiyobi, "tbody"), "tr")
                var infos: Array<GalleryItem.InfoItem> = makeInfoItems(infoElemsList)

                if (init) {
                    galleryAdapter.removeAll()
                    galleryAdapter.addData(makeGalleryItems(codes, titles, infos))
                    refreshLayout.isRefreshing = false
                }
                else
                    galleryAdapter.addData(makeGalleryItems(codes, titles, infos))
            }

            override fun doInBackground(vararg params: Void?): Any {
                hiyobi = Jsoup.connect("https://hiyobi.me/list/".plus(PAGE))
                        .timeout(30000).ignoreHttpErrors(true)
                        .followRedirects(true)
                        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                        .header("accept-encoding", "gzip, deflate, br")
                        .header("accept-language", "ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3")
                        .header("user-agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:61.0) Gecko/20100101 Firefox/61.0")
                        .header("upgrade-insecure-requests", "1")
                        .get().getElementsByClass("gallery-content")
                return Any()
            }
        }.execute()
    }

    override fun onRefresh() {
        // loadPage(true)
        refreshLayout.isRefreshing = false
        // galleryAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_bookmarks -> { Toast.makeText(applicationContext, "Bookmarks", Toast.LENGTH_SHORT).show(); return true }
            R.id.action_downloads -> { Toast.makeText(applicationContext, "Downloads", Toast.LENGTH_SHORT).show(); return true }
            else -> return super.onOptionsItemSelected(item)
        }
    }*/

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
