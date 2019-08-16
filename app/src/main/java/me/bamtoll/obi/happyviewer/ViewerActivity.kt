package me.bamtoll.obi.happyviewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_reader.*
import me.bamtoll.obi.happyviewer.Viewer.ViewerAdapter
import me.bamtoll.obi.happyviewer.Viewer.ViewerLayoutManager
import org.json.JSONArray
import org.json.JSONObject

class ViewerActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var viewerAdapter: ViewerAdapter
    lateinit var piece: String
    val PIECE_ENTRY = "https://hiyobi.me/data/"
    var showActionBar = 1

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutInflater.inflate(R.layout.layout_reader, findViewById(R.id.layout_main),true)
        setSupportActionBar(toolbar)
//        toolbar.alpha = 0f
//        toolbar.background.alpha = 0
//        toolbar.visibility = View.INVISIBLE

        fab.visibility = View.GONE

        pieceRecycler.onFlingListener = object: RecyclerView.OnFlingListener() {
            override fun onFling(p0: Int, p1: Int): Boolean {
                Log.d("SPEED", p0.toString() + "." + p1.toString())
//                if (Math.abs(p1) <= 3000)
//                    ViewerLayoutManager.EXTRA_SPACE_RANGE = 1.0f
//                else
//                    ViewerLayoutManager.EXTRA_SPACE_RANGE = Math.abs(p1) / 3000.0f
                ViewerLayoutManager.EXTRA_SPACE_RANGE = Math.abs(p1).toFloat()
                Log.d("flingfling", "p0: ".plus(p0).plus(", p1: ").plus(p1))
                return false
            }
        }
        pieceRecycler.layoutManager = ViewerLayoutManager(this)
        viewerAdapter = ViewerAdapter(listOf(
                "file:///android_asset/" + "ta/a1.jpg",
                "file:///android_asset/" + "ta/a2.jpg",
                "file:///android_asset/" + "ta/a3.jpg",
                "file:///android_asset/" + "ta/a4.jpg",
                "file:///android_asset/" + "ta/a5.jpg",
                "file:///android_asset/" + "ta/a6.jpg",
                "file:///android_asset/" + "ta/a7.jpg",
                "file:///android_asset/" + "ta/a8.jpg",
                "file:///android_asset/" + "ta/a9.jpg",
                "file:///android_asset/" + "ta/a10.jpg",
                "file:///android_asset/" + "ta/a11.jpg",
                "file:///android_asset/" + "ta/a12.jpg",
                "file:///android_asset/" + "ta/a13.jpg",
                "file:///android_asset/" + "ta/a14.jpg",
                "file:///android_asset/" + "ta/a15.jpg",
                "file:///android_asset/" + "ta/a16.jpg",
                "file:///android_asset/" + "ta/a17.jpg",
                "file:///android_asset/" + "ta/a18.jpg",
                "file:///android_asset/" + "ta/a19.jpg",
                "file:///android_asset/" + "ta/a20.jpg",
                "file:///android_asset/" + "ta/a21.jpg",
                "file:///android_asset/" + "ta/a22.jpg",
                "file:///android_asset/" + "ta/a23.jpg",
                "file:///android_asset/" + "ta/a24.jpg"/*,
                "file:///android_asset/" + "ta/a25.jpg",
                "file:///android_asset/" + "ta/a26.jpg",
                "file:///android_asset/" + "ta/a27.jpg",
                "file:///android_asset/" + "ta/a28.jpg",
                "file:///android_asset/" + "ta/a29.jpg",
                "file:///android_asset/" + "ta/a30.jpg",
                "file:///android_asset/" + "ta/a31.jpg",
                "file:///android_asset/" + "ta/a32.jpg",
                "file:///android_asset/" + "ta/a33.jpg",
                "file:///android_asset/" + "ta/a34.jpg",
                "file:///android_asset/" + "ta/a35.jpg",
                "file:///android_asset/" + "ta/a36.jpg",
                "file:///android_asset/" + "ta/a37.jpg"*/
        ))
        pieceRecycler.adapter = viewerAdapter

        // val INHERENCE_CODE = intent.extras.getString("inherence_code")

        /*object: AsyncTask<Void, Void, Any>() {
            override fun onPostExecute(result: Any?) {
                super.onPostExecute(result)

                var jsonArray = JSONArray(piece)
                var pieceUrls: List<String> = parsePieceNames(PIECE_ENTRY + INHERENCE_CODE + "/", jsonArray)
                readerAdapter = ReaderAdapter(pieceUrls)
                pieceRecycler.adapter = readerAdapter
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

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if (showActionBar == 0) {
                    Toast.makeText(this, "Show ActionBar", Toast.LENGTH_SHORT)
                    showActionBar = 1
                } else {
                    Toast.makeText(this, "Hide ActionBar", Toast.LENGTH_SHORT)
                    showActionBar = 0
                }
            }
        }

        return super.onTouchEvent(event)
    }

    override fun finish() {
        super.finish()

//        toolbar.alpha = 1f
//        toolbar.background.alpha = 255
//        toolbar.visibility = View.VISIBLE
    }

    fun disappear() {
        toolbar.apply {
            animate()
        }

        toolbar.background.alpha = 0
        toolbar.visibility = View.INVISIBLE

        showActionBar = 0
    }
}