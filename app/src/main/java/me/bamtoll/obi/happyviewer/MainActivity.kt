package me.bamtoll.obi.happyviewer

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var galleryAdapter: GalleryAdapter
    lateinit var hiyobi: Elements

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        var galleryItem: Array<GalleryAdapter.GalleryItem>

        object: AsyncTask<Void, Void, Any>() {
            override fun onPostExecute(result: Any?) {
                super.onPostExecute(result)
                var urls: Array<String> = getAttributes(getElementChilds(hiyobi, "img"), "src")
                var titles: Array<String> = getElementChilds(hiyobi, "b").eachText().toTypedArray()
                galleryItem = Array(1) {GalleryAdapter.GalleryItem(urls[0], titles[0])}
                galleryAdapter = GalleryAdapter(galleryItem)
                galleryRecycler.adapter = galleryAdapter

                Log.d("Result", galleryItem[0].thumbnailUrl)
                Log.d("Result", galleryItem[0].title)
            }

            override fun doInBackground(vararg params: Void?): Any {
                hiyobi = Jsoup.connect("https://hiyobi.me/list").get().getElementsByClass("gallery-content")
                return Any()
            }
        }.execute()
    }

    fun getElementChilds(elements: Elements, cssQuery: String): Elements {
        var elementList: ArrayList<Element> = ArrayList()

        for (childElem: Element in elements) {
            elementList.add(childElem.select(cssQuery).first())
        }
        return Elements(elementList)
    }

    fun getAttributes(elements: Elements, attr: String): Array<String> {
        var stringList: ArrayList<String> = ArrayList()

        for (elem: Element in elements) {
            stringList.add(elem.attr(attr))
        }
        return stringList.toTypedArray()
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
