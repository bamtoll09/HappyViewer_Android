package me.bamtoll.obi.happyviewer

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_reader.*
import me.bamtoll.obi.happyviewer.Reader.ReaderAdapter
import me.bamtoll.obi.happyviewer.Reader.ReaderLayoutManager
import org.json.JSONArray
import org.json.JSONObject

class ReaderActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var readerAdapter: ReaderAdapter
    lateinit var piece: String
    val PIECE_ENTRY = "https://hiyobi.me/data/"
    var showActionBar = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutInflater.inflate(R.layout.layout_reader, findViewById(R.id.layout_main),true)
        setSupportActionBar(toolbar)
        toolbar.alpha = 0f
        toolbar.background.alpha = 0
        toolbar.visibility = View.INVISIBLE

        pieceRecycler.onFlingListener = object: RecyclerView.OnFlingListener() {
            override fun onFling(p0: Int, p1: Int): Boolean {
                Log.d("SPEED", p0.toString() + "." + p1.toString())
                if (Math.abs(p1) <= 3000)
                    ReaderLayoutManager.EXTRA_SPACE_RANGE = 1.0f
                else
                    ReaderLayoutManager.EXTRA_SPACE_RANGE = Math.abs(p1) / 3000.0f
                return false
            }
        }
        pieceRecycler.layoutManager = ReaderLayoutManager(this)
        readerAdapter = ReaderAdapter(listOf(
                "file:///android_asset/" + "mono7/a1.jpg",
                "file:///android_asset/" + "mono7/a2.jpg",
                "file:///android_asset/" + "mono7/a3.jpg",
                "file:///android_asset/" + "mono7/a4.jpg",
                "file:///android_asset/" + "mono7/a5.jpg",
                "file:///android_asset/" + "mono7/a6.jpg",
                "file:///android_asset/" + "mono7/a7.jpg",
                "file:///android_asset/" + "mono7/a8.jpg",
                "file:///android_asset/" + "mono7/a9.jpg",
                "file:///android_asset/" + "mono7/a10.jpg",
                "file:///android_asset/" + "mono7/a11.jpg",
                "file:///android_asset/" + "mono7/a12.jpg",
                "file:///android_asset/" + "mono7/a13.jpg",
                "file:///android_asset/" + "mono7/a14.jpg",
                "file:///android_asset/" + "mono7/a15.jpg",
                "file:///android_asset/" + "mono7/a16.jpg",
                "file:///android_asset/" + "mono7/a17.jpg",
                "file:///android_asset/" + "mono7/a18.jpg",
                "file:///android_asset/" + "mono7/a19.jpg",
                "file:///android_asset/" + "mono7/a20.jpg",
                "file:///android_asset/" + "mono7/a21.jpg",
                "file:///android_asset/" + "mono7/a22.jpg",
                "file:///android_asset/" + "mono7/a23.jpg",
                "file:///android_asset/" + "mono7/a24.jpg",
                "file:///android_asset/" + "mono7/a25.jpg",
                "file:///android_asset/" + "mono7/a26.jpg",
                "file:///android_asset/" + "mono7/a27.jpg",
                "file:///android_asset/" + "mono7/a28.jpg",
                "file:///android_asset/" + "mono7/a29.jpg",
                "file:///android_asset/" + "mono7/a30.jpg",
                "file:///android_asset/" + "mono7/a31.jpg",
                "file:///android_asset/" + "mono7/a32.jpg",
                "file:///android_asset/" + "mono7/a33.jpg",
                "file:///android_asset/" + "mono7/a34.jpg",
                "file:///android_asset/" + "mono7/a35.jpg",
                "file:///android_asset/" + "mono7/a36.jpg",
                "file:///android_asset/" + "mono7/a37.jpg"
        ))
        pieceRecycler.adapter = readerAdapter

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

                    showActionBar = 1
                }
                else showActionBar = 0
            }
        }

        return super.onTouchEvent(event)
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