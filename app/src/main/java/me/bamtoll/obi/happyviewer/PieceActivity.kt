package me.bamtoll.obi.happyviewer

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.layout_piece.*
import me.bamtoll.obi.happyviewer.Reader.ReaderAdapter
import me.bamtoll.obi.happyviewer.Reader.ReaderLayoutManager
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup

class PieceActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var readerAdapter: ReaderAdapter
    lateinit var piece: String
    val PIECE_ENTRY = "https://hiyobi.me/data/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_piece)

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

        val INHERENCE_CODE = intent.extras.getString("inherence_code")
        object: AsyncTask<Void, Void, Any>() {
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
        }.execute()
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
}